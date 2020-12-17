package vista;

import controlador.AgendaController;
import controlador.EspecialidadController;
import controlador.HorarioController;
import controlador.TurnoController;
import controlador.UsuarioController;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Especialidad;
import modelo.Horario;
import modelo.Paciente;
import modelo.Turno;
import modelo.Usuario;
import static vista.AgendaForm.getInteger;

public final class TurnosAlta extends javax.swing.JPanel {

    Paciente paciente;

    Especialidad especialidad;
    EspecialidadController especialidadController = new EspecialidadController();

    Usuario medico;
    UsuarioController usuarioController = new UsuarioController();
    ArrayList<Usuario> listaMedico = usuarioController.cargarMedicos("");

    Horario horario;
    HorarioController horarioController = new HorarioController();
    ArrayList<Horario> listaHorario = new ArrayList<>();
    ArrayList<Integer> diasSemanaHorario;

    Agenda agenda;
    AgendaController agendaController = new AgendaController();
    ArrayList<Agenda> listaAgenda;

    Turno turno;
    TurnoController turnoController = new TurnoController();
    ArrayList<Turno> listaTurno = turnoController.cargarTurnos(1, -1, -1, -1, "", -1, null);

    DefaultTableModel modelo;
    String[] encabezadoTurnos = {"Desde", "Hasta", "Estado"};

    public TurnosAlta(Paciente patient, Agenda agendaUser) {
        paciente = patient;
        System.out.println(paciente.getId());
        initComponents();
        agenda = agendaUser;
        mostrarDatosAgenda(agendaUser);
        reloadTable(tblHorarios, encabezadoTurnos);
    }

    public void reloadTable(JTable table, String[] encabezado) { // carga el formato de la tabla con su encabezado

        modelo = new DefaultTableModel() {//Instancia de la clase DefaultTableModel para crear el modelo de la tabla.
            boolean[] canEdit = new boolean[]{//Se crea una instancia en la que se establecen los valores de las celdas para ser o no  modificables.
                false, false, false
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

    public void reloadInfoHorario(Horario horario, String fecha) {
        reloadTable(tblHorarios, encabezadoTurnos);
        ArrayList<String> intervalos = generarIntervalos(horario.getHs_inicio(), horario.getHs_fin(), horario.getIntervalo());
        Object[] fila = new Object[3];
        for (int i = 0; i < intervalos.size() - 1; i++) {
            fila[0] = intervalos.get(i);
            fila[1] = intervalos.get(i + 1);
            fila[2] = disponibilidadTurno(i + 1, intervalos.get(i), fecha);
            modelo.addRow(fila);
        }
    }

    public String disponibilidadTurno(int numSlotTable, String hora, String fecha) {
        if (!validarHorarioPasado(hora, fecha)) {
            return "No disponible";
        }
        for (int i = 0; i < listaTurno.size(); i++) {
            turno = listaTurno.get(i);
            if (numSlotTable == turno.getNum_slot()) {
                return "No disponible"; //Si se modifica, modificar el método del botón guardar
            }
        }
        return "Disponible";
    }

    public boolean validarHorarioPasado(String hora, String fecha) {
        LocalDate diaActual = LocalDate.now();
        String fechaActual = diaActual.toString();
        if (fechaActual.equals(fecha)) {
            LocalTime horaActual = LocalTime.now();
            String[] c = hora.split(":");
            String[] d = horaActual.toString().split(":");
            if (getInteger(c[0]) == getInteger(d[0])) {
                return getInteger(c[1]) > getInteger(d[1]);
            }
            return getInteger(c[0]) > getInteger(d[0]);
        } else {
            return true;
        }
    }//Devuelve true la fecha ingresada es despues o antes de la actual o, si son iguales, devuelve true si el horario ingresado es despues del horario actual, falso en caso contrario

    public ArrayList<String> generarIntervalos(String horaIn, String horaFin, int intervalo) {
        ArrayList<String> intervalos = new ArrayList<>();
        LocalTime timeInicio = LocalTime.parse(horaIn);
        int cantTurnos = AgendaForm.restarHoras(horaIn, horaFin) / intervalo;
        intervalos.add(timeInicio.toString());
        for (int i = 0; i < cantTurnos; i++) {
            timeInicio = timeInicio.plusMinutes(intervalo);
            intervalos.add(timeInicio.toString());
        }
        return intervalos;
    }

    @SuppressWarnings("unchecked")
    public void mostrarDatosAgenda(Agenda agenda) {
        listaHorario.clear();
        listaHorario = horarioController.cargarHorario(agenda.getId());
        medico = usuarioController.buscarMedico(agenda.getIdMedico(), listaMedico);
        especialidad = especialidadController.buscarEspecialidad(agenda.getIdEspecialidad());
        lblNombreEspecialidad.setText(especialidad.getNombre());
        lblNombreMedico.setText(medico.getNombre() + " " + medico.getApellido());
        lblNombreAgenda.setText(agenda.getNombre());
        for (int i = 0; i < listaHorario.size(); i++) {
            horario = listaHorario.get(i);
            cbHorarios.addItem(horario.getNombre());
        }
        horario = listaHorario.get(cbHorarios.getSelectedIndex());
        mostrarDatosHorario(horario);
    }

    public void mostrarDatosHorario(Horario horario) {
        diasSemanaHorario = new ArrayList<>();
        limpiarDiasHorario();
        int[] bitdias = {horario.isLunes(), horario.isMartes(), horario.isMiercoles(), horario.isJueves(), horario.isViernes(), horario.isSabado()};
        for (int j = 1; j <= 6; j++) {
            if (bitdias[j - 1] == 1) {
                switch (j) {
                    case 1 -> {
                        diasSemanaHorario.add(2);
                        cbLunes.setSelected(true);
                    }
                    case 2 -> {
                        diasSemanaHorario.add(3);
                        cbMartes.setSelected(true);
                    }
                    case 3 -> {
                        diasSemanaHorario.add(4);
                        cbMiercoles.setSelected(true);
                    }
                    case 4 -> {
                        diasSemanaHorario.add(5);
                        cbJueves.setSelected(true);
                    }
                    case 5 -> {
                        diasSemanaHorario.add(6);
                        cbViernes.setSelected(true);
                    }
                    case 6 -> {
                        diasSemanaHorario.add(7);
                        cbSabado.setSelected(true);
                    }
                    default -> {
                    }
                }
            }
        }
        lblNombreHsInc.setText(horario.getHs_inicio());
        lblNombreHsFin.setText(horario.getHs_fin());
    }

    public void limpiarDiasHorario() {
        cbLunes.setSelected(false);
        cbMartes.setSelected(false);
        cbMiercoles.setSelected(false);
        cbJueves.setSelected(false);
        cbViernes.setSelected(false);
        cbSabado.setSelected(false);
    }

    public boolean validarDiaSeleccionado(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        for (int i = 0; i < diasSemanaHorario.size(); i++) {
            if (calendario.get(Calendar.DAY_OF_WEEK) == diasSemanaHorario.get(i)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblEspecialidad = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        lblAgenda = new javax.swing.JLabel();
        lblNombreEspecialidad = new javax.swing.JLabel();
        lblNombreMedico = new javax.swing.JLabel();
        lblNombreAgenda = new javax.swing.JLabel();
        lblHorario = new javax.swing.JLabel();
        cbHorarios = new javax.swing.JComboBox<>();
        lblDias = new javax.swing.JLabel();
        cbLunes = new javax.swing.JCheckBox();
        cbMartes = new javax.swing.JCheckBox();
        cbMiercoles = new javax.swing.JCheckBox();
        cbJueves = new javax.swing.JCheckBox();
        cbViernes = new javax.swing.JCheckBox();
        cbSabado = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        lblSeleccione = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();
        btnBuscarTurnos = new javax.swing.JButton();
        btnGuardarTurno = new javax.swing.JButton();
        lblHsIn = new javax.swing.JLabel();
        lblNombreHsInc = new javax.swing.JLabel();
        lblHsFin = new javax.swing.JLabel();
        lblNombreHsFin = new javax.swing.JLabel();
        lblFechaDeseada = new javax.swing.JLabel();
        dcFechaDeseada = new com.toedter.calendar.JDateChooser();
        btnVolverInicio = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(100000, 10000));
        setMinimumSize(new java.awt.Dimension(1043, 500));
        setPreferredSize(new java.awt.Dimension(1043, 500));

        lblTitulo.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblTitulo.setText("Agregar turno");

        lblEspecialidad.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblEspecialidad.setText("Especialidad:");
        lblEspecialidad.setMaximumSize(new java.awt.Dimension(88, 25));
        lblEspecialidad.setMinimumSize(new java.awt.Dimension(88, 25));
        lblEspecialidad.setPreferredSize(new java.awt.Dimension(88, 25));

        lblMedico.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblMedico.setText("Médico:");
        lblMedico.setMaximumSize(new java.awt.Dimension(88, 25));
        lblMedico.setMinimumSize(new java.awt.Dimension(88, 25));
        lblMedico.setPreferredSize(new java.awt.Dimension(88, 25));

        lblAgenda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblAgenda.setText("Agenda:");
        lblAgenda.setMaximumSize(new java.awt.Dimension(88, 25));
        lblAgenda.setMinimumSize(new java.awt.Dimension(88, 25));
        lblAgenda.setPreferredSize(new java.awt.Dimension(88, 25));

        lblNombreEspecialidad.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombreEspecialidad.setMaximumSize(new java.awt.Dimension(200, 25));
        lblNombreEspecialidad.setMinimumSize(new java.awt.Dimension(200, 25));
        lblNombreEspecialidad.setPreferredSize(new java.awt.Dimension(200, 25));

        lblNombreMedico.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombreMedico.setMaximumSize(new java.awt.Dimension(200, 25));
        lblNombreMedico.setMinimumSize(new java.awt.Dimension(200, 25));
        lblNombreMedico.setPreferredSize(new java.awt.Dimension(200, 25));

        lblNombreAgenda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombreAgenda.setMaximumSize(new java.awt.Dimension(200, 25));
        lblNombreAgenda.setMinimumSize(new java.awt.Dimension(200, 25));
        lblNombreAgenda.setPreferredSize(new java.awt.Dimension(200, 25));

        lblHorario.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblHorario.setText("Horario:");
        lblHorario.setMaximumSize(new java.awt.Dimension(88, 25));
        lblHorario.setMinimumSize(new java.awt.Dimension(88, 25));
        lblHorario.setPreferredSize(new java.awt.Dimension(88, 25));

        cbHorarios.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cbHorarios.setMaximumSize(new java.awt.Dimension(200, 25));
        cbHorarios.setMinimumSize(new java.awt.Dimension(200, 25));
        cbHorarios.setPreferredSize(new java.awt.Dimension(200, 25));
        cbHorarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHorariosActionPerformed(evt);
            }
        });

        lblDias.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDias.setText("Días:");
        lblDias.setMaximumSize(new java.awt.Dimension(88, 25));
        lblDias.setMinimumSize(new java.awt.Dimension(88, 25));
        lblDias.setPreferredSize(new java.awt.Dimension(88, 25));

        cbLunes.setText("L");
        cbLunes.setEnabled(false);

        cbMartes.setText("M");
        cbMartes.setEnabled(false);

        cbMiercoles.setText("X");
        cbMiercoles.setEnabled(false);

        cbJueves.setText("J");
        cbJueves.setEnabled(false);

        cbViernes.setText("V");
        cbViernes.setEnabled(false);

        cbSabado.setText("S");
        cbSabado.setEnabled(false);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblSeleccione.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblSeleccione.setText("Seleccione la hora del turno");

        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblHorarios);

        btnBuscarTurnos.setText("Buscar");
        btnBuscarTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTurnosActionPerformed(evt);
            }
        });

        btnGuardarTurno.setText("Guardar");
        btnGuardarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTurnoActionPerformed(evt);
            }
        });

        lblHsIn.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblHsIn.setText("Hs. Inicio:");
        lblHsIn.setMaximumSize(new java.awt.Dimension(88, 25));
        lblHsIn.setMinimumSize(new java.awt.Dimension(88, 25));
        lblHsIn.setPreferredSize(new java.awt.Dimension(88, 25));

        lblNombreHsInc.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombreHsInc.setMaximumSize(new java.awt.Dimension(88, 25));
        lblNombreHsInc.setMinimumSize(new java.awt.Dimension(88, 25));
        lblNombreHsInc.setPreferredSize(new java.awt.Dimension(88, 25));

        lblHsFin.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblHsFin.setText("Hs. Fin:");
        lblHsFin.setMaximumSize(new java.awt.Dimension(88, 25));
        lblHsFin.setMinimumSize(new java.awt.Dimension(88, 25));
        lblHsFin.setPreferredSize(new java.awt.Dimension(88, 25));

        lblNombreHsFin.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombreHsFin.setMaximumSize(new java.awt.Dimension(88, 25));
        lblNombreHsFin.setMinimumSize(new java.awt.Dimension(88, 25));
        lblNombreHsFin.setPreferredSize(new java.awt.Dimension(88, 25));

        lblFechaDeseada.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblFechaDeseada.setText("Fecha deseada:");
        lblFechaDeseada.setMaximumSize(new java.awt.Dimension(88, 25));
        lblFechaDeseada.setMinimumSize(new java.awt.Dimension(88, 25));
        lblFechaDeseada.setPreferredSize(new java.awt.Dimension(88, 25));

        dcFechaDeseada.setDateFormatString("dd/MM/yyyy");
        dcFechaDeseada.setMinSelectableDate(new Date());
        dcFechaDeseada.setMinimumSize(new java.awt.Dimension(28, 25));
        dcFechaDeseada.setPreferredSize(new java.awt.Dimension(170, 25));

        btnVolverInicio.setText("Volver al inicio");
        btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardarTurno)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnVolverInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBuscarTurnos)
                                    .addGap(9, 9, 9))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(lblTitulo))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNombreEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNombreMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblHsIn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNombreHsInc, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(lblHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNombreHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cbLunes)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbMartes)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbMiercoles)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbJueves)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbViernes)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbSabado)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dcFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSeleccione)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLunes)
                    .addComponent(cbMartes)
                    .addComponent(cbMiercoles)
                    .addComponent(cbJueves)
                    .addComponent(cbViernes)
                    .addComponent(cbSabado))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreHsFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHsIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombreHsInc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHsFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFechaDeseada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarTurnos)
                    .addComponent(btnVolverInicio))
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblSeleccione)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnGuardarTurno)
                .addGap(0, 27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTurnosActionPerformed
        horario = listaHorario.get(cbHorarios.getSelectedIndex());
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = dcFechaDeseada.getDate();
        listaTurno.clear();
        if (fecha != null) {
            if (validarDiaSeleccionado(fecha)) {
                listaTurno = turnoController.cargarTurnos(horario.getId_agenda(), -1, horario.getId(), -1, formato.format(fecha), -1, null);
                reloadInfoHorario(horario, formato.format(fecha));
            } else {
                reloadTable(tblHorarios, encabezadoTurnos);
            }
        } else {
            reloadTable(tblHorarios, encabezadoTurnos);
        }
    }//GEN-LAST:event_btnBuscarTurnosActionPerformed

    private void cbHorariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHorariosActionPerformed
        horario = listaHorario.get(cbHorarios.getSelectedIndex());
        mostrarDatosHorario(horario);
    }//GEN-LAST:event_cbHorariosActionPerformed

    private void btnGuardarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTurnoActionPerformed
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (tblHorarios.getSelectedRow() >= 0) {
            if (((String) modelo.getValueAt(tblHorarios.getSelectedRow(), 2)).equals("Disponible")) {
                int rta = JOptionPane.showConfirmDialog(this, "¿Quiere agregar este turno?", "Agregar", JOptionPane.YES_NO_OPTION);
                if (rta == JOptionPane.YES_OPTION) {
                    int numSlot = tblHorarios.getSelectedRow() + 1;
                    Date fecha = dcFechaDeseada.getDate();
                    String horaIn = (String) modelo.getValueAt(tblHorarios.getSelectedRow(), 0);
                    String horaFin = (String) modelo.getValueAt(tblHorarios.getSelectedRow(), 1);
                    String[] datos = {String.valueOf(horario.getId_agenda()), String.valueOf(agenda.getIdEspecialidad()), String.valueOf(horario.getId()), String.valueOf(paciente.getId()), formato.format(fecha),
                        horaIn, horaFin, String.valueOf(numSlot)};
                    TurnoController.agregarTurno(datos);
                    listaTurno = turnoController.cargarTurnos(horario.getId_agenda(), -1, horario.getId(), -1, formato.format(fecha), -1, null);
                    reloadInfoHorario(horario, formato.format(fecha));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Turno no disponible, por favor elija otro turno.", "Turnos", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un slot de turno.", "Turnos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarTurnoActionPerformed

    private void btnVolverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverInicioActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }//GEN-LAST:event_btnVolverInicioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarTurnos;
    private javax.swing.JButton btnGuardarTurno;
    private javax.swing.JButton btnVolverInicio;
    private javax.swing.JComboBox<String> cbHorarios;
    private javax.swing.JCheckBox cbJueves;
    private javax.swing.JCheckBox cbLunes;
    private javax.swing.JCheckBox cbMartes;
    private javax.swing.JCheckBox cbMiercoles;
    private javax.swing.JCheckBox cbSabado;
    private javax.swing.JCheckBox cbViernes;
    private com.toedter.calendar.JDateChooser dcFechaDeseada;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAgenda;
    private javax.swing.JLabel lblDias;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblFechaDeseada;
    private javax.swing.JLabel lblHorario;
    private javax.swing.JLabel lblHsFin;
    private javax.swing.JLabel lblHsIn;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblNombreAgenda;
    private javax.swing.JLabel lblNombreEspecialidad;
    private javax.swing.JLabel lblNombreHsFin;
    private javax.swing.JLabel lblNombreHsInc;
    private javax.swing.JLabel lblNombreMedico;
    private javax.swing.JLabel lblSeleccione;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblHorarios;
    // End of variables declaration//GEN-END:variables
}
