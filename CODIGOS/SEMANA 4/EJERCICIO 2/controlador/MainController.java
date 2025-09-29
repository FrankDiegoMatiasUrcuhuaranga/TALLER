package com.figuras.controlador;
import com.figuras.vista.MainView;
import com.figuras.modelo.User;
import com.figuras.modelo.ConversionEntry;
import com.figuras.modelo.ConversionUtils;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class MainController {
    private final MainView vista;
    private final User usuario;
    private final List<ConversionEntry> historial = new ArrayList<>();
    public MainController(MainView vista, User usuario) { this.vista=vista; this.usuario=usuario; init(); }
    private void init(){
        vista.onConvertir(e -> procesarConversion());
        vista.onGuardar(e -> guardarConversion());
        vista.onRefrescar(e -> refrescarLista());
        vista.configurarPorRol(usuario.getRole());
        refrescarLista();
    }
    private void procesarConversion() {
        try {
            String cat = vista.getCategoriaSeleccionada();
            String desde = vista.getUnidadDesde();
            String hacia = vista.getUnidadHacia();
            double entrada = vista.getValorEntrada();
            double resultado;
            switch (cat) {
                case "Longitud": resultado = ConversionUtils.convertirLongitud(desde,hacia,entrada); break;
                case "Masa": resultado = ConversionUtils.convertirMasa(desde,hacia,entrada); break;
                case "Temperatura": resultado = ConversionUtils.convertirTemperatura(desde,hacia,entrada); break;
                default: throw new IllegalArgumentException("Categoría no soportada");
            }
            vista.mostrarResultado(resultado);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número válido.", "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void guardarConversion() {
        if (usuario.getRole().toString().equals("ALUMNO")) { JOptionPane.showMessageDialog(vista, "Los alumnos no pueden guardar conversiones.", "Acceso denegado", JOptionPane.WARNING_MESSAGE); return; }
        try {
            String cat = vista.getCategoriaSeleccionada();
            String desde = vista.getUnidadDesde();
            String hacia = vista.getUnidadHacia();
            double entrada = vista.getValorEntrada();
            double resultado = vista.getResultadoMostrado();
            ConversionEntry e = new ConversionEntry(cat, desde, hacia, entrada, resultado, usuario.getUsername());
            historial.add(e);
            JOptionPane.showMessageDialog(vista, "Conversión guardada.", "OK", JOptionPane.INFORMATION_MESSAGE);
            refrescarLista();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "No hay resultado para guardar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void refrescarLista() {
        vista.limpiarTabla();
        for (ConversionEntry e : historial) vista.agregarFilaALista(e);
    }
}
