package com.figuras.vista;
import com.figuras.modelo.User;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.awt.event.ActionListener;
public class MainView extends JFrame {
    private final User usuario;
    private final JComboBox<String> cbCategoria; private final JComboBox<String> cbDesde; private final JComboBox<String> cbHacia;
    private final JTextField txtValor; private final JButton btnConvertir, btnGuardar, btnRefrescar; private final JLabel lblResultado;
    private final DefaultTableModel tablaModel;
    public MainView(User usuario) {
        this.usuario = usuario;
        setTitle("Conversor - Usuario: " + usuario.getUsername() + " (" + usuario.getRole() + ")"); setSize(760,520); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); setResizable(false); getContentPane().setBackground(Color.WHITE);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); top.setBackground(Color.WHITE); top.setBorder(BorderFactory.createEmptyBorder(8,8,8,8)); top.add(new JLabel("Categoría:"));
        cbCategoria = new JComboBox<>(new String[]{"Longitud","Masa","Temperatura"}); top.add(cbCategoria);
        JPanel center = new JPanel(new GridBagLayout()); center.setBackground(Color.WHITE); GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(8,8,8,8); gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0; gbc.gridy=0; center.add(new JLabel("Desde:"), gbc); cbDesde = new JComboBox<>(); gbc.gridx=1; center.add(cbDesde, gbc); gbc.gridx=2; center.add(new JLabel("Hacia:"), gbc); cbHacia = new JComboBox<>(); gbc.gridx=3; center.add(cbHacia, gbc);
        gbc.gridx=0; gbc.gridy=1; center.add(new JLabel("Valor:"), gbc); txtValor = new JTextField(); gbc.gridx=1; gbc.gridwidth=3; center.add(txtValor, gbc);
        btnConvertir = new JButton("Convertir"); btnConvertir.setBackground(new Color(33,150,243)); btnConvertir.setForeground(Color.WHITE);
        btnGuardar = new JButton("Guardar"); btnGuardar.setBackground(new Color(76,175,80)); btnGuardar.setForeground(Color.WHITE);
        btnRefrescar = new JButton("Refrescar lista"); btnRefrescar.setBackground(new Color(255,193,7)); btnRefrescar.setForeground(Color.DARK_GRAY);
        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1; center.add(btnConvertir, gbc); gbc.gridx=1; center.add(btnGuardar, gbc); gbc.gridx=2; center.add(btnRefrescar, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=4; lblResultado = new JLabel("Resultado: "); center.add(lblResultado, gbc);
        tablaModel = new DefaultTableModel(new Object[]{"Fecha","Autor","Categoría","Desde","Hacia","Entrada","Resultado"},0); JTable tabla = new JTable(tablaModel); JScrollPane scroll = new JScrollPane(tabla); scroll.setPreferredSize(new Dimension(720,200));
        setLayout(new BorderLayout(8,8)); add(top, BorderLayout.NORTH); add(center, BorderLayout.CENTER); add(scroll, BorderLayout.SOUTH);
        actualizarUnidades(); cbCategoria.addActionListener(e -> actualizarUnidades());
    }
    private void actualizarUnidades() {
        String cat = (String) cbCategoria.getSelectedItem(); cbDesde.removeAllItems(); cbHacia.removeAllItems();
        switch(cat) {
            case "Longitud": cbDesde.addItem("m"); cbDesde.addItem("km"); cbDesde.addItem("cm"); cbHacia.addItem("m"); cbHacia.addItem("km"); cbHacia.addItem("cm"); break;
            case "Masa": cbDesde.addItem("g"); cbDesde.addItem("kg"); cbHacia.addItem("g"); cbHacia.addItem("kg"); break;
            case "Temperatura": cbDesde.addItem("C"); cbDesde.addItem("F"); cbHacia.addItem("C"); cbHacia.addItem("F"); break;
        }
    }
    public void onConvertir(ActionListener l){ btnConvertir.addActionListener(l); }
    public void onGuardar(ActionListener l){ btnGuardar.addActionListener(l); }
    public void onRefrescar(ActionListener l){ btnRefrescar.addActionListener(l); }
    public String getCategoriaSeleccionada(){ return (String) cbCategoria.getSelectedItem(); }
    public String getUnidadDesde(){ return (String) cbDesde.getSelectedItem(); }
    public String getUnidadHacia(){ return (String) cbHacia.getSelectedItem(); }
    public double getValorEntrada(){ return Double.parseDouble(txtValor.getText().trim()); }
    public void mostrarResultado(double r){ lblResultado.setText("Resultado: " + String.format("%.4f", r)); }
    public double getResultadoMostrado(){ String t = lblResultado.getText().replace("Resultado: ", "").trim(); return Double.parseDouble(t); }
    public void configurarPorRol(com.figuras.modelo.Role role) {
        if (role == com.figuras.modelo.Role.ALUMNO) { txtValor.setEditable(false); btnGuardar.setEnabled(false); btnConvertir.setEnabled(false); }
    }
    public void limpiarTabla(){ tablaModel.setRowCount(0); }
    public void agregarFilaALista(com.figuras.modelo.ConversionEntry e){ tablaModel.addRow(new Object[]{e.getFechaHora().toString(), e.getAutor(), e.getCategoria(), e.getDesde(), e.getHacia(), e.getEntrada(), e.getResultado()}); }
}
