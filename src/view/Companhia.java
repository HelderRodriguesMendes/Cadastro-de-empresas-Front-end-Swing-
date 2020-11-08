/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cadastroempresas.retrofit.repository_retrofit.CompanhiaRepository;
import cadastroempresas.retrofit.repository_retrofit.CountryRepository;
import cadastroempresas.retrofit.service_retrofit.Retrofit_URL;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.City;
import model.Company;
import model.Country;
import model.Neighborhood;
import model.State;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author helde
 */
public class Companhia extends javax.swing.JFrame {

    private static String STATUS_FORM, NOME_COMERCIAL, NOME_EMPRESA, ENDERECO, FONE, NUMERO;
    
    Retrofit_URL retrofit = new Retrofit_URL();

    Neighborhood neighborhood = new Neighborhood();
    City city = new City();
    State state = new State();
    Country country = new Country();
    Company company = new Company();

    public Companhia() {
        initComponents();

        addWindowListener(new WindowAdapter() { // para confirna se deseja ralmente fechar a janela
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Sim", "Não"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Deseja realmente fechar essa janela de cadastro?", "ATENÇÃO",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    Home h = new Home();
                    h.habilitarForm();
                    Companhia.this.dispose();
                }
            }
        });
        
        soNumeros(txtNumeroFiscal);
    }

    public void bloquiarCampus() {
        txtEstado_.setEnabled(false);
        txtCidade_.setEnabled(false);
        txtVizinhanca_.setEnabled(false);
    }

    public void desbloquiarCampus() {
        txtEstado_.setEnabled(true);
        txtCidade_.setEnabled(true);
        txtVizinhanca_.setEnabled(true);
    }

    public void preencherCampusLocalizacao(Neighborhood neighborhood) {
        city = neighborhood.getCity();
        state = city.getState();
        country = state.getCountry();
        
        txtNomeComercial.setText(NOME_COMERCIAL);
        txtNomeEmpresa.setText(NOME_EMPRESA);
        txtEndereco.setText(ENDERECO);
        txtTelefone.setText(FONE);
        txtNumeroFiscal.setText(NUMERO);

        txtPais_.setText(country.getName());
        if(country.getId() != null){
            lbl_idPais.setText(String.valueOf(country.getId()));
        }        
        lbl_idPais.setVisible(false);
        

        txtEstado_.setText(state.getName());
        if(state.getId() != null){
            lbl_idEstado.setText(String.valueOf(state.getId()));
        }        
        lbl_idEstado.setVisible(false);
        

        txtCidade_.setText(city.getName());
        if(city.getId() != null){
            lbl_idCidade.setText(String.valueOf(city.getId()));
        }        
        lbl_idCidade.setVisible(false);
        

        txtVizinhanca_.setText(neighborhood.getName());
        if(neighborhood.getId() != null){
            lbl_idVizinhanca.setText(String.valueOf(neighborhood.getId()));
        }       
        lbl_idVizinhanca.setVisible(false);
    }
 
    //tambem pode ser utilizado para enviar dados para o formulario de localizacao
    public Company preencherObjeto() {
        company.setTradeName(txtNomeComercial.getText());
        company.setCorporateName(txtNomeEmpresa.getText());
        NOME_COMERCIAL = txtNomeComercial.getText();
        NOME_EMPRESA = txtNomeEmpresa.getText();
                
        if (!lbl_idPais.getText().equals("")) {
            country.setId(Long.valueOf(lbl_idPais.getText()));
        }
        country.setName(txtPais_.getText());
        company.setCountry(country);

        state.setCountry(country);
        if (!lbl_idEstado.getText().equals("")) {
            state.setId(Long.valueOf(lbl_idEstado.getText()));
        }
        state.setName(txtEstado_.getText());
        company.setState(state);
        

        city.setState(state);
        if (!lbl_idCidade.getText().equals("")) {
            city.setId(Long.valueOf(lbl_idCidade.getText()));
        }
        city.setName(txtCidade_.getText());
        company.setCity(city);

        neighborhood.setCity(city);
        if (!lbl_idVizinhanca.getText().equals("")) {
            neighborhood.setId(Long.valueOf(lbl_idVizinhanca.getText()));
        }
        neighborhood.setName(txtVizinhanca_.getText());
        company.setNeighborhood(neighborhood);
        
        company.setAddress(txtEndereco.getText());
        ENDERECO = txtEndereco.getText();
        company.setPhone(txtTelefone.getText());
        System.out.println("telefone: " + txtTelefone.getText());
        FONE = txtTelefone.getText();
        company.setFederalTaxNumber(txtNumeroFiscal.getText());
        NUMERO = txtNumeroFiscal.getText();
        company.setAtivo(true);

        return company;
    }
    
    private boolean validarCampus(){
        boolean ok = false;
        
        if(txtNomeComercial.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o Nome Comercial da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeComercial.requestFocus();
        }else if(txtNomeEmpresa.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o Nome da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeEmpresa.requestFocus();
        }else if(txtPais_.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o País da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtPais_.requestFocus();
        }else if(txtEndereco.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o Endereço da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtEndereco.requestFocus();
        }else if(txtTelefone.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o Telefone da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtTelefone.requestFocus();
        }else if(txtNumeroFiscal.getText().equals("")){
            JOptionPane.showMessageDialog(Companhia.this, "Informe o Número Fiscal Federal da Companhia", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNumeroFiscal.requestFocus();
        }else if(verificarLength()){
            ok = true;
        }
        return ok;
    }
    
    private boolean verificarLength(){
        boolean ok = false;

        if(txtNomeComercial.getText().length() < 2 || txtNomeComercial.getText().length() > 30){
            JOptionPane.showMessageDialog(Companhia.this, "O Nome Comercial da Companhia deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeComercial.requestFocus();
        }else if(txtNomeEmpresa.getText().length() < 2 || txtNomeEmpresa.getText().length() > 30){
            JOptionPane.showMessageDialog(Companhia.this, "O Nome da Companhia deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeEmpresa.requestFocus();
        }else if(txtEndereco.getText().length() < 2 || txtEndereco.getText().length() > 30){
            JOptionPane.showMessageDialog(Companhia.this, "O Endereço da Companhia deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtEndereco.requestFocus();
        }else if(txtTelefone.getText().length() < 2 || txtTelefone.getText().length() > 15){
            JOptionPane.showMessageDialog(Companhia.this, "O Endereço da Companhia deve conter entre 2 a 15 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtTelefone.requestFocus();
        }else{
            ok = true;
        }
        return ok;
    }
    
    private static void soNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {   // evento de teclas pressionadas no teclado
                char c = e.getKeyChar();         // captura a letra ou o numero digitado
                if (!Character.isDigit(c)) {        // verifica si o que foi digitado é letras
                    e.consume();                 // não permite digitar letras
                }
            }
        });
    }

    public void statusForm(String status) {
        STATUS_FORM = status;

        if (status.equals("cadastrar")) {
            bloquiarCampus();
        }
    }

    public void habilitarForm() {
        this.setVisible(true);
        if (STATUS_FORM.equals("cadastrar")) {
            bloquiarCampus();
            btn.setText("Cadastrar");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNomeComercial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNomeEmpresa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPais_ = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEstado_ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCidade_ = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtVizinhanca_ = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtNumeroFiscal = new javax.swing.JTextField();
        btn = new javax.swing.JButton();
        lbl_idPais = new javax.swing.JLabel();
        lbl_idCidade = new javax.swing.JLabel();
        lbl_idEstado = new javax.swing.JLabel();
        lbl_idVizinhanca = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setText("Nome Comercial:");

        jLabel2.setText("Nome da companhia:");

        jLabel3.setText("País:");

        txtPais_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPais_MouseClicked(evt);
            }
        });
        txtPais_.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPais_KeyPressed(evt);
            }
        });

        jLabel4.setText("Estado:");

        jLabel5.setText("Cidade:");

        txtCidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidade_ActionPerformed(evt);
            }
        });

        jLabel6.setText("Vizinhança:");

        jLabel8.setText("Endereço:");

        jLabel9.setText("Número Fiscal Federal:");

        jLabel10.setText("Telefone:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btn.setText("jButton1");
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(255, 0, 0));
        jLabel19.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("*");
        jLabel19.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel20.setBackground(new java.awt.Color(255, 0, 0));
        jLabel20.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("*");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel21.setBackground(new java.awt.Color(255, 0, 0));
        jLabel21.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 0, 51));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("*");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel22.setBackground(new java.awt.Color(255, 0, 0));
        jLabel22.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 51));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("*");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel23.setBackground(new java.awt.Color(255, 0, 0));
        jLabel23.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("*");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel24.setBackground(new java.awt.Color(255, 0, 0));
        jLabel24.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 0, 51));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("*");
        jLabel24.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel25.setBackground(new java.awt.Color(255, 0, 0));
        jLabel25.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 0, 51));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("*");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel26.setBackground(new java.awt.Color(255, 0, 0));
        jLabel26.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("*");
        jLabel26.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel27.setBackground(new java.awt.Color(255, 0, 0));
        jLabel27.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("*");
        jLabel27.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))
                            .addComponent(txtNumeroFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCidade_, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_idPais, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPais_)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20))
                            .addComponent(txtEndereco))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addComponent(txtNomeComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_idCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btn)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24))
                                    .addComponent(txtNomeEmpresa, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_idEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtEstado_, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_idVizinhanca, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtVizinhanca_, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(lbl_idEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstado_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNomeComercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(13, 13, 13)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbl_idPais, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addGap(7, 7, 7)
                                    .addComponent(txtPais_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(lbl_idVizinhanca, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVizinhanca_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(lbl_idCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCidade_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPais_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPais_KeyPressed

    }//GEN-LAST:event_txtPais_KeyPressed

    private void txtPais_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPais_MouseClicked
        Localizacao l = new Localizacao();
        l.statusForm("cadastrar", "companhia");
        l.selecionar_guia(0);
        l.setTitle("Cadastrar Informações Sobre a Companhia");
        l.preencherCampus(preencherObjeto());
        l.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_txtPais_MouseClicked

    private void txtCidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidade_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidade_ActionPerformed

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        if(validarCampus()){
            //salvar pais
            CompanhiaRepository BaseURL = retrofit.BaseURL().create(CompanhiaRepository.class);
            Call<Boolean> callState = BaseURL.salvar(preencherObjeto());
            callState.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {                    
                    if (rspns.isSuccessful()) {
                        Boolean ok = rspns.body();
                        if (ok) {
                            JOptionPane.showMessageDialog(Companhia.this, "Dados cadastrados com sucesso");
                            Home h = new Home();
                            h.habilitarForm();
                            Companhia.this.dispose();
                        }
                        callState.cancel();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                }
            });
        }
    }//GEN-LAST:event_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lbl_idCidade;
    private javax.swing.JLabel lbl_idEstado;
    private javax.swing.JLabel lbl_idPais;
    private javax.swing.JLabel lbl_idVizinhanca;
    private javax.swing.JTextField txtCidade_;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtEstado_;
    private javax.swing.JTextField txtNomeComercial;
    private javax.swing.JTextField txtNomeEmpresa;
    private javax.swing.JTextField txtNumeroFiscal;
    private javax.swing.JTextField txtPais_;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtVizinhanca_;
    // End of variables declaration//GEN-END:variables
}
