import java.awt.Color;
import java.awt.Dimension;


public class EJERCICIO1_CAMISAS extends javax.swing.JFrame {

    double precioCamisa, importeCompra, primerDescuento, segundoDescuento, descuentoTotal, importePagar;
    int cantidadUnidades;
    
    public EJERCICIO1_CAMISAS() {
        initComponents();
        formulario();
        inicializarElementos();
    }

    
    private void formulario() {
        this.setTitle("Descuento 7% + 7% - Camisas");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(400, 280));
        this.panelDatos.setBackground(new Color(255, 255, 255));
        this.panelCalcular.setBackground(new Color(255, 255, 255));
    }

    private void inicializarElementos() {
        this.jLabel1.setText("Precio por camisa:");
        this.jLabel2.setText("Cantidad de unidades:");
        this.txtPrecioCamisa.requestFocus();
    }

    void ingresarDatos() {
        precioCamisa = Double.parseDouble(txtPrecioCamisa.getText());
        cantidadUnidades = Integer.parseInt(txtCantidad.getText());
    }

    void calcularImporteCompra() {
        importeCompra = precioCamisa * cantidadUnidades;
    }

   
    void calcularPrimerDescuento() {
        primerDescuento = 0.07 * importeCompra;
    }

 
    void calcularSegundoDescuento() {
        double importeDespuesPrimerDescuento = importeCompra - primerDescuento;
        segundoDescuento = 0.07 * importeDespuesPrimerDescuento;
    }

 
    void calcularDescuentoTotal() {
        descuentoTotal = primerDescuento + segundoDescuento;
    }

 
    void calcularImportePagar() {
        importePagar = importeCompra - descuentoTotal;
    }

    void mostrarResultados() {
        this.txtSalida.setText("");
        imprimir("");
        imprimir("=== RESULTADOS ===");
        imprimir("");
        imprimir("Importe de compra : S/. " + String.format("%.2f", importeCompra));
        imprimir("Primer descuento (7%) : S/. " + String.format("%.2f", primerDescuento));
        imprimir("Segundo descuento (7%) : S/. " + String.format("%.2f", segundoDescuento));
        imprimir("Descuento total : S/. " + String.format("%.2f", descuentoTotal));
        imprimir("Importe a pagar : S/. " + String.format("%.2f", importePagar));
    }

    void imprimir(String cad) {
        this.txtSalida.append(cad + "\n");
    }

                             
    private void initComponents() {

        panelCalcular = new javax.swing.JPanel();
        btnProcesar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        panelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSalida = new javax.swing.JTextArea();
        txtPrecioCamisa = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();

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

        jLabel2.setText("jLabel2");

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
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecioCamisa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPrecioCamisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }                       

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        try {
            ingresarDatos();
            calcularImporteCompra();
            calcularPrimerDescuento();
            calcularSegundoDescuento();
            calcularDescuentoTotal();
            calcularImportePagar();
            mostrarResultados();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", 
                    "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.txtPrecioCamisa.setText("");
        this.txtCantidad.setText("");
        this.txtSalida.setText("");
        this.txtPrecioCamisa.requestFocus();
    }                                        

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }                                        

                    
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCalcular;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JTextField txtPrecioCamisa;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtSalida;
                     
}
