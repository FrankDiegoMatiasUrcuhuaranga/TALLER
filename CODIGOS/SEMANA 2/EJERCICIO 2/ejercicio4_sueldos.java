import java.awt.Color;
import java.awt.Dimension;
import javax.swing.DefaultListCellRenderer;

public class EJERCICIO4_SUELDOS extends javax.swing.JFrame {

    private DefaultListCellRenderer listRenderer;
    int categoria, horasTrabajadas, numeroHijos;
    double tarifaHoraria, sueldoBasico, bonificacionHijos, sueldoBruto, descuento, sueldoNeto;

    public EJERCICIO4_SUELDOS() {
        initComponents();
        formulario();
        inicializarElementos();
    }

    private void formulario() {
        this.setTitle("Cálculo de Sueldos de Empleados");
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(480, 380));
        this.panelDatos.setBackground(new Color(255, 255, 255));
        this.panelCalcular.setBackground(new Color(255, 255, 255));
    }

    private void inicializarElementos() {
        listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        cboCategoria.setRenderer(listRenderer);
        this.cboCategoria.addItem("Seleccione");
        this.cboCategoria.addItem("A");
        this.cboCategoria.addItem("B");
        this.jLabel1.setText("Categoría:");
        this.jLabel2.setText("Horas trabajadas:");
        this.jLabel3.setText("Número de hijos:");
        this.cboCategoria.requestFocus();
    }

    void ingresarDatos() {
        categoria = cboCategoria.getSelectedIndex();
        horasTrabajadas = Integer.parseInt(txtHorasTrabajadas.getText());
        numeroHijos = Integer.parseInt(txtNumeroHijos.getText());
    }

    void calcularTarifaHoraria() {
        switch (categoria) {
            case 1: // Categoría A
                tarifaHoraria = 45.0;
                break;
            case 2: // Categoría B
                tarifaHoraria = 37.5;
                break;
            default:
                tarifaHoraria = 0;
                break;
        }
    }

    void calcularSueldoBasico() {
        sueldoBasico = horasTrabajadas * tarifaHoraria;
    }

    void calcularBonificacionHijos() {
        if (numeroHijos <= 3) {
            bonificacionHijos = numeroHijos * 40.5;
        } else {
            bonificacionHijos = (3 * 40.5) + ((numeroHijos - 3) * 35.0);
        }
    }

    void calcularSueldoBruto() {
        sueldoBruto = sueldoBasico + bonificacionHijos;
    }

    void calcularDescuento() {
        if (sueldoBruto >= 3500) {
            descuento = 0.135 * sueldoBruto;
        } else {
            descuento = 0.10 * sueldoBruto;
        }
    }

    void calcularSueldoNeto() {
        sueldoNeto = sueldoBruto - descuento;
    }

    void mostrarResultados() {
        this.txtSalida.setText("");
        imprimir("");
        imprimir("=== CÁLCULO DE SUELDO ===");
        imprimir("");
        imprimir("DATOS DEL EMPLEADO:");
        String categoriaTexto = (categoria == 1) ? "A" : "B";
        imprimir("Categoría : " + categoriaTexto);
        imprimir("Horas trabajadas : " + horasTrabajadas);
        imprimir("Número de hijos : " + numeroHijos);
        imprimir("");
        imprimir("CÁLCULOS:");
        imprimir("Tarifa horaria : S/. " + String.format("%.2f", tarifaHoraria));
        imprimir("Sueldo básico : S/. " + String.format("%.2f", sueldoBasico));
        imprimir("Bonificación por hijos : S/. " + String.format("%.2f", bonificacionHijos));
        imprimir("Sueldo bruto : S/. " + String.format("%.2f", sueldoBruto));
        imprimir("");
        String porcentajeDescuento = (sueldoBruto >= 3500) ? "13.5%" : "10.0%";
        imprimir("Descuento (" + porcentajeDescuento + ") : S/. " + String.format("%.2f", descuento));
        imprimir("SUELDO NETO : S/. " + String.format("%.2f", sueldoNeto));
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
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSalida = new javax.swing.JTextArea();
        cboCategoria = new javax.swing.JComboBox<>();
        txtHorasTrabajadas = new javax.swing.JTextField();
        txtNumeroHijos = new javax.swing.JTextField();

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

        jLabel3.setText("jLabel3");

        txtSalida.setColumns(20);
        txtSalida.setRows(5);
        jScrollPane1.setViewportView(txtSalida);

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHorasTrabajadas, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumeroHijos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtHorasTrabajadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNumeroHijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            if (categoria == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor, seleccione una categoría válida", 
                        "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            ingresarDatos();
            calcularTarifaHoraria();
            calcularSueldoBasico();
            calcularBonificacionHijos();
            calcularSueldoBruto();
            calcularDescuento();
            calcularSueldoNeto();
            mostrarResultados();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos", 
                    "Error de entrada", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.cboCategoria.setSelectedIndex(0);
        this.txtHorasTrabajadas.setText("");
        this.txtNumeroHijos.setText("");
        this.txtSalida.setText("");
        this.cboCategoria.requestFocus();
    }                                        

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {                                         
        System.exit(0);
    }                                        

    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCalcular;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JTextField txtHorasTrabajadas;
    private javax.swing.JTextField txtNumeroHijos;
    private javax.swing.JTextArea txtSalida;
}