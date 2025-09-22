import java.awt.Color;
import java.awt.Dimension;

public class EJERCICIO_TRANSPORTES_POO extends javax.swing.JFrame {

    private EmpresaTransporte empresa;

    public EJERCICIO_TRANSPORTES_POO() {
        initComponents();
        formulario();
        inicializarElementos();
        empresa = new EmpresaTransporte();
    }

    private void formulario() {
        this.setTitle("Sistema de Descuentos POO - Empresa de Transportes");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(500, 350));
        this.panelDatos.setBackground(new Color(255, 255, 255));
        this.panelCalcular.setBackground(new Color(255, 255, 255));
    }

    private void inicializarElementos() {
        this.jLabel1.setText("Turno:");
        this.jLabel2.setText("Cantidad de pasajes:");
        this.cboTurno.addItem("Mañana");
        this.cboTurno.addItem("Noche");
        this.cboTurno.requestFocus();
    }

    void ingresarDatos() {
        String turno = (String) cboTurno.getSelectedItem();
        int cantidad = Integer.parseInt(txtCantidadPasajes.getText());
        empresa.setTurno(turno);
        empresa.setCantidadPasajes(cantidad);
    }

    void mostrarResultados() {
        this.txtSalida.setText("");
        imprimir("");
        imprimir("=== RESULTADOS DE LA COMPRA ===");
        imprimir("");
        imprimir("Turno seleccionado : " + empresa.getTurno());
        imprimir("Precio por pasaje : S/. " + String.format("%.2f", empresa.getPrecioPasaje()));
        imprimir("Cantidad de pasajes : " + empresa.getCantidadPasajes());
        imprimir("");
        imprimir("Importe de la compra : S/. " + String.format("%.2f", empresa.calcularImporteCompra()));
        imprimir("Importe del descuento : S/. " + String.format("%.2f", empresa.calcularDescuento()));
        imprimir("Importe a pagar : S/. " + String.format("%.2f", empresa.calcularImportePagar()));
        imprimir("");
        if (empresa.calcularCaramelos() > 0) {
            imprimir("Caramelos de obsequio : " + empresa.calcularCaramelos() + " unidades");
        } else {
            imprimir("Caramelos de obsequio : No hay obsequio");
        }
        imprimir("");
        if (empresa.getCantidadPasajes() >= 15) {
            imprimir("Descuento aplicado: 8% (≥ 15 pasajes)");
        } else {
            imprimir("Descuento aplicado: 5% (< 15 pasajes)");
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
        cboTurno = new javax.swing.JComboBox<>();
        txtCantidadPasajes = new javax.swing.JTextField();

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
                            .addComponent(cboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadPasajes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidadPasajes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            mostrarResultados();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", 
                    "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.cboTurno.setSelectedIndex(0);
        this.txtCantidadPasajes.setText("");
        this.txtSalida.setText("");
        this.cboTurno.requestFocus();
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
    private javax.swing.JComboBox<String> cboTurno;
    private javax.swing.JTextField txtCantidadPasajes;
    private javax.swing.JTextArea txtSalida;
}

class EmpresaTransporte {
    private String turno;
    private int cantidadPasajes;
    private final double PRECIO_PASAJE = 37.5;

    public EmpresaTransporte() {
        this.turno = "Mañana";
        this.cantidadPasajes = 0;
    }

    public EmpresaTransporte(String turno, int cantidadPasajes) {
        this.turno = turno;
        this.cantidadPasajes = cantidadPasajes;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setCantidadPasajes(int cantidadPasajes) {
        this.cantidadPasajes = cantidadPasajes;
    }

    public String getTurno() {
        return turno;
    }

    public int getCantidadPasajes() {
        return cantidadPasajes;
    }

    public double getPrecioPasaje() {
        return PRECIO_PASAJE;
    }

    public double calcularImporteCompra() {
        return PRECIO_PASAJE * cantidadPasajes;
    }

    public double calcularDescuento() {
        double importeCompra = calcularImporteCompra();
        if (cantidadPasajes >= 15) {
            return 0.08 * importeCompra; // 8%
        } else {
            return 0.05 * importeCompra; // 5%
        }
    }

    public double calcularImportePagar() {
        return calcularImporteCompra() - calcularDescuento();
    }

    public int calcularCaramelos() {
        double importePagar = calcularImportePagar();
        if (importePagar > 200) {
            return 2 * cantidadPasajes; // 2 caramelos por cada boleto
        } else {
            return 0;
        }
    }
}