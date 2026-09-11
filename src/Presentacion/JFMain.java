package Presentacion;

import Logica.controladores.Fabrica;
import Logica.controladores.IControlador;
import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class JFMain extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFMain.class.getName());
    int xMouse, yMouse;
    private IControlador controlador;
    public JFMain() {
        initComponents();
        controlador= Fabrica.getInstance().getIControlador(); 
        
        //Oculte la tpOptions para que tengamos un solo dpPrincipal!!!!
        //tpOptionPages.setVisible(false);
        
        pnlMenuUsuarios.setVisible(false);
        pnlMenuCursos.setVisible(false);
        pnlMenuEdiciones.setVisible(false);
        pnlMenuProgramas.setVisible(false);
        pnlMenuInstituciones.setVisible(false);
    }
    
    private void abrirInternalFrame(javax.swing.JInternalFrame ventana) {

    // Cierra cualquier InternalFrame que ya esté abierto
    for (javax.swing.JInternalFrame frame : dpPrincipal.getAllFrames()) {
        frame.dispose();
    }

    dpPrincipal.add(ventana);
    ventana.setVisible(true);
}
    
    private void cerrarSubmenus() {
        pnlMenuUsuarios.setVisible(false);
        pnlMenuCursos.setVisible(false);
        pnlMenuEdiciones.setVisible(false);
        pnlMenuProgramas.setVisible(false);
        pnlMenuInstituciones.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeftPanel = new javax.swing.JPanel();
        pnlOpcionesMenu = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        usersSeparator = new javax.swing.JSeparator();
        pnlMenuUsuarios = new javax.swing.JPanel();
        lblAltaUsuario = new javax.swing.JLabel();
        lblConsultaUsuario = new javax.swing.JLabel();
        lblModificarUsuario = new javax.swing.JLabel();
        lblCursos = new javax.swing.JLabel();
        cursosSeparator = new javax.swing.JSeparator();
        pnlMenuCursos = new javax.swing.JPanel();
        lblAltaCurso = new javax.swing.JLabel();
        lblConsultaCurso = new javax.swing.JLabel();
        lblEdiciones = new javax.swing.JLabel();
        editionSeparator = new javax.swing.JSeparator();
        pnlMenuEdiciones = new javax.swing.JPanel();
        lblAltaEdicion = new javax.swing.JLabel();
        lblConsultaEdicion = new javax.swing.JLabel();
        lblInscripcionEdicion = new javax.swing.JLabel();
        lblProgramas = new javax.swing.JLabel();
        programsSeparator = new javax.swing.JSeparator();
        pnlMenuProgramas = new javax.swing.JPanel();
        lblCrearPrograma = new javax.swing.JLabel();
        lblAgregarCursoPrograma = new javax.swing.JLabel();
        lblConsultaPrograma = new javax.swing.JLabel();
        lblInstituciones = new javax.swing.JLabel();
        institutionSeparator = new javax.swing.JSeparator();
        pnlMenuInstituciones = new javax.swing.JPanel();
        lblAltaInstituto = new javax.swing.JLabel();
        lblCargarDatosPrueba = new javax.swing.JLabel();
        separatorDatosPrueba = new javax.swing.JSeparator();
        pnlTopPanel = new javax.swing.JPanel();
        btnSalir = new javax.swing.JLabel();
        lblUsuarios1 = new javax.swing.JLabel();
        pnlCenterPanel = new javax.swing.JPanel();
        dpPrincipal = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EDEXT");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(10, 10));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1150, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeftPanel.setBackground(new java.awt.Color(47, 93, 98));
        pnlLeftPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlLeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlOpcionesMenu.setBackground(new java.awt.Color(47, 93, 98));
        pnlOpcionesMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 12, 1, 12));
        pnlOpcionesMenu.setLayout(new javax.swing.BoxLayout(pnlOpcionesMenu, javax.swing.BoxLayout.Y_AXIS));

        lblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuarios.setForeground(new java.awt.Color(206, 206, 206));
        lblUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsuarios.setText("Usuarios");
        lblUsuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUsuarios.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblUsuarios.setPreferredSize(new java.awt.Dimension(260, 40));
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
        pnlOpcionesMenu.add(lblUsuarios);

        usersSeparator.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        usersSeparator.setMaximumSize(new java.awt.Dimension(32767, 1));
        usersSeparator.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(usersSeparator);

        pnlMenuUsuarios.setBackground(new java.awt.Color(47, 93, 98));
        pnlMenuUsuarios.setAlignmentX(0.0F);
        pnlMenuUsuarios.setMaximumSize(new java.awt.Dimension(32767, 105));
        pnlMenuUsuarios.setPreferredSize(new java.awt.Dimension(260, 105));
        pnlMenuUsuarios.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        lblAltaUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAltaUsuario.setForeground(new java.awt.Color(242, 242, 242));
        lblAltaUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAltaUsuario.setText("Alta Usuario");
        lblAltaUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblAltaUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAltaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAltaUsuarioMouseClicked(evt);
            }
        });
        pnlMenuUsuarios.add(lblAltaUsuario);

        lblConsultaUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConsultaUsuario.setForeground(new java.awt.Color(242, 242, 242));
        lblConsultaUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblConsultaUsuario.setText("Consulta de Usuario");
        lblConsultaUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblConsultaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConsultaUsuarioMouseClicked(evt);
            }
        });
        pnlMenuUsuarios.add(lblConsultaUsuario);

        lblModificarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblModificarUsuario.setForeground(new java.awt.Color(242, 242, 242));
        lblModificarUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblModificarUsuario.setText("Modificar Usuario");
        lblModificarUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblModificarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblModificarUsuarioMouseClicked(evt);
            }
        });
        pnlMenuUsuarios.add(lblModificarUsuario);

        pnlOpcionesMenu.add(pnlMenuUsuarios);

        lblCursos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCursos.setForeground(new java.awt.Color(206, 206, 206));
        lblCursos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCursos.setText("Cursos");
        lblCursos.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblCursos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCursos.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblCursos.setPreferredSize(new java.awt.Dimension(260, 40));
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
        pnlOpcionesMenu.add(lblCursos);

        cursosSeparator.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        cursosSeparator.setMaximumSize(new java.awt.Dimension(32767, 1));
        cursosSeparator.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(cursosSeparator);

        pnlMenuCursos.setBackground(new java.awt.Color(47, 93, 98));
        pnlMenuCursos.setAlignmentX(0.0F);
        pnlMenuCursos.setMaximumSize(new java.awt.Dimension(32767, 70));
        pnlMenuCursos.setPreferredSize(new java.awt.Dimension(260, 70));
        pnlMenuCursos.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        lblAltaCurso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAltaCurso.setForeground(new java.awt.Color(242, 242, 242));
        lblAltaCurso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAltaCurso.setText("Alta de  Curso");
        lblAltaCurso.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblAltaCurso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAltaCurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAltaCursoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAltaCursoMouseEntered(evt);
            }
        });
        pnlMenuCursos.add(lblAltaCurso);

        lblConsultaCurso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConsultaCurso.setForeground(new java.awt.Color(242, 242, 242));
        lblConsultaCurso.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblConsultaCurso.setText("Consulta de Curso");
        lblConsultaCurso.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblConsultaCurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConsultaCursoMouseClicked(evt);
            }
        });
        pnlMenuCursos.add(lblConsultaCurso);

        pnlOpcionesMenu.add(pnlMenuCursos);

        lblEdiciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEdiciones.setForeground(new java.awt.Color(206, 206, 206));
        lblEdiciones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEdiciones.setText("Ediciones");
        lblEdiciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblEdiciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEdiciones.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblEdiciones.setPreferredSize(new java.awt.Dimension(260, 40));
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
        pnlOpcionesMenu.add(lblEdiciones);

        editionSeparator.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        editionSeparator.setMaximumSize(new java.awt.Dimension(32767, 1));
        editionSeparator.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(editionSeparator);

        pnlMenuEdiciones.setBackground(new java.awt.Color(47, 93, 98));
        pnlMenuEdiciones.setAlignmentX(0.0F);
        pnlMenuEdiciones.setMaximumSize(new java.awt.Dimension(32767, 105));
        pnlMenuEdiciones.setPreferredSize(new java.awt.Dimension(260, 105));
        pnlMenuEdiciones.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        lblAltaEdicion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAltaEdicion.setForeground(new java.awt.Color(242, 242, 242));
        lblAltaEdicion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAltaEdicion.setText("Alta de Edicion de Curso");
        lblAltaEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblAltaEdicion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAltaEdicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAltaEdicionMouseClicked(evt);
            }
        });
        pnlMenuEdiciones.add(lblAltaEdicion);

        lblConsultaEdicion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConsultaEdicion.setForeground(new java.awt.Color(242, 242, 242));
        lblConsultaEdicion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblConsultaEdicion.setText("Consulta de Edicion");
        lblConsultaEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblConsultaEdicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConsultaEdicionMouseClicked(evt);
            }
        });
        pnlMenuEdiciones.add(lblConsultaEdicion);

        lblInscripcionEdicion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInscripcionEdicion.setForeground(new java.awt.Color(242, 242, 242));
        lblInscripcionEdicion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInscripcionEdicion.setText("Inscripción a Edición ");
        lblInscripcionEdicion.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblInscripcionEdicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInscripcionEdicionMouseClicked(evt);
            }
        });
        pnlMenuEdiciones.add(lblInscripcionEdicion);

        pnlOpcionesMenu.add(pnlMenuEdiciones);

        lblProgramas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProgramas.setForeground(new java.awt.Color(206, 206, 206));
        lblProgramas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblProgramas.setText("Programas");
        lblProgramas.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblProgramas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblProgramas.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblProgramas.setPreferredSize(new java.awt.Dimension(260, 40));
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
        pnlOpcionesMenu.add(lblProgramas);

        programsSeparator.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        programsSeparator.setMaximumSize(new java.awt.Dimension(32767, 1));
        programsSeparator.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(programsSeparator);

        pnlMenuProgramas.setBackground(new java.awt.Color(47, 93, 98));
        pnlMenuProgramas.setAlignmentX(0.0F);
        pnlMenuProgramas.setMaximumSize(new java.awt.Dimension(32767, 105));
        pnlMenuProgramas.setPreferredSize(new java.awt.Dimension(260, 105));
        pnlMenuProgramas.setLayout(new java.awt.GridLayout(3, 1, 0, 2));

        lblCrearPrograma.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCrearPrograma.setForeground(new java.awt.Color(242, 242, 242));
        lblCrearPrograma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCrearPrograma.setText("Crear Programa de Formacion");
        lblCrearPrograma.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblCrearPrograma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCrearPrograma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCrearProgramaMouseClicked(evt);
            }
        });
        pnlMenuProgramas.add(lblCrearPrograma);

        lblAgregarCursoPrograma.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAgregarCursoPrograma.setForeground(new java.awt.Color(242, 242, 242));
        lblAgregarCursoPrograma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAgregarCursoPrograma.setText("Agregar Curso a Programa");
        lblAgregarCursoPrograma.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblAgregarCursoPrograma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAgregarCursoProgramaMouseClicked(evt);
            }
        });
        pnlMenuProgramas.add(lblAgregarCursoPrograma);

        lblConsultaPrograma.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConsultaPrograma.setForeground(new java.awt.Color(242, 242, 242));
        lblConsultaPrograma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblConsultaPrograma.setText("Consulta de Programa");
        lblConsultaPrograma.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblConsultaPrograma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConsultaProgramaMouseClicked(evt);
            }
        });
        pnlMenuProgramas.add(lblConsultaPrograma);

        pnlOpcionesMenu.add(pnlMenuProgramas);

        lblInstituciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblInstituciones.setForeground(new java.awt.Color(206, 206, 206));
        lblInstituciones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInstituciones.setText("Instituciones");
        lblInstituciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblInstituciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInstituciones.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblInstituciones.setPreferredSize(new java.awt.Dimension(260, 40));
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
        pnlOpcionesMenu.add(lblInstituciones);

        institutionSeparator.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        institutionSeparator.setMaximumSize(new java.awt.Dimension(32767, 1));
        institutionSeparator.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(institutionSeparator);

        pnlMenuInstituciones.setBackground(new java.awt.Color(47, 93, 98));
        pnlMenuInstituciones.setAlignmentX(0.0F);
        pnlMenuInstituciones.setMaximumSize(new java.awt.Dimension(32767, 35));
        pnlMenuInstituciones.setPreferredSize(new java.awt.Dimension(260, 35));
        pnlMenuInstituciones.setLayout(new java.awt.GridLayout(1, 1, 0, 2));

        lblAltaInstituto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAltaInstituto.setForeground(new java.awt.Color(242, 242, 242));
        lblAltaInstituto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAltaInstituto.setText("Alta de Instituto");
        lblAltaInstituto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 0));
        lblAltaInstituto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAltaInstituto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAltaInstitutoMouseClicked(evt);
            }
        });
        pnlMenuInstituciones.add(lblAltaInstituto);

        pnlOpcionesMenu.add(pnlMenuInstituciones);

        lblCargarDatosPrueba.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCargarDatosPrueba.setForeground(new java.awt.Color(206, 206, 206));
        lblCargarDatosPrueba.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCargarDatosPrueba.setText("Cargar datos");
        lblCargarDatosPrueba.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 14, 7, 0));
        lblCargarDatosPrueba.setMaximumSize(new java.awt.Dimension(32767, 40));
        lblCargarDatosPrueba.setPreferredSize(new java.awt.Dimension(260, 40));
        lblCargarDatosPrueba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCargarDatosPruebaMouseClicked(evt);
            }
        });
        pnlOpcionesMenu.add(lblCargarDatosPrueba);

        separatorDatosPrueba.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 12, 1, 1));
        separatorDatosPrueba.setMaximumSize(new java.awt.Dimension(32767, 1));
        separatorDatosPrueba.setPreferredSize(new java.awt.Dimension(250, 1));
        pnlOpcionesMenu.add(separatorDatosPrueba);

        pnlLeftPanel.add(pnlOpcionesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 440));

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

        lblUsuarios1.setFont(new java.awt.Font("Wide Latin", 0, 18)); // NOI18N
        lblUsuarios1.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarios1.setText("EDEXT");
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

        javax.swing.GroupLayout pnlTopPanelLayout = new javax.swing.GroupLayout(pnlTopPanel);
        pnlTopPanel.setLayout(pnlTopPanelLayout);
        pnlTopPanelLayout.setHorizontalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblUsuarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(913, 913, 913)
                .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );
        pnlTopPanelLayout.setVerticalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopPanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlTopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuarios1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSalir.getAccessibleContext().setAccessibleName("x");

        getContentPane().add(pnlTopPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 60));

        pnlCenterPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout dpPrincipalLayout = new javax.swing.GroupLayout(dpPrincipal);
        dpPrincipal.setLayout(dpPrincipalLayout);
        dpPrincipalLayout.setHorizontalGroup(
            dpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        dpPrincipalLayout.setVerticalGroup(
            dpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        pnlCenterPanel.add(dpPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 910, 650));

        getContentPane().add(pnlCenterPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 910, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
         //tpOptionPages.setSelectedIndex(0);
        boolean estabaAbierto = pnlMenuUsuarios.isVisible();

        cerrarSubmenus();

        pnlMenuUsuarios.setVisible(!estabaAbierto);
        pnlMenuUsuarios.getParent().revalidate();
        pnlMenuUsuarios.getParent().repaint();
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        lblUsuarios.setForeground(Color.white);
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        lblUsuarios.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblCursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCursosMouseClicked
        //tpOptionPages.setSelectedIndex(1);
        boolean estabaAbierto = pnlMenuCursos.isVisible();

        cerrarSubmenus();

        pnlMenuCursos.setVisible(!estabaAbierto);
        pnlMenuCursos.getParent().revalidate();
        pnlMenuCursos.getParent().repaint();
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
        //tpOptionPages.setSelectedIndex(2);
        boolean estabaAbierto = pnlMenuEdiciones.isVisible();

        cerrarSubmenus();

        pnlMenuEdiciones.setVisible(!estabaAbierto);

        pnlMenuEdiciones.getParent().revalidate();
        pnlMenuEdiciones.getParent().repaint();

    }//GEN-LAST:event_lblEdicionesMouseClicked

    private void lblEdicionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEdicionesMouseEntered
        lblEdiciones.setForeground(Color.white);
    }//GEN-LAST:event_lblEdicionesMouseEntered

    private void lblProgramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseClicked

        boolean estabaAbierto = pnlMenuProgramas.isVisible();

        cerrarSubmenus();

        pnlMenuProgramas.setVisible(!estabaAbierto);
        pnlMenuProgramas.getParent().revalidate();
        pnlMenuProgramas.getParent().repaint();
    }//GEN-LAST:event_lblProgramasMouseClicked

    private void lblProgramasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseEntered
        lblProgramas.setForeground(Color.white);
    }//GEN-LAST:event_lblProgramasMouseEntered

    private void lblProgramasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProgramasMouseExited
        lblProgramas.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblProgramasMouseExited

    private void lblInstitucionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseClicked
        //tpOptionPages.setSelectedIndex(4);
        boolean estabaAbierto = pnlMenuInstituciones.isVisible();

        cerrarSubmenus();

        pnlMenuInstituciones.setVisible(!estabaAbierto);
        pnlMenuInstituciones.getParent().revalidate();
        pnlMenuInstituciones.getParent().repaint();
    }//GEN-LAST:event_lblInstitucionesMouseClicked

    private void lblInstitucionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseEntered
        lblInstituciones.setForeground(Color.white);
    }//GEN-LAST:event_lblInstitucionesMouseEntered

    private void lblInstitucionesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInstitucionesMouseExited
        lblInstituciones.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblInstitucionesMouseExited

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


    private void lblAltaCursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAltaCursoMouseClicked
        abrirInternalFrame(new IFAltaCurso());
    }//GEN-LAST:event_lblAltaCursoMouseClicked

    private void lblAltaInstitutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAltaInstitutoMouseClicked
        abrirInternalFrame(new IFAltaInstituto());
    }//GEN-LAST:event_lblAltaInstitutoMouseClicked

    private void lblAltaEdicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAltaEdicionMouseClicked
    abrirInternalFrame(new IFAltaEdicionCurso(controlador));
    }//GEN-LAST:event_lblAltaEdicionMouseClicked

    private void lblAltaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAltaUsuarioMouseClicked
       
        abrirInternalFrame(new IFAltaUsuario());

    }//GEN-LAST:event_lblAltaUsuarioMouseClicked

    private void lblConsultaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConsultaUsuarioMouseClicked
        abrirInternalFrame(new IFConsultaUsuario());
    }//GEN-LAST:event_lblConsultaUsuarioMouseClicked

    private void lblModificarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblModificarUsuarioMouseClicked
        abrirInternalFrame(new IFModificarUsuario());
    }//GEN-LAST:event_lblModificarUsuarioMouseClicked

    private void lblAltaCursoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAltaCursoMouseEntered
           }//GEN-LAST:event_lblAltaCursoMouseEntered

    private void lblCrearProgramaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCrearProgramaMouseClicked
        abrirInternalFrame(new IFAltaPrograma());
    }//GEN-LAST:event_lblCrearProgramaMouseClicked

    private void lblConsultaProgramaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConsultaProgramaMouseClicked
        abrirInternalFrame(new IFConsultaPrograma());
    }//GEN-LAST:event_lblConsultaProgramaMouseClicked

    private void lblConsultaCursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConsultaCursoMouseClicked
        abrirInternalFrame(new IFConsultaCurso());
    }//GEN-LAST:event_lblConsultaCursoMouseClicked

    private void lblConsultaEdicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConsultaEdicionMouseClicked
         abrirInternalFrame(new IFConsultaEdicionCurso(controlador));
    }//GEN-LAST:event_lblConsultaEdicionMouseClicked

    private void lblInscripcionEdicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInscripcionEdicionMouseClicked
        abrirInternalFrame(new IFInscripcionEdicionCurso(controlador));
    }//GEN-LAST:event_lblInscripcionEdicionMouseClicked


    private void lblAgregarCursoProgramaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAgregarCursoProgramaMouseClicked
        abrirInternalFrame(new IFAgregarCursoAPrograma());
    }//GEN-LAST:event_lblAgregarCursoProgramaMouseClicked

    private void lblCargarDatosPruebaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCargarDatosPruebaMouseClicked
 
    try {
        controlador.cargarDatosPrueba();

        JOptionPane.showMessageDialog(
                this,
                "Los datos de prueba fueron cargados correctamente.",
                "Datos de prueba",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al cargar los datos de prueba:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
    }

    }//GEN-LAST:event_lblCargarDatosPruebaMouseClicked


    private void lblUsuarios1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseClicked

    private void lblUsuarios1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseEntered

    private void lblUsuarios1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarios1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUsuarios1MouseExited





    public static void main(String args[]) {
        

        java.awt.EventQueue.invokeLater(() -> new JFMain().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnSalir;
    private javax.swing.JSeparator cursosSeparator;
    private javax.swing.JDesktopPane dpPrincipal;
    private javax.swing.JSeparator editionSeparator;
    private javax.swing.JSeparator institutionSeparator;
    private javax.swing.JLabel lblAgregarCursoPrograma;
    private javax.swing.JLabel lblAltaCurso;
    private javax.swing.JLabel lblAltaEdicion;
    private javax.swing.JLabel lblAltaInstituto;
    private javax.swing.JLabel lblAltaUsuario;
    private javax.swing.JLabel lblCargarDatosPrueba;
    private javax.swing.JLabel lblConsultaCurso;
    private javax.swing.JLabel lblConsultaEdicion;
    private javax.swing.JLabel lblConsultaPrograma;
    private javax.swing.JLabel lblConsultaUsuario;
    private javax.swing.JLabel lblCrearPrograma;
    private javax.swing.JLabel lblCursos;
    private javax.swing.JLabel lblEdiciones;
    private javax.swing.JLabel lblInscripcionEdicion;
    private javax.swing.JLabel lblInstituciones;
    private javax.swing.JLabel lblModificarUsuario;
    private javax.swing.JLabel lblProgramas;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblUsuarios1;
    private javax.swing.JPanel pnlCenterPanel;
    private javax.swing.JPanel pnlLeftPanel;
    private javax.swing.JPanel pnlMenuCursos;
    private javax.swing.JPanel pnlMenuEdiciones;
    private javax.swing.JPanel pnlMenuInstituciones;
    private javax.swing.JPanel pnlMenuProgramas;
    private javax.swing.JPanel pnlMenuUsuarios;
    private javax.swing.JPanel pnlOpcionesMenu;
    private javax.swing.JPanel pnlTopPanel;
    private javax.swing.JSeparator programsSeparator;
    private javax.swing.JSeparator separatorDatosPrueba;
    private javax.swing.JSeparator usersSeparator;
    // End of variables declaration//GEN-END:variables
}
