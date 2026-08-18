package Presentacion;

import java.awt.Color;
import javax.swing.JInternalFrame;

public class JFMain extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFMain.class.getName());
    int xMouse, yMouse;
    public JFMain() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeftPanel = new javax.swing.JPanel();
        lblEdiciones = new javax.swing.JLabel();
        usersSeparator = new javax.swing.JSeparator();
        lblCursos = new javax.swing.JLabel();
        editionSeparator = new javax.swing.JSeparator();
        lblUsuarios = new javax.swing.JLabel();
        cursosSeparator = new javax.swing.JSeparator();
        programsSeparator = new javax.swing.JSeparator();
        lblProgramas = new javax.swing.JLabel();
        institutionSeparator = new javax.swing.JSeparator();
        lblInstituciones = new javax.swing.JLabel();
        pnlTopPanel = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        pnlCenterPanel = new javax.swing.JPanel();
        tpOptionPages = new javax.swing.JTabbedPane();
        pnlTab1 = new javax.swing.JPanel();
        btnAltaUsuario = new javax.swing.JButton();
        dpCRUDusuarios = new javax.swing.JDesktopPane();
        btnConsultaUsuario = new javax.swing.JButton();
        btnModificarUsuario = new javax.swing.JButton();
        pnlTab2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        pnlTab3 = new javax.swing.JPanel();
        pnlTab4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        pnlTab5 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1150, 570));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeftPanel.setBackground(new java.awt.Color(47, 93, 98));
        pnlLeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblEdiciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEdiciones.setForeground(new java.awt.Color(206, 206, 206));
        lblEdiciones.setText("Ediciones");
        lblEdiciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEdiciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEdicionesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEdicionesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEdicionesMouseExited(evt);
            }
        });
        pnlLeftPanel.add(lblEdiciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 142, 38));
        pnlLeftPanel.add(usersSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 210, 10));

        lblCursos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCursos.setForeground(new java.awt.Color(206, 206, 206));
        lblCursos.setText("Cursos");
        lblCursos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCursosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCursosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCursosMouseExited(evt);
            }
        });
        pnlLeftPanel.add(lblCursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 142, 38));
        pnlLeftPanel.add(editionSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 210, 10));

        lblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuarios.setForeground(new java.awt.Color(206, 206, 206));
        lblUsuarios.setText("Usuarios");
        lblUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        pnlLeftPanel.add(lblUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 133, 38));
        pnlLeftPanel.add(cursosSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 210, 10));
        pnlLeftPanel.add(programsSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 210, 20));

        lblProgramas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProgramas.setForeground(new java.awt.Color(206, 206, 206));
        lblProgramas.setText("Programas");
        lblProgramas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblProgramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProgramasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProgramasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblProgramasMouseExited(evt);
            }
        });
        pnlLeftPanel.add(lblProgramas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 142, 40));
        pnlLeftPanel.add(institutionSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 210, 20));

        lblInstituciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInstituciones.setForeground(new java.awt.Color(206, 206, 206));
        lblInstituciones.setText("Instituciones");
        lblInstituciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInstituciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInstitucionesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInstitucionesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInstitucionesMouseExited(evt);
            }
        });
        pnlLeftPanel.add(lblInstituciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 142, 38));

        getContentPane().add(pnlLeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 230, 680));
        pnlLeftPanel.getAccessibleContext().setAccessibleName("pnlLeftPanel");
        pnlLeftPanel.getAccessibleContext().setAccessibleDescription("");

        pnlTopPanel.setBackground(new java.awt.Color(35, 71, 75));
        pnlTopPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTopPanelMouseDragged(evt);
            }
        });
        pnlTopPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTopPanelMousePressed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(35, 71, 75));
        btnSalir.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSalir.setText("X");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setMaximumSize(new java.awt.Dimension(35, 35));
        btnSalir.setMinimumSize(new java.awt.Dimension(35, 35));
        btnSalir.setOpaque(true);
        btnSalir.setPreferredSize(new java.awt.Dimension(35, 35));
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalirMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlTopPanelLayout = new javax.swing.GroupLayout(pnlTopPanel);
        pnlTopPanel.setLayout(pnlTopPanelLayout);
        pnlTopPanelLayout.setHorizontalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopPanelLayout.createSequentialGroup()
                .addGap(1092, 1092, 1092)
                .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        pnlTopPanelLayout.setVerticalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopPanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnSalir.getAccessibleContext().setAccessibleName("x");

        getContentPane().add(pnlTopPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 60));

        pnlCenterPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tpOptionPages.setBackground(new java.awt.Color(255, 255, 255));
        tpOptionPages.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        pnlTab1.setBackground(new java.awt.Color(255, 255, 255));
        pnlTab1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAltaUsuario.setText("Agregar Usuario");
        btnAltaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAltaUsuarioMouseClicked(evt);
            }
        });
        btnAltaUsuario.addActionListener(this::btnAltaUsuarioActionPerformed);
        pnlTab1.add(btnAltaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        javax.swing.GroupLayout dpCRUDusuariosLayout = new javax.swing.GroupLayout(dpCRUDusuarios);
        dpCRUDusuarios.setLayout(dpCRUDusuariosLayout);
        dpCRUDusuariosLayout.setHorizontalGroup(
            dpCRUDusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        dpCRUDusuariosLayout.setVerticalGroup(
            dpCRUDusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        pnlTab1.add(dpCRUDusuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 910, 640));

        btnConsultaUsuario.setText("Consulta Usuario");
        btnConsultaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConsultaUsuarioMouseClicked(evt);
            }
        });
        pnlTab1.add(btnConsultaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        btnModificarUsuario.setText("Modificar Usuario");
        btnModificarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarUsuarioMouseClicked(evt);
            }
        });
        pnlTab1.add(btnModificarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        tpOptionPages.addTab("tab1", pnlTab1);

        pnlTab2.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setText("Agregar Curso");

        javax.swing.GroupLayout pnlTab2Layout = new javax.swing.GroupLayout(pnlTab2);
        pnlTab2.setLayout(pnlTab2Layout);
        pnlTab2Layout.setHorizontalGroup(
            pnlTab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton2)
                .addContainerGap(1156, Short.MAX_VALUE))
        );
        pnlTab2Layout.setVerticalGroup(
            pnlTab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jButton2)
                .addContainerGap(606, Short.MAX_VALUE))
        );

        tpOptionPages.addTab("tab2", pnlTab2);

        pnlTab3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlTab3Layout = new javax.swing.GroupLayout(pnlTab3);
        pnlTab3.setLayout(pnlTab3Layout);
        pnlTab3Layout.setHorizontalGroup(
            pnlTab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1291, Short.MAX_VALUE)
        );
        pnlTab3Layout.setVerticalGroup(
            pnlTab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 705, Short.MAX_VALUE)
        );

        tpOptionPages.addTab("tab3", pnlTab3);

        pnlTab4.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setText("Agregar Programa de formacion");

        javax.swing.GroupLayout pnlTab4Layout = new javax.swing.GroupLayout(pnlTab4);
        pnlTab4.setLayout(pnlTab4Layout);
        pnlTab4Layout.setHorizontalGroup(
            pnlTab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab4Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButton3)
                .addContainerGap(1026, Short.MAX_VALUE))
        );
        pnlTab4Layout.setVerticalGroup(
            pnlTab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab4Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jButton3)
                .addContainerGap(618, Short.MAX_VALUE))
        );

        tpOptionPages.addTab("tab4", pnlTab4);

        pnlTab5.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setText("Alta Instituto");

        javax.swing.GroupLayout pnlTab5Layout = new javax.swing.GroupLayout(pnlTab5);
        pnlTab5.setLayout(pnlTab5Layout);
        pnlTab5Layout.setHorizontalGroup(
            pnlTab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab5Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButton4)
                .addContainerGap(1156, Short.MAX_VALUE))
        );
        pnlTab5Layout.setVerticalGroup(
            pnlTab5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTab5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jButton4)
                .addContainerGap(623, Short.MAX_VALUE))
        );

        tpOptionPages.addTab("tab5", pnlTab5);

        pnlCenterPanel.add(tpOptionPages, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 740));

        getContentPane().add(pnlCenterPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 1220, 1010));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        tpOptionPages.setSelectedIndex(0);
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        lblUsuarios.setForeground(Color.white);
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        lblUsuarios.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblCursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCursosMouseClicked
        tpOptionPages.setSelectedIndex(1);
    }//GEN-LAST:event_lblCursosMouseClicked

    private void lblCursosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCursosMouseEntered
        lblCursos.setForeground(Color.white);
    }//GEN-LAST:event_lblCursosMouseEntered

    private void lblEdicionesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEdicionesMouseExited
        lblEdiciones.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblEdicionesMouseExited

    private void lblCursosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCursosMouseExited
        lblCursos.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblCursosMouseExited

    private void lblEdicionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEdicionesMouseClicked
        tpOptionPages.setSelectedIndex(2);
    }//GEN-LAST:event_lblEdicionesMouseClicked

    private void lblEdicionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEdicionesMouseEntered
        lblEdiciones.setForeground(Color.white);
    }//GEN-LAST:event_lblEdicionesMouseEntered

    private void lblProgramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseClicked
        tpOptionPages.setSelectedIndex(3);
    }//GEN-LAST:event_lblProgramasMouseClicked

    private void lblProgramasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseEntered
        lblProgramas.setForeground(Color.white);
    }//GEN-LAST:event_lblProgramasMouseEntered

    private void lblProgramasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseExited
        lblProgramas.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblProgramasMouseExited

    private void lblInstitucionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseClicked
        tpOptionPages.setSelectedIndex(4);
    }//GEN-LAST:event_lblInstitucionesMouseClicked

    private void lblInstitucionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseEntered
        lblInstituciones.setForeground(Color.white);
    }//GEN-LAST:event_lblInstitucionesMouseEntered

    private void lblInstitucionesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseExited
        lblInstituciones.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblInstitucionesMouseExited
private void limpiarDesktopPane() {
    for (JInternalFrame frame : dpCRUDusuarios.getAllFrames()) {
        frame.dispose();
    }
}
    private void btnAltaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaUsuarioMouseClicked
        limpiarDesktopPane();
        IFAltaUsuario au = new IFAltaUsuario();
        dpCRUDusuarios.add(au);
        au.setVisible(true);
    
        //au.setVisible(true);
    }//GEN-LAST:event_btnAltaUsuarioMouseClicked

    private void btnConsultaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultaUsuarioMouseClicked
        limpiarDesktopPane();
        IFConsultaUsuario cu = new IFConsultaUsuario();
        dpCRUDusuarios.add(cu);
        cu.setVisible(true);
        
    }//GEN-LAST:event_btnConsultaUsuarioMouseClicked

    private void btnModificarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarUsuarioMouseClicked
        limpiarDesktopPane();
        IFModificarUsuario mu = new IFModificarUsuario();
        dpCRUDusuarios.add(mu);
        mu.setVisible(true);
    }//GEN-LAST:event_btnModificarUsuarioMouseClicked

    private void pnlTopPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopPanelMousePressed
        xMouse =evt.getX();
        yMouse =evt.getY();
    }//GEN-LAST:event_pnlTopPanelMousePressed

    private void pnlTopPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTopPanelMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xMouse,y-yMouse);
    }//GEN-LAST:event_pnlTopPanelMouseDragged

    private void btnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnSalirMouseClicked

    private void btnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseEntered
        btnSalir.setBackground(new Color(204,0,51));
    }//GEN-LAST:event_btnSalirMouseEntered

    private void btnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalirMouseExited
       btnSalir.setBackground(new Color(35, 71, 75));
    }//GEN-LAST:event_btnSalirMouseExited

    private void btnAltaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAltaUsuarioActionPerformed

    public static void main(String args[]) {
        
        /*IControlador controlador =
            Fabrica.getInstance().getIControlador();

    controlador.altaInstituto("UTEC");*/

        java.awt.EventQueue.invokeLater(() -> new JFMain().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAltaUsuario;
    private javax.swing.JButton btnConsultaUsuario;
    private javax.swing.JButton btnModificarUsuario;
    private javax.swing.JLabel btnSalir;
    private javax.swing.JSeparator cursosSeparator;
    private javax.swing.JDesktopPane dpCRUDusuarios;
    private javax.swing.JSeparator editionSeparator;
    private javax.swing.JSeparator institutionSeparator;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel lblCursos;
    private javax.swing.JLabel lblEdiciones;
    private javax.swing.JLabel lblInstituciones;
    private javax.swing.JLabel lblProgramas;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JPanel pnlCenterPanel;
    private javax.swing.JPanel pnlLeftPanel;
    private javax.swing.JPanel pnlTab1;
    private javax.swing.JPanel pnlTab2;
    private javax.swing.JPanel pnlTab3;
    private javax.swing.JPanel pnlTab4;
    private javax.swing.JPanel pnlTab5;
    private javax.swing.JPanel pnlTopPanel;
    private javax.swing.JSeparator programsSeparator;
    private javax.swing.JTabbedPane tpOptionPages;
    private javax.swing.JSeparator usersSeparator;
    // End of variables declaration//GEN-END:variables
}
