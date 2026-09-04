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
        btnCancelar = new javax.swing.JButton();
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

        jLabel8.setText("Dia");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        cmbInstituto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbInstituto.addActionListener(this::cmbInstitutoActionPerformed);

        cmbCurso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCurso.addActionListener(this::cmbCursoActionPerformed);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        jLabel1.setText("Instituto:");
        jLabel1.setToolTipText("");

        jLabel2.setText("Curso:");
        jLabel2.setToolTipText("");

        jLabel3.setText("Edicion vigente:");

        jLabel4.setText("Estudiante:");

        jLabel6.setText("Fecha Inscripción:");

        cmbEstudiante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEstudiante.addActionListener(this::cmbEstudianteActionPerformed);

        jLabel5.setText("Dia");

        jLabel9.setText("Mes");

        jLabel10.setText("Año");

        cmbEdicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEdicion.addActionListener(this::cmbEdicionActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAceptar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addGap(51, 51, 51))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbInstituto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbEstudiante, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spDiaInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spMesInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spAnioInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbInstituto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spDiaInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spMesInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spAnioInscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAceptar)
                            .addComponent(btnCancelar))
                        .addGap(21, 21, 21))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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

        dispose();

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
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
    private javax.swing.JSpinner spAnioInscripcion;
    private javax.swing.JSpinner spDiaInscripcion;
    private javax.swing.JSpinner spMesInscripcion;
    // End of variables declaration//GEN-END:variables
}
