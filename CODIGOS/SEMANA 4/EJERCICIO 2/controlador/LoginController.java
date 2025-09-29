package com.figuras.controlador;
import com.figuras.vista.LoginView;
import com.figuras.vista.MainView;
import com.figuras.modelo.User;
import com.figuras.modelo.Role;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class LoginController {
    private final LoginView vista;
    private final List<User> usuarios = new ArrayList<>();
    public LoginController(LoginView vista) { this.vista = vista; seedUsuarios(); initListeners(); }
    private void seedUsuarios() {
        usuarios.add(new User("docente", "123", Role.DOCENTE));
        usuarios.add(new User("alumno", "456", Role.ALUMNO));
    }
    private void initListeners() { vista.onLogin(e -> autenticar()); }
    private void autenticar() {
        String user = vista.getUsuario(); String pass = vista.getPassword();
        if (user.isEmpty() || pass.isEmpty()) { JOptionPane.showMessageDialog(vista, "Ingrese usuario y contraseña.", "Datos faltantes", JOptionPane.WARNING_MESSAGE); return; }
        User encontrado = usuarios.stream().filter(u -> u.getUsername().equals(user) && u.getPassword().equals(pass)).findFirst().orElse(null);
        if (encontrado == null) { JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE); return; }
        SwingUtilities.invokeLater(() -> {
            MainView main = new MainView(encontrado);
            new MainController(main, encontrado);
            vista.dispose();
            main.setVisible(true);
        });
    }
}
