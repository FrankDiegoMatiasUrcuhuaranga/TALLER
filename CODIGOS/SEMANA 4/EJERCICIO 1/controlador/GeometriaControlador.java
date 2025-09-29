package com.figuras.controlador;

import com.figuras.modelo.Circulo;
import com.figuras.modelo.Rectangulo;
import com.figuras.modelo.Triangulo;
import com.figuras.vista.GeometriaVista;
import com.figuras.vista.ImagenPixelArt;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class GeometriaControlador {
    private final GeometriaVista vista;
    private BufferedImage ultimaImagen; // para mostrar en ventana ampliada

    public GeometriaControlador(GeometriaVista vista) {
        this.vista = vista;
        initListeners();
    }

    private void initListeners() {
        vista.onCambiarFigura(e -> vista.actualizarCamposSegunFigura());
        vista.onCalcular(e -> calcular());
        vista.onNuevo(e -> vista.limpiar());
        vista.onSalir(e -> System.exit(0));
        vista.onVerImagen(e -> mostrarImagenAmpliada());
    }

    private void calcular() {
        try {
            String figura = vista.getFiguraSeleccionada();
            double area, perimetro;
            switch (figura) {
                case "Rectángulo": {
                    double base = vista.getValor("base");
                    double altura = vista.getValor("altura");
                    validarPositivo(base, "Base");
                    validarPositivo(altura, "Altura");
                    Rectangulo r = new Rectangulo(base, altura);
                    area = r.calcularArea();
                    perimetro = r.calcularPerimetro();
                    ultimaImagen = ImagenPixelArt.rectangulo(220, 160, 16);
                    break;
                }
                case "Círculo": {
                    double radio = vista.getValor("radio");
                    validarPositivo(radio, "Radio");
                    Circulo c = new Circulo(radio);
                    area = c.calcularArea();
                    perimetro = c.calcularPerimetro();
                    ultimaImagen = ImagenPixelArt.circulo(200, 200, 16);
                    break;
                }
                case "Triángulo": {
                    double a = vista.getValor("ladoA");
                    double b = vista.getValor("ladoB");
                    double c = vista.getValor("ladoC");
                    validarPositivo(a, "Lado A");
                    validarPositivo(b, "Lado B");
                    validarPositivo(c, "Lado C");
                    validarDesigualdadTriangular(a, b, c);
                    Triangulo t = new Triangulo(a, b, c);
                    area = t.calcularArea();
                    perimetro = t.calcularPerimetro();
                    ultimaImagen = ImagenPixelArt.triangulo(220, 160, 16);
                    break;
                }
                default:
                    throw new IllegalStateException("Figura no soportada");
            }
            vista.mostrarResultados(area, perimetro);
            vista.mostrarMiniatura(ultimaImagen);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese valores numéricos válidos.",
                    "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarImagenAmpliada() {
        if (ultimaImagen == null) {
            JOptionPane.showMessageDialog(vista, "Primero calcule para ver la figura.",
                    "Sin imagen", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JDialog dialog = new JDialog(vista, "Vista de figura", true);
        JLabel lbl = new JLabel(new ImageIcon(ultimaImagen));
        dialog.getContentPane().add(new JScrollPane(lbl));
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(vista);
        dialog.setVisible(true);
    }

    private void validarPositivo(double v, String nombre) {
        if (Double.isNaN(v) || v <= 0) {
            throw new IllegalArgumentException(nombre + " debe ser un número positivo.");
        }
    }

    private void validarDesigualdadTriangular(double a, double b, double c) {
        if (!(a + b > c && a + c > b && b + c > a)) {
            throw new IllegalArgumentException("Los lados no cumplen la desigualdad triangular.");
        }
    }
}
