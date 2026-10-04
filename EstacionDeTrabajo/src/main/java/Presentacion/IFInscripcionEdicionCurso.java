/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Presentacion;

import Logica.Entidades.Curso;
import Logica.Entidades.EdicionCurso;
import Logica.Entidades.Estudiante;
import Logica.Entidades.InscripcionEdicion;
import Logica.Entidades.Instituto;
import Logica.controladores.IControlador;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author xnahu
 */
public class IFInscripcionEdicionCurso extends javax.swing.JInternalFrame {

    private IControlador controlador; 
    
    public IFInscripcionEdicionCurso(IControlador controlador) {
            initComponents();

    this.controlador = controlador;

   

    cmbInstituto.removeAllItems();
    cmbInstituto.addItem("Seleccione un instituto...");

    cmbCurso.removeAllItems();
    cmbCurso.addItem("Seleccione primero un instituto...");
    cmbCurso.setEnabled(false);

    cmbEstudiante.removeAllItems();
    cmbEstudiante.addItem("Seleccione primero un curso...");
    cmbEstudiante.setEnabled(false);
    
    cmbEdicion.removeAllItems();
    cmbEdicion.addItem("Seleccione primero un curso...");
    cmbEdicion.setEnabled(false);

    cargarInstitutos();
    configurarSpinnersFecha();
    }
    
    private void cargarInstitutos() {

    List<Instituto> institutos = controlador.listarInstitutos();

    for (Instituto instituto : institutos) {
        cmbInstituto.addItem(instituto.getNombre());
    }
}
    private void configurarSpinnersFecha() {

    LocalDate hoy = LocalDate.now();

    spDiaInscripcion.setModel(
            new SpinnerNumberModel(hoy.getDayOfMonth(), 1, 31, 1)
    );

    spMesInscripcion.setModel(
            new SpinnerNumberModel(hoy.getMonthValue(), 1, 12, 1)
    );

    spAnioInscripcion.setModel(
            new SpinnerNumberModel(hoy.getYear(), 1900, 2100, 1)
    );

    spAnioInscripcion.setEditor(
            new JSpinner.NumberEditor(spAnioInscripcion, "0")
    );
}
    private void cargarCursosPorInstituto(){

    cmbCurso.removeAllItems();

    cmbEdicion.removeAllItems();
    cmbEdicion.addItem("Seleccione primero un curso...");
    cmbEdicion.setEnabled(false);

    cmbEstudiante.removeAllItems();
    cmbEstudiante.addItem("Seleccione primero una edición...");
    cmbEstudiante.setEnabled(false);

    if (cmbInstituto.getSelectedIndex() <= 0) {
        cmbCurso.addItem("Seleccione primero un instituto...");
        cmbCurso.setEnabled(false);
        return;
    }

    String nombreInstituto =
            cmbInstituto.getSelectedItem().toString();

    List<Curso> cursos =
            controlador.listarCursosPorInstituto(nombreInstituto);

    if (cursos.isEmpty()) {
        cmbCurso.addItem("No hay cursos disponibles");
        cmbCurso.setEnabled(false);
        return;
    }

    cmbCurso.addItem("Seleccione un curso...");

    for (Curso curso : cursos) {
        cmbCurso.addItem(curso.getNombre());
    }

    cmbCurso.setEnabled(true);

}
private void cargarEdicionesVigentes() {

    cmbEdicion.removeAllItems();

    cmbEstudiante.removeAllItems();
    cmbEstudiante.addItem("Seleccione primero una edición...");
    cmbEstudiante.setEnabled(false);

    if (!cmbCurso.isEnabled() || cmbCurso.getSelectedIndex() <= 0) {
        cmbEdicion.addItem("Seleccione primero un curso...");
        cmbEdicion.setEnabled(false);
        return;
    }

    String nombreCurso =
            cmbCurso.getSelectedItem().toString();

    List<EdicionCurso> ediciones =
            controlador.listarEdicionesVigentesPorCurso(nombreCurso);

    if (ediciones.isEmpty()) {
        cmbEdicion.addItem("No hay ediciones vigentes");
        cmbEdicion.setEnabled(false);
        return;
    }

    cmbEdicion.addItem("Seleccione una edición...");

    for (EdicionCurso edicion : ediciones) {
        cmbEdicion.addItem(edicion.getNombre());
    }

    cmbEdicion.setEnabled(true);
}
    
    private void cargarEstudiantes() {

    cmbEstudiante.removeAllItems();

    List<Estudiante> estudiantes =
            controlador.listarEstudiantes();

    if (estudiantes.isEmpty()) {
        cmbEstudiante.addItem("No hay estudiantes registrados");
        cmbEstudiante.setEnabled(false);
        return;
    }

    cmbEstudiante.addItem("Seleccione un estudiante...");

    for (Estudiante estudiante : estudiantes) {
        cmbEstudiante.addItem(estudiante.getNickname());
    }

    cmbEstudiante.setEnabled(true);
}
    
    private void verificarInscripcion() {

    if (!cmbEstudiante.isEnabled()
            || cmbEstudiante.getSelectedIndex() <= 0
            || !cmbEdicion.isEnabled()
            || cmbEdicion.getSelectedIndex() <= 0) {

        btnAceptar.setText("Inscribir");
        return;
    }

    String nickname =
            cmbEstudiante.getSelectedItem().toString();

    String nombreEdicion =
            cmbEdicion.getSelectedItem().toString();

    InscripcionEdicion inscripcion =
            controlador.buscarInscripcionEdicion(
                    nickname,
                    nombreEdicion
            );

    if (inscripcion == null) {

        btnAceptar.setText("Inscribir");
        configurarSpinnersFecha();

    } else {

        btnAceptar.setText("Modificar");

        LocalDate fecha =
                inscripcion.getFechaInscripcion();

        spDiaInscripcion.setValue(fecha.getDayOfMonth());
        spMesInscripcion.setValue(fecha.getMonthValue());
        spAnioInscripcion.setValue(fecha.getYear());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        cmbInstituto = new javax.swing.JComboBox<>();
        cmbCurso = new javax.swing.JComboBox<>();
        spDiaInscripcion = new javax.swing.JSpinner();
        spMesInscripcion = new javax.swing.JSpinner();
        spAnioInscripcion = new javax.swing.JSpinner();
        btnAceptar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbEstudiante = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbEdicion = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        lblUsuarios1 = new javax.swing.JLabel();

        jLabel8.setText("Dia");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setPreferredSize(new java.awt.Dimension(910, 910));
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbInstituto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbInstituto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmbInstituto.addActionListener(this::cmbInstitutoActionPerformed);
        getContentPane().add(cmbInstituto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 320, 30));

        cmbCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCurso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmbCurso.addActionListener(this::cmbCursoActionPerformed);
        getContentPane().add(cmbCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 320, 30));
        getContentPane().add(spDiaInscripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 60, 40));
        getContentPane().add(spMesInscripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, 60, 40));
        getContentPane().add(spAnioInscripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 340, 60, 40));

        btnAceptar.setBackground(new java.awt.Color(35, 71, 75));
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);
        getContentPane().add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 100, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(35, 71, 75));
        jLabel1.setText("Instituto:");
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(35, 71, 75));
        jLabel2.setText("Curso:");
        jLabel2.setToolTipText("");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(35, 71, 75));
        jLabel3.setText("Edicion vigente:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 71, 75));
        jLabel4.setText("Estudiante:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(35, 71, 75));
        jLabel6.setText("Fecha Inscripción");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, -1, -1));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 230, -1, -1));

        cmbEstudiante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEstudiante.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmbEstudiante.addActionListener(this::cmbEstudianteActionPerformed);
        getContentPane().add(cmbEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 320, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(35, 71, 75));
        jLabel5.setText("Dia");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(35, 71, 75));
        jLabel9.setText("Mes");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(35, 71, 75));
        jLabel10.setText("Año");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 360, -1, -1));

        cmbEdicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmbEdicion.addActionListener(this::cmbEdicionActionPerformed);
        getContentPane().add(cmbEdicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 320, 30));

        jPanel2.setBackground(new java.awt.Color(35, 71, 75));

        lblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuarios.setForeground(new java.awt.Color(206, 206, 206));
        lblUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Presentacion/usuarios_white_32px.png"))); // NOI18N
        lblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseExited(evt);
            }
        });

        lblUsuarios1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuarios1.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarios1.setText("Inscripcion a Edicion de Curso");
        lblUsuarios1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuarios1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuarios1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuarios1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(600, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(lblUsuarios1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (!cmbEdicion.isEnabled()
            || cmbEdicion.getSelectedIndex() <= 0) {

        JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una edición vigente.",
                "Inscripción",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    if (!cmbEstudiante.isEnabled()
            || cmbEstudiante.getSelectedIndex() <= 0) {

        JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un estudiante.",
                "Inscripción",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    LocalDate fechaInscripcion;

    try {

        int dia =
                (Integer) spDiaInscripcion.getValue();

        int mes =
                (Integer) spMesInscripcion.getValue();

        int anio =
                (Integer) spAnioInscripcion.getValue();

        fechaInscripcion =
                LocalDate.of(anio, mes, dia);

    } catch (DateTimeException e) {

        JOptionPane.showMessageDialog(
                this,
                "La fecha ingresada no es válida.",
                "Inscripción",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    String nickname =
            cmbEstudiante.getSelectedItem().toString();

    String nombreEdicion =
            cmbEdicion.getSelectedItem().toString();

    try {

        InscripcionEdicion inscripcion =
                controlador.buscarInscripcionEdicion(
                        nickname,
                        nombreEdicion
                );

        if (inscripcion == null) {

            controlador.inscribirEstudianteEdicion(
                    nickname,
                    nombreEdicion,
                    fechaInscripcion
            );

            JOptionPane.showMessageDialog(
                    this,
                    "El estudiante fue inscripto correctamente.",
                    "Inscripción",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            controlador.modificarInscripcionEdicion(
                    inscripcion.getId(),
                    fechaInscripcion
            );

            JOptionPane.showMessageDialog(
                    this,
                    "La inscripción fue modificada correctamente.",
                    "Inscripción",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "No se pudo realizar la inscripción.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
    }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cmbInstitutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbInstitutoActionPerformed
        cargarCursosPorInstituto();

    }//GEN-LAST:event_cmbInstitutoActionPerformed

    private void cmbCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCursoActionPerformed
         cargarEdicionesVigentes();
    }//GEN-LAST:event_cmbCursoActionPerformed

    private void cmbEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstudianteActionPerformed
        verificarInscripcion();
    }//GEN-LAST:event_cmbEstudianteActionPerformed

    private void cmbEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEdicionActionPerformed


    if (!cmbEdicion.isEnabled()
            || cmbEdicion.getSelectedIndex() <= 0) {

        cmbEstudiante.removeAllItems();
        cmbEstudiante.addItem("Seleccione primero una edición...");
        cmbEstudiante.setEnabled(false);
        return;
    }

    cargarEstudiantes();

    }//GEN-LAST:event_cmbEdicionActionPerformed

    private void lblInscripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInscripcionMouseClicked

    }//GEN-LAST:event_lblInscripcionMouseClicked

    private void lblInscripcionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInscripcionMouseEntered

    }//GEN-LAST:event_lblInscripcionMouseEntered

    private void lblInscripcionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInscripcionMouseExited

    }//GEN-LAST:event_lblInscripcionMouseExited

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked

    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered

    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited

    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblUsuarios1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseClicked

    private void lblUsuarios1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseEntered

    private void lblUsuarios1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JComboBox<String> cmbCurso;
    private javax.swing.JComboBox<String> cmbEdicion;
    private javax.swing.JComboBox<String> cmbEstudiante;
    private javax.swing.JComboBox<String> cmbInstituto;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblUsuarios1;
    private javax.swing.JSpinner spAnioInscripcion;
    private javax.swing.JSpinner spDiaInscripcion;
    private javax.swing.JSpinner spMesInscripcion;
    // End of variables declaration//GEN-END:variables
}
