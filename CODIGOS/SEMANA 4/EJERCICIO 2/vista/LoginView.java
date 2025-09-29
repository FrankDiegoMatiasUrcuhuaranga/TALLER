package com.figuras.vista;
import javax.swing.*; import java.awt.*; import java.awt.event.ActionListener;
public class LoginView extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnLogin;
    public LoginView() {
        setTitle("Login - Figuras"); setSize(360,200); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); setResizable(false); getContentPane().setBackground(Color.WHITE);
        JPanel p = new JPanel(new GridLayout(3,2,8,8)); p.setBorder(BorderFactory.createEmptyBorder(18,18,18,18)); p.setBackground(Color.WHITE);
        p.add(new JLabel("Usuario:")); txtUsuario = new JTextField(); p.add(txtUsuario);
        p.add(new JLabel("Contraseña:")); txtPassword = new JPasswordField(); p.add(txtPassword);
        btnLogin = new JButton("Ingresar"); btnLogin.setBackground(new Color(33,150,243)); btnLogin.setForeground(Color.WHITE);
        p.add(new JLabel()); p.add(btnLogin);
        add(p);
    }
    public String getUsuario(){return txtUsuario.getText().trim();}
    public String getPassword(){return new String(txtPassword.getPassword());}
    public void onLogin(ActionListener l){ btnLogin.addActionListener(l); }
}
