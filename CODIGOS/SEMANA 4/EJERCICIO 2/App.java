package com.figuras;
import com.figuras.vista.LoginView;
import com.figuras.controlador.LoginController;
import javax.swing.*;
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            new LoginController(login);
            login.setVisible(true);
        });
    }
}
