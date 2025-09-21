import java.awt.Color;
import java.awt.Dimension;

/**
 * @author Estudiante UPLA
 * Actividad 01 - Problema 2: Distribución de inversión en feria por rubros
 */
public class EJERCICIO2_FERIA extends javax.swing.JFrame {

    double montoTotal, alquilerEspacio, publicidad, transporte, serviciosferiales, decoracion, gastosVarios;

    // Constructor
    public EJERCICIO2_FERIA() {
        initComponents();
        formulario();
        inicializarElementos();
    }

    // Valores del Formulario - JFrame
    private void formulario() {
        this.setTitle("Distribución de Inversión en Feria");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(450, 320));
        this.panelDatos.setBackground(new Color(255, 255, 255));
        this.panelCalcular.setBackground(new Color(255, 255, 255));
    }

    private void inicializarElementos() {
        this.jLabel1.setText("Monto total a invertir:");
        this.txtMontoTotal.requestFocus();
    }

    void ingresarDatos() {
        montoTotal = Double.parseDouble(txtMontoTotal.getText());
    }

    void calcularAlquilerEspacio() {
        alquilerEspacio = 0.23 * montoTotal; // 23%
    }

    void calcularPublicidad() {
        publicidad = 0.07 * montoTotal; // 7%
    }

    void calcularTransporte() {
        transporte = 0.26 * montoTotal; // 26%
    }

    void calcularServiciosferiales() {
        serviciosferiales = 0.12 * montoTotal; // 12%
    }

    void calcularDecoracion() {
        decoracion = 0.21 * montoTotal; // 21%
    }

    void calcularGastosVarios() {
        gastosVarios = 0.11 * montoTotal; // 11%
    }

    // Muestra resultados
    void mostrarResultados() {
        this.txtSalida.setText("");
        imprimir("");
        imprimir("=== DISTRIBUCIÓN DE INVERSIÓN ===");
        imprimir("");
        imprimir("Monto total a invertir : S/. " + String.format("%.2f", montoTotal));
        imprimir("");
        imprimir("RUBROS:");
        imprimir("Alquiler de espacio (23%) : S/. " + String.format("%.2f", alquilerEspacio));
        imprimir("Publicidad (7%) : S/. " + String.format("%.2f", publicidad));
        imprimir("Transporte (26%) : S/. " + String.format("%.2f", transporte));
        imprimir("Servicios feriales (12%) : S/. " + String.format("%.2f", serviciosferiales));
        imprimir("Decoración (21%) : S/. " + String.format("%.2f", decoracion));
        imprimir("Gastos varios (11%) : S/. " + String.format("%.2f", gastosVarios));
        imprimir("");
        double total = alquilerEspacio + publicidad + transporte + serviciosferiales + decoracion + gastosVarios;
        imprimir("TOTAL DISTRIBUIDO : S/. " + String.format("%.2f", total));
    }

    void imprimir(String cad) {
        this.txtSalida.append(cad + "\n");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelCalcular = new javax.swing.JPanel();
        btnProcesar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSalida = new javax.swing.JTextArea();
        txtMontoTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnProcesar.setText("Procesar");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCalcularLayout = new javax.swing.GroupLayout(panelCalcular);
        panelCalcular.setLayout(panelCalcularLayout);
        panelCalcularLayout.setHorizontalGroup(
            panelCalcularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalcularLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelCalcularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(btnNuevo)
                    .addComponent(btnProcesar))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        panelCalcularLayout.setVerticalGroup(
            panelCalcularLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalcularLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btnProcesar)
                .addGap(52, 52, 52)
                .addComponent(btnNuevo)
                .addGap(49, 49, 49)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("jLabel1");

        txtSalida.setColumns(20);
        txtSalida.setRows(5);
        jScrollPane1.setViewportView(txtSalida);

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCalcular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        try {
            ingresarDatos();
            calcularAlquilerEspacio();
            calcularPublicidad();
            calcularTransporte();
            calcularServiciosferiales();
            calcularDecoracion();
            calcularGastosVarios();
            mostrarResultados();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido", 
                    "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.txtMontoTotal.setText("");
        this.txtSalida.setText("");
        this.txtMontoTotal.requestFocus();
    }                                        

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }                                        

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCalcular;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JTextField txtMontoTotal;
    private javax.swing.JTextArea txtSalida;
    // End of variables declaration                   
}