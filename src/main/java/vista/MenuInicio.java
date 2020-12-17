package vista;

import javax.swing.JOptionPane;

public final class MenuInicio extends javax.swing.JFrame {

    public MenuInicio(String rol) {
        initComponents();
        this.setLocationRelativeTo(null);
        setWelcomeText();
        habilitarBtnMedico(false);
        habilitarBtnsAdministrativo(false);
        habilitarBotones(rol);
    }

    public void setWelcomeText() {
        welcomeText.setText("Bienvenido " + Main.usuarioActual.getNombre());
    }

    public void habilitarBotones(String rol) {
        if (rol.equals("Administrativo")) {
            habilitarBtnsAdministrativo(true);
        } else {
            habilitarBtnMedico(true);
        }
    }

    public void habilitarBtnMedico(boolean Boolean) {
        btnMisAgendas.setVisible(Boolean);
    }

    public void habilitarBtnsAdministrativo(boolean Boolean) {
        btnMenuPaciente.setVisible(Boolean);
        btnMenuCitas.setVisible(Boolean);
        btnMenuAgenda.setVisible(Boolean);
        btnMenuEspecialidad.setVisible(Boolean);
        btnMenuUsuario.setVisible(Boolean);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMenuPaciente = new javax.swing.JButton();
        btnMenuUsuario = new javax.swing.JButton();
        lblMenuAdm = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        btnMenuAgenda = new javax.swing.JButton();
        welcomeText = new javax.swing.JLabel();
        btnMenuEspecialidad = new javax.swing.JButton();
        btnMenuCitas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnMisAgendas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnMenuPaciente.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMenuPaciente.setText("Pacientes");
        btnMenuPaciente.setToolTipText("Paciente");
        btnMenuPaciente.setFocusPainted(false);
        btnMenuPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuPacienteActionPerformed(evt);
            }
        });

        btnMenuUsuario.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMenuUsuario.setText("Usuarios");
        btnMenuUsuario.setFocusPainted(false);
        btnMenuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuUsuarioActionPerformed(evt);
            }
        });

        lblMenuAdm.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblMenuAdm.setText("Panel de Control");

        btnCerrarSesion.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.setToolTipText("Salir del Programa");
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        btnMenuAgenda.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMenuAgenda.setText("Agendas");
        btnMenuAgenda.setFocusPainted(false);
        btnMenuAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuAgendaActionPerformed(evt);
            }
        });

        welcomeText.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        welcomeText.setText("WelcomeText");
        welcomeText.setMaximumSize(new java.awt.Dimension(200, 25));
        welcomeText.setMinimumSize(new java.awt.Dimension(200, 25));

        btnMenuEspecialidad.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMenuEspecialidad.setText("Especialidades");
        btnMenuEspecialidad.setFocusPainted(false);
        btnMenuEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuEspecialidadActionPerformed(evt);
            }
        });

        btnMenuCitas.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMenuCitas.setText("Citas otorgadas");
        btnMenuCitas.setFocusPainted(false);
        btnMenuCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuCitasActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background.jpg"))); // NOI18N

        btnMisAgendas.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        btnMisAgendas.setText("Mis Agendas");
        btnMisAgendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMisAgendasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(welcomeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMenuAdm)
                    .addComponent(btnMenuPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenuCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMisAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(welcomeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMenuAdm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnMenuPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMenuCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMenuAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMenuEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMenuUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMisAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPacienteActionPerformed
        //al presionar abre el menu de Paciente
        PacienteABM pf = new PacienteABM();
        pf.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMenuPacienteActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        int rta = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesión?", "SALIR!", JOptionPane.YES_NO_OPTION);
        if (rta == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnMenuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuUsuarioActionPerformed
        new UsuariosABM().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMenuUsuarioActionPerformed

    private void btnMenuAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuAgendaActionPerformed
        AgendaForm agendaMedico = new AgendaForm();
        agendaMedico.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMenuAgendaActionPerformed

    private void btnMenuEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuEspecialidadActionPerformed
        new EspecialidadesABM().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMenuEspecialidadActionPerformed

    private void btnMenuCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuCitasActionPerformed
        new CitasForm().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMenuCitasActionPerformed

    private void btnMisAgendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMisAgendasActionPerformed
        AgendaMedico agendaMedico = new AgendaMedico();
        agendaMedico.setSize(1103, 665);
        agendaMedico.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMisAgendasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnMenuAgenda;
    private javax.swing.JButton btnMenuCitas;
    private javax.swing.JButton btnMenuEspecialidad;
    private javax.swing.JButton btnMenuPaciente;
    private javax.swing.JButton btnMenuUsuario;
    private javax.swing.JButton btnMisAgendas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblMenuAdm;
    private javax.swing.JLabel welcomeText;
    // End of variables declaration//GEN-END:variables
}
