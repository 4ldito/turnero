package vista;

import controlador.AgendaController;
import controlador.EspecialidadController;
import controlador.HorarioController;
import controlador.UsuarioController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Especialidad;
import modelo.Horario;
import modelo.Usuario;

public final class AgendaForm extends javax.swing.JFrame {

    Agenda agenda;
    AgendaController agendaController = new AgendaController();
    ArrayList<Agenda> listaAgenda;

    Usuario medico;
    UsuarioController usuarioController = new UsuarioController();
    ArrayList<Usuario> listaMedico = usuarioController.cargarMedicos("");
    ArrayList<Usuario> listaAdministrativos;

    Especialidad especialidad;
    EspecialidadController especialidadController = new EspecialidadController();
    @SuppressWarnings("unchecked")
    ArrayList<Especialidad> listaEspecialidad = especialidadController.cargarEspecialidad();

    Horario horario;
    HorarioController horarioController = new HorarioController();
    ArrayList<Horario> listaHorario = new ArrayList<>();

    DefaultTableModel modelo;
    String[] encabezadoAgendas = {"Nombre", "Médico", "Especialidad"};
    String[] encabezadoHorarios = {"Nombre", "Hs. Inicio", "Hs. Fin", "Intervalo", "Días"};

    int idAgendaFocus;
    int idHorarioFocus;

    @SuppressWarnings("unchecked")
    public AgendaForm() {
        initComponents();
        reloadInfoAgendas(listaAgenda = agendaController.cargarAgendas("", -1, -1));
    }

    public void reloadTable(JTable table, String[] encabezado) { // carga el formato de la tabla con su encabezado

        modelo = new DefaultTableModel() {//Instancia de la clase DefaultTableModel para crear el modelo de la tabla.
            boolean[] canEdit = new boolean[]{//Se crea una instancia en la que se establecen los valores de las celdas para ser o no  modificables.
                false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {//Método para pasar por parpametros los índices de las columnas y filas.
                return canEdit[columnIndex];//Se le asigna al índice de la columna si se permite o no modificar las celdas de cada columna.
            }
        };

        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        //Añadimos los encabezados
        for (String encabezado1 : encabezado) {
            modelo.addColumn(encabezado1);
        }
        //Centramos los datos NO TA FUNCANDO WEY
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
        table.setModel(modelo);
    }

    public void reloadInfoAgendas(ArrayList<Agenda> listaAgendaN) {
        reloadTable(tblAgenda, encabezadoAgendas);
        Object[] fila = new Object[3];
        for (int i = 0; i < listaAgendaN.size(); i++) {
            agenda = listaAgendaN.get(i);
            fila[0] = agenda.getNombre();
            for (int j = 0; j < listaMedico.size(); j++) {
                medico = listaMedico.get(j);
                if (medico.getId() == agenda.getIdMedico()) {
                    String nombremedico = medico.getNombre() + " " + medico.getApellido();
                    fila[1] = nombremedico;
                }
            }
            for (int x = 0; x < listaEspecialidad.size(); x++) {
                especialidad = listaEspecialidad.get(x);
                if (especialidad.getId() == agenda.getIdEspecialidad()) {
                    fila[2] = especialidad.getNombre();
                }
            }
            modelo.addRow(fila);
        }
    }

    public void reloadInfoHorarios(ArrayList<Horario> listaHorarioN) {
        reloadTable(tblHorario, encabezadoHorarios);
        Object[] fila = new Object[5];
        ArrayList<String> dias = new ArrayList<>();
        for (int i = 0; i < listaHorarioN.size(); i++) {
            horario = listaHorarioN.get(i);

            int[] bitdias = {horario.isLunes(), horario.isMartes(), horario.isMiercoles(), horario.isJueves(), horario.isViernes(), horario.isSabado()};
            for (int j = 1; j <= 6; j++) {
                if (bitdias[j - 1] == 1) {
                    switch (j) {
                        case 1 ->
                            dias.add("L");
                        case 2 ->
                            dias.add("M");
                        case 3 ->
                            dias.add("X");
                        case 4 ->
                            dias.add("J");
                        case 5 ->
                            dias.add("V");
                        case 6 ->
                            dias.add("S");
                        default -> {
                        }
                    }
                }
            }

            fila[0] = horario.getNombre();
            fila[1] = horario.getHs_inicio();
            fila[2] = horario.getHs_fin();
            fila[3] = horario.getIntervalo();
            fila[4] = dias.toString();

            modelo.addRow(fila);
            dias.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public void listarMedicos(JComboBox combobox) {
        combobox.addItem("");
        for (int i = 0; i < listaMedico.size(); i++) {
            medico = listaMedico.get(i);
            String nombreMedico = medico.getNombre() + " " + medico.getApellido();
            combobox.addItem(nombreMedico);
        }
    }

    @SuppressWarnings("unchecked")
    public void listarEspecialidades(JComboBox combobox) {
        combobox.addItem("");
        for (int i = 0; i < listaEspecialidad.size(); i++) {
            especialidad = listaEspecialidad.get(i);
            String nespecialidad = especialidad.getNombre();
            combobox.addItem(nespecialidad);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAgregarAgenda = new javax.swing.JPanel();
        lblNuevaAgenda = new javax.swing.JLabel();
        lblMedico2 = new javax.swing.JLabel();
        tfNuevoNombreAgenda = new javax.swing.JTextField();
        lblNombre3 = new javax.swing.JLabel();
        cbListaMedicoNuevo = new javax.swing.JComboBox<>();
        lblEspecialidad = new javax.swing.JLabel();
        cbEspecialidadesNuevo = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        btGuardarAgenda = new javax.swing.JButton();
        lblAgregarHorario = new javax.swing.JLabel();
        lblNombreHorario = new javax.swing.JLabel();
        tfNombreHorario = new javax.swing.JTextField();
        lblHsInicio = new javax.swing.JLabel();
        lblHsFin = new javax.swing.JLabel();
        lblIntervalo = new javax.swing.JLabel();
        cbIntervalo = new javax.swing.JComboBox<>();
        cbLunes = new javax.swing.JCheckBox();
        cbMartes = new javax.swing.JCheckBox();
        cbMiercoles = new javax.swing.JCheckBox();
        cbJueves = new javax.swing.JCheckBox();
        cbViernes = new javax.swing.JCheckBox();
        cbSabado = new javax.swing.JCheckBox();
        btGuardarHorario = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        scplHorario = new javax.swing.JScrollPane();
        tblHorario = new javax.swing.JTable();
        btnEditarHorario = new javax.swing.JButton();
        btnEliminarHorario = new javax.swing.JButton();
        ffHsInicio = new javax.swing.JFormattedTextField();
        ffHsFin = new javax.swing.JFormattedTextField();
        btnVolver = new javax.swing.JButton();
        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlVistaAgendas = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        tfNombreAgenda = new javax.swing.JTextField();
        lblMedico = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        spTblAgenda = new javax.swing.JScrollPane();
        tblAgenda = new javax.swing.JTable();
        btnNueva = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jcListaMedicosBusq = new javax.swing.JComboBox<>();
        btnVolverAIncio = new javax.swing.JButton();

        pnlAgregarAgenda.setMaximumSize(new java.awt.Dimension(600, 600));
        pnlAgregarAgenda.setMinimumSize(new java.awt.Dimension(600, 450));
        pnlAgregarAgenda.setPreferredSize(new java.awt.Dimension(600, 600));

        lblNuevaAgenda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNuevaAgenda.setText("Nueva agenda:");

        lblMedico2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblMedico2.setText("Médico:");
        lblMedico2.setMaximumSize(new java.awt.Dimension(60, 25));
        lblMedico2.setMinimumSize(new java.awt.Dimension(60, 25));
        lblMedico2.setPreferredSize(new java.awt.Dimension(60, 25));

        tfNuevoNombreAgenda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfNuevoNombreAgenda.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombreAgenda.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombreAgenda.setPreferredSize(new java.awt.Dimension(200, 25));

        lblNombre3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNombre3.setText("Nombre:");
        lblNombre3.setMaximumSize(new java.awt.Dimension(60, 25));
        lblNombre3.setMinimumSize(new java.awt.Dimension(60, 25));
        lblNombre3.setPreferredSize(new java.awt.Dimension(60, 25));

        listarMedicos(cbListaMedicoNuevo);
        cbListaMedicoNuevo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbListaMedicoNuevo.setMaximumSize(new java.awt.Dimension(200, 25));
        cbListaMedicoNuevo.setMinimumSize(new java.awt.Dimension(200, 25));
        cbListaMedicoNuevo.setPreferredSize(new java.awt.Dimension(200, 25));
        cbListaMedicoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbListaMedicoNuevoActionPerformed(evt);
            }
        });

        lblEspecialidad.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblEspecialidad.setText("Especialidad:");
        lblEspecialidad.setMaximumSize(new java.awt.Dimension(60, 25));
        lblEspecialidad.setMinimumSize(new java.awt.Dimension(60, 25));
        lblEspecialidad.setPreferredSize(new java.awt.Dimension(60, 25));

        listarEspecialidades(cbEspecialidadesNuevo);
        cbEspecialidadesNuevo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbEspecialidadesNuevo.setMaximumSize(new java.awt.Dimension(200, 25));
        cbEspecialidadesNuevo.setMinimumSize(new java.awt.Dimension(200, 25));
        cbEspecialidadesNuevo.setPreferredSize(new java.awt.Dimension(200, 25));
        cbEspecialidadesNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEspecialidadesNuevoActionPerformed(evt);
            }
        });

        btGuardarAgenda.setText("Guardar");
        btGuardarAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarAgendaActionPerformed(evt);
            }
        });

        lblAgregarHorario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblAgregarHorario.setText("Agregar horario:");
        lblAgregarHorario.setMaximumSize(new java.awt.Dimension(100, 25));
        lblAgregarHorario.setMinimumSize(new java.awt.Dimension(100, 25));
        lblAgregarHorario.setPreferredSize(new java.awt.Dimension(100, 25));

        lblNombreHorario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNombreHorario.setText("Nombre:");
        lblNombreHorario.setMaximumSize(new java.awt.Dimension(54, 25));
        lblNombreHorario.setMinimumSize(new java.awt.Dimension(54, 25));
        lblNombreHorario.setPreferredSize(new java.awt.Dimension(54, 25));

        tfNombreHorario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfNombreHorario.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNombreHorario.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNombreHorario.setPreferredSize(new java.awt.Dimension(200, 25));

        lblHsInicio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblHsInicio.setText("Hs. inicio:");
        lblHsInicio.setMaximumSize(new java.awt.Dimension(57, 25));
        lblHsInicio.setMinimumSize(new java.awt.Dimension(57, 25));
        lblHsInicio.setPreferredSize(new java.awt.Dimension(57, 25));

        lblHsFin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblHsFin.setText("Hs. fin:");
        lblHsFin.setMaximumSize(new java.awt.Dimension(57, 25));
        lblHsFin.setMinimumSize(new java.awt.Dimension(57, 25));
        lblHsFin.setPreferredSize(new java.awt.Dimension(57, 25));

        lblIntervalo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblIntervalo.setText("Intervalo");
        lblIntervalo.setMaximumSize(new java.awt.Dimension(55, 25));
        lblIntervalo.setMinimumSize(new java.awt.Dimension(55, 25));
        lblIntervalo.setPreferredSize(new java.awt.Dimension(55, 25));

        cbIntervalo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60" }));
        cbIntervalo.setMaximumSize(new java.awt.Dimension(40, 25));
        cbIntervalo.setMinimumSize(new java.awt.Dimension(40, 25));
        cbIntervalo.setPreferredSize(new java.awt.Dimension(40, 25));

        cbLunes.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbLunes.setText("L");
        cbLunes.setMaximumSize(new java.awt.Dimension(35, 25));
        cbLunes.setMinimumSize(new java.awt.Dimension(35, 25));
        cbLunes.setPreferredSize(new java.awt.Dimension(35, 25));

        cbMartes.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbMartes.setText("M");

        cbMiercoles.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbMiercoles.setText("x");
        cbMiercoles.setMaximumSize(new java.awt.Dimension(35, 25));
        cbMiercoles.setMinimumSize(new java.awt.Dimension(35, 25));
        cbMiercoles.setPreferredSize(new java.awt.Dimension(35, 25));

        cbJueves.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbJueves.setText("J");
        cbJueves.setMaximumSize(new java.awt.Dimension(35, 25));
        cbJueves.setMinimumSize(new java.awt.Dimension(35, 25));
        cbJueves.setPreferredSize(new java.awt.Dimension(35, 25));

        cbViernes.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbViernes.setText("V");
        cbViernes.setMaximumSize(new java.awt.Dimension(35, 25));
        cbViernes.setMinimumSize(new java.awt.Dimension(35, 25));
        cbViernes.setPreferredSize(new java.awt.Dimension(35, 25));

        cbSabado.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbSabado.setText("S");
        cbSabado.setMaximumSize(new java.awt.Dimension(35, 25));
        cbSabado.setMinimumSize(new java.awt.Dimension(35, 25));
        cbSabado.setPreferredSize(new java.awt.Dimension(35, 25));

        btGuardarHorario.setText("Guardar");
        btGuardarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarHorarioActionPerformed(evt);
            }
        });

        tblHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scplHorario.setViewportView(tblHorario);
        if (tblHorario.getColumnModel().getColumnCount() > 0) {
            tblHorario.getColumnModel().getColumn(0).setResizable(false);
            tblHorario.getColumnModel().getColumn(1).setResizable(false);
            tblHorario.getColumnModel().getColumn(2).setResizable(false);
            tblHorario.getColumnModel().getColumn(3).setResizable(false);
        }

        btnEditarHorario.setText("Editar");
        btnEditarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarHorarioActionPerformed(evt);
            }
        });

        btnEliminarHorario.setText("Eliminar");
        btnEliminarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarHorarioActionPerformed(evt);
            }
        });

        try {
            ffHsInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ffHsInicio.setText("00:00  ");
        ffHsInicio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        ffHsInicio.setMaximumSize(new java.awt.Dimension(50, 25));
        ffHsInicio.setMinimumSize(new java.awt.Dimension(50, 25));
        ffHsInicio.setPreferredSize(new java.awt.Dimension(50, 25));

        try {
            ffHsFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ffHsFin.setText("00:00  ");
        ffHsFin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        ffHsFin.setMaximumSize(new java.awt.Dimension(50, 25));
        ffHsFin.setMinimumSize(new java.awt.Dimension(50, 25));
        ffHsFin.setPreferredSize(new java.awt.Dimension(50, 25));

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAgregarAgendaLayout = new javax.swing.GroupLayout(pnlAgregarAgenda);
        pnlAgregarAgenda.setLayout(pnlAgregarAgendaLayout);
        pnlAgregarAgendaLayout.setHorizontalGroup(
            pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarAgendaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btGuardarAgenda)
                        .addGap(30, 30, 30))
                    .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scplHorario)
                            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditarHorario)
                                .addGap(37, 37, 37)
                                .addComponent(btnEliminarHorario))
                            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNombreHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblHsInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                                .addComponent(ffHsInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(ffHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1))
                                            .addComponent(tfNombreHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(lblIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)
                                                .addComponent(cbIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(cbLunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbMartes)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbMiercoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbJueves, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbViernes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbSabado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNuevaAgenda)
                                            .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lblNombre3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(lblMedico2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblEspecialidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(15, 15, 15)
                                        .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfNuevoNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbListaMedicoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbEspecialidadesNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 35, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarAgendaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btGuardarHorario)
                .addGap(32, 32, 32))
        );
        pnlAgregarAgendaLayout.setVerticalGroup(
            pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgregarAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNuevaAgenda)
                .addGap(18, 18, 18)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNuevoNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedico2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbListaMedicoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEspecialidadesNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btGuardarAgenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNombreHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMartes)
                    .addComponent(cbMiercoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbJueves, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbViernes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSabado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHsInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ffHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ffHsInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(btGuardarHorario)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scplHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAgregarAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarHorario)
                    .addComponent(btnEliminarHorario)
                    .addComponent(btnVolver))
                .addGap(18, 18, 18))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 550));
        setName("Agendas"); // NOI18N

        pnlTitulo.setMaximumSize(new java.awt.Dimension(600, 50));
        pnlTitulo.setMinimumSize(new java.awt.Dimension(600, 50));
        pnlTitulo.setPreferredSize(new java.awt.Dimension(600, 50));

        lblTitulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTitulo.setText("Agendas");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addContainerGap(977, Short.MAX_VALUE))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(lblTitulo))
        );

        getContentPane().add(pnlTitulo, java.awt.BorderLayout.PAGE_START);

        pnlVistaAgendas.setMaximumSize(new java.awt.Dimension(600, 450));
        pnlVistaAgendas.setMinimumSize(new java.awt.Dimension(600, 450));

        lblNombre.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        lblNombre.setMaximumSize(new java.awt.Dimension(60, 25));
        lblNombre.setMinimumSize(new java.awt.Dimension(60, 25));
        lblNombre.setPreferredSize(new java.awt.Dimension(60, 25));

        tfNombreAgenda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfNombreAgenda.setMaximumSize(new java.awt.Dimension(150, 25));
        tfNombreAgenda.setMinimumSize(new java.awt.Dimension(150, 25));
        tfNombreAgenda.setPreferredSize(new java.awt.Dimension(150, 25));

        lblMedico.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblMedico.setText("Médico:");
        lblMedico.setMaximumSize(new java.awt.Dimension(150, 25));
        lblMedico.setMinimumSize(new java.awt.Dimension(150, 25));
        lblMedico.setPreferredSize(new java.awt.Dimension(150, 25));

        btnBuscar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblAgenda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblAgenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAgendaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAgendaMousePressed(evt);
            }
        });
        spTblAgenda.setViewportView(tblAgenda);
        if (tblAgenda.getColumnModel().getColumnCount() > 0) {
            tblAgenda.getColumnModel().getColumn(0).setResizable(false);
            tblAgenda.getColumnModel().getColumn(1).setResizable(false);
            tblAgenda.getColumnModel().getColumn(2).setResizable(false);
            tblAgenda.getColumnModel().getColumn(3).setResizable(false);
        }

        btnNueva.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnNueva.setText("Nueva");
        btnNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaActionPerformed(evt);
            }
        });

        btnEditar.setEnabled(false);
        btnEditar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setEnabled(false);
        btnEliminar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        listarMedicos(jcListaMedicosBusq);
        jcListaMedicosBusq.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jcListaMedicosBusq.setMaximumSize(new java.awt.Dimension(150, 25));
        jcListaMedicosBusq.setMinimumSize(new java.awt.Dimension(150, 25));
        jcListaMedicosBusq.setPreferredSize(new java.awt.Dimension(150, 25));
        jcListaMedicosBusq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcListaMedicosBusqActionPerformed(evt);
            }
        });

        btnVolverAIncio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverAIncio.setText("Volver al inicio");
        btnVolverAIncio.setToolTipText("Volver a inicio");
        btnVolverAIncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverAIncioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlVistaAgendasLayout = new javax.swing.GroupLayout(pnlVistaAgendas);
        pnlVistaAgendas.setLayout(pnlVistaAgendasLayout);
        pnlVistaAgendasLayout.setHorizontalGroup(
            pnlVistaAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVistaAgendasLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlVistaAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTblAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
                    .addGroup(pnlVistaAgendasLayout.createSequentialGroup()
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcListaMedicosBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlVistaAgendasLayout.createSequentialGroup()
                        .addComponent(btnVolverAIncio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNueva)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)))
                .addGap(30, 30, 30))
        );
        pnlVistaAgendasLayout.setVerticalGroup(
            pnlVistaAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVistaAgendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVistaAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcListaMedicosBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(37, 37, 37)
                .addComponent(spTblAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlVistaAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNueva)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVolverAIncio))
                .addContainerGap())
        );

        getContentPane().add(pnlVistaAgendas, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaActionPerformed
        //btnVolver.setVisible(true);
        lblNuevaAgenda.setText("Agregar agenda:");
        AgendaForm.this.remove(pnlVistaAgendas);
        reBuildPnlAgregarAgenda();
        AgendaForm.this.add(pnlAgregarAgenda);
        AgendaForm.this.setSize(600, 700);
        reloadTable(tblHorario, encabezadoHorarios);
        habilitarABMHorarios(false);
        idAgendaFocus = -1;
    }//GEN-LAST:event_btnNuevaActionPerformed

    private void jcListaMedicosBusqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcListaMedicosBusqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcListaMedicosBusqActionPerformed

    @SuppressWarnings("unchecked")
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tblAgenda.getSelectedRow() >= 0) {
            lblNuevaAgenda.setText("Editar agenda:");
            AgendaForm.this.remove(pnlVistaAgendas);
            reBuildPnlAgregarAgenda();
            AgendaForm.this.add(pnlAgregarAgenda);
            AgendaForm.this.setSize(600, 700);
            habilitarABMHorarios(true);
            agenda = listaAgenda.get(tblAgenda.getSelectedRow());
            idAgendaFocus = agenda.getId();
            listaHorario.clear();
            listaHorario = horarioController.cargarHorario(idAgendaFocus);
            reloadInfoHorarios(listaHorario);
            medico = usuarioController.buscarMedico(agenda.getIdMedico(), listaMedico);
            String nombreMedico = medico.getNombre() + " " + medico.getApellido();
            especialidad = especialidadController.buscarEspecialidad(agenda.getIdEspecialidad());
            tfNuevoNombreAgenda.setText(agenda.getNombre());
            cbListaMedicoNuevo.setSelectedItem(nombreMedico);
            cbEspecialidadesNuevo.setSelectedItem(especialidad.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una agenda.", "Agendas", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    @SuppressWarnings("unchecked")
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        listaAgenda.clear();
        String inputNombreAgenda = tfNombreAgenda.getText();
        int medicoid;
        if (jcListaMedicosBusq.getSelectedIndex() == 0) {
            medicoid = -1;
        } else {
            medico = listaMedico.get(jcListaMedicosBusq.getSelectedIndex() - 1);//Resto uno porque el primer item del jc es ""
            medicoid = medico.getId();
        }
        listaAgenda = agendaController.cargarAgendas(inputNombreAgenda, medicoid, -1);
        reloadInfoAgendas(listaAgenda);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblAgendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAgendaMouseClicked
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblAgendaMouseClicked

    private void tblAgendaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAgendaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblAgendaMousePressed

    private void cbEspecialidadesNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEspecialidadesNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEspecialidadesNuevoActionPerformed

    private void btGuardarAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarAgendaActionPerformed
        String inputNombreAgenda = tfNuevoNombreAgenda.getText();
        int idmedico = cbListaMedicoNuevo.getSelectedIndex() - 1; //id en listaMedico
        int idespecialidad = cbEspecialidadesNuevo.getSelectedIndex() - 1; //id en listaEspecialidad
        if (verificarCamposNuevaAgenda()) {
            medico = listaMedico.get(idmedico);//Resto uno porque el primer item del jc es ""
            especialidad = listaEspecialidad.get(idespecialidad);//Resto uno porque el primer item del jc es ""
            if (verificarDuplicidadAgenda(inputNombreAgenda, medico.getId(), especialidad.getId())) {
                int res;
                if (idAgendaFocus > 0) {
                    res = AgendaController.editarAgenda(idAgendaFocus, inputNombreAgenda, especialidad.getId(), medico.getId());
                } else {
                    String[] datos = {inputNombreAgenda, String.valueOf(especialidad.getId()), String.valueOf(medico.getId())};
                    res = AgendaController.agregarAgenda(datos);
                }
                if (res == 1) {
                    JOptionPane.showMessageDialog(null, "Se agregó correctamente la agenda.", "AGREGAR", JOptionPane.INFORMATION_MESSAGE);
                    idAgendaFocus = AgendaController.buscarIdAgenda(inputNombreAgenda, medico.getId(), especialidad.getId());
                    reBuildPnlVistaAgendas();
                    idHorarioFocus = -1;
                    habilitarABMHorarios(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Agenda ya existente.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btGuardarAgendaActionPerformed

    public boolean verificarCamposNuevaAgenda() {
        return (!"".equals(tfNuevoNombreAgenda.getText()))
                && (cbListaMedicoNuevo.getSelectedIndex() != 0)
                && (cbEspecialidadesNuevo.getSelectedIndex() != 0);
    }

    public boolean verificarCamposNuevoHorario() {
        String nombre = tfNombreHorario.getText();
        if (!nombre.equals("")) {
            return !(!cbLunes.isSelected() && !cbMartes.isSelected() && !cbMiercoles.isSelected() && !cbJueves.isSelected()
                    && !cbViernes.isSelected() && !cbSabado.isSelected());
        } else {
            return false;
        }
    }//Revisar para eficar 

    public boolean verificarDuplicidadAgenda(String nombre, int idmedico, int idespecialidad) {
        return AgendaController.buscarIdAgenda(nombre, idmedico, idespecialidad) == -1;
    } //Devuelve true si no existe ninguna agenda identica 

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (tblAgenda.getSelectedRow() >= 0) {
            agenda = listaAgenda.get(tblAgenda.getSelectedRow());
            int rta = JOptionPane.showConfirmDialog(this, "¿Desea borrar esta agenda?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (rta == JOptionPane.YES_OPTION) {
                borrarAgenda(agenda.getId(), agenda);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una agenda.", "Agendas", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    @SuppressWarnings("unchecked")
    private void btGuardarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarHorarioActionPerformed
        if (verificarCamposNuevoHorario()) {
            String idagenda = String.valueOf(idAgendaFocus);
            String inputNombreHorario = tfNombreHorario.getText();
            String hsInicio = ffHsInicio.getText();
            String hsFin = ffHsFin.getText();
            String intervalo = cbIntervalo.getItemAt(cbIntervalo.getSelectedIndex());
            List<JCheckBox> diasCb = Arrays.asList(cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado);
            String[] dias = new String[6];
            for (int i = 0; i < diasCb.size(); i++) {
                if (diasCb.get(i).isSelected()) {
                    dias[i] = "1";
                } else {
                    dias[i] = "0";
                }
            }
            if (validarNuevoHorario(hsInicio, hsFin, intervalo)) {
                String[] datos = {idagenda, inputNombreHorario, hsInicio, hsFin, intervalo, dias[0], dias[1], dias[2], dias[3], dias[4], dias[5]};
                if (idHorarioFocus >= 0) {
                    horarioController.editarHorario(idHorarioFocus, datos);
                    JOptionPane.showMessageDialog(null, "Se editó correctamente el horario.", "AGREGAR", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    horarioController.agregarHorario(datos);
                    JOptionPane.showMessageDialog(null, "Se agregó correctamente el horario.", "AGREGAR", JOptionPane.INFORMATION_MESSAGE);
                }
                listaHorario.clear();
                listaHorario = horarioController.cargarHorario(idAgendaFocus);
                reloadInfoHorarios(listaHorario);
                reBuildPnlHorarios();
            }
            idHorarioFocus = -1;
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos del horario (Elegir al menos un día).", "AGREGAR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btGuardarHorarioActionPerformed

    private void btnEditarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarHorarioActionPerformed
        horario = listaHorario.get(tblHorario.getSelectedRow());
        idHorarioFocus = horario.getId();
        btnVolver.setVisible(true);
        mostrarHorario(horario);
    }//GEN-LAST:event_btnEditarHorarioActionPerformed

    @SuppressWarnings("unchecked")
    private void btnEliminarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarHorarioActionPerformed
        if (tblHorario.getSelectedRow() >= 0) {
            horario = listaHorario.get(tblHorario.getSelectedRow());
            int rta = JOptionPane.showConfirmDialog(this, "¿Desea borrar este horario?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (rta == JOptionPane.YES_OPTION) {
                horarioController.borrarHorario(horario.getId());
                listaHorario.clear();
                listaHorario = horarioController.cargarHorario(horario.getId_agenda());
                reloadInfoHorarios(listaHorario);
                reBuildPnlHorarios();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un horario.", "Horario", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarHorarioActionPerformed

    private void cbListaMedicoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbListaMedicoNuevoActionPerformed
        if (cbListaMedicoNuevo.getSelectedIndex() > 0) {
            medico = listaMedico.get(cbListaMedicoNuevo.getSelectedIndex() - 1);//Resto uno porque el primer item del jc es ""
            especialidad = especialidadController.buscarEspecialidad(medico.getId_especialidad());
            cbEspecialidadesNuevo.setSelectedItem(especialidad.getNombre());
        }
    }//GEN-LAST:event_cbListaMedicoNuevoActionPerformed

    private void btnVolverAIncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverAIncioActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
        idAgendaFocus = -1;
        idHorarioFocus = -1;
    }//GEN-LAST:event_btnVolverAIncioActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
//        btnVolver.setVisible(false);
        AgendaForm.this.remove(pnlAgregarAgenda);
        reBuildPnlVistaAgendas();
        AgendaForm.this.add(pnlVistaAgendas);
        AgendaForm.this.setSize(600, 550);
        idAgendaFocus = -1;
        idHorarioFocus = -1;
    }//GEN-LAST:event_btnVolverActionPerformed

    public void mostrarHorario(Horario horario) {
        idHorarioFocus = horario.getId();
        reBuildPnlHorarios();
        tfNombreHorario.setText(horario.getNombre());
        ffHsInicio.setText(horario.getHs_inicio());
        ffHsFin.setText(horario.getHs_fin());
        cbIntervalo.setSelectedItem(String.valueOf(horario.getIntervalo()));
        int[] bitdias = {horario.isLunes(), horario.isMartes(), horario.isMiercoles(), horario.isJueves(), horario.isViernes(), horario.isSabado()};
        for (int j = 1; j <= 6; j++) {
            if (bitdias[j - 1] == 1) {
                switch (j) {
                    case 1 ->
                        cbLunes.setSelected(true);
                    case 2 ->
                        cbMartes.setSelected(true);
                    case 3 ->
                        cbMiercoles.setSelected(true);
                    case 4 ->
                        cbJueves.setSelected(true);
                    case 5 ->
                        cbViernes.setSelected(true);
                    case 6 ->
                        cbSabado.setSelected(true);
                    default -> {
                    }
                }
            }
        }
    }

    public void borrarAgenda(int id, Agenda agenda) {
        boolean res = AgendaController.borrarAgenda(id);
        if (res == true) {
            reBuildPnlVistaAgendas();
            JOptionPane.showMessageDialog(null, agenda.getNombre() + " borrada correctamente.", "Agendas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "Agendas", JOptionPane.ERROR_MESSAGE);

        }
    }

    public boolean validarNuevoHorario(String horaInicio, String horaFin, String intervalo) {
        int difHoras = restarHoras(horaInicio, horaFin);
        int interv = Integer.parseInt(intervalo);
        if (validarHora(horaInicio) && validarHora(horaFin)) {
            if (difHoras > 0) {
                if ((difHoras % interv) == 0) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "La diferencia entre los horarios inicio y fin deben ser divisibles por el intervalo", "Horarios", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "La hora inicio debe ser menor a hora fin", "Horarios", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar horarios válidos", "Horarios", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean validarHora(String hora) {
        char[] a = hora.toCharArray();
        String[] c = hora.split(":");
        return !((a[0] == ' ') || (a[1] == ' ') || (a[2] == ' ') || (a[3] == ' ') || (a[4] == ' ')
                || (getInteger(c[0]) > 24) || (getInteger(c[1]) > 59));
    }

    public static int restarHoras(String horaMenor, String HoraMayor) {
        String[] horaMen = horaMenor.split(":");
        String[] horaMay = HoraMayor.split(":");
        if (getInteger(horaMay[0]) >= getInteger(horaMen[0])) {
            int restoHoras = (getInteger(horaMay[0]) - getInteger(horaMen[0]));
            int restoMinutos = (getInteger(horaMay[1]) - getInteger(horaMen[1]));
            return (restoHoras * 60) + restoMinutos;
        } else {
            return -1;
        }
    }//Devuelve la diferencia entre las horas en minutos, pasar como argumento primero la hora menor y luego la hora mayor, de lo contrario devuelve -1

    public static int getInteger(String valor) {
        int integer = Integer.parseInt(valor);
        return integer;
    }

    public void habilitarABMHorarios(boolean bolean) {
        tfNombreHorario.setEnabled(bolean);
        cbLunes.setEnabled(bolean);
        cbMartes.setEnabled(bolean);
        cbMiercoles.setEnabled(bolean);
        cbJueves.setEnabled(bolean);
        cbViernes.setEnabled(bolean);
        cbSabado.setEnabled(bolean);
        ffHsFin.setEnabled(bolean);
        ffHsInicio.setEnabled(bolean);
        cbIntervalo.setEnabled(bolean);
        btGuardarHorario.setEnabled(bolean);
        tblHorario.setEnabled(bolean);
    }

    //Revisar metodos reBuild
    @SuppressWarnings("unchecked")
    public void reBuildPnlVistaAgendas() {
        tfNombreAgenda.setText("");
        jcListaMedicosBusq.setSelectedIndex(0);
        listaAgenda.clear();
        listaAgenda = agendaController.cargarAgendas("", -1, -1);
        reloadInfoAgendas(listaAgenda);
    }

    public void reBuildPnlAgregarAgenda() {
        tfNuevoNombreAgenda.setText("");
        cbListaMedicoNuevo.setSelectedIndex(0);
        cbEspecialidadesNuevo.setSelectedIndex(0);
        reBuildPnlHorarios();
    }

    public void reBuildPnlHorarios() {
        tfNombreHorario.setText("");
        cbLunes.setSelected(false);
        cbMartes.setSelected(false);
        cbMiercoles.setSelected(false);
        cbJueves.setSelected(false);
        cbViernes.setSelected(false);
        cbSabado.setSelected(false);
        ffHsInicio.setText("00:00");
        ffHsFin.setText("00:00");
        cbIntervalo.setSelectedIndex(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGuardarAgenda;
    private javax.swing.JButton btGuardarHorario;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarHorario;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarHorario;
    private javax.swing.JButton btnNueva;
    private javax.swing.JButton btnVolver;
    private javax.swing.JButton btnVolverAIncio;
    private javax.swing.JComboBox<String> cbEspecialidadesNuevo;
    private javax.swing.JComboBox<String> cbIntervalo;
    private javax.swing.JCheckBox cbJueves;
    private javax.swing.JComboBox<String> cbListaMedicoNuevo;
    private javax.swing.JCheckBox cbLunes;
    private javax.swing.JCheckBox cbMartes;
    private javax.swing.JCheckBox cbMiercoles;
    private javax.swing.JCheckBox cbSabado;
    private javax.swing.JCheckBox cbViernes;
    private javax.swing.JFormattedTextField ffHsFin;
    private javax.swing.JFormattedTextField ffHsInicio;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> jcListaMedicosBusq;
    private javax.swing.JLabel lblAgregarHorario;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblHsFin;
    private javax.swing.JLabel lblHsInicio;
    private javax.swing.JLabel lblIntervalo;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblMedico2;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre3;
    private javax.swing.JLabel lblNombreHorario;
    private javax.swing.JLabel lblNuevaAgenda;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAgregarAgenda;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JPanel pnlVistaAgendas;
    private javax.swing.JScrollPane scplHorario;
    private javax.swing.JScrollPane spTblAgenda;
    private javax.swing.JTable tblAgenda;
    private javax.swing.JTable tblHorario;
    private javax.swing.JTextField tfNombreAgenda;
    private javax.swing.JTextField tfNombreHorario;
    private javax.swing.JTextField tfNuevoNombreAgenda;
    // End of variables declaration//GEN-END:variables
}
