package vista;

import controlador.AgendaController;
import controlador.EspecialidadController;
import controlador.UsuarioController;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Especialidad;
import modelo.Rol;
import modelo.Usuario;

public final class UsuariosABM extends javax.swing.JFrame {

    Agenda agenda;
    AgendaController agendaController = new AgendaController();
    @SuppressWarnings("unchecked")
    ArrayList<Agenda> listaAgenda = agendaController.cargarAgendas("", -1, -1);

    Usuario usuario;
    UsuarioController usuarioController = new UsuarioController();
    ArrayList<Usuario> listaUsuario = usuarioController.cargarUsuarios("");
    Rol rol;
    ArrayList<Rol> listaRol = usuarioController.cargarRoles();

    Especialidad especialidad;
    EspecialidadController especialidadController = new EspecialidadController();
    @SuppressWarnings("unchecked")
    ArrayList<Especialidad> listaEspecialidad = especialidadController.cargarEspecialidad();

    DefaultTableModel modelo;
    String[] encabezadoMedicos = {"Nombre", "Apellido", "Nombre usuario", "DNI", "Celular", "Género", "Rol"};

    int idUserFocus;

    @SuppressWarnings("unchecked")
    public UsuariosABM() {
        initComponents();
        habilitarDatosBarraExtraMedico(false);
        reloadInfoUsuarios(listaUsuario);
        jdAgregarUsuario.setLocationRelativeTo(this);
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
        //Centramos los datos ~~ no funciona
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(modelocentrar);
        }
        table.setModel(modelo);
    }

    public void reloadInfoUsuarios(ArrayList<Usuario> listaMedicoN) {
        reloadTable(tblUsuario, encabezadoMedicos);
        Object[] fila = new Object[7];

        for (int i = 0; i < listaMedicoN.size(); i++) {
            usuario = listaMedicoN.get(i);
            fila[0] = usuario.getNombre();
            fila[1] = usuario.getApellido();
            fila[2] = usuario.getNombre_usuario();
            fila[3] = usuario.getDNI();
            fila[4] = usuario.getCelular();
            fila[5] = usuarioController.nombreSexo(usuario.getId_genero());
            fila[6] = usuario.getRol();
            modelo.addRow(fila);

        }
        tblUsuario.setModel(modelo);
    }

    public void mostrarDatosBarraUsuarios(Usuario usuario) {
        especialidad = especialidadController.buscarEspecialidad(usuario.getId_especialidad());
        lblNombreUsuario.setText(usuario.getNombre());
        lblApellidoUsuario.setText(usuario.getApellido());
        lblCelularUsuario.setText(String.valueOf(usuario.getCelular()));
        lblGeneroUsuario.setText(usuarioController.nombreSexo(usuario.getId_genero()));
        lblImageUsuario.setEnabled(true);
        if (!usuario.getRol().equals("Administrativo")) {
            habilitarDatosBarraExtraMedico(true);
            lblMatriculaMedico.setText(String.valueOf(usuario.getMatricula()));
            lblEspecialidadMedico.setText(especialidad.getNombre());
        } else {
            habilitarDatosBarraExtraMedico(false);
        }

        String genero = usuarioController.nombreSexo(usuario.getId_genero());
        switch (genero) {
            case "Masculino" ->
                lblImageUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png")));
            case "Femenino" ->
                lblImageUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mujer.png")));
            case "Otro" ->
                lblImageUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/otro.png")));
            default -> {
            }
        }
    }

    public void limpiarDatosBarraUsuarios() {
        lblNombreUsuario.setText("");
        lblApellidoUsuario.setText("");
        lblMatriculaMedico.setText("");
        lblEspecialidadMedico.setText("");
        lblCelularUsuario.setText("");
        lblGeneroUsuario.setText("");
        lblImageUsuario.setEnabled(false);
    }

    int obtenerIndiceItem(JComboBox combobox, String nombre) {
        for (int i = 0; i < combobox.getItemCount(); i++) {
            if (combobox.getItemAt(i).equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public void mostrarDatosUsuario(Usuario usuario) {
        String genero = usuarioController.nombreSexo(usuario.getId_genero());
        tfNuevoNombre.setText(usuario.getNombre());
        tfNuevoApellido.setText(usuario.getApellido());
        tfNuevoDni.setText(String.valueOf(usuario.getDNI()));
        tfNuevoNombreUsuario.setText(usuario.getNombre_usuario());
        tfNuevaClave.setText(usuario.getClave());
        tfNuevaClaveRepetida.setText(usuario.getClave());
        tfNuevoCelular.setText(String.valueOf(usuario.getCelular()));
        cbNuevoGenero.setSelectedIndex(obtenerIndiceItem(cbNuevoGenero, genero));
        cbNuevoRol.setSelectedIndex(obtenerIndiceItem(cbNuevoRol, usuario.getRol()));
        if (!usuario.getRol().equals("Administrativo")) {
            habilitarDatosExtraNuevoMedico(true);
            especialidad = especialidadController.buscarEspecialidad(usuario.getId_especialidad());
            tfNuevaMatricula.setText(String.valueOf(usuario.getMatricula()));
            cbNuevaEspecialidad.setSelectedIndex(obtenerIndiceItem(cbNuevaEspecialidad, especialidad.getNombre()));
        }
    }

    public void limpiarDatosAgregarUsuario() {
        tfNuevoNombre.setText("");
        tfNuevoApellido.setText("");
        tfNuevoDni.setText("");
        tfNuevoNombreUsuario.setText("");
        tfNuevaClave.setText("");
        tfNuevaClaveRepetida.setText("");
        tfNuevoCelular.setText("");
        cbNuevoGenero.setSelectedIndex(0);
        cbNuevoRol.setSelectedIndex(0);
        tfNuevaMatricula.setText("");
        cbNuevaEspecialidad.setSelectedIndex(0);
    }

    public void listarEspecialidades() {
        cbNuevaEspecialidad.removeAllItems();
        cbNuevaEspecialidad.addItem("");
        for (int i = 0; i < listaEspecialidad.size(); i++) {
            especialidad = listaEspecialidad.get(i);
            String nespecialidad = especialidad.getNombre();
            cbNuevaEspecialidad.addItem(nespecialidad);
        }
    }

    public void listarRol() {
        cbNuevoRol.removeAllItems();
        for (int i = 0; i < listaRol.size(); i++) {
            rol = listaRol.get(i);
            cbNuevoRol.addItem(rol.getNombre());
        }
    }

    public void listarAgendas(JComboBox combobox, ArrayList<Agenda> listaAgendaN) {
        combobox.removeAllItems();
        combobox.addItem("");
        for (int i = 0; i < listaAgendaN.size(); i++) {
            agenda = listaAgendaN.get(i);
            String nagenda = agenda.getNombre();
            combobox.addItem(nagenda);
        }
    }

    public void habilitarDatosBarraExtraMedico(boolean decision) {
        lblMatricula.setVisible(decision);
        lblMatriculaMedico.setVisible(decision);
        lblEsp.setVisible(decision);
        lblEspecialidadMedico.setVisible(decision);
    }

    public void habilitarDatosExtraNuevoMedico(boolean decision) {
        lblMatricula1.setVisible(decision);
        lblEspecialidad1.setVisible(decision);
        tfNuevaMatricula.setVisible(decision);
        cbNuevaEspecialidad.setVisible(decision);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdAgregarUsuario = new javax.swing.JDialog();
        pnlAgregarUsuario = new javax.swing.JPanel();
        btnGuardarUsuario = new javax.swing.JButton();
        btnCerrarFormUsuario = new javax.swing.JButton();
        cbNuevoGenero = new javax.swing.JComboBox<>();
        lblGenero1 = new javax.swing.JLabel();
        lblCelular1 = new javax.swing.JLabel();
        tfNuevoCelular = new javax.swing.JTextField();
        lblEspecialidad1 = new javax.swing.JLabel();
        lblMatricula1 = new javax.swing.JLabel();
        tfNuevaMatricula = new javax.swing.JTextField();
        tfNuevoApellido = new javax.swing.JTextField();
        lblApellido1 = new javax.swing.JLabel();
        lblNombre1 = new javax.swing.JLabel();
        tfNuevoNombre = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        cbNuevaEspecialidad = new javax.swing.JComboBox<>();
        lblDni = new javax.swing.JLabel();
        tfNuevoDni = new javax.swing.JTextField();
        lblDni2 = new javax.swing.JLabel();
        tfNuevoNombreUsuario = new javax.swing.JTextField();
        lblCelular2 = new javax.swing.JLabel();
        tfNuevaClave = new javax.swing.JTextField();
        lblCelular3 = new javax.swing.JLabel();
        tfNuevaClaveRepetida = new javax.swing.JTextField();
        cbNuevoRol = new javax.swing.JComboBox<>();
        lblRol = new javax.swing.JLabel();
        pnlDatosUsuario = new javax.swing.JPanel();
        lblImageUsuario = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblApellidoUsuario = new javax.swing.JLabel();
        lblGeneroUsuario = new javax.swing.JLabel();
        lblGenero = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblCelularUsuario = new javax.swing.JLabel();
        separadorUsuario = new javax.swing.JSeparator();
        lblDniBusq = new javax.swing.JLabel();
        btnBuscarUsuario = new javax.swing.JButton();
        lblEspecialidadMedico = new javax.swing.JLabel();
        lblEsp = new javax.swing.JLabel();
        lblMatriculaMedico = new javax.swing.JLabel();
        lblMatricula = new javax.swing.JLabel();
        tfDniBusq = new javax.swing.JTextField();
        pnlListaUsuario = new javax.swing.JPanel();
        spTblUsuario = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        btnAñadirUsuario = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        btnVolverMenu = new javax.swing.JButton();

        jdAgregarUsuario.setMinimumSize(new java.awt.Dimension(450, 400));
        jdAgregarUsuario.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jdAgregarUsuario.setUndecorated(true);

        pnlAgregarUsuario.setBackground(new java.awt.Color(255, 255, 255));
        pnlAgregarUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlAgregarUsuario.setMaximumSize(new java.awt.Dimension(450, 500));
        pnlAgregarUsuario.setMinimumSize(new java.awt.Dimension(450, 500));
        pnlAgregarUsuario.setPreferredSize(new java.awt.Dimension(450, 500));

        btnGuardarUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnGuardarUsuario.setText("Guardar");
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });

        btnCerrarFormUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCerrarFormUsuario.setText("Cerrar");
        btnCerrarFormUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarFormUsuarioActionPerformed(evt);
            }
        });

        cbNuevoGenero.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cbNuevoGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "Otro" }));
        cbNuevoGenero.setMaximumSize(new java.awt.Dimension(200, 25));
        cbNuevoGenero.setMinimumSize(new java.awt.Dimension(200, 25));
        cbNuevoGenero.setPreferredSize(new java.awt.Dimension(200, 25));
        cbNuevoGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNuevoGeneroActionPerformed(evt);
            }
        });

        lblGenero1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblGenero1.setText("Género:");
        lblGenero1.setMaximumSize(new java.awt.Dimension(150, 25));
        lblGenero1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblGenero1.setPreferredSize(new java.awt.Dimension(150, 25));

        lblCelular1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblCelular1.setText("Celular:");
        lblCelular1.setMaximumSize(new java.awt.Dimension(150, 25));
        lblCelular1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblCelular1.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevoCelular.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoCelular.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoCelular.setPreferredSize(new java.awt.Dimension(200, 25));

        lblEspecialidad1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblEspecialidad1.setText("Especialidad:");
        lblEspecialidad1.setMaximumSize(new java.awt.Dimension(150, 25));
        lblEspecialidad1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblEspecialidad1.setPreferredSize(new java.awt.Dimension(150, 25));

        lblMatricula1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblMatricula1.setText("Matrícula:");
        lblMatricula1.setMaximumSize(new java.awt.Dimension(150, 25));
        lblMatricula1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblMatricula1.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevaMatricula.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevaMatricula.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevaMatricula.setPreferredSize(new java.awt.Dimension(200, 25));

        tfNuevoApellido.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoApellido.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoApellido.setPreferredSize(new java.awt.Dimension(200, 25));

        lblApellido1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblApellido1.setText("Apellido:");
        lblApellido1.setMaximumSize(new java.awt.Dimension(110, 25));
        lblApellido1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblApellido1.setPreferredSize(new java.awt.Dimension(150, 25));

        lblNombre1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombre1.setText("Nombre:");
        lblNombre1.setMaximumSize(new java.awt.Dimension(150, 25));
        lblNombre1.setMinimumSize(new java.awt.Dimension(150, 25));
        lblNombre1.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevoNombre.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombre.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombre.setPreferredSize(new java.awt.Dimension(200, 25));

        lblTitulo.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblTitulo.setText("Agregar usuario");

        cbNuevaEspecialidad.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cbNuevaEspecialidad.setMaximumSize(new java.awt.Dimension(200, 25));
        cbNuevaEspecialidad.setMinimumSize(new java.awt.Dimension(200, 25));
        cbNuevaEspecialidad.setPreferredSize(new java.awt.Dimension(200, 25));

        lblDni.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDni.setText("DNI:");
        lblDni.setMaximumSize(new java.awt.Dimension(150, 25));
        lblDni.setMinimumSize(new java.awt.Dimension(150, 25));
        lblDni.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevoDni.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoDni.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoDni.setPreferredSize(new java.awt.Dimension(200, 25));

        lblDni2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDni2.setText("Nombre usuario:");
        lblDni2.setMaximumSize(new java.awt.Dimension(150, 25));
        lblDni2.setMinimumSize(new java.awt.Dimension(150, 25));
        lblDni2.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevoNombreUsuario.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombreUsuario.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevoNombreUsuario.setPreferredSize(new java.awt.Dimension(200, 25));

        lblCelular2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblCelular2.setText("Clave:");
        lblCelular2.setMaximumSize(new java.awt.Dimension(150, 25));
        lblCelular2.setMinimumSize(new java.awt.Dimension(150, 25));
        lblCelular2.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevaClave.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevaClave.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevaClave.setPreferredSize(new java.awt.Dimension(200, 25));

        lblCelular3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblCelular3.setText("Repetir clave:");
        lblCelular3.setMaximumSize(new java.awt.Dimension(150, 25));
        lblCelular3.setMinimumSize(new java.awt.Dimension(150, 25));
        lblCelular3.setPreferredSize(new java.awt.Dimension(150, 25));

        tfNuevaClaveRepetida.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevaClaveRepetida.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevaClaveRepetida.setPreferredSize(new java.awt.Dimension(200, 25));

        cbNuevoRol.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cbNuevoRol.setMaximumSize(new java.awt.Dimension(200, 25));
        cbNuevoRol.setMinimumSize(new java.awt.Dimension(200, 25));
        cbNuevoRol.setPreferredSize(new java.awt.Dimension(200, 25));
        cbNuevoRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNuevoRolActionPerformed(evt);
            }
        });

        lblRol.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblRol.setText("Rol:");
        lblRol.setMaximumSize(new java.awt.Dimension(150, 25));
        lblRol.setMinimumSize(new java.awt.Dimension(150, 25));
        lblRol.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout pnlAgregarUsuarioLayout = new javax.swing.GroupLayout(pnlAgregarUsuario);
        pnlAgregarUsuario.setLayout(pnlAgregarUsuarioLayout);
        pnlAgregarUsuarioLayout.setHorizontalGroup(
            pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                                .addComponent(btnCerrarFormUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardarUsuario))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAgregarUsuarioLayout.createSequentialGroup()
                                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGenero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatricula1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEspecialidad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblApellido1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblNombre1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(lblDni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblCelular2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbNuevoGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNuevaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbNuevaEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfNuevoApellido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfNuevoNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfNuevoDni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tfNuevoNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNuevaClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                                .addComponent(lblCelular1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfNuevoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                                        .addComponent(lblCelular3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                                        .addComponent(lblRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(27, 27, 27)))
                                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbNuevoRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNuevaClaveRepetida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(37, 37, 37))))
        );
        pnlAgregarUsuarioLayout.setVerticalGroup(
            pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarUsuarioLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(4, 4, 4)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addComponent(lblNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lblApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfNuevoNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addComponent(tfNuevoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(tfNuevoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfNuevoDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCelular2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNuevaClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCelular3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNuevaClaveRepetida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNuevoCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCelular1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbNuevoGenero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenero1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNuevoRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addComponent(tfNuevaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbNuevaEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarUsuarioLayout.createSequentialGroup()
                        .addComponent(lblMatricula1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEspecialidad1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 27, Short.MAX_VALUE)
                .addGroup(pnlAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarUsuario)
                    .addComponent(btnCerrarFormUsuario))
                .addContainerGap())
        );

        javax.swing.GroupLayout jdAgregarUsuarioLayout = new javax.swing.GroupLayout(jdAgregarUsuario.getContentPane());
        jdAgregarUsuario.getContentPane().setLayout(jdAgregarUsuarioLayout);
        jdAgregarUsuarioLayout.setHorizontalGroup(
            jdAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAgregarUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jdAgregarUsuarioLayout.setVerticalGroup(
            jdAgregarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAgregarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        pnlDatosUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDatosUsuario.setMaximumSize(new java.awt.Dimension(780, 125));
        pnlDatosUsuario.setMinimumSize(new java.awt.Dimension(780, 125));
        pnlDatosUsuario.setPreferredSize(new java.awt.Dimension(780, 125));

        lblImageUsuario.setEnabled(false);
        lblImageUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hombre.png"))); // NOI18N

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

        lblNombreUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblNombreUsuario.setMaximumSize(new java.awt.Dimension(125, 20));
        lblNombreUsuario.setMinimumSize(new java.awt.Dimension(125, 20));
        lblNombreUsuario.setPreferredSize(new java.awt.Dimension(125, 20));

        lblApellidoUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblApellidoUsuario.setMaximumSize(new java.awt.Dimension(125, 20));
        lblApellidoUsuario.setMinimumSize(new java.awt.Dimension(125, 20));
        lblApellidoUsuario.setPreferredSize(new java.awt.Dimension(125, 20));

        lblGeneroUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblGeneroUsuario.setMaximumSize(new java.awt.Dimension(125, 20));
        lblGeneroUsuario.setMinimumSize(new java.awt.Dimension(125, 20));
        lblGeneroUsuario.setPreferredSize(new java.awt.Dimension(125, 20));

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

        lblCelularUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblCelularUsuario.setMaximumSize(new java.awt.Dimension(125, 20));
        lblCelularUsuario.setMinimumSize(new java.awt.Dimension(125, 20));
        lblCelularUsuario.setPreferredSize(new java.awt.Dimension(125, 20));

        lblDniBusq.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblDniBusq.setText("DNI:");
        lblDniBusq.setMaximumSize(new java.awt.Dimension(70, 25));
        lblDniBusq.setMinimumSize(new java.awt.Dimension(70, 25));
        lblDniBusq.setPreferredSize(new java.awt.Dimension(70, 25));

        btnBuscarUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnBuscarUsuario.setText("Buscar");
        btnBuscarUsuario.setMaximumSize(new java.awt.Dimension(65, 25));
        btnBuscarUsuario.setMinimumSize(new java.awt.Dimension(65, 25));
        btnBuscarUsuario.setPreferredSize(new java.awt.Dimension(65, 25));
        btnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioActionPerformed(evt);
            }
        });

        lblEspecialidadMedico.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblEspecialidadMedico.setMaximumSize(new java.awt.Dimension(125, 20));
        lblEspecialidadMedico.setMinimumSize(new java.awt.Dimension(125, 20));
        lblEspecialidadMedico.setPreferredSize(new java.awt.Dimension(125, 20));

        lblEsp.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblEsp.setText("Especialidad:");
        lblEsp.setMaximumSize(new java.awt.Dimension(70, 20));
        lblEsp.setMinimumSize(new java.awt.Dimension(70, 20));
        lblEsp.setPreferredSize(new java.awt.Dimension(70, 20));

        lblMatriculaMedico.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblMatriculaMedico.setMaximumSize(new java.awt.Dimension(125, 20));
        lblMatriculaMedico.setMinimumSize(new java.awt.Dimension(125, 20));
        lblMatriculaMedico.setPreferredSize(new java.awt.Dimension(125, 20));

        lblMatricula.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblMatricula.setText("Matrícula:");
        lblMatricula.setMaximumSize(new java.awt.Dimension(70, 20));
        lblMatricula.setMinimumSize(new java.awt.Dimension(70, 20));
        lblMatricula.setPreferredSize(new java.awt.Dimension(70, 20));

        tfDniBusq.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfDniBusq.setMaximumSize(new java.awt.Dimension(125, 25));
        tfDniBusq.setMinimumSize(new java.awt.Dimension(125, 25));
        tfDniBusq.setPreferredSize(new java.awt.Dimension(125, 25));

        javax.swing.GroupLayout pnlDatosUsuarioLayout = new javax.swing.GroupLayout(pnlDatosUsuario);
        pnlDatosUsuario.setLayout(pnlDatosUsuarioLayout);
        pnlDatosUsuarioLayout.setHorizontalGroup(
            pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImageUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separadorUsuario)
                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                        .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(lblCelularUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(lblEsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEspecialidadMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatriculaMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 22, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        pnlDatosUsuarioLayout.setVerticalGroup(
            pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                        .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblApellidoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlDatosUsuarioLayout.createSequentialGroup()
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCelular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCelularUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatricula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMatriculaMedico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblGeneroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEspecialidadMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(separadorUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfDniBusq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImageUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
        );

        pnlListaUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlListaUsuario.setMaximumSize(new java.awt.Dimension(478, 442));
        pnlListaUsuario.setMinimumSize(new java.awt.Dimension(478, 442));

        tblUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseEntered(evt);
            }
        });
        spTblUsuario.setViewportView(tblUsuario);

        btnAñadirUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAñadirUsuario.setText("Añadir");
        btnAñadirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirUsuarioActionPerformed(evt);
            }
        });

        btnEditarUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEliminarUsuario.setText("Eliminar");
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        btnVolverMenu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverMenu.setText("Volver al Inicio");
        btnVolverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListaUsuarioLayout = new javax.swing.GroupLayout(pnlListaUsuario);
        pnlListaUsuario.setLayout(pnlListaUsuarioLayout);
        pnlListaUsuarioLayout.setHorizontalGroup(
            pnlListaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListaUsuarioLayout.createSequentialGroup()
                        .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAñadirUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarUsuario))
                    .addComponent(spTblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlListaUsuarioLayout.setVerticalGroup(
            pnlListaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlListaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAñadirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlListaUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 1043, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlListaUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void btnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioActionPerformed
        String dni = tfDniBusq.getText();
        listaUsuario.clear();
        if (dni.equals("")) {
            limpiarDatosBarraUsuarios();
            listaUsuario = usuarioController.cargarUsuarios("");
            idUserFocus = -1;
        } else {
            listaUsuario = usuarioController.cargarUsuarios(dni);
        }
        reloadInfoUsuarios(listaUsuario);
    }//GEN-LAST:event_btnBuscarUsuarioActionPerformed

    private void btnAñadirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirUsuarioActionPerformed
        lblTitulo.setText("Agregar usuario");
        idUserFocus = -1;
        listarEspecialidades();
        listarRol();
        jdAgregarUsuario.setSize(450, 500);
        jdAgregarUsuario.setVisible(true);
        habilitarDatosExtraNuevoMedico(false);
    }//GEN-LAST:event_btnAñadirUsuarioActionPerformed

    @SuppressWarnings("unchecked")
    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        if (tblUsuario.getSelectedRow() >= 0) {
            usuario = listaUsuario.get(tblUsuario.getSelectedRow());
            int rta = JOptionPane.showConfirmDialog(this, "¿Desea borrar este médico?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (rta == JOptionPane.YES_OPTION) {
                UsuarioController.borrarUsuario(usuario.getId());
                listaUsuario.clear();
                listaUsuario = usuarioController.cargarUsuarios("");
                reloadInfoUsuarios(listaUsuario);
                limpiarDatosBarraUsuarios();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un médico.", "Médico", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        lblTitulo.setText("Editar usuario");
        if (tblUsuario.getSelectedRow() >= 0) {
            usuario = listaUsuario.get(tblUsuario.getSelectedRow());
            idUserFocus = usuario.getId();
            listarEspecialidades();
            listarRol();
            mostrarDatosUsuario(usuario);
            jdAgregarUsuario.setSize(450, 500);
            jdAgregarUsuario.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un usuario.", "Usuario", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        usuario = listaUsuario.get(tblUsuario.getSelectedRow());
        idUserFocus = usuario.getId();
        mostrarDatosBarraUsuarios(usuario);
    }//GEN-LAST:event_tblUsuarioMouseClicked

    private void btnVolverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverMenuActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverMenuActionPerformed

    private void tblUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUsuarioMouseEntered

    @SuppressWarnings("unchecked")
    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        //Verificamos que no haya ningun campo vacio
        if (tfNuevoNombre.getText().equals("") || tfNuevoApellido.getText().equals("") || tfNuevoDni.getText().equals("") || tfNuevoNombreUsuario.getText().equals("")
                || tfNuevaClave.getText().equals("") || tfNuevaClaveRepetida.getText().equals("") || tfNuevoCelular.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario. Uno o más campos vacíos.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
        } else {
            // verificamos que si el rol elegido no es administrativo, se completen los campos de la matricula y la especialidad
            if (!cbNuevoRol.getSelectedItem().equals("Administrativo") && (tfNuevaMatricula.getText().equals("") || cbNuevaEspecialidad.getSelectedItem().equals(""))) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario. Uno o más campos vacíos.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
            } else {
                //Veriificamos que las claves ingresadas sean iguales
                if (!tfNuevaClave.getText().equals(tfNuevaClaveRepetida.getText())) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas deben ser iguales.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
                } else {
                    //Creamos los String que van a albergar la id de la especialidad y la matricula, las inicializamos como nulas por si el rol elegido es administrativo
                    String idespecialidad = null;
                    String matricula = null;
                    //Si el rol elegido no es administrativo hay que completar las variables anteriores
                    if (cbNuevoRol.getSelectedIndex() > 0) {
                        especialidad = listaEspecialidad.get(cbNuevaEspecialidad.getSelectedIndex() - 1);
                        idespecialidad = String.valueOf(especialidad.getId());
                        matricula = tfNuevaMatricula.getText();
                    }
                    // Creamos el objeto rol con uno de la listaRol segun el seleccionado
                    rol = listaRol.get(cbNuevoRol.getSelectedIndex());
                    //Guardamos los datos en el orden necesario para guardar por statement
                    String datos[] = {String.valueOf(rol.getId()), tfNuevoDni.getText(), tfNuevoNombreUsuario.getText(), tfNuevaClave.getText(), tfNuevoNombre.getText(), tfNuevoApellido.getText(),
                        tfNuevoCelular.getText(), String.valueOf(cbNuevoGenero.getSelectedIndex() + 1), idespecialidad, matricula};
                    int res;
                    //Si no hay ningun user focus entonces hay que agregar un usuario, por el contrario hay que editarlo
                    if (idUserFocus > 0) {
                        res = UsuarioController.editarUsuario(datos, idUserFocus);
                    } else {
                        res = UsuarioController.agregarUsuario(datos);
                    }
                    //Verificamos que los datos hayan sido guardados
                    if (res == 1) {
                        //Si fueron guardados pasamos a informalo y a volver todo a su estado base
                        JOptionPane.showMessageDialog(null, "Datos guardados correctamente.", "AGREGAR", JOptionPane.INFORMATION_MESSAGE);
                        listaUsuario.clear();
                        listaUsuario = usuarioController.cargarUsuarios("");
                        reloadInfoUsuarios(listaUsuario);
                        idUserFocus = -1;
                        jdAgregarUsuario.dispose();
                        limpiarDatosAgregarUsuario();
                        limpiarDatosBarraUsuarios();
                        habilitarDatosExtraNuevoMedico(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrió un ERROR.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void btnCerrarFormUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarFormUsuarioActionPerformed
        idUserFocus = -1;
        limpiarDatosAgregarUsuario();
        limpiarDatosBarraUsuarios();
        jdAgregarUsuario.dispose();
    }//GEN-LAST:event_btnCerrarFormUsuarioActionPerformed

    private void cbNuevoRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNuevoRolActionPerformed
        habilitarDatosExtraNuevoMedico(cbNuevoRol.getSelectedIndex() > 0);
    }//GEN-LAST:event_cbNuevoRolActionPerformed

    private void cbNuevoGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNuevoGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNuevoGeneroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadirUsuario;
    private javax.swing.JButton btnBuscarUsuario;
    private javax.swing.JButton btnCerrarFormUsuario;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnGuardarUsuario;
    private javax.swing.JButton btnVolverMenu;
    private javax.swing.JComboBox<String> cbNuevaEspecialidad;
    private javax.swing.JComboBox<String> cbNuevoGenero;
    private javax.swing.JComboBox<String> cbNuevoRol;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JDialog jdAgregarUsuario;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblApellido1;
    private javax.swing.JLabel lblApellidoUsuario;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCelular1;
    private javax.swing.JLabel lblCelular2;
    private javax.swing.JLabel lblCelular3;
    private javax.swing.JLabel lblCelularUsuario;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblDni2;
    private javax.swing.JLabel lblDniBusq;
    private javax.swing.JLabel lblEsp;
    private javax.swing.JLabel lblEspecialidad1;
    private javax.swing.JLabel lblEspecialidadMedico;
    private javax.swing.JLabel lblGenero;
    private javax.swing.JLabel lblGenero1;
    private javax.swing.JLabel lblGeneroUsuario;
    private javax.swing.JLabel lblImageUsuario;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblMatricula1;
    private javax.swing.JLabel lblMatriculaMedico;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAgregarUsuario;
    private javax.swing.JPanel pnlDatosUsuario;
    private javax.swing.JPanel pnlListaUsuario;
    private javax.swing.JSeparator separadorUsuario;
    private javax.swing.JScrollPane spTblUsuario;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField tfDniBusq;
    private javax.swing.JTextField tfNuevaClave;
    private javax.swing.JTextField tfNuevaClaveRepetida;
    private javax.swing.JTextField tfNuevaMatricula;
    private javax.swing.JTextField tfNuevoApellido;
    private javax.swing.JTextField tfNuevoCelular;
    private javax.swing.JTextField tfNuevoDni;
    private javax.swing.JTextField tfNuevoNombre;
    private javax.swing.JTextField tfNuevoNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
