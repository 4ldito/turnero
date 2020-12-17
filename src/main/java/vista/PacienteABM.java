package vista;

import controlador.AgendaController;
import controlador.EspecialidadController;
import controlador.PacienteController;
import controlador.UsuarioController;
import java.awt.Font;
import modelo.Paciente;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Especialidad;
import modelo.Usuario;

public final class PacienteABM extends javax.swing.JFrame {

    Paciente paciente;
    PacienteController pacienteController = new PacienteController();
    ArrayList<Paciente> listaPaciente;

    Agenda agenda;
    AgendaController agendaController = new AgendaController();
    @SuppressWarnings("unchecked")
    ArrayList<Agenda> listaAgenda = agendaController.cargarAgendas("", -1, -1);

    Usuario medico;
    UsuarioController usuarioController = new UsuarioController();
    @SuppressWarnings("unchecked")
    ArrayList<Usuario> listaMedico = usuarioController.cargarMedicos("");

    Especialidad especialidad;
    EspecialidadController especialidadController = new EspecialidadController();
    @SuppressWarnings("unchecked")
    ArrayList<Especialidad> listaEspecialidad = especialidadController.cargarEspecialidad();

    DefaultTableModel modelo;
    String[] encabezadoPacientes = {"DNI", "Nombre", "Apellido", "Dirección", "Celular", "Género"};

    int idPacienteFocus;

    @SuppressWarnings("unchecked")
    public PacienteABM() {
        initComponents();
        listaPaciente = pacienteController.cargarPacientes("", "");
        pnlContenido.add(pnlListaPacientes);
        pnlListaPacientes.setSize(1043, 500);
        reloadInfoPacientes(listaPaciente);
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
        table.getTableHeader().setFont(new Font("Verdana", Font.PLAIN, 16));

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

    public void limpiarDatosBarraPaciente() {
        lblNombrePaciente.setText("");
        lblApellidoPaciente.setText("");
        lblDniPaciente.setText("");
        lblDireccionPaciente.setText("");
        lblCelularPaciente.setText("");
        lblGeneroPaciente.setText("");
        lblImagePaciente.setEnabled(false);
    }

    public void mostrarDatosPaciente(Paciente paciente) {
        tfNuevoNombre.setText(paciente.getNombre());
        tfNuevoApellido.setText(paciente.getApellido());
        tfNuevoDni.setText(String.valueOf(paciente.getDni()));
        tfNuevaDireccion.setText(paciente.getDireccion());
        tfNuevoCelular.setText(paciente.getCelular());
        cbNuevoGenero.setSelectedIndex(paciente.getId_genero());
    }

    public void limpiarDatosPaciente() {
        tfNuevoNombre.setText("");
        tfNuevoApellido.setText("");
        tfNuevoDni.setText("");
        tfNuevaDireccion.setText("");
        tfNuevoCelular.setText("");
        cbNuevoGenero.setSelectedIndex(0);
    }

    public void limpiarDatosBusqTurnos() {
        jcEspecialidades.setSelectedIndex(0);
        jcMedicos.setEnabled(false);
        jcAgendas.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    public void listarMedicos(JComboBox combobox, ArrayList<Usuario> listaMedicoN) {
        combobox.removeAllItems();
        combobox.addItem("");
        for (int i = 0; i < listaMedicoN.size(); i++) {
            medico = listaMedicoN.get(i);
            String nombreMedico = medico.getNombre() + " " + medico.getApellido();
            combobox.addItem(nombreMedico);
        }
    }

    @SuppressWarnings("unchecked")
    public void listarEspecialidades(JComboBox combobox) {
        combobox.removeAllItems();
        combobox.addItem("");
        for (int i = 0; i < listaEspecialidad.size(); i++) {
            especialidad = listaEspecialidad.get(i);
            String nespecialidad = especialidad.getNombre();
            combobox.addItem(nespecialidad);
        }
    }

    @SuppressWarnings("unchecked")
    public void listarAgendas(JComboBox combobox, ArrayList<Agenda> listaAgendaN) {
        combobox.removeAllItems();
        combobox.addItem("");
        for (int i = 0; i < listaAgendaN.size(); i++) {
            agenda = listaAgendaN.get(i);
            String nagenda = agenda.getNombre();
            combobox.addItem(nagenda);
        }
    }

    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jdAgregarPaciente = new javax.swing.JDialog();
    pnlAgregarPaciente = new javax.swing.JPanel();
    btnGuardarPaciente = new javax.swing.JButton();
    btnCerrarABMPaciente = new javax.swing.JButton();
    cbNuevoGenero = new javax.swing.JComboBox<>();
    lblGenero1 = new javax.swing.JLabel();
    lblCelular1 = new javax.swing.JLabel();
    tfNuevoCelular = new javax.swing.JTextField();
    tfNuevaDireccion = new javax.swing.JTextField();
    lblDireccion1 = new javax.swing.JLabel();
    lblDni1 = new javax.swing.JLabel();
    tfNuevoDni = new javax.swing.JTextField();
    tfNuevoApellido = new javax.swing.JTextField();
    lblApellido1 = new javax.swing.JLabel();
    lblNombre1 = new javax.swing.JLabel();
    tfNuevoNombre = new javax.swing.JTextField();
    lblTitulo = new javax.swing.JLabel();
    jSeparator3 = new javax.swing.JSeparator();
    jdBuscarTurno = new javax.swing.JDialog();
    pnlBuscarTurno = new javax.swing.JPanel();
    lblBusqueda = new javax.swing.JLabel();
    lblEspecialidad = new javax.swing.JLabel();
    lblMedico = new javax.swing.JLabel();
    lblAgenda = new javax.swing.JLabel();
    jSeparator2 = new javax.swing.JSeparator();
    jcEspecialidades = new javax.swing.JComboBox<>();
    jcMedicos = new javax.swing.JComboBox<>();
    jcAgendas = new javax.swing.JComboBox<>();
    btnBuscarTurno = new javax.swing.JButton();
    btnCerrarPnlBuscarTurno = new javax.swing.JButton();
    pnlListaPacientes = new javax.swing.JPanel();
    spTblPaciente = new javax.swing.JScrollPane();
    tblPaciente = new javax.swing.JTable();
    btnAñadirPaciente = new javax.swing.JButton();
    btnEditarPaciente = new javax.swing.JButton();
    btnEliminarPaciente = new javax.swing.JButton();
    btnVolverMenu = new javax.swing.JButton();
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
    btnCrearTurno = new javax.swing.JButton();
    pnlContenido = new javax.swing.JPanel();

    jdAgregarPaciente.setMinimumSize(new java.awt.Dimension(450, 300));
    jdAgregarPaciente.setLocationRelativeTo(null);
    jdAgregarPaciente.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
    jdAgregarPaciente.setUndecorated(true);

    pnlAgregarPaciente.setBackground(new java.awt.Color(255, 255, 255));
    pnlAgregarPaciente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    pnlAgregarPaciente.setMaximumSize(new java.awt.Dimension(315, 300));
    pnlAgregarPaciente.setMinimumSize(new java.awt.Dimension(315, 300));
    pnlAgregarPaciente.setPreferredSize(new java.awt.Dimension(450, 300));

    btnGuardarPaciente.setText("Guardar");
    btnGuardarPaciente.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnGuardarPacienteActionPerformed(evt);
      }
    });

    btnCerrarABMPaciente.setText("Cerrar");
    btnCerrarABMPaciente.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCerrarABMPacienteActionPerformed(evt);
      }
    });

    cbNuevoGenero.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    cbNuevoGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "Otro" }));
    cbNuevoGenero.setMinimumSize(new java.awt.Dimension(72, 25));
    cbNuevoGenero.setPreferredSize(new java.awt.Dimension(72, 25));

    lblGenero1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblGenero1.setText("Género:");
    lblGenero1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblGenero1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblGenero1.setPreferredSize(new java.awt.Dimension(70, 25));

    lblCelular1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblCelular1.setText("Celular:");
    lblCelular1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblCelular1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblCelular1.setPreferredSize(new java.awt.Dimension(70, 25));

    tfNuevoCelular.setMaximumSize(new java.awt.Dimension(200, 25));
    tfNuevoCelular.setMinimumSize(new java.awt.Dimension(200, 25));
    tfNuevoCelular.setPreferredSize(new java.awt.Dimension(200, 25));

    tfNuevaDireccion.setMaximumSize(new java.awt.Dimension(200, 25));
    tfNuevaDireccion.setMinimumSize(new java.awt.Dimension(200, 25));
    tfNuevaDireccion.setPreferredSize(new java.awt.Dimension(200, 25));

    lblDireccion1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblDireccion1.setText("Dirección:");
    lblDireccion1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblDireccion1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblDireccion1.setPreferredSize(new java.awt.Dimension(70, 25));

    lblDni1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblDni1.setText("Dni:");
    lblDni1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblDni1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblDni1.setPreferredSize(new java.awt.Dimension(70, 25));

    tfNuevoDni.setMaximumSize(new java.awt.Dimension(200, 25));
    tfNuevoDni.setMinimumSize(new java.awt.Dimension(200, 25));
    tfNuevoDni.setPreferredSize(new java.awt.Dimension(200, 25));

    tfNuevoApellido.setMaximumSize(new java.awt.Dimension(200, 25));
    tfNuevoApellido.setMinimumSize(new java.awt.Dimension(200, 25));
    tfNuevoApellido.setPreferredSize(new java.awt.Dimension(200, 25));

    lblApellido1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblApellido1.setText("Apellido:");
    lblApellido1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblApellido1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblApellido1.setPreferredSize(new java.awt.Dimension(70, 25));

    lblNombre1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblNombre1.setText("Nombre:");
    lblNombre1.setMaximumSize(new java.awt.Dimension(70, 25));
    lblNombre1.setMinimumSize(new java.awt.Dimension(70, 25));
    lblNombre1.setPreferredSize(new java.awt.Dimension(70, 25));

    tfNuevoNombre.setMaximumSize(new java.awt.Dimension(200, 25));
    tfNuevoNombre.setMinimumSize(new java.awt.Dimension(200, 25));
    tfNuevoNombre.setPreferredSize(new java.awt.Dimension(200, 25));

    lblTitulo.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
    lblTitulo.setText("Agregar paciente");

    javax.swing.GroupLayout pnlAgregarPacienteLayout = new javax.swing.GroupLayout(pnlAgregarPaciente);
    pnlAgregarPaciente.setLayout(pnlAgregarPacienteLayout);
    pnlAgregarPacienteLayout.setHorizontalGroup(
      pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(pnlAgregarPacienteLayout.createSequentialGroup()
        .addGap(30, 30, 30)
        .addGroup(pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(pnlAgregarPacienteLayout.createSequentialGroup()
            .addComponent(btnCerrarABMPaciente)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnGuardarPaciente))
          .addComponent(lblTitulo)
          .addGroup(pnlAgregarPacienteLayout.createSequentialGroup()
            .addGroup(pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(lblCelular1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
              .addComponent(lblDireccion1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(lblDni1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(lblNombre1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(lblApellido1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(lblGenero1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(10, 10, 10)
            .addGroup(pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(cbNuevoGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(tfNuevaDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
              .addComponent(tfNuevoCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(tfNuevoDni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(tfNuevoApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(tfNuevoNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addGap(30, 30, 30))
      .addComponent(jSeparator3)
    );
    pnlAgregarPacienteLayout.setVerticalGroup(
      pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarPacienteLayout.createSequentialGroup()
        .addComponent(lblTitulo)
        .addGap(4, 4, 4)
        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(4, 4, 4)
        .addGroup(pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(pnlAgregarPacienteLayout.createSequentialGroup()
            .addComponent(lblNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(lblDni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblCelular1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblGenero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(pnlAgregarPacienteLayout.createSequentialGroup()
            .addComponent(tfNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(tfNuevoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tfNuevoDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(tfNuevaDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(tfNuevoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(cbNuevoGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addGap(18, 18, 18)
        .addGroup(pnlAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(btnGuardarPaciente)
          .addComponent(btnCerrarABMPaciente))
        .addContainerGap(12, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jdAgregarPacienteLayout = new javax.swing.GroupLayout(jdAgregarPaciente.getContentPane());
    jdAgregarPaciente.getContentPane().setLayout(jdAgregarPacienteLayout);
    jdAgregarPacienteLayout.setHorizontalGroup(
      jdAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(pnlAgregarPaciente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jdAgregarPacienteLayout.setVerticalGroup(
      jdAgregarPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jdAgregarPacienteLayout.createSequentialGroup()
        .addComponent(pnlAgregarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    jdBuscarTurno.setMinimumSize(new java.awt.Dimension(315, 250));
    jdBuscarTurno.setLocationRelativeTo(null);
    jdBuscarTurno.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
    jdBuscarTurno.setUndecorated(true);

    pnlBuscarTurno.setBackground(new java.awt.Color(255, 255, 255));
    pnlBuscarTurno.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    pnlBuscarTurno.setMaximumSize(new java.awt.Dimension(315, 300));
    pnlBuscarTurno.setMinimumSize(new java.awt.Dimension(315, 300));
    pnlBuscarTurno.setPreferredSize(new java.awt.Dimension(315, 300));

    lblBusqueda.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
    lblBusqueda.setText("Búsqueda:");

    lblEspecialidad.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblEspecialidad.setText("Especialidad:");
    lblEspecialidad.setMaximumSize(new java.awt.Dimension(70, 25));
    lblEspecialidad.setMinimumSize(new java.awt.Dimension(70, 25));
    lblEspecialidad.setPreferredSize(new java.awt.Dimension(70, 25));

    lblMedico.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblMedico.setText("Médico:");
    lblMedico.setMaximumSize(new java.awt.Dimension(70, 25));
    lblMedico.setMinimumSize(new java.awt.Dimension(70, 25));
    lblMedico.setPreferredSize(new java.awt.Dimension(70, 25));

    lblAgenda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
    lblAgenda.setText("Agenda:");
    lblAgenda.setMaximumSize(new java.awt.Dimension(70, 25));
    lblAgenda.setMinimumSize(new java.awt.Dimension(70, 25));
    lblAgenda.setPreferredSize(new java.awt.Dimension(70, 25));

    listarEspecialidades(jcEspecialidades);
    jcEspecialidades.setMaximumSize(new java.awt.Dimension(175, 25));
    jcEspecialidades.setMinimumSize(new java.awt.Dimension(175, 25));
    jcEspecialidades.setPreferredSize(new java.awt.Dimension(175, 25));
    jcEspecialidades.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jcEspecialidadesActionPerformed(evt);
      }
    });

    jcMedicos.setEnabled(false);
    jcMedicos.setMaximumSize(new java.awt.Dimension(175, 25));
    jcMedicos.setMinimumSize(new java.awt.Dimension(175, 25));
    jcMedicos.setPreferredSize(new java.awt.Dimension(175, 25));
    jcMedicos.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jcMedicosActionPerformed(evt);
      }
    });

    jcAgendas.setEnabled(false);
    jcAgendas.setMaximumSize(new java.awt.Dimension(175, 25));
    jcAgendas.setMinimumSize(new java.awt.Dimension(175, 25));
    jcAgendas.setPreferredSize(new java.awt.Dimension(175, 25));

    btnBuscarTurno.setText("Buscar");
    btnBuscarTurno.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnBuscarTurnoActionPerformed(evt);
      }
    });

    btnCerrarPnlBuscarTurno.setText("Cerrar");
    btnCerrarPnlBuscarTurno.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCerrarPnlBuscarTurnoActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout pnlBuscarTurnoLayout = new javax.swing.GroupLayout(pnlBuscarTurno);
    pnlBuscarTurno.setLayout(pnlBuscarTurnoLayout);
    pnlBuscarTurnoLayout.setHorizontalGroup(
      pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jSeparator2)
      .addGroup(pnlBuscarTurnoLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBuscarTurnoLayout.createSequentialGroup()
            .addComponent(btnCerrarPnlBuscarTurno)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBuscarTurno))
          .addGroup(pnlBuscarTurnoLayout.createSequentialGroup()
            .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(pnlBuscarTurnoLayout.createSequentialGroup()
                .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(lblAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jcEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jcMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jcAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addComponent(lblBusqueda))
            .addGap(0, 8, Short.MAX_VALUE)))
        .addContainerGap())
    );
    pnlBuscarTurnoLayout.setVerticalGroup(
      pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(pnlBuscarTurnoLayout.createSequentialGroup()
        .addComponent(lblBusqueda)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jcEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(lblMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jcMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(lblAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jcAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(29, 29, 29)
        .addGroup(pnlBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnBuscarTurno)
          .addComponent(btnCerrarPnlBuscarTurno))
        .addGap(71, 71, 71))
    );

    javax.swing.GroupLayout jdBuscarTurnoLayout = new javax.swing.GroupLayout(jdBuscarTurno.getContentPane());
    jdBuscarTurno.getContentPane().setLayout(jdBuscarTurnoLayout);
    jdBuscarTurnoLayout.setHorizontalGroup(
      jdBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(pnlBuscarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    jdBuscarTurnoLayout.setVerticalGroup(
      jdBuscarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(pnlBuscarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pnlListaPacientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    pnlListaPacientes.setMaximumSize(new java.awt.Dimension(478, 442));
    pnlListaPacientes.setMinimumSize(new java.awt.Dimension(478, 442));

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

    btnAñadirPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    btnAñadirPaciente.setText("Añadir");
    btnAñadirPaciente.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAñadirPacienteActionPerformed(evt);
      }
    });

    btnEditarPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    btnEditarPaciente.setText("Editar");
    btnEditarPaciente.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEditarPacienteActionPerformed(evt);
      }
    });

    btnEliminarPaciente.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    btnEliminarPaciente.setText("Eliminar");
    btnEliminarPaciente.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnEliminarPacienteActionPerformed(evt);
      }
    });

    btnVolverMenu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    btnVolverMenu.setText("Volver al Inicio");
    btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnVolverMenuActionPerformed(evt);
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
            .addComponent(btnAñadirPaciente)
            .addGap(18, 18, 18)
            .addComponent(btnEditarPaciente)
            .addGap(18, 18, 18)
            .addComponent(btnEliminarPaciente))
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
          .addComponent(btnAñadirPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnEditarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnEliminarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setMinimumSize(new java.awt.Dimension(800, 600));
    setResizable(false);

    pnlDatosPaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    pnlDatosPaciente.setMaximumSize(new java.awt.Dimension(1043, 137));
    pnlDatosPaciente.setMinimumSize(new java.awt.Dimension(1043, 137));
    pnlDatosPaciente.setPreferredSize(new java.awt.Dimension(1043, 137));

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

    btnCrearTurno.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
    btnCrearTurno.setText("Crear turno");
    btnCrearTurno.setMaximumSize(new java.awt.Dimension(65, 25));
    btnCrearTurno.setMinimumSize(new java.awt.Dimension(65, 25));
    btnCrearTurno.setPreferredSize(new java.awt.Dimension(65, 25));
    btnCrearTurno.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCrearTurnoActionPerformed(evt);
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
                .addComponent(lblGeneroPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(0, 0, Short.MAX_VALUE))
          .addComponent(jSeparator1)
          .addGroup(pnlDatosPacienteLayout.createSequentialGroup()
            .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(39, 39, 39)
            .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(btnBuscarPacient, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCrearTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(btnCrearTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(pnlDatosPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
          .addComponent(lblImagePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
    );

    pnlContenido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    pnlContenido.setMaximumSize(new java.awt.Dimension(1043, 500));
    pnlContenido.setMinimumSize(new java.awt.Dimension(1043, 500));
    pnlContenido.setPreferredSize(new java.awt.Dimension(1043, 500));

    javax.swing.GroupLayout pnlContenidoLayout = new javax.swing.GroupLayout(pnlContenido);
    pnlContenido.setLayout(pnlContenidoLayout);
    pnlContenidoLayout.setHorizontalGroup(
      pnlContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 1039, Short.MAX_VALUE)
    );
    pnlContenidoLayout.setVerticalGroup(
      pnlContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 496, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(30, 30, 30)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(pnlContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(pnlDatosPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(30, Short.MAX_VALUE))
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

    @SuppressWarnings("unchecked")
    private void btnBuscarPacientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacientActionPerformed
        pnlContenido.removeAll();
        pnlContenido.add(pnlListaPacientes);
        pnlListaPacientes.setSize(1043, 500);
        pnlContenido.updateUI();
        btnCrearTurno.setEnabled(true);
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

    private void btnAñadirPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirPacienteActionPerformed
        lblTitulo.setText("Agregar paciente");
        idPacienteFocus = -1;
        jdAgregarPaciente.setVisible(true);
    }//GEN-LAST:event_btnAñadirPacienteActionPerformed

    @SuppressWarnings("unchecked")
    private void btnEliminarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPacienteActionPerformed
        //Corroboramos que primero se haya seleccionado un paciente
        if (tblPaciente.getSelectedRow() >= 0) {
            //Creamos el objeto paciente con el paciente seleccionado
            paciente = listaPaciente.get(tblPaciente.getSelectedRow());
            //Validamos la elección del usuario
            int rta = JOptionPane.showConfirmDialog(this, "¿Desea borrar este paciente?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (rta == JOptionPane.YES_OPTION) {
                //Borramos el paciente
                PacienteController.borrarPaciente(paciente.getId());
                //Limpiamos la lista y cargamos la nueva actualizada
                listaPaciente.clear();
                listaPaciente = pacienteController.cargarPacientes("", "");
                //Mostramos la info de los pacientes en la tabla
                reloadInfoPacientes(listaPaciente);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un paciente.", "Paciente", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarPacienteActionPerformed

    private void btnEditarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPacienteActionPerformed
        //Cambiamos el titulo del pnlAgregarPaciente
        lblTitulo.setText("Editar paciente");
        //Corroboramos que primero se haya seleccionado un paciente
        if (tblPaciente.getSelectedRow() >= 0) {
            //Creamos el objeto paciente con el paciente seleccionado
            paciente = listaPaciente.get(tblPaciente.getSelectedRow());
            //Guardamos el la variable global idPacienteFocus el id del paciente seleccionado
            idPacienteFocus = paciente.getId();
            //Mostramos los datos del paciente en la barra de Pacientes
            mostrarDatosPaciente(paciente);
            //Abrimos el panel para agregar pacientes
            jdAgregarPaciente.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un paciente.", "Paciente", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarPacienteActionPerformed

    private void tblPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacienteMouseClicked
        //Creamos el objeto paciente con el paciente seleccionado
        paciente = listaPaciente.get(tblPaciente.getSelectedRow());
        //Guardamos el la variable global idPacienteFocus el id del paciente seleccionado
        idPacienteFocus = paciente.getId();
        //Mostramos los datos del paciente en la barra de Pacientes
        mostrarDatosBarraPaciente(paciente);
    }//GEN-LAST:event_tblPacienteMouseClicked

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenuActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverMenuActionPerformed

    private void tblPacienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacienteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPacienteMouseEntered

    private void btnCerrarPnlBuscarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarPnlBuscarTurnoActionPerformed
        //Limpiamos los comboboxs del pnlBuscarTurno
        jcEspecialidades.setSelectedIndex(0);
        jcMedicos.removeAllItems();
        jcMedicos.setEnabled(false);
        jcAgendas.removeAllItems();
        jcAgendas.setEnabled(false);
        jdBuscarTurno.dispose();
    }//GEN-LAST:event_btnCerrarPnlBuscarTurnoActionPerformed

    private void btnCrearTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearTurnoActionPerformed
        limpiarDatosBusqTurnos();
        if (idPacienteFocus > 0) {
            jdBuscarTurno.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un paciente.", "Paciente", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCrearTurnoActionPerformed

    @SuppressWarnings("unchecked")
    private void jcEspecialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcEspecialidadesActionPerformed
        if (jcEspecialidades.getSelectedIndex() > 0) {
            //limpiamos las agendas
            listaAgenda.clear();
            //Creamos el objeto especialidad con la especialidad seleccionada
            especialidad = listaEspecialidad.get(jcEspecialidades.getSelectedIndex() - 1);
            //Rellenamos las listas de los médicos y las agendas correspondientes a la especialidad seleccionada
            listaMedico = usuarioController.buscarMedicosPorEspecialidad(especialidad.getId(), listaMedico);
            listaAgenda = agendaController.cargarAgendas("", -1, especialidad.getId());
            //Listamos los comboboxs con las listas
            listarMedicos(jcMedicos, listaMedico);
            listarAgendas(jcAgendas, listaAgenda);
            //Habilitamos los combobox 
            jcMedicos.setEnabled(true);
            jcAgendas.setEnabled(true);
        }
    }//GEN-LAST:event_jcEspecialidadesActionPerformed

    @SuppressWarnings("unchecked")
    private void jcMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcMedicosActionPerformed
        //Limpiamos la lista de agendas
        listaAgenda.clear();
        //Corroboramos que se haya seleccionado un médico
        if (jcMedicos.getSelectedIndex() > 0) {
            //Creamos el objeto medico con el médico seleccionado
            medico = listaMedico.get(jcMedicos.getSelectedIndex() - 1);
            //Rellenamos la lista con las agendas correspondientes al médico seleccionado
            listaAgenda = agendaController.cargarAgendas("", medico.getId(), -1);
            //Listamos el combobox con la lista
            listarAgendas(jcAgendas, listaAgenda);
        } else {
            //Si no selecciona ningun médico
            //Rellenamos la lista de las agendas con la especialidad seleccionada
            listaAgenda = agendaController.cargarAgendas("", -1, especialidad.getId());
            //Listamos el combobox con la lista de agendas
            listarAgendas(jcAgendas, listaAgenda);
        }
    }//GEN-LAST:event_jcMedicosActionPerformed

    @SuppressWarnings("unchecked")
    private void btnBuscarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTurnoActionPerformed
        //Corroboramos que se haya seleccionado una agenda
        if (jcAgendas.getSelectedIndex() > 0) {
            jdBuscarTurno.dispose();
            //Buscamos el paciente
            listaPaciente.clear();
            listaPaciente = pacienteController.cargarPacientes(String.valueOf(idPacienteFocus), "");
            //creamos el objeto paciente con los datos del paciente seleccionado
            paciente = listaPaciente.get(0);
            //Creamos el objeto agenda con los datos de la agenda seleccionada
            agenda = listaAgenda.get(jcAgendas.getSelectedIndex() - 1);//Resto uno porque al jcAgenda le agreamos el item "" (Vacío)
            TurnosAlta tf = new TurnosAlta(paciente, agenda);
            pnlContenido.removeAll();
            tf.setSize(1043, 500);
            pnlContenido.add(tf);
            pnlContenido.updateUI();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una agenda.", "Turnos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarTurnoActionPerformed

    private void btnCerrarABMPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarABMPacienteActionPerformed
        idPacienteFocus = -1;
        limpiarDatosPaciente();
        limpiarDatosBarraPaciente();
        tblPaciente.clearSelection();
        jdAgregarPaciente.dispose();
    }//GEN-LAST:event_btnCerrarABMPacienteActionPerformed

    @SuppressWarnings("unchecked")
    private void btnGuardarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPacienteActionPerformed
        if (tfNuevoNombre.getText().equals("") || tfNuevoApellido.getText().equals("") || tfNuevoDni.getText().equals("") || tfNuevaDireccion.getText().equals("") || tfNuevoCelular.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar paciente. Uno o más campos vacíos.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
        } else {
            String datos[] = {tfNuevoDni.getText(), tfNuevoNombre.getText(), tfNuevoApellido.getText(), tfNuevaDireccion.getText(), tfNuevoCelular.getText(), String.valueOf(cbNuevoGenero.getSelectedIndex() + 1)};
            int res;
            if (idPacienteFocus > 0) {
                res = PacienteController.editarPaciente(idPacienteFocus, datos);
            } else {
                res = PacienteController.agregarPaciente(datos);
            }
            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se agregó correctamente el Paciente.", "AGREGAR", JOptionPane.INFORMATION_MESSAGE);
                listaPaciente.clear();
                listaPaciente = pacienteController.cargarPacientes("", "");
                reloadInfoPacientes(listaPaciente);
                idPacienteFocus = -1;
                jdAgregarPaciente.dispose();
                limpiarDatosPaciente();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un ERROR.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarPacienteActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAñadirPaciente;
  private javax.swing.JButton btnBuscarPacient;
  private javax.swing.JButton btnBuscarTurno;
  private javax.swing.JButton btnCerrarABMPaciente;
  private javax.swing.JButton btnCerrarPnlBuscarTurno;
  private javax.swing.JButton btnCrearTurno;
  private javax.swing.JButton btnEditarPaciente;
  private javax.swing.JButton btnEliminarPaciente;
  private javax.swing.JButton btnGuardarPaciente;
  private javax.swing.JButton btnVolverMenu;
  private javax.swing.JComboBox<String> cbNuevoGenero;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSeparator jSeparator3;
  private javax.swing.JComboBox<String> jcAgendas;
  private javax.swing.JComboBox<String> jcEspecialidades;
  private javax.swing.JComboBox<String> jcMedicos;
  private javax.swing.JDialog jdAgregarPaciente;
  private javax.swing.JDialog jdBuscarTurno;
  private javax.swing.JLabel lblAgenda;
  private javax.swing.JLabel lblApellido;
  private javax.swing.JLabel lblApellido1;
  private javax.swing.JLabel lblApellidoPaciente;
  private javax.swing.JLabel lblBusqueda;
  private javax.swing.JLabel lblCelular;
  private javax.swing.JLabel lblCelular1;
  private javax.swing.JLabel lblCelularPaciente;
  private javax.swing.JLabel lblDNI;
  private javax.swing.JLabel lblDireccion;
  private javax.swing.JLabel lblDireccion1;
  private javax.swing.JLabel lblDireccionPaciente;
  private javax.swing.JLabel lblDni1;
  private javax.swing.JLabel lblDniBusq;
  private javax.swing.JLabel lblDniPaciente;
  private javax.swing.JLabel lblEspecialidad;
  private javax.swing.JLabel lblGenero;
  private javax.swing.JLabel lblGenero1;
  private javax.swing.JLabel lblGeneroPaciente;
  private javax.swing.JLabel lblImagePaciente;
  private javax.swing.JLabel lblMedico;
  private javax.swing.JLabel lblNombre;
  private javax.swing.JLabel lblNombre1;
  private javax.swing.JLabel lblNombrePaciente;
  private javax.swing.JLabel lblTitulo;
  private javax.swing.JPanel pnlAgregarPaciente;
  private javax.swing.JPanel pnlBuscarTurno;
  private javax.swing.JPanel pnlContenido;
  private javax.swing.JPanel pnlDatosPaciente;
  private javax.swing.JPanel pnlListaPacientes;
  private javax.swing.JScrollPane spTblPaciente;
  private javax.swing.JTable tblPaciente;
  private javax.swing.JTextField tfDniBusq;
  private javax.swing.JTextField tfNuevaDireccion;
  private javax.swing.JTextField tfNuevoApellido;
  private javax.swing.JTextField tfNuevoCelular;
  private javax.swing.JTextField tfNuevoDni;
  private javax.swing.JTextField tfNuevoNombre;
  // End of variables declaration//GEN-END:variables
}
