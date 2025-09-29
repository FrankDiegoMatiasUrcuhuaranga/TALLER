import java.awt.Color;
import java.awt.Dimension;

public class EJERCICIO3_DOCENAS extends javax.swing.JFrame {

    double precioDocena, importeCompra, importeDescuento, importePagar;
    int cantidadDocenas, cantidadLapiceros;

    public EJERCICIO3_DOCENAS() {
        initComponents();
        formulario();
        inicializarElementos();
    }

    private void formulario() {
        this.setTitle("Sistema de Descuentos por Docenas");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(450, 300));
        this.panelDatos.setBackground(new Color(255, 255, 255));
        this.panelCalcular.setBackground(new Color(255, 255, 255));
    }

    private void inicializarElementos() {
        this.jLabel1.setText("Precio por docena:");
        this.jLabel2.setText("Cantidad de docenas:");
        this.txtPrecioDocena.requestFocus();
    }

    void ingresarDatos() {
        precioDocena = Double.parseDouble(txtPrecioDocena.getText());
        cantidadDocenas = Integer.parseInt(txtCantidadDocenas.getText());
    }

    void calcularImporteCompra() {
        importeCompra = precioDocena * cantidadDocenas;
    }

    void calcularImporteDescuento() {
        if (cantidadDocenas >= 10) {
            importeDescuento = 0.20 * importeCompra; 
        } else {
            importeDescuento = 0.10 * importeCompra; 
        }
    }

    void calcularImportePagar() {
        importePagar = importeCompra - importeDescuento;
    }

    void calcularCantidadLapiceros() {
        if (importePagar >= 200) {
            cantidadLapiceros = 2 * cantidadDocenas; 
        } else {
            cantidadLapiceros = 0; 
        }
    }

    void mostrarResultados() {
        this.txtSalida.setText("");
        imprimir("");
        imprimir("=== RESULTADOS DE LA COMPRA ===");
        imprimir("");
        imprimir("Precio por docena : S/. " + String.format("%.2f", precioDocena));
        imprimir("Cantidad de docenas : " + cantidadDocenas);
        imprimir("");
        imprimir("Importe de la compra : S/. " + String.format("%.2f", importeCompra));
        imprimir("Importe del descuento : S/. " + String.format("%.2f", importeDescuento));
        imprimir("Importe a pagar : S/. " + String.format("%.2f", importePagar));
        imprimir("");
        if (cantidadLapiceros > 0) {
            imprimir("Lapiceros de obsequio : " + cantidadLapiceros + " unidades");
        } else {
            imprimir("Lapiceros de obsequio : No hay obsequio");
        }
        imprimir("");
        
        if (cantidadDocenas >= 10) {
            imprimir("Descuento aplicado: 20% (≥ 10 docenas)");
        } else {
            imprimir("Descuento aplicado: 10% (< 10 docenas)");
        }
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
        txtPrecioDocena = new javax.swing.JTextField();
        txtCantidadDocenas = new javax.swing.JTextField();

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
                            .addComponent(txtPrecioDocena, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadDocenas, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPrecioDocena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidadDocenas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            calcularImporteDescuento();
            calcularImportePagar();
            calcularCantidadLapiceros();
            mostrarResultados();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", 
                    "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.txtPrecioDocena.setText("");
        this.txtCantidadDocenas.setText("");
        this.txtSalida.setText("");
        this.txtPrecioDocena.requestFocus();
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
    private javax.swing.JTextField txtPrecioDocena;
    private javax.swing.JTextField txtCantidadDocenas;
    private javax.swing.JTextArea txtSalida;                  
}
