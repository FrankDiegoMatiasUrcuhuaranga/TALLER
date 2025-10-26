/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;

import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.ConexionBD;
/**
 *
 * @author ASUS
 */
public class Login extends javax.swing.JFrame {
    private Registro VistaRegistro=new Registro();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setTitle("Inicio de Seccion");
        this.setLocationRelativeTo(null);
    }
    
    
    public void OpenFacebook(){
        try {
            Desktop.getDesktop().browse(URI.create("https://www.facebook.com/WriteCodeLV"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenYoutube(){
        try {
            Desktop.getDesktop().browse(URI.create("https://www.youtube.com/c/writecode"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenX(){
        try {
            Desktop.getDesktop().browse(URI.create("https://x.com/"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenUpla(){
        try {
            Desktop.getDesktop().browse(URI.create("https://upla.edu.pe/"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private String iniciarSesion(String usuario, String contrasena) {
        String sql = "SELECT rol, nombre_completo FROM Usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        
        try (Connection conn = Modelo.ConexionBD.obtenerConexion(); // Asumiendo que esta clase existe
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("nombre_completo");
                String rol = rs.getString("rol");
                
                JOptionPane.showMessageDialog(this, 
                        "✅ Bienvenido " + nombre + "\nRol: " + rol,
                        "Acceso Concedido",
                        JOptionPane.INFORMATION_MESSAGE);
                return rol; // Retorna el rol del usuario
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Usuario o contraseña incorrectos",
                        "Error de inicio de sesión",
                        JOptionPane.ERROR_MESSAGE);
                return null; // Retorna null si las credenciales son incorrectas
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            // Logear el error para depuración
            logger.log(java.util.logging.Level.SEVERE, "Error en iniciarSesion", e); 
            return null; // Retorna null en caso de error de conexión/SQL
        }
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jpsPasword = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jblFacebook = new javax.swing.JLabel();
        jblYoutube = new javax.swing.JLabel();
        jblX = new javax.swing.JLabel();
        jbtnEntrar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(900, 630));
        setMaximumSize(new java.awt.Dimension(900, 630));
        setMinimumSize(new java.awt.Dimension(900, 630));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 630));
        setSize(new java.awt.Dimension(900, 630));

        jLabel1.setBackground(new java.awt.Color(60, 63, 65));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(187, 187, 187));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Fondo 1.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(900, 630));
        jLabel1.setMinimumSize(new java.awt.Dimension(900, 630));
        jLabel1.setPreferredSize(new java.awt.Dimension(900, 630));

        jPanel1.setBackground(new java.awt.Color(16, 16, 26));
        jPanel1.setForeground(new java.awt.Color(187, 187, 187));
        jPanel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabel2.setBackground(new java.awt.Color(60, 63, 65));
        jLabel2.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(78, 34, 88));
        jLabel2.setText("X");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(78, 34, 88));
        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(78, 34, 88));
        jLabel3.setText("-");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(60, 63, 65));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(78, 34, 88));
        jLabel4.setText("Login_");

        jLabel5.setBackground(new java.awt.Color(60, 63, 65));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(78, 34, 88));
        jLabel5.setText("USER:");

        jLabel6.setBackground(new java.awt.Color(60, 63, 65));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(78, 34, 88));
        jLabel6.setText("CONTRASEÑA:");

        txtUser.setBackground(new java.awt.Color(16, 16, 16));
        txtUser.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtUser.setForeground(new java.awt.Color(78, 34, 88));
        txtUser.setText("Ingrese su Usuario");
        txtUser.setBorder(null);
        txtUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserMouseClicked(evt);
            }
        });

        jpsPasword.setBackground(new java.awt.Color(16, 16, 16));
        jpsPasword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jpsPasword.setForeground(new java.awt.Color(78, 34, 88));
        jpsPasword.setText("jPasswordField1");
        jpsPasword.setBorder(null);
        jpsPasword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpsPaswordMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(78, 34, 88));
        jSeparator1.setForeground(new java.awt.Color(78, 34, 88));

        jSeparator2.setBackground(new java.awt.Color(78, 34, 88));
        jSeparator2.setForeground(new java.awt.Color(78, 34, 88));

        jblFacebook.setForeground(new java.awt.Color(255, 255, 255));
        jblFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/facebook_off.png"))); // NOI18N
        jblFacebook.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblFacebook.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jblFacebookMouseMoved(evt);
            }
        });
        jblFacebook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblFacebookMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jblFacebookMouseExited(evt);
            }
        });

        jblYoutube.setForeground(new java.awt.Color(255, 255, 255));
        jblYoutube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/youtube.png"))); // NOI18N
        jblYoutube.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblYoutube.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jblYoutubeMouseMoved(evt);
            }
        });
        jblYoutube.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblYoutubeMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jblYoutubeMouseExited(evt);
            }
        });

        jblX.setForeground(new java.awt.Color(255, 255, 255));
        jblX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x.png"))); // NOI18N
        jblX.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblX.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jblXMouseMoved(evt);
            }
        });
        jblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblXMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jblXMouseExited(evt);
            }
        });

        jbtnEntrar.setBackground(new java.awt.Color(78, 34, 88));
        jbtnEntrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbtnEntrar.setForeground(new java.awt.Color(78, 34, 88));
        jbtnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtnEntrar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jbtnEntrarMouseMoved(evt);
            }
        });
        jbtnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnEntrarMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel10.setText("Already  a Member");
        jLabel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel10MouseMoved(evt);
            }
        });
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jbtnEntrarLayout = new javax.swing.GroupLayout(jbtnEntrar);
        jbtnEntrar.setLayout(jbtnEntrarLayout);
        jbtnEntrarLayout.setHorizontalGroup(
            jbtnEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jbtnEntrarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        jbtnEntrarLayout.setVerticalGroup(
            jbtnEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jbtnEntrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jSeparator3.setBackground(new java.awt.Color(78, 34, 88));
        jSeparator3.setForeground(new java.awt.Color(78, 34, 88));

        jLabel11.setBackground(new java.awt.Color(60, 63, 65));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(78, 34, 88));
        jLabel11.setText("Sing Up");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel7.setMaximumSize(new java.awt.Dimension(70, 45));
        jLabel7.setMinimumSize(new java.awt.Dimension(70, 45));
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 45));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jpsPasword)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jblYoutube)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jblFacebook))
                            .addComponent(jbtnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jSeparator3))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(jLabel11)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jblX)))))
                .addGap(57, 57, 57))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jpsPasword, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jbtnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jblFacebook, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jblYoutube, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jblX))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        
        this.setState(Login.ICONIFIED);
        
        
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
       
        
        int dialogoButton = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "Desea Salir del Login?","EXIT",dialogoButton);
        if (result == 0) {
            System.exit(0);
        }
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txtUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserMouseClicked
        
        txtUser.setText("");
        
        
    }//GEN-LAST:event_txtUserMouseClicked

    private void jpsPaswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpsPaswordMouseClicked
       
        jpsPasword.setText("");
        
        
    }//GEN-LAST:event_jpsPaswordMouseClicked

    private void jLabel10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseMoved
        
        
        
        
    }//GEN-LAST:event_jLabel10MouseMoved

    private void jbtnEntrarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnEntrarMouseMoved
        
        jbtnEntrar.setBackground(new java.awt.Color(78,34,88));
        
        
    }//GEN-LAST:event_jbtnEntrarMouseMoved

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        
        jbtnEntrar.setBackground(new java.awt.Color(54,6,55));
        
        
    }//GEN-LAST:event_jLabel10MouseExited

    private void jblYoutubeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblYoutubeMouseMoved
        
        jblYoutube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/youtube_ON.PNG")));
        
        
    }//GEN-LAST:event_jblYoutubeMouseMoved

    private void jblYoutubeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblYoutubeMouseExited
        
        jblYoutube.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/youtube.PNG")));
        
        
    }//GEN-LAST:event_jblYoutubeMouseExited

    private void jblFacebookMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblFacebookMouseMoved
        
        jblFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Facebook_ON.PNG")));
        
        
    }//GEN-LAST:event_jblFacebookMouseMoved

    private void jblFacebookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblFacebookMouseExited
        
        jblFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Facebook_off.PNG")));
        
        
    }//GEN-LAST:event_jblFacebookMouseExited

    private void jblXMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblXMouseMoved
        
        jblX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x.PNG")));
        
        
    }//GEN-LAST:event_jblXMouseMoved

    private void jblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblXMouseExited
        
        jblX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/x.PNG")));
        
        
    }//GEN-LAST:event_jblXMouseExited

    private void jblYoutubeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblYoutubeMouseClicked
        
        OpenYoutube();
        
        
    }//GEN-LAST:event_jblYoutubeMouseClicked

    private void jblFacebookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblFacebookMouseClicked
        
        OpenFacebook();
        
        
    }//GEN-LAST:event_jblFacebookMouseClicked

    private void jblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblXMouseClicked
        
        OpenX();
        
        
    }//GEN-LAST:event_jblXMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        
        OpenUpla();
        
        
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jbtnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnEntrarMouseClicked

String usuario = txtUser.getText().trim();
        String contrasena = new String(jpsPasword.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese su usuario y contraseña.",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Llamamos al método que ahora devuelve el rol
        String rol = iniciarSesion(usuario, contrasena);

        if (rol != null) {
            // Acceso concedido: Abrir PantallaAsistencia con el rol obtenido
            
            // 1. Ocultar la ventana de Login
            this.dispose();
            
            // 2. Crear y mostrar la PantallaAsistencia
            PantallaAsistencia asistencia = new PantallaAsistencia(rol);
            asistencia.setVisible(true);
        }


        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnEntrarMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked

VistaRegistro.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jblFacebook;
    private javax.swing.JLabel jblX;
    private javax.swing.JLabel jblYoutube;
    private javax.swing.JPanel jbtnEntrar;
    private javax.swing.JPasswordField jpsPasword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
