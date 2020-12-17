package vista;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class Login extends javax.swing.JFrame {

    Usuario usuario;

    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIniciarSesion = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        tfUsuario = new javax.swing.JTextField();
        pfPassword = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión de Turnos");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(6);
        setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        setName("frmLogin"); // NOI18N
        setResizable(false);

        lblIniciarSesion.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblIniciarSesion.setText("Iniciar Sesión");

        lblUsuario.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblUsuario.setText("Usuario");

        lblPassword.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        lblPassword.setText("Contraseña");

        tfUsuario.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tfUsuario.setText("adm");

        pfPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        pfPassword.setText("adm");
        pfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfPasswordActionPerformed(evt);
            }
        });

        btnAceptar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAceptar.setText("Ingresar");
        btnAceptar.setFocusPainted(false);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mostrar.png"))); // NOI18N
        btnMostrar.setFocusPainted(false);
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background.jpg"))); // NOI18N
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword)
                            .addComponent(tfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuario)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lblIniciarSesion)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblIniciarSesion)
                .addGap(138, 138, 138)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // Guardo en dos variables locales el nombre de usuario y la contraseña para tener un manejo más fácil
        String nusuario = tfUsuario.getText().toUpperCase(),
                password = String.valueOf(pfPassword.getPassword());
        // Controlo que ningún campo este vacio
        if (nusuario.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Uno o más campos están vacios");
        } else { // Si no estan vacios, paso a la validación
            // Guardo en array de Strings los datos a verificar en el preparedStatement
            String[] datos = {nusuario, password};
            // realizo la consulta
            ResultSet res = Conexion.consultar("SELECT * ,rol.tipo FROM usuario INNER JOIN rol ON usuario.rol = rol.id WHERE activo = 1 AND usuario.nombre_usuario = ? AND usuario.clave = ?", datos);
            try {
                if (res.next()) { // Comprobamos que la consulta haya retornado algo
                    usuario = new Usuario(
                            res.getInt("id"),
                            res.getInt("dni"),
                            res.getInt("celular"),
                            res.getInt("id_genero"),
                            res.getInt("id_especialidad"),
                            res.getInt("matricula"),
                            res.getString("tipo"),
                            res.getString("nombre_usuario"),
                            res.getString("clave"),
                            res.getString("nombre"),
                            res.getString("apellido")
                    );
                    Main.usuarioActual = usuario;
                    MenuInicio af = new MenuInicio(Main.usuarioActual.getRol());
                    af.setVisible(true);
                    dispose();
                } else { // Si no, el usuario o contraseña es incorrecta
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }

        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        String URLImg = "src/main/resources/";
        // Verifica cual es el caracter que oculta las letras, y dependiendo de si es el predeterminado o no,
        // oculta o muestra la contraseña y cambia el icono correspondiente.
        if (pfPassword.getEchoChar() == '•') {
            pfPassword.setEchoChar((char) 0);
            this.btnMostrar.setIcon(new javax.swing.ImageIcon(URLImg + "ocultar.png"));
        } else {
            pfPassword.setEchoChar('•');
            this.btnMostrar.setIcon(new javax.swing.ImageIcon(URLImg + "mostrar.png"));
        }
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void pfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pfPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblIniciarSesion;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tfUsuario;
    // End of variables declaration//GEN-END:variables

}
