package com.figuras.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GeometriaVista extends JFrame {
    private final JComboBox<String> cbFigura;
    private final JPanel panelInputs;
    private final JTextArea txtSalida;
    private final JLabel lblMiniatura;
    private final JButton btnCalcular, btnNuevo, btnSalir, btnVerImagen;

    private final Map<String, JTextField> campos = new HashMap<>();
    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    public GeometriaVista() {
        setTitle("Áreas y Perímetros – MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        // Paleta sencilla y colorida
        Color fondo = Color.WHITE;
        Color primario = new Color(33, 150, 243);   // azul
        Color exito = new Color(76, 175, 80);       // verde
        Color alerta = new Color(244, 67, 54);      // rojo
        Color acento = new Color(255, 193, 7);      // amarillo

        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout(8, 8));

        // Norte: selección de figura
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(fondo);
        top.add(new JLabel("Figura:"));
        cbFigura = new JComboBox<>(new String[]{"Rectángulo", "Círculo", "Triángulo"});
        cbFigura.setFocusable(false);
        top.add(cbFigura);
        add(top, BorderLayout.NORTH);

        // Centro: inputs + resultados + miniatura
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(fondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        // Panel de inputs
        panelInputs = new JPanel(new GridLayout(4, 2, 6, 6));
        panelInputs.setBorder(BorderFactory.createTitledBorder("Datos de entrada"));
        panelInputs.setBackground(new Color(232, 245, 233)); // verde muy claro
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 0.6;
        center.add(panelInputs, gbc);

        // Panel de resultados
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBackground(new Color(227, 242, 253)); // azul muy claro
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        txtSalida = new JTextArea(8, 20);
        txtSalida.setEditable(false);
        txtSalida.setLineWrap(true);
        txtSalida.setWrapStyleWord(true);
        panelResultados.add(new JScrollPane(txtSalida), BorderLayout.CENTER);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weighty = 0.6;
        center.add(panelResultados, gbc);

        // Miniatura
        JPanel panelMini = new JPanel(new BorderLayout());
        panelMini.setBackground(new Color(255, 253, 231)); // amarillo muy claro
        panelMini.setBorder(BorderFactory.createTitledBorder("Vista previa"));
        lblMiniatura = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblMiniatura.setPreferredSize(new Dimension(220, 160));
        panelMini.add(lblMiniatura, BorderLayout.CENTER);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 0.4;
        center.add(panelMini, gbc);

        add(center, BorderLayout.CENTER);

        // Sur: botones
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(fondo);
        btnCalcular = crearBoton("Calcular", primario, Color.WHITE);
        btnVerImagen = crearBoton("Ver figura", acento, Color.DARK_GRAY);
        btnNuevo = crearBoton("Nuevo", exito, Color.WHITE);
        btnSalir = crearBoton("Salir", alerta, Color.WHITE);
        bottom.add(btnCalcular);
        bottom.add(btnVerImagen);
        bottom.add(btnNuevo);
        bottom.add(btnSalir);
        add(bottom, BorderLayout.SOUTH);

        // Inicializar campos por figura
        actualizarCamposSegunFigura();
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton b = new JButton(texto);
        b.setBackground(fondo);
        b.setForeground(textoColor);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ==== Gestión de eventos =====
    public void onCambiarFigura(ActionListener l) { cbFigura.addActionListener(l); }
    public void onCalcular(ActionListener l) { btnCalcular.addActionListener(l); }
    public void onNuevo(ActionListener l) { btnNuevo.addActionListener(l); }
    public void onSalir(ActionListener l) { btnSalir.addActionListener(l); }
    public void onVerImagen(ActionListener l) { btnVerImagen.addActionListener(l); }

    // ==== Dinamismo de inputs =====
    public void actualizarCamposSegunFigura() {
        panelInputs.removeAll();
        campos.clear();
        String figura = getFiguraSeleccionada();
        switch (figura) {
            case "Rectángulo":
                agregarCampo("base", "Base:");
                agregarCampo("altura", "Altura:");
                break;
            case "Círculo":
                agregarCampo("radio", "Radio:");
                break;
            case "Triángulo":
                agregarCampo("ladoA", "Lado A:");
                agregarCampo("ladoB", "Lado B:");
                agregarCampo("ladoC", "Lado C:");
                break;
        }
        panelInputs.revalidate();
        panelInputs.repaint();
        limpiarSalida();
    }

    private void agregarCampo(String clave, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        JTextField txt = new JTextField();
        campos.put(clave, txt);
        panelInputs.add(lbl);
        panelInputs.add(txt);
    }

    public String getFiguraSeleccionada() { return (String) cbFigura.getSelectedItem(); }

    public double getValor(String clave) throws NumberFormatException {
        JTextField t = campos.get(clave);
        if (t == null) throw new NumberFormatException("Campo no encontrado: " + clave);
        return Double.parseDouble(t.getText().trim());
    }

    public void mostrarResultados(double area, double perimetro) {
        txtSalida.setText("Resultados:\n");
        txtSalida.append("Área: " + df.format(area) + "\n");
        txtSalida.append("Perímetro: " + df.format(perimetro) + "\n");
    }

    public void mostrarMiniatura(BufferedImage img) {
        if (img == null) { lblMiniatura.setText("Sin imagen"); return; }
        // Escalar a miniatura conservando pixel-art (nearest-neighbor)
        Image scaled = img.getScaledInstance(lblMiniatura.getWidth(), lblMiniatura.getHeight(), Image.SCALE_DEFAULT);
        lblMiniatura.setIcon(new ImageIcon(scaled));
        lblMiniatura.setText("");
    }

    public void limpiar() {
        for (JTextField t : campos.values()) t.setText("");
        limpiarSalida();
        lblMiniatura.setIcon(null);
        lblMiniatura.setText("Sin imagen");
    }

    private void limpiarSalida() { txtSalida.setText(""); }
}
