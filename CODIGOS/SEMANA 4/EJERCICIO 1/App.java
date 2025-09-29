package com.figuras;

import com.figuras.controlador.GeometriaControlador;
import com.figuras.vista.GeometriaVista;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GeometriaVista vista = new GeometriaVista();
            new GeometriaControlador(vista);
            vista.setVisible(true);
        });
    }
}
