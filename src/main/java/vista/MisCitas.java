package vista;

import controlador.AgendaController;
import controlador.HorarioController;
import controlador.PacienteController;
import controlador.TurnoController;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.EstadoTurno;
import modelo.Horario;
import modelo.Paciente;
import modelo.Turno;

public class MisCitas extends javax.swing.JFrame {

    Paciente paciente;
    PacienteController pacienteController = new PacienteController();
    ArrayList<Paciente> listaPaciente = new ArrayList<>();

    Agenda agenda;
    AgendaController agendaController = new AgendaController();

    Horario horario;
    HorarioController horarioController = new HorarioController();
    ArrayList<Horario> listaHorario = new ArrayList();

    Turno turno;
    TurnoController turnoController = new TurnoController();
    ArrayList<Turno> listaTurno = new ArrayList<>();

    EstadoTurno estadoTurno;
    ArrayList<EstadoTurno> listaEstados = turnoController.cargarEstados();

    LocalDate fechaActual = LocalDate.now();
    int idPacienteFocus;

    DefaultTableModel modelo;
    String[] encabezadoPacientes = {"DNI", "Nombre", "Apellido", "Dirección", "Celular", "Género"};
    String[] encabezadoTurnos = {"Hs. inicio", "Hs. fin", "Nombre", "Apellido", "DNI", "Estado"};

    public MisCitas(Agenda agenda) {
        this.agenda = agenda;
        listaTurno = turnoController.cargarTurnos(agenda.getId(), -1, -1, -1, fechaActual.toString(), -1, null);
        initComponents();
        pnlContenido.add(pnlMisTurnos);
        pnlMisTurnos.setSize(1043, 500);
        reloadInfoTurnos(listaTurno);
    }

    public void reloadInfoPacientes(ArrayList<Paciente> listaPacienteN) {
        reloadTable(tblPaciente, encabezadoPacientes);
        Object[] fila = new Object[11];

        for (int i = 0; i < listaPacienteN.size(); i++) {
            paciente = listaPacienteN.get(i);
            fila[0] = paciente.getDni();
            fila[1] = paciente.getNombre();
            fila[2] = paciente.getApellido();
            fila[3] = paciente.getDireccion();
            fila[4] = paciente.getCelular();
            fila[5] = paciente.getGenero();

            modelo.addRow(fila);

        }
        tblPaciente.setModel(modelo);
    }

    private void reloadInfoTurnos(ArrayList<Turno> listaTurnoN) {
        reloadTable(tblTurnos, encabezadoTurnos);

        Object[] fila = new Object[6];

        for (int i = 0; i < listaTurnoN.size(); i++) {
            turno = listaTurnoN.get(i);
            paciente = pacienteController.traerPaciente(turno.getId_paciente());
            fila[0] = turno.getHoraInicio();
            fila[1] = turno.getHoraFin();
            fila[2] = paciente.getNombre();
            fila[3] = paciente.getApellido();
            fila[4] = paciente.getDni();
            fila[5] = turnoController.traerEstado(turno.getId_estado()).getNombre();

            modelo.addRow(fila);
        }

        tblTurnos.setModel(modelo);
    }

    public void reloadTable(JTable table, String[] encabezado) { // carga el formato de la tabla con su encabezado

        modelo = new DefaultTableModel() {//Instancia de la clase DefaultTableModel para crear el modelo de la tabla.
            boolean[] canEdit = new boolean[]{//Se crea una instancia en la que se establecen los valores de las celdas para ser o no  modificables.
                false, false, false, false, false, false
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

    public void limpiarDatosBarraPaciente() {
        lblNombrePaciente.setText("");
        lblApellidoPaciente.setText("");
        lblDniPaciente.setText("");
        lblDireccionPaciente.setText("");
        lblCelularPaciente.setText("");
        lblGeneroPaciente.setText("");
        lblImagePaciente.setEnabled(false);
    }

    public void mostrarDatosBarraPaciente(Paciente paciente) {
        lblNombrePaciente.setText(paciente.getNombre());
        lblApellidoPaciente.setText(paciente.getApellido());
        lblDniPaciente.setText(String.valueOf(paciente.getDni()));
        lblDireccionPaciente.setText(paciente.getDireccion());
        lblCelularPaciente.setText(paciente.getCelular());
        lblGeneroPaciente.setText(paciente.getGenero());
        lblImagePaciente.setEnabled(true);
        if (null != paciente.getGenero()) {
            switch (paciente.getGenero()) {
                case "Masculino" ->
                    lblImagePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png")));
                case "Femenino" ->
                    lblImagePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mujer.png")));
                case "Otro" ->
                    lblImagePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/otro.png")));
                default -> {
                }
            }
        }
    }

    public void cambiarEstadoTurno(int idEstadoTurno) {
        if (fechaActual == LocalDate.now()) {
            if (tblTurnos.getSelectedRow() >= 0) {
                int rta = JOptionPane.showConfirmDialog(this, "¿Desea cambiar el estado del turno?", "Turno", JOptionPane.YES_NO_OPTION);
                if (rta == JOptionPane.YES_OPTION) {
                    turno = listaTurno.get(tblTurnos.getSelectedRow());
                    TurnoController.editarEstadoTurno(turno.getId(), idEstadoTurno);
                    listaTurno.clear();
                    listaTurno = turnoController.cargarTurnos(agenda.getId(), -1, -1, -1, fechaActual.toString(), -1, null);
                    reloadInfoTurnos(listaTurno);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un turno", "Turno", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No puede cambiar el estado de un turno anterior o posterior al dia de hoy", "Turno", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlListaPacientes = new javax.swing.JPanel();
        spTblPaciente = new javax.swing.JScrollPane();
        tblPaciente = new javax.swing.JTable();
        btnVolverMenu = new javax.swing.JButton();
        btnVolverMisTurnos = new javax.swing.JButton();
        pnlMisTurnos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTurnos = new javax.swing.JTable();
        btnTurnoAtendido = new javax.swing.JButton();
        btnTurnoCancelado = new javax.swing.JButton();
        lblMisTurnos = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        dcFechaDeseada = new com.toedter.calendar.JDateChooser();
        btnBuscarTurnos = new javax.swing.JButton();
        btnVolverMenu1 = new javax.swing.JButton();
        pnlDatosPaciente = new javax.swing.JPanel();
        lblImagePaciente = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblNombrePaciente = new javax.swing.JLabel();
        lblApellidoPaciente = new javax.swing.JLabel();
        lblDireccionPaciente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblDNI = new javax.swing.JLabel();
        lblDniPaciente = new javax.swing.JLabel();
        lblGeneroPaciente = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblCelularPaciente = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblDniBusq = new javax.swing.JLabel();
        tfDniBusq = new javax.swing.JTextField();
        btnBuscarPacient = new javax.swing.JButton();
        btnCitaUrgente = new javax.swing.JButton();
        pnlContenido = new javax.swing.JPanel();

        pnlListaPacientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlListaPacientes.setMaximumSize(new java.awt.Dimension(1043, 469));
        pnlListaPacientes.setMinimumSize(new java.awt.Dimension(1043, 469));

        tblPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblPacienteMouseEntered(evt);
            }
        });
        spTblPaciente.setViewportView(tblPaciente);

        btnVolverMenu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverMenu.setText("Volver al Inicio");
        btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenuActionPerformed(evt);
            }
        });

        btnVolverMisTurnos.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverMisTurnos.setText("Mis Turnos");
        btnVolverMisTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMisTurnosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListaPacientesLayout = new javax.swing.GroupLayout(pnlListaPacientes);
        pnlListaPacientes.setLayout(pnlListaPacientesLayout);
        pnlListaPacientesLayout.setHorizontalGroup(
            pnlListaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListaPacientesLayout.createSequentialGroup()
                        .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolverMisTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlListaPacientesLayout.setVerticalGroup(
            pnlListaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlListaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolverMisTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlMisTurnos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlMisTurnos.setMaximumSize(new java.awt.Dimension(1043, 469));
        pnlMisTurnos.setMinimumSize(new java.awt.Dimension(1043, 469));
        pnlMisTurnos.setPreferredSize(new java.awt.Dimension(1043, 469));

        tblTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTurnos);

        btnTurnoAtendido.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnTurnoAtendido.setText("Atendido");
        btnTurnoAtendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnoAtendidoActionPerformed(evt);
            }
        });

        btnTurnoCancelado.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnTurnoCancelado.setText("Cancelar");
        btnTurnoCancelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnoCanceladoActionPerformed(evt);
            }
        });

        lblMisTurnos.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblMisTurnos.setText("Mis Turnos");

        lblFecha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblFecha.setText("Fecha:");
        lblFecha.setMaximumSize(new java.awt.Dimension(46, 25));
        lblFecha.setMinimumSize(new java.awt.Dimension(46, 25));
        lblFecha.setPreferredSize(new java.awt.Dimension(46, 25));

        dcFechaDeseada.setDateFormatString("dd/MM/yyyy");
        dcFechaDeseada.setMinimumSize(new java.awt.Dimension(150, 25));
        dcFechaDeseada.setPreferredSize(new java.awt.Dimension(150, 25));

        btnBuscarTurnos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBuscarTurnos.setText("Buscar");
        btnBuscarTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTurnosActionPerformed(evt);
            }
        });

        btnVolverMenu1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverMenu1.setText("Volver al Inicio");
        btnVolverMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenu1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMisTurnosLayout = new javax.swing.GroupLayout(pnlMisTurnos);
        pnlMisTurnos.setLayout(pnlMisTurnosLayout);
        pnlMisTurnosLayout.setHorizontalGroup(
            pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMisTurnosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMisTurnosLayout.createSequentialGroup()
                        .addComponent(btnVolverMenu1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTurnoCancelado)
                        .addGap(18, 18, 18)
                        .addComponent(btnTurnoAtendido))
                    .addGroup(pnlMisTurnosLayout.createSequentialGroup()
                        .addGroup(pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMisTurnos)
                            .addGroup(pnlMisTurnosLayout.createSequentialGroup()
                                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dcFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarTurnos)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMisTurnosLayout.setVerticalGroup(
            pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMisTurnosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMisTurnos)
                .addGap(18, 18, 18)
                .addGroup(pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarTurnos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(pnlMisTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTurnoAtendido)
                    .addComponent(btnTurnoCancelado)
                    .addComponent(btnVolverMenu1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlDatosPaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDatosPaciente.setMaximumSize(new java.awt.Dimension(1043, 137));
        pnlDatosPaciente.setMinimumSize(new java.awt.Dimension(1043, 137));

        lblImagePaciente.setEnabled(false);
        lblImagePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N

        lblNombre.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombre.setText("Nombre:");
        lblNombre.setMaximumSize(new java.awt.Dimension(70, 20));
        lblNombre.setMinimumSize(new java.awt.Dimension(70, 20));
        lblNombre.setPreferredSize(new java.awt.Dimension(70, 20));

        lblApellido.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblApellido.setText("Apellido:");
        lblApellido.setMaximumSize(new java.awt.Dimension(70, 20));
        lblApellido.setMinimumSize(new java.awt.Dimension(70, 20));
        lblApellido.setPreferredSize(new java.awt.Dimension(70, 20));

        lblNombrePaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNombrePaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblNombrePaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblNombrePaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblApellidoPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblApellidoPaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblApellidoPaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblApellidoPaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblDireccionPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblDireccionPaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblDireccionPaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblDireccionPaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblDireccion.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDireccion.setText("Dirección:");
        lblDireccion.setMaximumSize(new java.awt.Dimension(70, 20));
        lblDireccion.setMinimumSize(new java.awt.Dimension(70, 20));
        lblDireccion.setPreferredSize(new java.awt.Dimension(70, 20));

        lblDNI.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDNI.setText("DNI:");
        lblDNI.setMaximumSize(new java.awt.Dimension(70, 20));
        lblDNI.setMinimumSize(new java.awt.Dimension(70, 20));
        lblDNI.setPreferredSize(new java.awt.Dimension(70, 20));

        lblDniPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblDniPaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblDniPaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblDniPaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblGeneroPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblGeneroPaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblGeneroPaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblGeneroPaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblGenero.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblGenero.setText("Género:");
        lblGenero.setMaximumSize(new java.awt.Dimension(70, 20));
        lblGenero.setMinimumSize(new java.awt.Dimension(70, 20));
        lblGenero.setPreferredSize(new java.awt.Dimension(70, 20));

        lblCelular.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblCelular.setText("Celular:");
        lblCelular.setMaximumSize(new java.awt.Dimension(70, 20));
        lblCelular.setMinimumSize(new java.awt.Dimension(70, 20));
        lblCelular.setPreferredSize(new java.awt.Dimension(70, 20));

        lblCelularPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblCelularPaciente.setMaximumSize(new java.awt.Dimension(125, 20));
        lblCelularPaciente.setMinimumSize(new java.awt.Dimension(125, 20));
        lblCelularPaciente.setPreferredSize(new java.awt.Dimension(125, 20));

        lblDniBusq.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDniBusq.setText("DNI:");
        lblDniBusq.setMaximumSize(new java.awt.Dimension(70, 25));
        lblDniBusq.setMinimumSize(new java.awt.Dimension(70, 25));
        lblDniBusq.setPreferredSize(new java.awt.Dimension(70, 25));

        tfDniBusq.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfDniBusq.setMaximumSize(new java.awt.Dimension(125, 25));
        tfDniBusq.setMinimumSize(new java.awt.Dimension(125, 25));
        tfDniBusq.setPreferredSize(new java.awt.Dimension(125, 25));

        btnBuscarPacient.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscarPacient.setText("Buscar");
        btnBuscarPacient.setMaximumSize(new java.awt.Dimension(65, 25));
        btnBuscarPacient.setMinimumSize(new java.awt.Dimension(65, 25));
        btnBuscarPacient.setPreferredSize(new java.awt.Dimension(65, 25));
        btnBuscarPacient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPacientActionPerformed(evt);
            }
        });

        btnCitaUrgente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCitaUrgente.setText("Cita urgente");
        btnCitaUrgente.setMaximumSize(new java.awt.Dimension(65, 25));
        btnCitaUrgente.setMinimumSize(new java.awt.Dimension(65, 25));
        btnCitaUrgente.setPreferredSize(new java.awt.Dimension(65, 25));
        btnCitaUrgente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCitaUrgenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosPacienteLayout = new javax.swing.GroupLayout(pnlDatosPaciente);
        pnlDatosPaciente.setLayout(pnlDatosPacienteLayout);
        pnlDatosPacienteLayout.setHorizontalGroup(
            pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagePaciente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                        .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(lblNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                        .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(lblApellidoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                    .addComponent(lblDNI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(lblDireccionPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosPacienteLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(lblDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(lblCelularPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosPacienteLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(lblGeneroPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarPacient, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCitaUrgente, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        pnlDatosPacienteLayout.setVerticalGroup(
            pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                        .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblApellidoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblDNI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDniPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDireccionPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCelularPaciente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblGeneroPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBuscarPacient, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCitaUrgente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblImagePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
        );

        pnlContenido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlContenido.setMaximumSize(new java.awt.Dimension(1043, 500));
        pnlContenido.setMinimumSize(new java.awt.Dimension(1043, 500));

        javax.swing.GroupLayout pnlContenidoLayout = new javax.swing.GroupLayout(pnlContenido);
        pnlContenido.setLayout(pnlContenidoLayout);
        pnlContenidoLayout.setHorizontalGroup(
            pnlContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlContenidoLayout.setVerticalGroup(
            pnlContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDatosPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDatosPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarPacientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacientActionPerformed
        pnlContenido.removeAll();
        pnlContenido.add(pnlListaPacientes);
        pnlListaPacientes.setSize(1043, 500);
        pnlContenido.updateUI();
        String dni = tfDniBusq.getText();
        listaPaciente.clear();
        if (dni.equals("")) {
            limpiarDatosBarraPaciente();
            listaPaciente = pacienteController.cargarPacientes("", "");
            idPacienteFocus = -1;
        } else {
            listaPaciente = pacienteController.cargarPacientes("", dni);
        }
        reloadInfoPacientes(listaPaciente);
    }//GEN-LAST:event_btnBuscarPacientActionPerformed

    private void tblPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacienteMouseClicked
        //Creamos el objeto paciente con el paciente seleccionado
        paciente = listaPaciente.get(tblPaciente.getSelectedRow());
        //Guardamos el la variable global idPacienteFocus el id del paciente seleccionado
        idPacienteFocus = paciente.getId();
        //Mostramos los datos del paciente en la barra de Pacientes
        mostrarDatosBarraPaciente(paciente);
    }//GEN-LAST:event_tblPacienteMouseClicked

    private void tblPacienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPacienteMouseEntered

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenuActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverMenuActionPerformed

    private void btnVolverMisTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMisTurnosActionPerformed
        pnlContenido.remove(pnlListaPacientes);
        pnlContenido.add(pnlMisTurnos);
        pnlMisTurnos.setSize(1043, 500);
        pnlContenido.updateUI();
    }//GEN-LAST:event_btnVolverMisTurnosActionPerformed

    private void btnCitaUrgenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCitaUrgenteActionPerformed
        listaHorario = horarioController.cargarHorario(agenda.getId());
        turno = listaTurno.get(0);
        horario = horarioController.traerHorario(turno.getId_horario());
        if (idPacienteFocus > 0) {
            paciente = pacienteController.traerPaciente(idPacienteFocus);
            String[] datos = {String.valueOf(agenda.getId()), String.valueOf(agenda.getIdEspecialidad()), String.valueOf(horario.getId()), String.valueOf(paciente.getId()), LocalDate.now().toString(),
                "00:00", "00:00", "0"};
            TurnoController.agregarTurno(datos);
            listaTurno.clear();
            listaTurno = turnoController.cargarTurnos(agenda.getId(), -1, -1, -1, LocalDate.now().toString(), -1, null);
            reloadInfoTurnos(listaTurno);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un paciente", "Turno", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCitaUrgenteActionPerformed

    private void btnVolverMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenu1ActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverMenu1ActionPerformed

    private void btnTurnoAtendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnoAtendidoActionPerformed
        estadoTurno = listaEstados.get(0);
        cambiarEstadoTurno(estadoTurno.getId());
    }//GEN-LAST:event_btnTurnoAtendidoActionPerformed

    private void btnTurnoCanceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnoCanceladoActionPerformed
        estadoTurno = listaEstados.get(2);
        cambiarEstadoTurno(estadoTurno.getId());
    }//GEN-LAST:event_btnTurnoCanceladoActionPerformed

    private void btnBuscarTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTurnosActionPerformed
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dcFechaDeseada.getDate();
        fechaActual = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        listaTurno.clear();
        listaTurno = turnoController.cargarTurnos(agenda.getId(), -1, -1, -1, formato.format(fecha), -1, null);
        reloadInfoTurnos(listaTurno);
    }//GEN-LAST:event_btnBuscarTurnosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarPacient;
    private javax.swing.JButton btnBuscarTurnos;
    private javax.swing.JButton btnCitaUrgente;
    private javax.swing.JButton btnTurnoAtendido;
    private javax.swing.JButton btnTurnoCancelado;
    private javax.swing.JButton btnVolverMenu;
    private javax.swing.JButton btnVolverMenu1;
    private javax.swing.JButton btnVolverMisTurnos;
    private com.toedter.calendar.JDateChooser dcFechaDeseada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblApellidoPaciente;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCelularPaciente;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionPaciente;
    private javax.swing.JLabel lblDniBusq;
    private javax.swing.JLabel lblDniPaciente;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblGeneroPaciente;
    private javax.swing.JLabel lblImagePaciente;
    private javax.swing.JLabel lblMisTurnos;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombrePaciente;
    private javax.swing.JPanel pnlContenido;
    private javax.swing.JPanel pnlDatosPaciente;
    private javax.swing.JPanel pnlListaPacientes;
    private javax.swing.JPanel pnlMisTurnos;
    private javax.swing.JScrollPane spTblPaciente;
    private javax.swing.JTable tblPaciente;
    private javax.swing.JTable tblTurnos;
    private javax.swing.JTextField tfDniBusq;
    // End of variables declaration//GEN-END:variables
}
