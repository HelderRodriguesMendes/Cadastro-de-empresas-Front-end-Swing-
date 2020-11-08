/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cadastroempresas.retrofit.model_retrofit.Cidade_retrofit;
import cadastroempresas.retrofit.model_retrofit.Estado_retrofit;
import cadastroempresas.retrofit.repository_retrofit.CityRepository;
import cadastroempresas.retrofit.repository_retrofit.CountryRepository;
import cadastroempresas.retrofit.repository_retrofit.EstadoRepository_retrofit;
import cadastroempresas.retrofit.repository_retrofit.MunicipiosRepository;
import cadastroempresas.retrofit.repository_retrofit.NeighborhoodRepository;
import cadastroempresas.retrofit.repository_retrofit.StateRepository;
import cadastroempresas.retrofit.service_retrofit.Retrofit_URL;
import controller.StateController;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
public class Localizacao extends javax.swing.JFrame {

    private static String status_Form = "", entidadeUtilizada = "";
    Retrofit_URL retrofit = new Retrofit_URL();
    StateController stateController = new StateController();
    boolean jaBuscoCidades = false, jaListouTabelaEstados = false, jaListouTabelaCidades = false, jaListouTabelaNeighborhood = false;
    Long id_SelecionadoNaTabela_pais = null, id_SelecionadoNaTabela_estado = null, id_SelecionadoNaTabela_cidade = null, id_SelecionadoNaTabela_vizinhanca = null;

    Country country = new Country();
    State state = new State();
    City city = new City();
    Neighborhood neighborhood = new Neighborhood();

    public Localizacao() {
        initComponents();
        //DESABILITA O BOTÃO DE MAXIMIZAR
        setResizable(false);

        addWindowListener(new WindowAdapter() { // para confirna se deseja ralmente fechar a janela
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Sim", "Não"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Deseja realmente fechar essa janela de cadastro?", "ATENÇÃO",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    if (!entidadeUtilizada.equals("companhia")) {
                        Home h = new Home();
                        h.habilitarForm();
                        Localizacao.this.dispose();
                    } else {
                        Companhia c = new Companhia();
                        c.habilitarForm();
                        Localizacao.this.dispose();
                    }
                }
            }
        });

        //busca todos os paises cadastrados
        CountryRepository BaseURL = retrofit.BaseURL().create(CountryRepository.class);
        Call<List<Country>> callState = BaseURL.getCountrysCadastrados();
        callState.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> rspns) {
                if (rspns.isSuccessful()) {
                    List<Country> countrys = rspns.body();
                    listarTabelaPaisesCadastrados(countrys);
                    callState.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable thrwbl) {
            }
        });
    }

    //configura os campus da janela
    private void configForm(int form) {

        //configurando o titulo dos botoes de cada entidade
        if (status_Form.equals("cadastrar")) {

            if (entidadeUtilizada.equals("pais")) {
                btnPais_.setText("Cadastrar");
            } else {
                btnPais_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("estado")) {
                btnEstado_.setText("Cadastrar");
            } else {
                btnEstado_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("cidade")) {
                btnCidade_.setText("Cadastrar");
            } else {
                btnCidade_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("vizinhanca")) {
                btnVizinhanca_.setText("Cadastrar");
            } else {
                btnVizinhanca_.setText("Finalizar");
            }
        } else if (status_Form.equals("alterar")) {

            if (entidadeUtilizada.equals("pais")) {
                btnPais_.setText("Alterar");
                txt_nomePais.setEnabled(false);
            } else {
                btnPais_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("estado")) {
                btnEstado_.setText("Alterar");
                txtNomeEstado_.setEnabled(false);
            } else {
                btnEstado_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("cidade")) {
                btnCidade_.setText("Alterar");
            } else {
                btnCidade_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("vizinhanca")) {
                btnVizinhanca_.setText("Alterar");
                txt_nomePais.setEnabled(false);
                btn_TODOS_ESTADOS_.setVisible(false);
                btn_TODAS_CIDADES_.setVisible(false);
            } else {
                btnVizinhanca_.setText("Finalizar");
            }
        } else {
            lblNome_Pais.setVisible(false);
            jLabel28.setVisible(false);
            txt_nomePais.setVisible(false);
            btnPais_.setVisible(false);
        }

        //configura os campus de acordo se o pais informado for o Brasil ou nao
        if (form == 1) {
            if (!txt_nomePais.getText().equals("Brasil")) {
                combo_estados_.setVisible(false);
                lblNomeCombo_estados.setVisible(false);
                jLabel26.setVisible(false);
            } else {
                txtNomeEstado_.setEnabled(false);
            }
        } else if (form == 2) {
            if (!txt_nomePais.getText().equals("Brasil")) {
                combo_cidades_.setVisible(false);
                lblNomeCombo_cidades.setVisible(false);
                jLabel25.setVisible(false);
            } else {
                txtNomeCidade_.setEnabled(false);
            }
        }
    }

    //altera a disponibilidade das abas altomaticamente
    public void selecionar_guia(int n) {
        this.FORM_GUIAS.setEnabledAt(n, true);
        this.FORM_GUIAS.setSelectedIndex(n);
        configForm(n);
        switch (n) {
            case 0:
                this.FORM_GUIAS.setEnabledAt(1, false);
                this.FORM_GUIAS.setEnabledAt(2, false);
                this.FORM_GUIAS.setEnabledAt(3, false);
                break;
            case 1:
                this.FORM_GUIAS.setEnabledAt(0, false);
                this.FORM_GUIAS.setEnabledAt(2, false);
                this.FORM_GUIAS.setEnabledAt(3, false);
                break;
            case 2:
                this.FORM_GUIAS.setEnabledAt(0, false);
                this.FORM_GUIAS.setEnabledAt(1, false);
                this.FORM_GUIAS.setEnabledAt(3, false);
                break;
            case 3:
                this.FORM_GUIAS.setEnabledAt(0, false);
                this.FORM_GUIAS.setEnabledAt(1, false);
                this.FORM_GUIAS.setEnabledAt(2, false);
                break;
            default:
                break;
        }
    }

    private Neighborhood preencherObjetoNeighborhood() {
        country.setId(id_SelecionadoNaTabela_pais);
        country.setName(txt_nomePais.getText());

        state.setId(id_SelecionadoNaTabela_estado);
        state.setName(txtNomeEstado_.getText());
        state.setCountry(country);

        city.setId(id_SelecionadoNaTabela_cidade);
        city.setName(txtNomeCidade_.getText());
        city.setState(state);

        neighborhood.setId(id_SelecionadoNaTabela_vizinhanca);
        neighborhood.setName(txtNomeVizinhanca_.getText());
        neighborhood.setAtivo(true);
        neighborhood.setCity(city);

        return neighborhood;
    }

    public void preencherCampus(Company company) {

        JOptionPane.showMessageDialog(Localizacao.this, "Informe o País, Estado, Cidade e Vizinhança da empresa que está sendo cadastrada", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
        combo_estados_.setVisible(false);
        lblNomeCombo_estados.setVisible(false);
        jLabel26.setVisible(false);

        combo_cidades_.setVisible(false);
        lblNomeCombo_cidades.setVisible(false);
        jLabel25.setVisible(false);

        neighborhood = company.getNeighborhood();
        city = company.getCity();
        state = company.getState();
        country = company.getCountry();

        txt_nomePais.setText(country.getName());
        id_SelecionadoNaTabela_pais = country.getId();

        txtNomeEstado_.setText(state.getName());
        id_SelecionadoNaTabela_estado = state.getId();

        txtNomeCidade_.setText(city.getName());
        id_SelecionadoNaTabela_cidade = city.getId();

        txtNomeVizinhanca_.setText(neighborhood.getName());
        id_SelecionadoNaTabela_vizinhanca = neighborhood.getId();
    }

    private void listarTabelaPaisesCadastrados(List<Country> countryCadastrados) {
        DefaultTableModel dtma = (DefaultTableModel) TABELA_Pais.getModel();
        dtma.setNumRows(0);
        TABELA_Pais.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_Pais.getColumnModel().getColumn(1).setPreferredWidth(65);

        TABELA_Pais.getColumnModel().getColumn(0).setMinWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        TABELA_Pais.getColumnModel().getColumn(0).setMaxWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        countryCadastrados.forEach((s) -> {
            dtma.addRow(new Object[]{
                s.getId(),
                s.getName()
            });
        });
        corLinhaTabelaAnuidade(TABELA_Pais);
        TABELA_Pais.getTableHeader().setReorderingAllowed(false);      // BLOQUIA AS COLUNAS DA TABELA PARA NÃO MOVELAS DO LUGAR
    }

    private void listarTabelaEstadosCadastrados(List<State> statesCadastrados) {

        //ORDENANDO LISTA
        Collections.sort(statesCadastrados, (State s1, State s2) -> s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase()));

        DefaultTableModel dtma = (DefaultTableModel) TABELA_Estados.getModel();
        dtma.setNumRows(0);
        TABELA_Estados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_Estados.getColumnModel().getColumn(1).setPreferredWidth(180);

        TABELA_Estados.getColumnModel().getColumn(0).setMinWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        TABELA_Estados.getColumnModel().getColumn(0).setMaxWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        statesCadastrados.forEach((s) -> {
            dtma.addRow(new Object[]{
                s.getId(),
                s.getName()
            });
        });
        corLinhaTabelaAnuidade(TABELA_Estados);
        TABELA_Estados.getTableHeader().setReorderingAllowed(false);      // BLOQUIA AS COLUNAS DA TABELA PARA NÃO MOVELAS DO LUGAR
    }

    private void listarTabelaCidadesCadastrados(List<City> citysCadastradas) {
        //ORDENANDO LISTA
        Collections.sort(citysCadastradas, (City c1, City c2) -> c1.getName().toUpperCase().compareTo(c2.getName().toUpperCase()));

        DefaultTableModel dtma = (DefaultTableModel) TABELA_cidade.getModel();
        dtma.setNumRows(0);
        TABELA_cidade.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_cidade.getColumnModel().getColumn(1).setPreferredWidth(180);

        TABELA_cidade.getColumnModel().getColumn(0).setMinWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        TABELA_cidade.getColumnModel().getColumn(0).setMaxWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        citysCadastradas.forEach((s) -> {
            dtma.addRow(new Object[]{
                s.getId(),
                s.getName()
            });
        });
        corLinhaTabelaAnuidade(TABELA_cidade);
        TABELA_cidade.getTableHeader().setReorderingAllowed(false);      // BLOQUIA AS COLUNAS DA TABELA PARA NÃO MOVELAS DO LUGAR
    }

    private void listarTabelaNeighborhoodCadastradas(List<Neighborhood> neighborhoodCadastradas) {
        //ORDENANDO LISTA
        Collections.sort(neighborhoodCadastradas, (Neighborhood n1, Neighborhood n2) -> n1.getName().toUpperCase().compareTo(n2.getName().toUpperCase()));

        DefaultTableModel dtma = (DefaultTableModel) TABELA_vizinhanca.getModel();
        dtma.setNumRows(0);
        TABELA_vizinhanca.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_vizinhanca.getColumnModel().getColumn(1).setPreferredWidth(180);

        TABELA_vizinhanca.getColumnModel().getColumn(0).setMinWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        TABELA_vizinhanca.getColumnModel().getColumn(0).setMaxWidth(0); // OCULTA A COLUNA (ID) DA TABELA PARA NÃO APARECER PARA O USUARIO
        neighborhoodCadastradas.forEach((s) -> {
            dtma.addRow(new Object[]{
                s.getId(),
                s.getName()
            });
        });
        corLinhaTabelaAnuidade(TABELA_vizinhanca);
        TABELA_vizinhanca.getTableHeader().setReorderingAllowed(false);      // BLOQUIA AS COLUNAS DA TABELA PARA NÃO MOVELAS DO LUGAR
    }

    private void corLinhaTabelaAnuidade(JTable tabela) {
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 != 0) {
                    setBackground(Color.BLUE);
                } else {
                    setBackground(Color.GRAY);
                }
                return this;
            }
        });
    }

    public void statusForm(String status, String entidade) {
        status_Form = status;
        entidadeUtilizada = entidade;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FORM_GUIAS = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblNome_Pais = new javax.swing.JLabel();
        txt_nomePais = new javax.swing.JTextField();
        btnPais_ = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        TABELA_Pais = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtPesquisarPais = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNomeEstado_ = new javax.swing.JTextField();
        btnEstado_ = new javax.swing.JButton();
        lblNomeCombo_estados = new javax.swing.JLabel();
        combo_estados_ = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        TABELA_Estados = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        btn_TODOS_ESTADOS_ = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblNomeCombo_cidades = new javax.swing.JLabel();
        combo_cidades_ = new javax.swing.JComboBox<>();
        txtNomeCidade_ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnCidade_ = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TABELA_cidade = new javax.swing.JTable();
        btn_TODAS_CIDADES_ = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeVizinhanca_ = new javax.swing.JTextField();
        btnVizinhanca_ = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TABELA_vizinhanca = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblVoltar_ = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setType(java.awt.Window.Type.UTILITY);

        lblNome_Pais.setText("Nome:");

        txt_nomePais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nomePaisKeyPressed(evt);
            }
        });

        btnPais_.setText("jButton1");
        btnPais_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPais_ActionPerformed(evt);
            }
        });

        TABELA_Pais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NOME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABELA_Pais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABELA_PaisMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TABELA_Pais);
        if (TABELA_Pais.getColumnModel().getColumnCount() > 0) {
            TABELA_Pais.getColumnModel().getColumn(0).setResizable(false);
            TABELA_Pais.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel11.setText("Nome:");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel28.setBackground(new java.awt.Color(255, 0, 0));
        jLabel28.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("*");
        jLabel28.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblNome_Pais)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel28))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPais_)
                            .addComponent(txt_nomePais, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPesquisarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(jLabel12)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNome_Pais)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nomePais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPais_)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisarPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        FORM_GUIAS.addTab("País", jPanel1);

        jLabel2.setText("Nome:");

        txtNomeEstado_.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeEstado_KeyPressed(evt);
            }
        });

        btnEstado_.setText("jButton1");
        btnEstado_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstado_ActionPerformed(evt);
            }
        });

        lblNomeCombo_estados.setText("Nome:");

        combo_estados_.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));
        combo_estados_.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_estados_ItemStateChanged(evt);
            }
        });
        combo_estados_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_estados_ActionPerformed(evt);
            }
        });

        TABELA_Estados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NOME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABELA_Estados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABELA_EstadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TABELA_Estados);
        if (TABELA_Estados.getColumnModel().getColumnCount() > 0) {
            TABELA_Estados.getColumnModel().getColumn(0).setResizable(false);
            TABELA_Estados.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel9.setText("Estados cadastrados");

        btn_TODOS_ESTADOS_.setText("+  Estados");
        btn_TODOS_ESTADOS_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TODOS_ESTADOS_ActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(btn_TODOS_ESTADOS_)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEstado_))
                        .addComponent(combo_estados_, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(lblNomeCombo_estados)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel26))
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27))
                    .addComponent(txtNomeEstado_, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNomeEstado_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lblNomeCombo_estados))
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_estados_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEstado_)
                    .addComponent(btn_TODOS_ESTADOS_))
                .addGap(28, 28, 28)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        FORM_GUIAS.addTab("Estado", jPanel2);

        lblNomeCombo_cidades.setText("Nome:");

        combo_cidades_.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        combo_cidades_.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_cidades_ItemStateChanged(evt);
            }
        });

        txtNomeCidade_.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeCidade_KeyPressed(evt);
            }
        });

        jLabel5.setText("Nome:");

        btnCidade_.setText("jButton1");
        btnCidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidade_ActionPerformed(evt);
            }
        });

        TABELA_cidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NOME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABELA_cidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABELA_cidadeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TABELA_cidade);
        if (TABELA_cidade.getColumnModel().getColumnCount() > 0) {
            TABELA_cidade.getColumnModel().getColumn(0).setResizable(false);
            TABELA_cidade.getColumnModel().getColumn(1).setResizable(false);
        }

        btn_TODAS_CIDADES_.setText("+  Cidades");
        btn_TODAS_CIDADES_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TODAS_CIDADES_ActionPerformed(evt);
            }
        });

        jLabel10.setText("Cidades cadastradas");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblNomeCombo_cidades)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(combo_cidades_, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(btn_TODAS_CIDADES_)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCidade_)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel24))
                            .addComponent(txtNomeCidade_, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeCidade_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNomeCombo_cidades)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_cidades_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCidade_)
                    .addComponent(btn_TODAS_CIDADES_))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        FORM_GUIAS.addTab("Cidade", jPanel3);

        jLabel3.setText("Nome:");

        btnVizinhanca_.setText("jButton1");
        btnVizinhanca_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVizinhanca_ActionPerformed(evt);
            }
        });

        TABELA_vizinhanca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NOME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TABELA_vizinhanca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABELA_vizinhancaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TABELA_vizinhanca);
        if (TABELA_vizinhanca.getColumnModel().getColumnCount() > 0) {
            TABELA_vizinhanca.getColumnModel().getColumn(0).setResizable(false);
            TABELA_vizinhanca.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel13.setText("Cidades cadastradas");

        jLabel23.setBackground(new java.awt.Color(255, 0, 0));
        jLabel23.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("*");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnVizinhanca_)
                                .addComponent(txtNomeVizinhanca_, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeVizinhanca_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVizinhanca_)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        FORM_GUIAS.addTab("Vizinhança", jPanel5);

        lblVoltar_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/voltar.png"))); // NOI18N
        lblVoltar_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVoltar_MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblVoltar_, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(FORM_GUIAS)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblVoltar_, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FORM_GUIAS))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPais_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPais_ActionPerformed

        if (txt_nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o país");
            txt_nomePais.requestFocus();
        } else if (txt_nomePais.getText().length() < 2 || txt_nomePais.getText().length() > 30) {
            JOptionPane.showMessageDialog(Localizacao.this, "O Nome do País deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();
        } else if (entidadeUtilizada.equals("pais")) {
            country.setId(id_SelecionadoNaTabela_pais);
            country.setName(txt_nomePais.getText());

            CountryRepository BaseURL = retrofit.BaseURL().create(CountryRepository.class);
            //salvar pais 
            if (status_Form.equals("cadastrar")) {
                Call<Boolean> callState = BaseURL.salvar(country);
                callState.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                        Localizacao l = new Localizacao();
                        if (rspns.isSuccessful()) {
                            Boolean ok = rspns.body();
                            if (ok) {
                                JOptionPane.showMessageDialog(Localizacao.this, "Dados cadastrados com sucesso");
                                Home h = new Home();
                                h.habilitarForm();
                                Localizacao.this.dispose();
                            }
                            callState.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                    }
                });
            } else {
                Call<Boolean> callState = BaseURL.alterar(id_SelecionadoNaTabela_pais, country);
                callState.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                        Localizacao l = new Localizacao();
                        if (rspns.isSuccessful()) {
                            Boolean ok = rspns.body();
                            if (ok) {
                                JOptionPane.showMessageDialog(Localizacao.this, "Dados Alterados com sucesso");
                                Home h = new Home();
                                h.habilitarForm();
                                Localizacao.this.dispose();
                            }
                            callState.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                    }
                });
            }

        } else {
            if (!txt_nomePais.getText().equals("Brasil")) {
                lblNomeCombo_estados.setVisible(false);
                combo_estados_.setVisible(false);
                btn_TODOS_ESTADOS_.setVisible(false);
            } else {
                btn_TODOS_ESTADOS_.setVisible(true);
                combo_estados_.setVisible(false);
                lblNomeCombo_estados.setVisible(false);
                jLabel26.setVisible(false);
            }
            StateRepository stateRepository = retrofit.BaseURL().create(StateRepository.class);
            Call<List<State>> callState = stateRepository.getStatesCadastrados_Country(txt_nomePais.getText());
            callState.enqueue(new Callback<List<State>>() {
                @Override
                public void onResponse(Call<List<State>> call, Response<List<State>> rspns) {
                    if (rspns.isSuccessful()) {
                        List<State> state = rspns.body();
                        listarTabelaEstadosCadastrados(state);
                        callState.cancel();

                        //ALTERANDO SEM SER DO BRASIL
                        if (status_Form.equals("alterar") && !txt_nomePais.getText().equals("Brasil")) {
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);

                            //ALTERANDO VIZINHANCA QUE É DO BRASIL                                
                        } else if (status_Form.equals("alterar") && txt_nomePais.getText().equals("Brasil") && entidadeUtilizada.equals("vizinhanca")) {
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                        } else if (status_Form.equals("alterar")) {
                            JOptionPane.showMessageDialog(Localizacao.this, "Não é permitido alterar Estados ou Cidades do Brasil", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                        } else if (status_Form.equals("cadastrar")) {
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<State>> call, Throwable thrwbl) {
                }
            });
        }
    }//GEN-LAST:event_btnPais_ActionPerformed

    private void lblVoltar_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVoltar_MouseClicked
        //verifica se não é primeira aba, que é representada por 0, para nao dar erro na subtracao
        if (this.FORM_GUIAS.getSelectedIndex() > 0) {
            //pega o numero da guia atual e diminui - 1 para voltar para a aba anterior
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);
            lblNomeCombo_cidades.setVisible(false);
            jLabel25.setVisible(false);
            combo_cidades_.setVisible(false);

            lblNomeCombo_estados.setVisible(false);
            jLabel26.setVisible(false);
            combo_estados_.setVisible(false);
        } else {
            String ObjButtons[] = {"Sim", "Não"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                    "Deseja cancelar e retornar a tela principal?", "ATENÇÃO",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    ObjButtons, ObjButtons[1]);
            if (PromptResult == 0) {
                if (!status_Form.equals("cadastro_company")) {
                    Home h = new Home();
                    h.habilitarForm();
                    Localizacao.this.dispose();
                }

            }
        }
    }//GEN-LAST:event_lblVoltar_MouseClicked

    private void btnEstado_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstado_ActionPerformed

        //verifica se o pais foi informado, porque o estado depende do pais no cadastro
        if (txt_nomePais.getText().equals("") && id_SelecionadoNaTabela_pais == null) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o País deste Estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();

            //volta para a aba de pais, para o mesmo ser informado
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

            //se estiver cadastrando apenas o estado
        } else if (entidadeUtilizada.equals("estado")) {
            if (txtNomeEstado_.getText().equals("") && combo_estados_.getSelectedItem().equals("Selecione") && txt_nomePais.getText().equals("Brasil")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o Estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();

            } else if (txtNomeEstado_.getText().equals("")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o Estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();
            } else if (txtNomeEstado_.getText().length() < 2 || txtNomeEstado_.getText().length() > 30) {
                JOptionPane.showMessageDialog(Localizacao.this, "O Nome do Estado deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();
            } else {
                country.setId(id_SelecionadoNaTabela_pais);
                country.setName(txt_nomePais.getText());

                state.setId(id_SelecionadoNaTabela_estado);
                state.setName(txtNomeEstado_.getText());
                state.setCountry(country);

                StateRepository BaseURL = retrofit.BaseURL().create(StateRepository.class);

                //salvar estado
                if (status_Form.equals("cadastrar")) {
                    Call<Boolean> callState = BaseURL.salvar(state);
                    callState.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                            if (rspns.isSuccessful()) {
                                Boolean ok = rspns.body();
                                if (ok) {
                                    JOptionPane.showMessageDialog(Localizacao.this, "Dados cadastrados com sucesso");
                                    Home h = new Home();
                                    h.habilitarForm();
                                    Localizacao.this.dispose();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                        }
                    });
                } else {
                    Call<Boolean> callState = BaseURL.alterar(id_SelecionadoNaTabela_estado, state);
                    callState.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                            if (rspns.isSuccessful()) {
                                Boolean ok = rspns.body();
                                if (ok) {
                                    JOptionPane.showMessageDialog(Localizacao.this, "Dados Alterados com sucesso");
                                    Home h = new Home();
                                    h.habilitarForm();
                                    Localizacao.this.dispose();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                        }
                    });
                }
            }

            //Se estiver cadastrando cidade ou vizinhanca
        } else {

            //valçidações para cadastramento de dados com o pais: Brasil
            if (txtNomeEstado_.getText().equals("") && combo_estados_.getSelectedItem().equals("Selecione") && txt_nomePais.getText().equals("Brasil")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();

            } else if (txtNomeEstado_.getText().equals("")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();
            } else {
                if (!txt_nomePais.getText().equals("Brasil")) {
                    lblNomeCombo_cidades.setVisible(false);
                    combo_cidades_.setVisible(false);
                    btn_TODAS_CIDADES_.setVisible(false);
                } else {
                    lblNomeCombo_cidades.setVisible(false);
                    combo_cidades_.setVisible(false);
                    jLabel25.setVisible(false);
                }

                CityRepository cityRepository = retrofit.BaseURL().create(CityRepository.class);
                Call<List<City>> callState = cityRepository.getCitysCadastrados_state(txtNomeEstado_.getText());
                callState.enqueue(new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> rspns) {
                        if (rspns.isSuccessful()) {
                            List<City> citys = rspns.body();
                            listarTabelaCidadesCadastrados(citys);
                            callState.cancel();
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                            combo_estados_.setVisible(true);
                            lblNomeCombo_estados.setVisible(true);
                            txt_nomePais.setEnabled(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable thrwbl) {
                    }
                });
            }
        }
    }//GEN-LAST:event_btnEstado_ActionPerformed

    private void combo_estados_ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_estados_ItemStateChanged
        //pega o estado selecionado e exite no campo de texto NOME que esta desabilitado
        txtNomeEstado_.setText(combo_estados_.getSelectedItem().toString());
    }//GEN-LAST:event_combo_estados_ItemStateChanged

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        if (!txtPesquisarPais.getText().equals("")) {
            //busca por nome todos os paises cadastrados
            CountryRepository BaseURL = retrofit.BaseURL().create(CountryRepository.class);
            Call<List<Country>> callState = BaseURL.getCountrysCadastrados_Nome(txtPesquisarPais.getText());
            callState.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> rspns) {
                    Localizacao l = new Localizacao();
                    if (rspns.isSuccessful()) {
                        List<Country> countrys = rspns.body();

                        listarTabelaPaisesCadastrados(countrys);
                        callState.cancel();
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable thrwbl) {
                    System.out.println("Arro ao consultar Country por nome: " + thrwbl.getMessage());
                }
            });

        } else {
            //busca todos os paises cadastrados
            CountryRepository BaseURL = retrofit.BaseURL().create(CountryRepository.class);
            Call<List<Country>> callState = BaseURL.getCountrysCadastrados();
            callState.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> rspns) {
                    Localizacao l = new Localizacao();
                    if (rspns.isSuccessful()) {
                        List<Country> countrys = rspns.body();
                        listarTabelaPaisesCadastrados(countrys);
                        callState.cancel();
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable thrwbl) {
                }
            });
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void TABELA_PaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_PaisMouseClicked
        //se estiver cadastrando um pais e clica em um pais ja cadastrado que esta na tabela, o nome desse pais nao ira para o campu de cadastramento
        if (!(status_Form.equals("cadastrar") && entidadeUtilizada.equals("pais"))) {
            txt_nomePais.setText(TABELA_Pais.getValueAt(TABELA_Pais.getSelectedRow(), 1).toString());
            id_SelecionadoNaTabela_pais = Long.parseLong(TABELA_Pais.getValueAt(TABELA_Pais.getSelectedRow(), 0).toString());
            txt_nomePais.setEnabled(true);
            txtNomeEstado_.setText("");
            txtNomeCidade_.setText("");
            txtNomeVizinhanca_.setText("");
        }
    }//GEN-LAST:event_TABELA_PaisMouseClicked

    private void TABELA_EstadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_EstadosMouseClicked
        //se estiver cadastrando um estado e clica em um estado ja cadastrado que esta na tabela, o nome desse estado nao ira para o campu de cadastramento
        if (!(status_Form.equals("cadastrar") && entidadeUtilizada.equals("estado"))) {
            String estado = TABELA_Estados.getValueAt(TABELA_Estados.getSelectedRow(), 1).toString();
            txtNomeEstado_.setText(estado);
            id_SelecionadoNaTabela_estado = Long.parseLong(TABELA_Estados.getValueAt(TABELA_Estados.getSelectedRow(), 0).toString());
            txtNomeCidade_.setText("");
            txtNomeVizinhanca_.setText("");
            if (!status_Form.equals("alterar") && !entidadeUtilizada.equals("estado")) {
                txtNomeEstado_.setEnabled(true);
            }
        }
    }//GEN-LAST:event_TABELA_EstadosMouseClicked

    private void btn_TODOS_ESTADOS_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TODOS_ESTADOS_ActionPerformed
        if (txt_nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o país deste estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();

            //volta para a aba de pais, para o mesmo ser informado
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

        } else if (txt_nomePais.getText().equals("Brasil")) {
            lblNomeCombo_estados.setVisible(true);
            combo_estados_.setVisible(true);
            jLabel26.setVisible(true);

            EstadoRepository_retrofit estado_retrofit = retrofit.BaseURL_estado().create(EstadoRepository_retrofit.class);

            List<String> ESTADOS = new ArrayList<>();
            Call<List<Estado_retrofit>> call = estado_retrofit.getEstados();

            call.enqueue(new Callback<List<Estado_retrofit>>() {
                @Override
                public void onResponse(Call<List<Estado_retrofit>> call, Response<List<Estado_retrofit>> response) {
                    if (response.isSuccessful()) {
                        List<Estado_retrofit> estados = response.body();

                        for (Estado_retrofit e : estados) {
                            String esta = e.getNome() + "-" + e.getSigla();
                            ESTADOS.add(esta);
                        }
                        //COLOCA A LISTA EM ORDEM ALFABETICA
                        Collections.sort(ESTADOS);

                        for (String e : ESTADOS) {
                            combo_estados_.removeAll();
                            combo_estados_.addItem(e);
                        }
                        btn_TODAS_CIDADES_.removeAll();
                        call.cancel();
                    }
                }

                @Override
                public void onFailure(Call<List<Estado_retrofit>> call, Throwable t) {
                    System.out.println("Erro ao listar COMBOBOX: " + t.getMessage());
                }
            });
        }
    }//GEN-LAST:event_btn_TODOS_ESTADOS_ActionPerformed

    private void btnCidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidade_ActionPerformed

        //verifica se o pais e estado foi informado
        if (txt_nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o País e Estado desta cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();

            //volta para a aba de pais
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 2);

            //se estiver cadastrando apenas a cidade
        } else if (entidadeUtilizada.equals("cidade")) {
            if (txtNomeCidade_.getText().equals("") && combo_cidades_.getSelectedItem().equals("Selecione") && txt_nomePais.getText().equals("Brasil")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe a cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeCidade_.requestFocus();

            } else if (txtNomeCidade_.getText().equals("")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe a cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeCidade_.requestFocus();

            } else if (txtNomeCidade_.getText().length() < 2 || txtNomeCidade_.getText().length() > 30) {
                JOptionPane.showMessageDialog(Localizacao.this, "O Nome da Cidade deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeVizinhanca_.requestFocus();

            } else {
                country.setId(id_SelecionadoNaTabela_pais);
                country.setName(txt_nomePais.getText());

                state.setId(id_SelecionadoNaTabela_estado);
                state.setName(txtNomeEstado_.getText());
                state.setCountry(country);

                city.setId(id_SelecionadoNaTabela_cidade);
                city.setName(txtNomeCidade_.getText());
                city.setState(state);

                CityRepository BaseURL = retrofit.BaseURL().create(CityRepository.class);

                //salvar cidade
                if (status_Form.equals("cadastrar")) {
                    Call<Boolean> callState = BaseURL.salvar(city);
                    callState.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                            if (rspns.isSuccessful()) {
                                Boolean ok = rspns.body();
                                if (ok) {
                                    JOptionPane.showMessageDialog(Localizacao.this, "Dados cadastrados com sucesso");
                                    Home h = new Home();
                                    h.habilitarForm();
                                    Localizacao.this.dispose();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                        }
                    });
                } else {
                    Call<Boolean> callState = BaseURL.alterar(id_SelecionadoNaTabela_cidade, city);
                    callState.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                            if (rspns.isSuccessful()) {
                                Boolean ok = rspns.body();
                                if (ok) {
                                    JOptionPane.showMessageDialog(Localizacao.this, "Dados alterados com sucesso");
                                    Home h = new Home();
                                    h.habilitarForm();
                                    Localizacao.this.dispose();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                        }
                    });
                }
            }
            //se estiver cadastrando vizinhanca
        } else {
            if (txtNomeCidade_.getText().equals("") && combo_cidades_.getSelectedItem().equals("Selecione") && txt_nomePais.getText().equals("Brasil")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe a cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeCidade_.requestFocus();
            } else if (txtNomeCidade_.getText().equals("")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe a cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeCidade_.requestFocus();
            } else {
                NeighborhoodRepository neighborhoodRepository = retrofit.BaseURL().create(NeighborhoodRepository.class);
                Call<List<Neighborhood>> callState = neighborhoodRepository.getNeighborhoodCadastradosFK(txtNomeCidade_.getText());
                callState.enqueue(new Callback<List<Neighborhood>>() {
                    @Override
                    public void onResponse(Call<List<Neighborhood>> call, Response<List<Neighborhood>> rspns) {
                        if (rspns.isSuccessful()) {
                            List<Neighborhood> neighborhoods = rspns.body();
                            listarTabelaNeighborhoodCadastradas(neighborhoods);
                            callState.cancel();
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Neighborhood>> call, Throwable thrwbl) {
                    }
                });
            }
        }
    }//GEN-LAST:event_btnCidade_ActionPerformed

    private void btn_TODAS_CIDADES_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TODAS_CIDADES_ActionPerformed
        if (txtNomeEstado_.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado desta cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();

            //volta para a aba de pais, para o mesmo ser informado
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

        } else if (txt_nomePais.getText().equals("Brasil")) {
            lblNomeCombo_cidades.setVisible(true);
            combo_cidades_.setVisible(true);
            jLabel25.setVisible(true);
            txtNomeCidade_.setEnabled(false);

            MunicipiosRepository municipiosRepository = retrofit.BaseURL_estado().create(MunicipiosRepository.class);

            String UF = stateController.separaSigla_State(txtNomeEstado_.getText());

            List<String> CIDADES = new ArrayList<>();
            Call<List<Cidade_retrofit>> call = municipiosRepository.getCidades(UF);

            combo_cidades_.removeAllItems();
            combo_cidades_.addItem("Selecione");
            call.enqueue(new Callback<List<Cidade_retrofit>>() {
                @Override
                public void onResponse(Call<List<Cidade_retrofit>> call, Response<List<Cidade_retrofit>> response) {
                    if (response.isSuccessful()) {
                        List<Cidade_retrofit> cidades = response.body();
                        for (Cidade_retrofit c : cidades) {
                            String cidade = c.getNome();
                            CIDADES.add(cidade);
                        }
                        //COLOCA A LISTA EM ORDEM ALFABETICA
                        Collections.sort(CIDADES);

                        for (String c : CIDADES) {
                            combo_cidades_.removeAll();
                            combo_cidades_.addItem(c);
                        }
                        jaBuscoCidades = true;
                        call.cancel();
                    }
                }

                @Override
                public void onFailure(Call<List<Cidade_retrofit>> call, Throwable t) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }//GEN-LAST:event_btn_TODAS_CIDADES_ActionPerformed

    private void combo_cidades_ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_cidades_ItemStateChanged
        //pega a cidade selecionada e exibe no campo de texto NOME que esta desabilitado
        if (combo_cidades_.getSelectedItem() != null) {
            String cidade = combo_cidades_.getSelectedItem().toString();
            if (!cidade.equals("Selecione")) {
                txtNomeCidade_.setText(cidade);
            } else {
                txtNomeCidade_.setText("");
            }

        }
    }//GEN-LAST:event_combo_cidades_ItemStateChanged

    private void btnVizinhanca_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVizinhanca_ActionPerformed

        //verifica se o pais, estado e cidade foi informado
        if (txt_nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe O País, Estado e Cidade desta vizinhança", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txt_nomePais.requestFocus();

            //volta para a aba de pais
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 3);

        } else if (txtNomeVizinhanca_.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe a vizinhança", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeCidade_.requestFocus();
        } else if (txtNomeVizinhanca_.getText().length() < 2 || txtNomeVizinhanca_.getText().length() > 30) {
            JOptionPane.showMessageDialog(Localizacao.this, "O Nome da Vizinhança deve conter entre 2 a 30 caracteres", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            txtNomeVizinhanca_.requestFocus();
        } else if (entidadeUtilizada.equals("vizinhanca")) {

            NeighborhoodRepository BaseURL = retrofit.BaseURL().create(NeighborhoodRepository.class);

            //salvar Neighborhood
            if (status_Form.equals("cadastrar")) {
                Call<Boolean> callState = BaseURL.salvar(preencherObjetoNeighborhood());
                callState.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                        if (rspns.isSuccessful()) {
                            Boolean ok = rspns.body();
                            if (ok) {
                                JOptionPane.showMessageDialog(Localizacao.this, "Dados cadastrados com sucesso");
                                Home h = new Home();
                                h.habilitarForm();
                                Localizacao.this.dispose();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                    }
                });
            } else {
                Call<Boolean> callState = BaseURL.alterar(id_SelecionadoNaTabela_vizinhanca, preencherObjetoNeighborhood());
                callState.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> rspns) {
                        if (rspns.isSuccessful()) {
                            Boolean ok = rspns.body();
                            if (ok) {
                                JOptionPane.showMessageDialog(Localizacao.this, "Dados Alterados com sucesso");
                                Home h = new Home();
                                h.habilitarForm();
                                Localizacao.this.dispose();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable thrwbl) {
                    }
                });
            }

        } else if (entidadeUtilizada.equals("companhia")) {

            Companhia c = new Companhia();
            c.preencherCampusLocalizacao(preencherObjetoNeighborhood());
            c.habilitarForm();
            if (status_Form.equals("cadastrar")) {
                c.setTitle("Cadastrar Companhia");
            }
            Localizacao.this.dispose();
        }

    }//GEN-LAST:event_btnVizinhanca_ActionPerformed

    private void TABELA_cidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_cidadeMouseClicked
        //se estiver cadastrando uma cidade e clica em um cidade ja cadastrada que esta na tabela, o nome dessa cidade nao ira para o campu de cadastramento
        if (!(status_Form.equals("cadastrar") && entidadeUtilizada.equals("cidade"))) {
            String cidade = TABELA_cidade.getValueAt(TABELA_cidade.getSelectedRow(), 1).toString();
            txtNomeCidade_.setText(cidade);
            id_SelecionadoNaTabela_cidade = Long.parseLong(TABELA_cidade.getValueAt(TABELA_cidade.getSelectedRow(), 0).toString());
            txtNomeVizinhanca_.setText("");
        }
    }//GEN-LAST:event_TABELA_cidadeMouseClicked

    private void TABELA_vizinhancaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_vizinhancaMouseClicked
        //se estiver cadastrando uma vizinhanca e clica em um vizinhanca ja cadastrada que esta na tabela, o nome dessa vizinhanca nao ira para o campu de cadastramento
        if (!(status_Form.equals("cadastrar") && entidadeUtilizada.equals("vizinhanca"))) {
            String cidade = TABELA_vizinhanca.getValueAt(TABELA_vizinhanca.getSelectedRow(), 1).toString();
            txtNomeVizinhanca_.setText(cidade);
            id_SelecionadoNaTabela_vizinhanca = Long.parseLong(TABELA_vizinhanca.getValueAt(TABELA_vizinhanca.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_TABELA_vizinhancaMouseClicked

    private void txt_nomePaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomePaisKeyPressed
        txtNomeEstado_.setText("");
        txtNomeCidade_.setText("");
        txtNomeVizinhanca_.setText("");
    }//GEN-LAST:event_txt_nomePaisKeyPressed

    private void txtNomeEstado_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEstado_KeyPressed
        txtNomeCidade_.setText("");
        txtNomeVizinhanca_.setText("");
    }//GEN-LAST:event_txtNomeEstado_KeyPressed

    private void txtNomeCidade_KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeCidade_KeyPressed
        txtNomeVizinhanca_.setText("");
    }//GEN-LAST:event_txtNomeCidade_KeyPressed

    private void combo_estados_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_estados_ActionPerformed
        txtNomeCidade_.setText("");
    }//GEN-LAST:event_combo_estados_ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane FORM_GUIAS;
    private javax.swing.JTable TABELA_Estados;
    private javax.swing.JTable TABELA_Pais;
    private javax.swing.JTable TABELA_cidade;
    private javax.swing.JTable TABELA_vizinhanca;
    private javax.swing.JButton btnCidade_;
    private javax.swing.JButton btnEstado_;
    private javax.swing.JButton btnPais_;
    private javax.swing.JButton btnVizinhanca_;
    private javax.swing.JButton btn_TODAS_CIDADES_;
    private javax.swing.JButton btn_TODOS_ESTADOS_;
    private javax.swing.JComboBox<String> combo_cidades_;
    private javax.swing.JComboBox combo_estados_;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblNomeCombo_cidades;
    private javax.swing.JLabel lblNomeCombo_estados;
    private javax.swing.JLabel lblNome_Pais;
    private javax.swing.JLabel lblVoltar_;
    private javax.swing.JTextField txtNomeCidade_;
    private javax.swing.JTextField txtNomeEstado_;
    private javax.swing.JTextField txtNomeVizinhanca_;
    private javax.swing.JTextField txtPesquisarPais;
    private javax.swing.JTextField txt_nomePais;
    // End of variables declaration//GEN-END:variables
}
