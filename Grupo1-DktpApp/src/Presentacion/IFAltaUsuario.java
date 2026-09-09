
package Presentacion;
import Logica.controladores.IControlador;
import Logica.controladores.Fabrica;
import java.awt.Color;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;


public class IFAltaUsuario extends javax.swing.JInternalFrame {

    public IFAltaUsuario() {
        initComponents();
        cargarInstitutos();
        actualizarEstadoInstituto();
    }

    private void cargarInstitutos() {
        IControlador ic = Fabrica.getInstance().getIControlador();
        List<String> nombresInstitutos = ic.listarNombresInstitutos();
        cbInstitutos.setModel(new DefaultComboBoxModel<>(nombresInstitutos.toArray(new String[0])));
    }


    private void actualizarEstadoInstituto() {
        cbInstitutos.setEnabled(chbDocente.isSelected());
    }


    private void limpiarFormulario() {
        tfNickname.setForeground(new Color(153, 153, 153));
        tfNickname.setText("Ingrese un nickname");
        tfNombre.setForeground(new Color(153, 153, 153));
        tfNombre.setText("Ingrese nombre del usuario");
        tfApellido.setForeground(new Color(153, 153, 153));
        tfApellido.setText("Ingrese apellido del usuario");
        tfCorreo.setForeground(new Color(153, 153, 153));
        tfCorreo.setText("Ingrese correo del usuario");
        tfDia.setForeground(new Color(153, 153, 153));
        tfDia.setText("dia");
        tfMes.setForeground(new Color(153, 153, 153));
        tfMes.setText("mes");
        tfAnio.setForeground(new Color(153, 153, 153));
        tfAnio.setText("año");
        chbAlumno.setSelected(false);
        chbDocente.setSelected(false);
        cargarInstitutos();
        actualizarEstadoInstituto();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        lblUsuarios1 = new javax.swing.JLabel();
        usersSeparator = new javax.swing.JSeparator();
        usersSeparator1 = new javax.swing.JSeparator();
        usersSeparator2 = new javax.swing.JSeparator();
        usersSeparator3 = new javax.swing.JSeparator();
        usersSeparator4 = new javax.swing.JSeparator();
        lblUsuarios7 = new javax.swing.JLabel();
        usersSeparator5 = new javax.swing.JSeparator();
        usersSeparator6 = new javax.swing.JSeparator();
        chbAlumno = new javax.swing.JCheckBox();
        chbDocente = new javax.swing.JCheckBox();
        btnAlta = new javax.swing.JButton();
        tfAnio = new javax.swing.JTextField();
        tfNickname = new javax.swing.JTextField();
        tfNombre = new javax.swing.JTextField();
        tfApellido = new javax.swing.JTextField();
        tfCorreo = new javax.swing.JTextField();
        tfDia = new javax.swing.JTextField();
        tfMes = new javax.swing.JTextField();
        usersSeparator7 = new javax.swing.JSeparator();
        cbInstitutos = new javax.swing.JComboBox<>();

        setBorder(null);
        setMinimumSize(new java.awt.Dimension(910, 640));
        setPreferredSize(new java.awt.Dimension(910, 910));
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        lblUsuarios1.setText("Alta usuario");
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
                .addContainerGap()
                .addComponent(lblUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(887, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 50));

        usersSeparator.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 270, 10));

        usersSeparator1.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 270, 10));

        usersSeparator2.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 270, 10));

        usersSeparator3.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 270, 10));

        usersSeparator4.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 40, 10));

        lblUsuarios7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuarios7.setForeground(new java.awt.Color(206, 206, 206));
        lblUsuarios7.setText("Fecha de nacimiento");
        lblUsuarios7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUsuarios7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuarios7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuarios7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuarios7MouseExited(evt);
            }
        });
        getContentPane().add(lblUsuarios7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 200, 38));

        usersSeparator5.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 40, 10));

        usersSeparator6.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 70, 10));

        chbAlumno.setText("Alumno");
        chbAlumno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chbAlumnoMouseClicked(evt);
            }
        });
        chbAlumno.addActionListener(this::chbAlumnoActionPerformed);
        getContentPane().add(chbAlumno, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        chbDocente.setText("Docente");
        chbDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chbDocenteMouseClicked(evt);
            }
        });
        chbDocente.addActionListener(this::chbDocenteActionPerformed);
        getContentPane().add(chbDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, -1, -1));

        btnAlta.setBackground(new java.awt.Color(35, 71, 75));
        btnAlta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAlta.setForeground(new java.awt.Color(255, 255, 255));
        btnAlta.setText("Dar de alta");
        btnAlta.setBorderPainted(false);
        btnAlta.addActionListener(this::btnAltaActionPerformed);
        getContentPane().add(btnAlta, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 253, -1, 30));

        tfAnio.setBackground(new java.awt.Color(242, 242, 242));
        tfAnio.setForeground(new java.awt.Color(153, 153, 153));
        tfAnio.setText("año");
        tfAnio.setToolTipText("");
        tfAnio.setBorder(null);
        tfAnio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfAnioMousePressed(evt);
            }
        });
        tfAnio.addActionListener(this::tfAnioActionPerformed);
        getContentPane().add(tfAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 70, 30));

        tfNickname.setBackground(new java.awt.Color(242, 242, 242));
        tfNickname.setForeground(new java.awt.Color(153, 153, 153));
        tfNickname.setText("Ingrese un nickname");
        tfNickname.setBorder(null);
        tfNickname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfNicknameMousePressed(evt);
            }
        });
        tfNickname.addActionListener(this::tfNicknameActionPerformed);
        getContentPane().add(tfNickname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 280, 30));

        tfNombre.setBackground(new java.awt.Color(242, 242, 242));
        tfNombre.setForeground(new java.awt.Color(153, 153, 153));
        tfNombre.setText("Ingrese nombre del usuario");
        tfNombre.setBorder(null);
        tfNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfNombreMousePressed(evt);
            }
        });
        tfNombre.addActionListener(this::tfNombreActionPerformed);
        getContentPane().add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 280, 30));

        tfApellido.setBackground(new java.awt.Color(242, 242, 242));
        tfApellido.setForeground(new java.awt.Color(153, 153, 153));
        tfApellido.setText("Ingrese apellido del usuario");
        tfApellido.setBorder(null);
        tfApellido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfApellidoMousePressed(evt);
            }
        });
        tfApellido.addActionListener(this::tfApellidoActionPerformed);
        getContentPane().add(tfApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 280, 30));

        tfCorreo.setBackground(new java.awt.Color(242, 242, 242));
        tfCorreo.setForeground(new java.awt.Color(153, 153, 153));
        tfCorreo.setText("Ingrese correo del usuario");
        tfCorreo.setBorder(null);
        tfCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfCorreoMousePressed(evt);
            }
        });
        tfCorreo.addActionListener(this::tfCorreoActionPerformed);
        getContentPane().add(tfCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 280, 30));

        tfDia.setBackground(new java.awt.Color(242, 242, 242));
        tfDia.setForeground(new java.awt.Color(153, 153, 153));
        tfDia.setText("dia");
        tfDia.setBorder(null);
        tfDia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfDiaMousePressed(evt);
            }
        });
        tfDia.addActionListener(this::tfDiaActionPerformed);
        getContentPane().add(tfDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 40, 30));

        tfMes.setBackground(new java.awt.Color(242, 242, 242));
        tfMes.setForeground(new java.awt.Color(153, 153, 153));
        tfMes.setText("mes");
        tfMes.setBorder(null);
        tfMes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tfMesMousePressed(evt);
            }
        });
        tfMes.addActionListener(this::tfMesActionPerformed);
        getContentPane().add(tfMes, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 40, 30));

        usersSeparator7.setForeground(new java.awt.Color(35, 71, 75));
        getContentPane().add(usersSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 270, 10));

        cbInstitutos.setBackground(new java.awt.Color(242, 242, 242));
        cbInstitutos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbInstitutos.setBorder(null);
        getContentPane().add(cbInstitutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 270, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void lblUsuarios7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios7MouseClicked

    private void lblUsuarios7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios7MouseEntered

    private void lblUsuarios7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios7MouseExited

    private void chbAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbAlumnoActionPerformed
        chbDocente.setSelected(false);
        actualizarEstadoInstituto();
    }//GEN-LAST:event_chbAlumnoActionPerformed

    private void chbDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbDocenteActionPerformed
       chbAlumno.setSelected(false);
       actualizarEstadoInstituto();
    }//GEN-LAST:event_chbDocenteActionPerformed

    private void tfAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAnioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAnioActionPerformed

    private void tfNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNicknameActionPerformed

    private void tfNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNombreActionPerformed

    private void tfApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfApellidoActionPerformed

    private void tfCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCorreoActionPerformed

    private void tfDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDiaActionPerformed

    private void tfMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMesActionPerformed

    private void tfNicknameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfNicknameMousePressed
        tfNickname.setForeground(new Color(0,0,0));
        if(tfNickname.getText().equals("Ingrese un nickname")){
            tfNickname.setText("");
        }
    }//GEN-LAST:event_tfNicknameMousePressed

    private void tfNombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfNombreMousePressed
        tfNombre.setForeground(new Color(0,0,0));
        if(tfNombre.getText().equals("Ingrese nombre del usuario")){
            tfNombre.setText("");
        }
    }//GEN-LAST:event_tfNombreMousePressed

    private void tfApellidoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfApellidoMousePressed
        tfApellido.setForeground(new Color(0,0,0));
        if(tfApellido.getText().equals("Ingrese apellido del usuario")){
            tfApellido.setText("");
        }
    }//GEN-LAST:event_tfApellidoMousePressed

    private void tfCorreoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfCorreoMousePressed
        tfCorreo.setForeground(new Color(0,0,0));
        if(tfCorreo.getText().equals("Ingrese correo del usuario")){
            tfCorreo.setText("");
        }
    }//GEN-LAST:event_tfCorreoMousePressed

    private void tfDiaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfDiaMousePressed
        tfDia.setForeground(new Color(0,0,0));
        if(tfDia.getText().equals("dia")){
            tfDia.setText("");
        }
    }//GEN-LAST:event_tfDiaMousePressed

    private void tfMesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfMesMousePressed
        tfMes.setForeground(new Color(0,0,0));
        if(tfMes.getText().equals("mes")){
            tfMes.setText("");
        }
    }//GEN-LAST:event_tfMesMousePressed

    private void tfAnioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfAnioMousePressed
        tfAnio.setForeground(new Color(0,0,0));
        if(tfAnio.getText().equals("año")){
            tfAnio.setText("");
        }
    }//GEN-LAST:event_tfAnioMousePressed

    private void chbDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chbDocenteMouseClicked

    }//GEN-LAST:event_chbDocenteMouseClicked

    private void chbAlumnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chbAlumnoMouseClicked

    }//GEN-LAST:event_chbAlumnoMouseClicked

    private void btnAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaActionPerformed
        if(tfNickname.getText().trim().isEmpty() || tfNickname.getText().equals("Ingrese un nickname") 
                || tfNombre.getText().trim().isEmpty() || tfNombre.getText().equals("Ingrese nombre del usuario")
                || tfApellido.getText().trim().isEmpty() || tfApellido.getText().equals("Ingrese apellido del usuario")
                || tfCorreo.getText().trim().isEmpty() || tfCorreo.getText().equals("Ingrese correo del usuario") 
                || tfDia.getText().trim().isEmpty() || tfDia.getText().equals("dia")
                || tfMes.getText().trim().isEmpty() || tfMes.getText().equals("mes")
                || tfAnio.getText().trim().isEmpty() || tfAnio.getText().equals("año")){
            
            JOptionPane.showMessageDialog(
            this, "Por favor, complete todos los campos obligatorios.", 
            "Campos Incompletos", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Control Docente/Alumno
        if (!chbAlumno.isSelected() && !chbDocente.isSelected()) {
            JOptionPane.showMessageDialog(
            this, "Por favor, indique si el usuario es Alumno o Docente.",
            "Campos Incompletos", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Control si es Docente
        if (chbDocente.isSelected() && cbInstitutos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
            this, "No hay institutos cargados. Debe existir al menos un instituto para dar de alta un docente.",
            "Campos Incompletos", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Fecha de nacimiento válida
        LocalDate fechaNacimiento;
        try {
            int dia = Integer.parseInt(tfDia.getText().trim());
            int mes = Integer.parseInt(tfMes.getText().trim());
            int anio = Integer.parseInt(tfAnio.getText().trim());
            fechaNacimiento = LocalDate.of(anio, mes, dia);
        } catch (NumberFormatException | DateTimeException ex) {
            JOptionPane.showMessageDialog(
            this, "La fecha de nacimiento ingresada no es válida.",
            "Fecha Inválida", JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(
            this, "La fecha de nacimiento no puede ser posterior a hoy.",
            "Fecha Inválida", JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String nickname = tfNickname.getText().trim();
        String nombre = tfNombre.getText().trim();
        String apellido = tfApellido.getText().trim();
        String correo = tfCorreo.getText().trim();

        IControlador ic = Fabrica.getInstance().getIControlador();

        //  Nickname y correo unicos
        if (ic.existeNickname(nickname)) {
            int opcion = JOptionPane.showConfirmDialog(
            this, "El nickname '" + nickname + "' ya está en uso.\n¿Desea corregirlo? (Cancelar para abandonar el alta)",
            "Nickname en uso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if (opcion != JOptionPane.OK_OPTION) {
                limpiarFormulario();
            }
            return;
        }

        if (ic.existeEmail(correo)) {
            int opcion = JOptionPane.showConfirmDialog(
            this, "El correo electrónico '" + correo + "' ya está en uso.\n¿Desea corregirlo? (Cancelar para abandonar el alta)",
            "Correo en uso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if (opcion != JOptionPane.OK_OPTION) {
                limpiarFormulario();
            }
            return;
        }

        // Alta del usuario segun el tipo indicado
        try {
            if (chbDocente.isSelected()) {
                String nombreInstituto = (String) cbInstitutos.getSelectedItem();
                ic.altaUsuarioDocente(nickname, nombre, apellido, correo, fechaNacimiento, nombreInstituto);
            } else {
                ic.altaUsuarioEstudiante(nickname, nombre, apellido, correo, fechaNacimiento);
            }

            JOptionPane.showMessageDialog(
            this, "Usuario dado de alta correctamente.",
            "Alta exitosa", JOptionPane.INFORMATION_MESSAGE
            );
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
            this, "Ocurrió un error al dar de alta al usuario: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnAltaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlta;
    private javax.swing.JComboBox<String> cbInstitutos;
    private javax.swing.JCheckBox chbAlumno;
    private javax.swing.JCheckBox chbDocente;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblUsuarios1;
    private javax.swing.JLabel lblUsuarios7;
    private javax.swing.JTextField tfAnio;
    private javax.swing.JTextField tfApellido;
    private javax.swing.JTextField tfCorreo;
    private javax.swing.JTextField tfDia;
    private javax.swing.JTextField tfMes;
    private javax.swing.JTextField tfNickname;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JSeparator usersSeparator;
    private javax.swing.JSeparator usersSeparator1;
    private javax.swing.JSeparator usersSeparator2;
    private javax.swing.JSeparator usersSeparator3;
    private javax.swing.JSeparator usersSeparator4;
    private javax.swing.JSeparator usersSeparator5;
    private javax.swing.JSeparator usersSeparator6;
    private javax.swing.JSeparator usersSeparator7;
    // End of variables declaration//GEN-END:variables
}
