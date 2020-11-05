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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.City;
import model.Country;
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
    boolean jaBuscoEstados = false, jaBuscoCidades = false, jaListouTabelaEstados = false, jaListouTabelaCidades = false;
    Long id_SelecionadoNaTabela_pais = null, id_SelecionadoNaTabela_estado = null, id_SelecionadoNaTabela_cidade = null;

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
                    if (!status_Form.equals("cadastro_company")) {
                        Home h = new Home();
                        h.habilitarForm();
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
    public void configForm(int form) {

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
            } else {
                btnPais_.setText("Avançar");
            }

            if (entidadeUtilizada.equals("estado")) {
                btnEstado_.setText("Alterar");
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
            } else {
                btnVizinhanca_.setText("Finalizar");
            }
        }

        //configura os campus de acordo se o pais informado for o Brasil ou nao
        if (form == 1) {
            if (!nomePais.getText().equals("Brasil")) {
                combo_estados_.setVisible(false);
                lblNomeCombo_estados.setVisible(false);
            } else {
                txtNomeEstado_.setEnabled(false);
            }
        } else if (form == 2) {
            if (!nomePais.getText().equals("Brasil")) {
                combo_cidades_.setVisible(false);
                lblNomeCombo_cidades.setVisible(false);
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

    public void listarTabelaPaisesCadastrados(List<Country> countryCadastrados) {
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

    public void listarTabelaEstadosCadastrados(List<State> statesCadastrados) {

        DefaultTableModel dtma = (DefaultTableModel) TABELA_Estados.getModel();
        dtma.setNumRows(0);
        TABELA_Estados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_Estados.getColumnModel().getColumn(1).setPreferredWidth(140);

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

    public void listarTabelaCidadesCadastrados(List<City> citysCadastradas) {
        DefaultTableModel dtma = (DefaultTableModel) TABELA_cidade.getModel();
        dtma.setNumRows(0);
        TABELA_cidade.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_cidade.getColumnModel().getColumn(1).setPreferredWidth(140);

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

    public void corLinhaTabelaAnuidade(JTable tabela) {
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
        jLabel1 = new javax.swing.JLabel();
        nomePais = new javax.swing.JTextField();
        btnPais_ = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        TABELA_Pais = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtPesquisarPais = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        lblNomeCombo_cidades = new javax.swing.JLabel();
        combo_cidades_ = new javax.swing.JComboBox<>();
        txtNomeCidade_ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnCidade_ = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TABELA_cidade = new javax.swing.JTable();
        btn_TODAS_CIDADES_ = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeVizinhanca_ = new javax.swing.JTextField();
        btnVizinhanca_ = new javax.swing.JButton();
        lblVoltar_ = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setType(java.awt.Window.Type.UTILITY);

        jLabel1.setText("Nome:");

        nomePais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomePaisActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnPais_)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nomePais, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, 0)
                        .addComponent(txtPesquisarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel12))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomePais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPais_)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtPesquisarPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );

        FORM_GUIAS.addTab("País", jPanel1);

        jLabel2.setText("Nome:");

        txtNomeEstado_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeEstado_ActionPerformed(evt);
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
        combo_estados_.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                combo_estados_AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
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
        TABELA_Estados.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TABELA_EstadosAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TABELA_Estados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TABELA_EstadosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TABELA_EstadosMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(TABELA_Estados);
        if (TABELA_Estados.getColumnModel().getColumnCount() > 0) {
            TABELA_Estados.getColumnModel().getColumn(0).setResizable(false);
            TABELA_Estados.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel9.setText("Estados cadastrados");

        btn_TODOS_ESTADOS_.setText("Todos Estados");
        btn_TODOS_ESTADOS_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TODOS_ESTADOS_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(txtNomeEstado_, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblNomeCombo_estados)
                        .addGap(0, 0, 0)
                        .addComponent(combo_estados_, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_TODOS_ESTADOS_)
                        .addGap(22, 22, 22)
                        .addComponent(btnEstado_))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addComponent(txtNomeEstado_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblNomeCombo_estados))
                    .addComponent(combo_estados_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEstado_)
                    .addComponent(btn_TODOS_ESTADOS_))
                .addGap(42, 42, 42)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
        );

        FORM_GUIAS.addTab("Estado", jPanel2);

        lblNomeCombo_cidades.setText("Nome:");

        combo_cidades_.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));
        combo_cidades_.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_cidades_ItemStateChanged(evt);
            }
        });

        txtNomeCidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCidade_ActionPerformed(evt);
            }
        });

        jLabel5.setText("Nome:");

        btnCidade_.setText("jButton1");
        btnCidade_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCidade_ActionPerformed(evt);
            }
        });

        jLabel7.setText("Nome:");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar.png"))); // NOI18N

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TABELA_cidadeMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(TABELA_cidade);
        if (TABELA_cidade.getColumnModel().getColumnCount() > 0) {
            TABELA_cidade.getColumnModel().getColumn(0).setResizable(false);
            TABELA_cidade.getColumnModel().getColumn(1).setResizable(false);
        }

        btn_TODAS_CIDADES_.setText("Todas Cidades");
        btn_TODAS_CIDADES_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TODAS_CIDADES_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(btn_TODAS_CIDADES_)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCidade_))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNomeCidade_, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(lblNomeCombo_cidades)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(combo_cidades_, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(2, 2, 2)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNomeCidade_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeCombo_cidades)
                    .addComponent(combo_cidades_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCidade_)
                    .addComponent(btn_TODAS_CIDADES_))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        FORM_GUIAS.addTab("Cidade", jPanel3);

        jLabel3.setText("Nome:");

        btnVizinhanca_.setText("jButton1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVizinhanca_)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addComponent(txtNomeVizinhanca_, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeVizinhanca_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVizinhanca_)
                .addContainerGap(244, Short.MAX_VALUE))
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
            .addComponent(lblVoltar_, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(FORM_GUIAS, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void nomePaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomePaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomePaisActionPerformed

    private void txtNomeEstado_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeEstado_ActionPerformed

    }//GEN-LAST:event_txtNomeEstado_ActionPerformed

    private void txtNomeCidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCidade_ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCidade_ActionPerformed

    private void btnPais_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPais_ActionPerformed

        if (nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o país");
            nomePais.requestFocus();
        } else if (entidadeUtilizada.equals("pais")) {
            Country country = new Country();
            country.setId(id_SelecionadoNaTabela_pais);
            country.setName(nomePais.getText());

            //salvar pais
            CountryRepository BaseURL = retrofit.BaseURL().create(CountryRepository.class);
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

        } else if (nomePais.getText().equals("Brasil")) {
            if (!jaListouTabelaEstados) {
                StateRepository stateRepository = retrofit.BaseURL().create(StateRepository.class);
                Call<List<State>> callState = stateRepository.getStatesCadastrados(nomePais.getText());
                callState.enqueue(new Callback<List<State>>() {
                    @Override
                    public void onResponse(Call<List<State>> call, Response<List<State>> rspns) {
                        Localizacao l = new Localizacao();
                        if (rspns.isSuccessful()) {
                            List<State> state = rspns.body();
                            listarTabelaEstadosCadastrados(state);
                            jaListouTabelaEstados = true;
                            callState.cancel();
                            selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);                         
                        }
                    }

                    @Override
                    public void onFailure(Call<List<State>> call, Throwable thrwbl) {
                    }
                });
            } else {
                //se no botao da aba PAIS estiver (Avançar), ir para proxima aba
                if (btnPais_.getText().equals("Avançar")) {
                    //pega o numero da guia atual e acrescenta + 1 para ir para a proxima aba
                    selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                    combo_estados_.setVisible(true);
                    lblNomeCombo_estados.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_btnPais_ActionPerformed

    private void combo_estados_AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_combo_estados_AncestorAdded

    }//GEN-LAST:event_combo_estados_AncestorAdded

    private void lblVoltar_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVoltar_MouseClicked
        //verifica se não é primeira aba, que é representada por 0, para nao dar erro na subtracao
        if (this.FORM_GUIAS.getSelectedIndex() > 0) {
            //pega o numero da guia atual e diminui - 1 para voltar para a aba anterior
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);
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
        //se estiver cadastrando apenas o estado
        if (entidadeUtilizada.equals("estado")) {
            //verifica se o pais foi informado, porque o estado depende do pais no cadastro
            if (nomePais.getText().equals("") && id_SelecionadoNaTabela_pais == null) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o país deste estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                nomePais.requestFocus();

                //volta para a aba de pais, para o mesmo ser informado
                selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

            } else if (txtNomeEstado_.getText().equals("") && combo_estados_.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();
            } else {
                Country country = new Country();
                country.setId(id_SelecionadoNaTabela_pais);
                country.setName(nomePais.getText());

                State state = new State();
                state.setId(id_SelecionadoNaTabela_estado);
                state.setName(txtNomeEstado_.getText());
                state.setCountry(country);

                //salvar estado
                StateRepository BaseURL = retrofit.BaseURL().create(StateRepository.class);
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
            }
        } else {
            if (nomePais.getText().equals("") && id_SelecionadoNaTabela_pais == null) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o país deste estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                nomePais.requestFocus();

                //volta para a aba de pais, para o mesmo ser informado
                selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

            } else if (txtNomeEstado_.getText().equals("") && combo_estados_.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeEstado_.requestFocus();
                
            } else if (nomePais.getText().equals("Brasil")) {
                if (!jaListouTabelaCidades) {
                    CityRepository cityRepository = retrofit.BaseURL().create(CityRepository.class);
                    Call<List<City>> callState = cityRepository.getCitysCadastrados(txtNomeEstado_.getText());
                    callState.enqueue(new Callback<List<City>>() {
                        @Override
                        public void onResponse(Call<List<City>> call, Response<List<City>> rspns) {
                            Localizacao l = new Localizacao();
                            if (rspns.isSuccessful()) {
                                List<City> citys = rspns.body();
                                listarTabelaCidadesCadastrados(citys);
                                jaListouTabelaCidades = true;
                                callState.cancel();
                                selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                                combo_estados_.setVisible(true);
                                lblNomeCombo_estados.setVisible(true);
                                nomePais.setEnabled(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<City>> call, Throwable thrwbl) {
                        }
                    });
                } else {
                    //se no botao da aba PAIS estiver (Avançar), ir para proxima aba
                    if (btnPais_.getText().equals("Avançar")) {
                        //pega o numero da guia atual e acrescenta + 1 para ir para a proxima aba
                        selecionar_guia(FORM_GUIAS.getSelectedIndex() + 1);
                        combo_estados_.setVisible(true);
                        lblNomeCombo_estados.setVisible(true);
                    }
                }
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
            nomePais.setText(TABELA_Pais.getValueAt(TABELA_Pais.getSelectedRow(), 1).toString());
            id_SelecionadoNaTabela_pais = Long.parseLong(TABELA_Pais.getValueAt(TABELA_Pais.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_TABELA_PaisMouseClicked

    private void TABELA_EstadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_EstadosMouseEntered

    }//GEN-LAST:event_TABELA_EstadosMouseEntered

    private void TABELA_EstadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_EstadosMouseClicked
        //se estiver cadastrando um estado e clica em um estado ja cadastrado que esta na tabela, o nome desse pais nao ira para o campu de cadastramento
        if (!(status_Form.equals("cadastrar") && entidadeUtilizada.equals("estado"))) {
            String estado = TABELA_Estados.getValueAt(TABELA_Estados.getSelectedRow(), 1).toString();
            txtNomeEstado_.setText(estado);
            id_SelecionadoNaTabela_estado = Long.parseLong(TABELA_Estados.getValueAt(TABELA_Estados.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_TABELA_EstadosMouseClicked

    private void btn_TODOS_ESTADOS_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TODOS_ESTADOS_ActionPerformed
        if (nomePais.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o país deste estado", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            nomePais.requestFocus();

            //volta para a aba de pais, para o mesmo ser informado
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

        } else if (nomePais.getText().equals("Brasil")) {
            lblNomeCombo_estados.setVisible(true);
            combo_estados_.setVisible(true);
            if (!jaBuscoEstados) {
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
                            jaBuscoEstados = true;
                            call.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Estado_retrofit>> call, Throwable t) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }
    }//GEN-LAST:event_btn_TODOS_ESTADOS_ActionPerformed

    private void btnCidade_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCidade_ActionPerformed
        //se estiver cadastrando apenas o estado
        if (entidadeUtilizada.equals("cidade")) {
            //verifica se o pais foi informado, porque o estado depende do pais no cadastro
            if (txtNomeEstado_.getText().equals("")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado desta cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                nomePais.requestFocus();

                //volta para a aba de pais, para o mesmo ser informado
                selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

            } else if (txtNomeCidade_.getText().equals("") || combo_cidades_.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(Localizacao.this, "Informe a cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
                txtNomeCidade_.requestFocus();
            } else {
                Country country = new Country();
                country.setId(id_SelecionadoNaTabela_pais);
                country.setName(nomePais.getText());

                State state = new State();
                state.setId(id_SelecionadoNaTabela_estado);
                state.setName(txtNomeEstado_.getText());
                state.setCountry(country);

                City city = new City();
                city.setId(id_SelecionadoNaTabela_cidade);
                city.setName(txtNomeCidade_.getText());
                city.setState(state);

                //salvar cidade
                CityRepository BaseURL = retrofit.BaseURL().create(CityRepository.class);
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
            }
        }
    }//GEN-LAST:event_btnCidade_ActionPerformed

    private void TABELA_cidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_cidadeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TABELA_cidadeMouseClicked

    private void TABELA_cidadeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TABELA_cidadeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TABELA_cidadeMouseEntered

    private void TABELA_EstadosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TABELA_EstadosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TABELA_EstadosAncestorAdded

    private void btn_TODAS_CIDADES_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TODAS_CIDADES_ActionPerformed
        if (txtNomeEstado_.getText().equals("")) {
            JOptionPane.showMessageDialog(Localizacao.this, "Informe o estado desta cidade", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
            nomePais.requestFocus();

            //volta para a aba de pais, para o mesmo ser informado
            selecionar_guia(this.FORM_GUIAS.getSelectedIndex() - 1);

        } else if (nomePais.getText().equals("Brasil")) {
            lblNomeCombo_cidades.setVisible(true);
            combo_cidades_.setVisible(true);
            txtNomeCidade_.setEnabled(false);
            if (!jaBuscoCidades) {
                MunicipiosRepository municipiosRepository = retrofit.BaseURL_estado().create(MunicipiosRepository.class);
                
                String UF = stateController.separaSigla_State(txtNomeEstado_.getText());

                List<String> CIDADES = new ArrayList<>();
                Call<List<Cidade_retrofit>> call = municipiosRepository.getCidades(UF);

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
        }
    }//GEN-LAST:event_btn_TODAS_CIDADES_ActionPerformed

    private void combo_cidades_ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_cidades_ItemStateChanged
        //pega a cidade selecionada e exibe no campo de texto NOME que esta desabilitado
        txtNomeCidade_.setText(combo_cidades_.getSelectedItem().toString());
    }//GEN-LAST:event_combo_cidades_ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane FORM_GUIAS;
    private javax.swing.JTable TABELA_Estados;
    private javax.swing.JTable TABELA_Pais;
    private javax.swing.JTable TABELA_cidade;
    private javax.swing.JButton btnCidade_;
    private javax.swing.JButton btnEstado_;
    private javax.swing.JButton btnPais_;
    private javax.swing.JButton btnVizinhanca_;
    private javax.swing.JButton btn_TODAS_CIDADES_;
    private javax.swing.JButton btn_TODOS_ESTADOS_;
    private javax.swing.JComboBox<String> combo_cidades_;
    private javax.swing.JComboBox combo_estados_;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblNomeCombo_cidades;
    private javax.swing.JLabel lblNomeCombo_estados;
    private javax.swing.JLabel lblVoltar_;
    private javax.swing.JTextField nomePais;
    private javax.swing.JTextField txtNomeCidade_;
    private javax.swing.JTextField txtNomeEstado_;
    private javax.swing.JTextField txtNomeVizinhanca_;
    private javax.swing.JTextField txtPesquisarPais;
    // End of variables declaration//GEN-END:variables
}
