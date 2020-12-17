package vista;

import controlador.AgendaController;
import controlador.EspecialidadController;
import controlador.PacienteController;
import controlador.TurnoController;
import controlador.UsuarioController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Especialidad;
import modelo.EstadoTurno;
import modelo.Paciente;
import modelo.Turno;
import modelo.Usuario;

public final class CitasForm extends javax.swing.JFrame {

    Paciente paciente;
    PacienteController pacienteController = new PacienteController();
    ArrayList<Paciente> listaPaciente;

    Agenda agenda;
    AgendaController agendaController = new AgendaController();
    @SuppressWarnings("unchecked")
    ArrayList<Agenda> listaAgenda = agendaController.cargarAgendas("", -1, -1);

    Especialidad especialidad;
    EspecialidadController especialidadController = new EspecialidadController();
    @SuppressWarnings("unchecked")
    ArrayList<Especialidad> listaEspecialidad = especialidadController.cargarEspecialidad();

    Turno turno;
    TurnoController turnoController = new TurnoController();
    ArrayList<Turno> listaTurno = turnoController.cargarTurnos(1, -1, -1, -1, "", -1, null);
    EstadoTurno estadoTurno;
    ArrayList<EstadoTurno> listaEstados = turnoController.cargarEstados();

    Usuario medico;
    UsuarioController usuarioController = new UsuarioController();
    ArrayList<Usuario> listaMedicos = usuarioController.cargarMedicos("");
    DefaultTableModel modelo;
    String[] encabezadoTurnos = {"Paciente", "Agenda", "Especialidad", "Médico", "Fecha", "Hs. inicio", "Hs. fin", "Estado"};

    public CitasForm() {
        initComponents();
        reloadTable(tblCitas, encabezadoTurnos);
        listarAgendas(cbAgendas);
        listarEspecialidades(cbEspecialidad);
        listarEstados(cbEstado);
    }

    public void reloadTable(JTable table, String[] encabezado) { // carga el formato de la tabla con su encabezado

        modelo = new DefaultTableModel() {//Instancia de la clase DefaultTableModel para crear el modelo de la tabla.
            boolean[] canEdit = new boolean[]{//Se crea una instancia en la que se establecen los valores de las celdas para ser o no  modificables.
                false, false, false, false, false, false, false
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

    public void reloadInfoTurnos(ArrayList<Turno> listaTurno) {
        reloadTable(tblCitas, encabezadoTurnos);
        Object[] fila = new Object[8];
        for (int i = 0; i < listaTurno.size(); i++) {
            turno = listaTurno.get(i);
            paciente = pacienteController.traerPaciente(turno.getId_paciente());
            agenda = agendaController.traerAgenda(turno.getId_agenda());
            especialidad = especialidadController.buscarEspecialidad(turno.getId_especialidad());
            estadoTurno = turnoController.traerEstado(turno.getId_estado());
            medico = usuarioController.buscarMedico(agenda.getIdMedico(), listaMedicos);
            fila[0] = paciente.getNombre() + " " + paciente.getApellido();
            fila[1] = agenda.getNombre();
            fila[2] = especialidad.getNombre();
            fila[3] = medico.getNombre() + " " + medico.getApellido();
            fila[4] = turno.getFecha();
            fila[5] = turno.getHoraInicio();
            fila[6] = turno.getHoraFin();
            fila[7] = estadoTurno.getNombre();
            modelo.addRow(fila);
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
    public void listarAgendas(JComboBox combobox) {
        combobox.addItem("");
        for (int i = 0; i < listaAgenda.size(); i++) {
            agenda = listaAgenda.get(i);
            String nespecialidad = agenda.getNombre();
            combobox.addItem(nespecialidad);
        }
    }

    @SuppressWarnings("unchecked")
    public void listarEstados(JComboBox combobox) {
        combobox.addItem("");
        for (int i = 0; i < listaEstados.size(); i++) {
            estadoTurno = listaEstados.get(i);
            combobox.addItem(estadoTurno.getNombre());
        }
    }

    public void buscarTurno() {
        ArrayList<Integer> idPacientes = new ArrayList();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = "";
        int idAgenda = -1;
        int idEspecialidad = -1;
        int idEstadoTurno = -1;
        if (tfDniPaciente.getText().equals("") && dcFechaCita.getDate() == null && cbAgendas.getSelectedIndex() == 0
                && cbEspecialidad.getSelectedIndex() == 0 && cbEstado.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete al menos un filtro de busqueda.", "Citas", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!tfDniPaciente.getText().equals("")) {
                listaPaciente = pacienteController.cargarPacientes("", tfDniPaciente.getText());
                for (int i = 0; i < listaPaciente.size(); i++) {
                    paciente = listaPaciente.get(i);
                    idPacientes.add(paciente.getId());
                }
            } else {
                idPacientes = null;
            }
            if (dcFechaCita.getDate() != null) {
                Date Datefecha = dcFechaCita.getDate();
                fecha = formato.format(Datefecha);
            }
            if (cbAgendas.getSelectedItem() != "") {
                agenda = listaAgenda.get(cbAgendas.getSelectedIndex() - 1);
                idAgenda = agenda.getId();
            }
            if (cbEspecialidad.getSelectedItem() != "") {
                especialidad = listaEspecialidad.get(cbEspecialidad.getSelectedIndex() - 1);
                idEspecialidad = especialidad.getId();
            }
            if (cbEstado.getSelectedItem() != "") {
                estadoTurno = listaEstados.get(cbEstado.getSelectedIndex() - 1);
                idEstadoTurno = estadoTurno.getId();
            }
            listaTurno.clear();
            listaTurno = turnoController.cargarTurnos(idAgenda, idEspecialidad, -1, -1, fecha, idEstadoTurno, idPacientes);
            reloadInfoTurnos(listaTurno);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBarraBusq = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblDniPaciente = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        tfDniPaciente = new javax.swing.JTextField();
        lblFechaCita = new javax.swing.JLabel();
        lblAgenda = new javax.swing.JLabel();
        dcFechaCita = new com.toedter.calendar.JDateChooser();
        cbAgendas = new javax.swing.JComboBox<>();
        lblEspecialidad = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        cbEspecialidad = new javax.swing.JComboBox<>();
        cbEstado = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        pnlListaCitas = new javax.swing.JPanel();
        btnVolverMenu = new javax.swing.JButton();
        spTblCitas = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();
        btnCancelarTurno = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1103, 665));

        pnlBarraBusq.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlBarraBusq.setMaximumSize(new java.awt.Dimension(1043, 137));
        pnlBarraBusq.setMinimumSize(new java.awt.Dimension(1043, 137));
        pnlBarraBusq.setPreferredSize(new java.awt.Dimension(1043, 137));

        lblTitulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTitulo.setText("Gestión de citas otorgadas");
        lblTitulo.setMaximumSize(new java.awt.Dimension(70, 20));
        lblTitulo.setMinimumSize(new java.awt.Dimension(70, 20));
        lblTitulo.setPreferredSize(new java.awt.Dimension(70, 20));

        lblDniPaciente.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDniPaciente.setText("DNI paciente:");
        lblDniPaciente.setMaximumSize(new java.awt.Dimension(115, 25));
        lblDniPaciente.setMinimumSize(new java.awt.Dimension(115, 25));
        lblDniPaciente.setPreferredSize(new java.awt.Dimension(115, 25));

        tfDniPaciente.setMinimumSize(new java.awt.Dimension(150, 25));
        tfDniPaciente.setPreferredSize(new java.awt.Dimension(150, 25));

        lblFechaCita.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblFechaCita.setText("Fecha cita:");
        lblFechaCita.setMaximumSize(new java.awt.Dimension(115, 25));
        lblFechaCita.setMinimumSize(new java.awt.Dimension(115, 25));
        lblFechaCita.setPreferredSize(new java.awt.Dimension(115, 25));

        lblAgenda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblAgenda.setText("Agenda:");
        lblAgenda.setMaximumSize(new java.awt.Dimension(115, 25));
        lblAgenda.setMinimumSize(new java.awt.Dimension(115, 25));
        lblAgenda.setPreferredSize(new java.awt.Dimension(115, 25));

        dcFechaCita.setDateFormatString("dd/MM/yyyy");
        dcFechaCita.setPreferredSize(new java.awt.Dimension(150, 25));

        cbAgendas.setMinimumSize(new java.awt.Dimension(150, 25));
        cbAgendas.setPreferredSize(new java.awt.Dimension(150, 25));

        lblEspecialidad.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblEspecialidad.setText("Especialidad:");
        lblEspecialidad.setMaximumSize(new java.awt.Dimension(115, 25));
        lblEspecialidad.setMinimumSize(new java.awt.Dimension(115, 25));
        lblEspecialidad.setPreferredSize(new java.awt.Dimension(115, 25));

        lblEstado.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblEstado.setText("Estado cita:");
        lblEstado.setMaximumSize(new java.awt.Dimension(115, 25));
        lblEstado.setMinimumSize(new java.awt.Dimension(115, 25));
        lblEstado.setPreferredSize(new java.awt.Dimension(115, 25));

        cbEspecialidad.setMinimumSize(new java.awt.Dimension(150, 25));
        cbEspecialidad.setPreferredSize(new java.awt.Dimension(150, 25));

        cbEstado.setMinimumSize(new java.awt.Dimension(150, 25));
        cbEstado.setPreferredSize(new java.awt.Dimension(150, 25));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBarraBusqLayout = new javax.swing.GroupLayout(pnlBarraBusq);
        pnlBarraBusq.setLayout(pnlBarraBusqLayout);
        pnlBarraBusqLayout.setHorizontalGroup(
            pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                        .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaCita, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                                .addComponent(tfDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                                .addComponent(dcFechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(123, 123, 123))
                            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(cbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuscar)
                                .addGap(20, 20, 20))))
                    .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                        .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlBarraBusqLayout.setVerticalGroup(
            pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBarraBusqLayout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDniPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFechaCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBarraBusqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBarraBusqLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addContainerGap())
        );

        pnlListaCitas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlListaCitas.setMaximumSize(new java.awt.Dimension(1043, 500));
        pnlListaCitas.setMinimumSize(new java.awt.Dimension(1043, 500));
        pnlListaCitas.setPreferredSize(new java.awt.Dimension(1043, 500));

        btnVolverMenu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverMenu.setText("Volver al Inicio");
        btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenuActionPerformed(evt);
            }
        });

        tblCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        spTblCitas.setViewportView(tblCitas);

        btnCancelarTurno.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelarTurno.setText("Cancelar");
        btnCancelarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarTurnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListaCitasLayout = new javax.swing.GroupLayout(pnlListaCitas);
        pnlListaCitas.setLayout(pnlListaCitasLayout);
        pnlListaCitasLayout.setHorizontalGroup(
            pnlListaCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListaCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListaCitasLayout.createSequentialGroup()
                        .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spTblCitas, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlListaCitasLayout.setVerticalGroup(
            pnlListaCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListaCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTblCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(pnlListaCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlListaCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBarraBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBarraBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlListaCitas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenuActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverMenuActionPerformed

    private void btnCancelarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarTurnoActionPerformed
        if (tblCitas.getSelectedRow() >= 0) {
            turno = listaTurno.get(tblCitas.getSelectedRow());
            estadoTurno = turnoController.traerEstado(turno.getId_estado());
            String estado = estadoTurno.getNombre();
            if (!estado.equals("Completado")) {
                int rta = JOptionPane.showConfirmDialog(this, "¿Desea cancelar este turno?", "Borrar", JOptionPane.YES_NO_OPTION);
                if (rta == JOptionPane.YES_OPTION) {
                    //Borramos el turno
                    TurnoController.borrarTurno(turno.getId());
                    //Limpiamos la lista y cargamos la nueva actualizada
                    listaTurno.clear();
                    buscarTurno();
                }
            } else {
                JOptionPane.showConfirmDialog(this, "No se puede cancelar una cita completada", "Borrar", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una cita.", "Citas", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCancelarTurnoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarTurno();
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarTurno;
    private javax.swing.JButton btnVolverMenu;
    private javax.swing.JComboBox<String> cbAgendas;
    private javax.swing.JComboBox<String> cbEspecialidad;
    private javax.swing.JComboBox<String> cbEstado;
    private com.toedter.calendar.JDateChooser dcFechaCita;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAgenda;
    private javax.swing.JLabel lblDniPaciente;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBarraBusq;
    private javax.swing.JPanel pnlListaCitas;
    private javax.swing.JScrollPane spTblCitas;
    private javax.swing.JTable tblCitas;
    private javax.swing.JTextField tfDniPaciente;
    // End of variables declaration//GEN-END:variables
}
