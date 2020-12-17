package vista;

import controlador.AgendaController;
import controlador.HorarioController;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Horario;
import static vista.Main.usuarioActual;

public final class AgendaMedico extends javax.swing.JFrame {

    Horario horario;
    HorarioController horarioController = new HorarioController();
    ArrayList<Horario> listaHorario = new ArrayList<>();

    AgendaController agendaController = new AgendaController();
    ArrayList<Agenda> listaAgendas;
    Agenda agenda;

    String[] encabezadoHorarios = {"Nombre", "Hs. Inicio", "Hs. Fin", "Intervalo", "Días"};
    String[] encabezadoAgenda = {"Nombre de la agendas", "Especialidad"};
    DefaultTableModel modelo;

    public AgendaMedico() {
        initComponents();
        setWelcomeText();
        setLocationRelativeTo(null);
        reloadInfoAgenda();
        reloadTable(tblHorarios, encabezadoHorarios);
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

    public void reloadInfoHorarios(ArrayList<Horario> listaHorarioN) {
        reloadTable(tblHorarios, encabezadoHorarios);

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
            tblHorarios.setModel(modelo);

        }
    }

    public void centrarDatos() {
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        tblAgendas.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
        tblAgendas.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
    }

    private void setWelcomeText() {
        String nombre = Main.usuarioActual.getNombre() + " " + Main.usuarioActual.getApellido();
        titulo.setText(" Agendas de " + nombre);
    }

    @SuppressWarnings("unchecked")
    private void reloadInfoAgenda() { // carga la tabla de usuarios
        reloadTable(tblAgendas, encabezadoAgenda);
        listaAgendas = agendaController.cargarAgendasByIdMedico(usuarioActual.getId());
        Object[] fila = new Object[2];

        for (int i = 0; i < listaAgendas.size(); i++) {
            agenda = listaAgendas.get(i);
            fila[0] = agenda.getNombre();
            fila[1] = agenda.getEspecialidad();

            modelo.addRow(fila);
        }
        tblAgendas.setModel(modelo);
        centrarDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        pnlAgendas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAgendas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHorarios = new javax.swing.JTable();
        btnVolverInicio = new javax.swing.JButton();
        lblAgendas = new javax.swing.JLabel();
        lblHorarios = new javax.swing.JLabel();
        btnAtenderAgenda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1103, 665));

        titulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        titulo.setText("Agendas de médico");

        pnlAgendas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlAgendas.setPreferredSize(new java.awt.Dimension(1005, 580));

        tblAgendas.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblAgendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre de agenda", "Especialidad"
            }
        ));
        tblAgendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAgendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAgendas);

        tblHorarios.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tblHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre de agenda", "Especialidad"
            }
        ));
        jScrollPane2.setViewportView(tblHorarios);

        btnVolverInicio.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnVolverInicio.setText("Volver al Inicio");
        btnVolverInicio.setToolTipText("Volver a Inicio");
        btnVolverInicio.setFocusPainted(false);
        btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverInicioActionPerformed(evt);
            }
        });

        lblAgendas.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblAgendas.setText("Agendas");

        lblHorarios.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblHorarios.setText("Horarios");

        btnAtenderAgenda.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAtenderAgenda.setText("Atender agenda");
        btnAtenderAgenda.setToolTipText("Volver a Inicio");
        btnAtenderAgenda.setFocusPainted(false);
        btnAtenderAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderAgendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAgendasLayout = new javax.swing.GroupLayout(pnlAgendas);
        pnlAgendas.setLayout(pnlAgendasLayout);
        pnlAgendasLayout.setHorizontalGroup(
            pnlAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(pnlAgendasLayout.createSequentialGroup()
                        .addGroup(pnlAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAgendas)
                            .addComponent(lblHorarios))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlAgendasLayout.createSequentialGroup()
                        .addComponent(btnVolverInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtenderAgenda)))
                .addContainerGap())
        );
        pnlAgendasLayout.setVerticalGroup(
            pnlAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgendasLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblAgendas)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHorarios)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAgendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolverInicio)
                    .addComponent(btnAtenderAgenda))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(pnlAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(titulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(titulo)
                .addGap(18, 18, 18)
                .addComponent(pnlAgendas, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverInicioActionPerformed
        MenuInicio mi = new MenuInicio(Main.usuarioActual.getRol());
        mi.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverInicioActionPerformed

    private void tblAgendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAgendasMouseClicked
        if (tblAgendas.getSelectedRow() >= 0) {
            int idAgendaFocus = listaAgendas.get(tblAgendas.getSelectedRow()).getId();
            listaHorario.clear();
            listaHorario = horarioController.cargarHorario(idAgendaFocus);
            reloadInfoHorarios(listaHorario);
        }
    }//GEN-LAST:event_tblAgendasMouseClicked

    private void btnAtenderAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderAgendaActionPerformed
        agenda = listaAgendas.get(tblAgendas.getSelectedRow());
        MisCitas mc = new MisCitas(agenda);
        mc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnAtenderAgendaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtenderAgenda;
    private javax.swing.JButton btnVolverInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAgendas;
    private javax.swing.JLabel lblHorarios;
    private javax.swing.JPanel pnlAgendas;
    private javax.swing.JTable tblAgendas;
    private javax.swing.JTable tblHorarios;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
