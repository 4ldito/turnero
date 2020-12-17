package vista;

import controlador.EspecialidadController;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Especialidad;

public class EspecialidadesABM extends javax.swing.JFrame {

    EspecialidadController especialidadController = new EspecialidadController();
    Especialidad especialidad;
    ArrayList<Especialidad> listaEspecialidades;

    int idEspecialidadFocus;

    public EspecialidadesABM() {
        initComponents();
        this.setLocationRelativeTo(null);
        reload();
    }

    public void centrarDatos() {
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        tblEspecialidades.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
    }

    @SuppressWarnings("unchecked")
    private void reload() { // carga la tabla de especialidades

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nombre");

        listaEspecialidades = especialidadController.cargarEspecialidad();
        Object[] fila = new Object[2];

        for (int i = 0; i < listaEspecialidades.size(); i++) {
            especialidad = listaEspecialidades.get(i);
            fila[0] = especialidad.getNombre();
            modelo.addRow(fila);
        }
        tblEspecialidades.setModel(modelo);
        centrarDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdAgregarEspecialidad = new javax.swing.JDialog();
        pnlAgregarEspecialidad = new javax.swing.JPanel();
        btnGuardarEspecialidad = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblNombre1 = new javax.swing.JLabel();
        tfNuevaEspecialidad = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        spTblPaciente = new javax.swing.JScrollPane();
        tblEspecialidades = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVolverInicio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jdAgregarEspecialidad.setLocationRelativeTo(this);
        jdAgregarEspecialidad.setMinimumSize(new java.awt.Dimension(400, 200));
        jdAgregarEspecialidad.setPreferredSize(new java.awt.Dimension(400, 200));

        pnlAgregarEspecialidad.setBackground(new java.awt.Color(255, 255, 255));
        pnlAgregarEspecialidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlAgregarEspecialidad.setMaximumSize(new java.awt.Dimension(400, 200));
        pnlAgregarEspecialidad.setMinimumSize(new java.awt.Dimension(400, 200));
        pnlAgregarEspecialidad.setPreferredSize(new java.awt.Dimension(400, 200));

        btnGuardarEspecialidad.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnGuardarEspecialidad.setText("Guardar");
        btnGuardarEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEspecialidadActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblNombre1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblNombre1.setText("Nombre:");
        lblNombre1.setMaximumSize(new java.awt.Dimension(70, 25));
        lblNombre1.setMinimumSize(new java.awt.Dimension(70, 25));
        lblNombre1.setPreferredSize(new java.awt.Dimension(70, 25));

        tfNuevaEspecialidad.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfNuevaEspecialidad.setMaximumSize(new java.awt.Dimension(200, 25));
        tfNuevaEspecialidad.setMinimumSize(new java.awt.Dimension(200, 25));
        tfNuevaEspecialidad.setPreferredSize(new java.awt.Dimension(200, 25));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Agregar Especialidad");

        javax.swing.GroupLayout pnlAgregarEspecialidadLayout = new javax.swing.GroupLayout(pnlAgregarEspecialidad);
        pnlAgregarEspecialidad.setLayout(pnlAgregarEspecialidadLayout);
        pnlAgregarEspecialidadLayout.setHorizontalGroup(
            pnlAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgregarEspecialidadLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAgregarEspecialidadLayout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarEspecialidad))
                    .addGroup(pnlAgregarEspecialidadLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlAgregarEspecialidadLayout.createSequentialGroup()
                        .addComponent(lblNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(tfNuevaEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
            .addComponent(jSeparator3)
        );
        pnlAgregarEspecialidadLayout.setVerticalGroup(
            pnlAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarEspecialidadLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(4, 4, 4)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNuevaEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarEspecialidad)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdAgregarEspecialidadLayout = new javax.swing.GroupLayout(jdAgregarEspecialidad.getContentPane());
        jdAgregarEspecialidad.getContentPane().setLayout(jdAgregarEspecialidadLayout);
        jdAgregarEspecialidadLayout.setHorizontalGroup(
            jdAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAgregarEspecialidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
        );
        jdAgregarEspecialidadLayout.setVerticalGroup(
            jdAgregarEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAgregarEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 190, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Especialidades");

        tblEspecialidades.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblEspecialidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        spTblPaciente.setViewportView(tblEspecialidades);

        btnAgregar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnVolverInicio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverInicio.setText("Volver al Inicio");
        btnVolverInicio.setToolTipText("Volver a Inicio");
        btnVolverInicio.setFocusPainted(false);
        btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverInicioActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Especialidades");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTblPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolverInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addGap(20, 20, 20)
                        .addComponent(btnEditar)
                        .addGap(20, 20, 20)
                        .addComponent(btnEliminar)))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(spTblPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnVolverInicio))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverInicioActionPerformed
        MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
        af.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverInicioActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        lblTitulo.setText("Agregar Especialidad");
        idEspecialidadFocus = -1;
        jdAgregarEspecialidad.setVisible(true);
        jdAgregarEspecialidad.setLocationRelativeTo(this);


    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tblEspecialidades.getSelectedRow() >= 0) {
            especialidad = (listaEspecialidades.get((tblEspecialidades.getSelectedRow())));
            idEspecialidadFocus = especialidad.getId();
            tfNuevaEspecialidad.setText(especialidad.getNombre());
            jdAgregarEspecialidad.setVisible(true);
            jdAgregarEspecialidad.setLocationRelativeTo(this);

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una especialidad.", "Especialidad", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (tblEspecialidades.getSelectedRow() >= 0) {
            int rta = JOptionPane.showConfirmDialog(this, "¿Desea borrar a esta especialidad?", "Borrar", JOptionPane.YES_NO_OPTION);
            if (rta == JOptionPane.YES_OPTION) {
                especialidad = (listaEspecialidades.get((tblEspecialidades.getSelectedRow())));
                idEspecialidadFocus = especialidad.getId();

                int res = especialidadController.borrarEspecialidad(idEspecialidadFocus);

                if (res == 1) {
                    JOptionPane.showMessageDialog(null, "Se borró correctamente la especialidad.", "Borrar", JOptionPane.INFORMATION_MESSAGE);
                    listaEspecialidades.clear();
                    reload();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error.", "Especialidades", JOptionPane.ERROR_MESSAGE);

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una especialidad.", "Especialidades", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEspecialidadActionPerformed
        if (tfNuevaEspecialidad.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar especialidad. Uno o más campos vacíos.", "AGREGAR", JOptionPane.ERROR_MESSAGE);
        } else {
            // guardo en un array de strings los datos que quiero verificar en el PreparedStatement
            String nombreEspecialidad = tfNuevaEspecialidad.getText().trim();
            String datos[] = {nombreEspecialidad.substring(0,1).toUpperCase() + nombreEspecialidad.substring(1).toLowerCase()};
            
            String msg;
            int res;
            // Si es mayor que 0, significa que va a editar, si no, que va a agregar una nueva especialidad
            if (idEspecialidadFocus > 0) {

                res = especialidadController.editarEspecialidad(datos, idEspecialidadFocus);
                msg = "Se editó correctamente la especialidad.";
            } else {
                res = especialidadController.agregarEspecialidad(datos);
                msg = "Se agregó correctamente la especialidad.";
            }
            // verifico si se guardó o no la especialidad y le aviso al usuario
            if (res == 1) {
                JOptionPane.showMessageDialog(null, msg, "Especialidades", JOptionPane.INFORMATION_MESSAGE);
                listaEspecialidades.clear();
                reload();
                idEspecialidadFocus = -1;
                jdAgregarEspecialidad.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un ERROR.", "Especialidades", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarEspecialidadActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        idEspecialidadFocus = -1;
        tblEspecialidades.clearSelection();
        jdAgregarEspecialidad.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarEspecialidad;
    private javax.swing.JButton btnVolverInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JDialog jdAgregarEspecialidad;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAgregarEspecialidad;
    private javax.swing.JScrollPane spTblPaciente;
    private javax.swing.JTable tblEspecialidades;
    private javax.swing.JTextField tfNuevaEspecialidad;
    // End of variables declaration//GEN-END:variables
}
