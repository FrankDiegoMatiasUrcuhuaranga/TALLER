/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frame;


import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import Modelo.ConexionBD;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Registro extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Registro.class.getName());
    private final Registro jFrame;

    /**
     * Creates new form Login
     */
    public Registro() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Inicializaciones adicionales para coincidir con la lista
        jFrame = this;
        setTitle("Registro de Usuario");
        setSize(900, 630);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        jpanelRegistro.setLayout(null);
        txtContraseña.setText("");
    }
    
    private void registrarUsuario() throws SQLException {
        // 1. Obtener los datos de los campos
        String nombreUsuario = txtUser.getText().trim();
        String contrasena = new String(txtContraseña.getPassword());
        String rol = (String) ComboRol.getSelectedItem();
        String nombreCompleto = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        
        // 2. Validación simple de campos
        if (nombreUsuario.isEmpty() || contrasena.isEmpty() || nombreCompleto.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos para el registro.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // **IMPORTANTE: En una aplicación real, la contraseña debe ser HASHADA antes de almacenarse.**
        // Por simplicidad, se almacena directamente, pero NO es seguro.
        
        Connection con = ConexionBD.obtenerConexion();
        PreparedStatement ps = null;

        if (con != null) {
            try {
                // Sentencia SQL de inserción con marcadores de posición (?)
                String sql = "INSERT INTO Usuarios (nombre_usuario, contrasena, rol, nombre_completo, correo) VALUES (?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);

                // Asignar valores a los marcadores de posición
                ps.setString(1, nombreUsuario);
                ps.setString(2, contrasena); // ⚠️ Recuerda HASHAR la contraseña
                ps.setString(3, rol);
                ps.setString(4, nombreCompleto);
                ps.setString(5, correo);

                // Ejecutar la inserción
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "¡Registro exitoso! Ya puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Opcional: limpiar campos o llevar al login
                    limpiarCampos();
                    mostrarLogin();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo completar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                // Manejar errores específicos, como nombre de usuario duplicado (clave UNIQUE)
                if (e.getErrorCode() == 1062) { // Código de error para duplicado en MySQL
                    JOptionPane.showMessageDialog(this, "El nombre de usuario '" + nombreUsuario + "' ya existe.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                } else {
                    logger.log(Level.SEVERE, "Error al registrar usuario en la base de datos:", e);
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar el usuario. Código: " + e.getErrorCode(), "Error de DB", JOptionPane.ERROR_MESSAGE);
                }
            } finally {
                // Cerrar recursos
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Error al cerrar recursos de DB:", e);
                }
            }
        }
    }
    
    private void limpiarCampos() {
        txtUser.setText("");
        txtContraseña.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        ComboRol.setSelectedIndex(0);
    }
    
    /**
     * Muestra la ventana de Login y cierra la actual.
     */
    private void mostrarLogin() {
        // ⚠️ Asume que existe una clase 'Login' en el paquete 'Frame'
        try {
            Login loginFrame = new Login(); 
            loginFrame.setVisible(true);
            this.dispose(); // Cierra la ventana de registro actual
        } catch (Exception e) {
            logger.log(Level.SEVERE, "No se pudo abrir la ventana de Login.", e);
            JOptionPane.showMessageDialog(this, "No se pudo encontrar la ventana de Login.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void OpenFacebook() {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.facebook.com/WriteCodeLV"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenYoutube() {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.youtube.com/c/writecode"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenX() {
        try {
            Desktop.getDesktop().browse(URI.create("https://x.com/"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    public void OpenUpla() {
        try {
            Desktop.getDesktop().browse(URI.create("https://upla.edu.pe/"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
   
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jblFondoImgene = new javax.swing.JLabel();
        jpanelRegistro = new javax.swing.JPanel();
        jblSalirPantalla = new javax.swing.JLabel();
        jblMinimizarPantalla = new javax.swing.JLabel();
        jblTituloRegistro_user = new javax.swing.JLabel();
        jblUser = new javax.swing.JLabel();
        jblContrasena = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jbtRegistrar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jblIconoUpla = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        txtContraseña = new javax.swing.JPasswordField();
        jblContrasena1 = new javax.swing.JLabel();
        jblContrasena2 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jblContrasena3 = new javax.swing.JLabel();
        ComboRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(900, 630));
        setMaximumSize(new java.awt.Dimension(900, 630));
        setMinimumSize(new java.awt.Dimension(900, 630));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(900, 630));
        setSize(new java.awt.Dimension(900, 630));

        jblFondoImgene.setBackground(new java.awt.Color(60, 63, 65));
        jblFondoImgene.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jblFondoImgene.setForeground(new java.awt.Color(187, 187, 187));
        jblFondoImgene.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Fondo 1.png"))); // NOI18N
        jblFondoImgene.setMaximumSize(new java.awt.Dimension(900, 630));
        jblFondoImgene.setMinimumSize(new java.awt.Dimension(900, 630));
        jblFondoImgene.setPreferredSize(new java.awt.Dimension(900, 630));

        jpanelRegistro.setBackground(new java.awt.Color(255, 204, 204));
        jpanelRegistro.setForeground(new java.awt.Color(187, 187, 187));
        jpanelRegistro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jblSalirPantalla.setBackground(new java.awt.Color(60, 63, 65));
        jblSalirPantalla.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 18)); // NOI18N
        jblSalirPantalla.setForeground(new java.awt.Color(78, 34, 88));
        jblSalirPantalla.setText("X");
        jblSalirPantalla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblSalirPantalla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblSalirPantallaMouseClicked(evt);
            }
        });

        jblMinimizarPantalla.setBackground(new java.awt.Color(78, 34, 88));
        jblMinimizarPantalla.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 18)); // NOI18N
        jblMinimizarPantalla.setForeground(new java.awt.Color(78, 34, 88));
        jblMinimizarPantalla.setText("-");
        jblMinimizarPantalla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblMinimizarPantalla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblMinimizarPantallaMouseClicked(evt);
            }
        });

        jblTituloRegistro_user.setBackground(new java.awt.Color(60, 63, 65));
        jblTituloRegistro_user.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jblTituloRegistro_user.setForeground(new java.awt.Color(78, 34, 88));
        jblTituloRegistro_user.setText("Registrarse_");

        jblUser.setBackground(new java.awt.Color(60, 63, 65));
        jblUser.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jblUser.setForeground(new java.awt.Color(78, 34, 88));
        jblUser.setText("USUARIO");

        jblContrasena.setBackground(new java.awt.Color(60, 63, 65));
        jblContrasena.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jblContrasena.setForeground(new java.awt.Color(78, 34, 88));
        jblContrasena.setText("CONTRASEÑA:");

        txtUser.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtUser.setForeground(new java.awt.Color(78, 34, 88));
        txtUser.setBorder(null);
        txtUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserMouseClicked(evt);
            }
        });
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        jbtRegistrar.setBackground(new java.awt.Color(255, 255, 255));
        jbtRegistrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbtRegistrar.setForeground(new java.awt.Color(78, 34, 88));
        jbtRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtRegistrar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jbtRegistrarMouseMoved(evt);
            }
        });
        jbtRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtRegistrarMouseClicked(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel10.setText("REGISTRAR");
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

        javax.swing.GroupLayout jbtRegistrarLayout = new javax.swing.GroupLayout(jbtRegistrar);
        jbtRegistrar.setLayout(jbtRegistrarLayout);
        jbtRegistrarLayout.setHorizontalGroup(
            jbtRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jbtRegistrarLayout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jbtRegistrarLayout.setVerticalGroup(
            jbtRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jbtRegistrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jblIconoUpla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N
        jblIconoUpla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jblIconoUpla.setMaximumSize(new java.awt.Dimension(70, 45));
        jblIconoUpla.setMinimumSize(new java.awt.Dimension(70, 45));
        jblIconoUpla.setPreferredSize(new java.awt.Dimension(70, 45));
        jblIconoUpla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblIconoUplaMouseClicked(evt);
            }
        });

        btnIniciar.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(78, 34, 88));
        btnIniciar.setText("INICIAR SECCION");
        btnIniciar.setBorder(null);
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        txtContraseña.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtContraseña.setForeground(new java.awt.Color(78, 34, 88));
        txtContraseña.setText("jPasswordField1");
        txtContraseña.setBorder(null);
        txtContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtContraseñaMouseClicked(evt);
            }
        });

        jblContrasena1.setBackground(new java.awt.Color(60, 63, 65));
        jblContrasena1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jblContrasena1.setForeground(new java.awt.Color(78, 34, 88));
        jblContrasena1.setText("ROL");

        jblContrasena2.setBackground(new java.awt.Color(60, 63, 65));
        jblContrasena2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jblContrasena2.setForeground(new java.awt.Color(78, 34, 88));
        jblContrasena2.setText("NOMBRE COMPLETO");

        txtCorreo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(78, 34, 88));
        txtCorreo.setBorder(null);
        txtCorreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCorreoMouseClicked(evt);
            }
        });
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(78, 34, 88));
        txtNombre.setBorder(null);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreMouseClicked(evt);
            }
        });
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jblContrasena3.setBackground(new java.awt.Color(60, 63, 65));
        jblContrasena3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jblContrasena3.setForeground(new java.awt.Color(78, 34, 88));
        jblContrasena3.setText("CORREO");

        ComboRol.setEditable(true);
        ComboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Maestro", "Padre" }));

        javax.swing.GroupLayout jpanelRegistroLayout = new javax.swing.GroupLayout(jpanelRegistro);
        jpanelRegistro.setLayout(jpanelRegistroLayout);
        jpanelRegistroLayout.setHorizontalGroup(
            jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelRegistroLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jblIconoUpla, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jblMinimizarPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jblSalirPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(jpanelRegistroLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jblContrasena1)
                    .addComponent(jblContrasena)
                    .addComponent(jblContrasena2)
                    .addComponent(jblContrasena3)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jblUser, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUser, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ComboRol, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpanelRegistroLayout.createSequentialGroup()
                .addGroup(jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelRegistroLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jblTituloRegistro_user, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelRegistroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jpanelRegistroLayout.setVerticalGroup(
            jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelRegistroLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jblSalirPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblMinimizarPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jblIconoUpla, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblTituloRegistro_user, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblContrasena)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblContrasena1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblContrasena2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblContrasena3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpanelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(148, 148, 148))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jblFondoImgene, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jblFondoImgene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpanelRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMouseClicked

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtCorreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCorreoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoMouseClicked

    private void txtContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContraseñaMouseClicked
        txtContraseña.setText("");
    }//GEN-LAST:event_txtContraseñaMouseClicked

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
mostrarLogin();
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void jblIconoUplaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblIconoUplaMouseClicked

        OpenUpla();

    }//GEN-LAST:event_jblIconoUplaMouseClicked

    private void jbtRegistrarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtRegistrarMouseMoved

        jbtRegistrar.setBackground(new java.awt.Color(78,34,88));

    }//GEN-LAST:event_jbtRegistrarMouseMoved

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited

        jbtRegistrar.setBackground(new java.awt.Color(54,6,55));

    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseMoved

    }//GEN-LAST:event_jLabel10MouseMoved

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserMouseClicked

        txtUser.setText("");

    }//GEN-LAST:event_txtUserMouseClicked

    private void jblMinimizarPantallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblMinimizarPantallaMouseClicked

        this.setState(Registro.ICONIFIED);

    }//GEN-LAST:event_jblMinimizarPantallaMouseClicked

    private void jblSalirPantallaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblSalirPantallaMouseClicked

        int dialogoButton = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "Desea Salir del Registro?","EXIT",dialogoButton);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }//GEN-LAST:event_jblSalirPantallaMouseClicked

    private void jbtRegistrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtRegistrarMouseClicked
        try {
            registrarUsuario();
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtRegistrarMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new Registro().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboRol;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jblContrasena;
    private javax.swing.JLabel jblContrasena1;
    private javax.swing.JLabel jblContrasena2;
    private javax.swing.JLabel jblContrasena3;
    private javax.swing.JLabel jblFondoImgene;
    private javax.swing.JLabel jblIconoUpla;
    private javax.swing.JLabel jblMinimizarPantalla;
    private javax.swing.JLabel jblSalirPantalla;
    private javax.swing.JLabel jblTituloRegistro_user;
    private javax.swing.JLabel jblUser;
    private javax.swing.JPanel jbtRegistrar;
    private javax.swing.JPanel jpanelRegistro;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
