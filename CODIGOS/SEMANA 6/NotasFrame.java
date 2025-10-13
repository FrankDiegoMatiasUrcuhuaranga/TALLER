package notasapp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotasFrame extends JFrame {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField[] txtCursos = new JTextField[5];
    private JTextField[] txtNotas = new JTextField[5];
    private JTextField txtPromedio;
    private JButton btnCalcular, btnReset, btnCerrar, btnMatricula;

    public NotasFrame() {
        setTitle("NotasApp - Notas del Estudiante");
        setSize(760, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Color celeste = new Color(179, 229, 252);
        Color celesteDark = new Color(3, 169, 244);
        Color fondo = Color.WHITE;
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(fondo);
        header.setBorder(BorderFactory.createMatteBorder(0,0,1,0, celesteDark));
        GridBagConstraints h = new GridBagConstraints();
        h.insets = new Insets(8,12,8,12);
        h.gridx = 0; h.gridy = 0; h.anchor = GridBagConstraints.WEST;
        JLabel title = new JLabel("Ficha de Notas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(celesteDark);
        header.add(title,h);

        h.gridx = 1; h.gridy = 0; h.anchor = GridBagConstraints.EAST;
        JPanel info = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        info.setOpaque(false);
        txtCodigo = new JTextField("EST-001");
        txtCodigo.setEditable(false);
        txtCodigo.setColumns(8);
        txtCodigo.setBackground(celeste);
        txtCodigo.setBorder(new LineBorder(celesteDark,1));
        txtNombre = new JTextField("Juan Pérez");
        txtNombre.setColumns(18);
        txtNombre.setBackground(celeste);
        txtNombre.setBorder(new LineBorder(celesteDark,1));
        info.add(new JLabel("Código:")); info.add(txtCodigo);
        info.add(new JLabel("Nombre:")); info.add(txtNombre);
        header.add(info,h);

        add(header, BorderLayout.NORTH);

        // Center - courses
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(fondo);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,12,6,12);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.weightx = 0.6;
        center.add(createHeaderLabel("Curso"), c);
        c.gridx = 1; c.weightx = 0.2;
        center.add(createHeaderLabel("Código Curso"), c);
        c.gridx = 2; c.weightx = 0.2;
        center.add(createHeaderLabel("Nota"), c);

        String[] cursosEj = new String[]{"Matemáticas","Física","Programación","Bases de Datos","Inglés"};
        String[] codigosEj = new String[]{"MAT101","FIS102","PROG201","BD202","ING103"};
        int[] notasPref = new int[]{18, 14, 14, 15, 17};

        for (int i = 0; i < 5; i++) {
            c.gridy = i + 1;
            c.gridx = 0;
            txtCursos[i] = new JTextField(cursosEj[i]);
            txtCursos[i].setBackground(new Color(245,245,245));
            txtCursos[i].setBorder(new LineBorder(celesteDark,1,true));
            center.add(txtCursos[i], c);

            c.gridx = 1;
            JTextField codigoCurso = new JTextField(codigosEj[i]);
            codigoCurso.setEditable(false);
            codigoCurso.setBackground(new Color(250,250,250));
            codigoCurso.setBorder(new LineBorder(celeste,1));
            center.add(codigoCurso, c);

            c.gridx = 2;
            txtNotas[i] = new JTextField(String.valueOf(notasPref[i]));
            txtNotas[i].setBorder(new LineBorder(celesteDark,1,true));
            txtNotas[i].setHorizontalAlignment(JTextField.CENTER);
            center.add(txtNotas[i], c);
        }

        add(center, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new GridBagLayout());
        footer.setBackground(fondo);
        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(8,12,8,12);
        f.gridx = 0; f.gridy = 0; f.anchor = GridBagConstraints.WEST;

        btnCalcular = new JButton("Calcular Promedio");
        btnCalcular.setBackground(celeste); btnCalcular.setForeground(Color.BLACK);
        btnCalcular.setFocusPainted(false); btnCalcular.setBorder(new LineBorder(celesteDark,1));
        f.gridx = 0; footer.add(btnCalcular, f);

        btnReset = new JButton("Resetear Notas");
        btnReset.setBackground(Color.WHITE); btnReset.setForeground(celesteDark);
        btnReset.setBorder(new LineBorder(celeste,1));
        f.gridx = 1; footer.add(btnReset, f);

        btnMatricula = new JButton("Matrícula");
        btnMatricula.setBackground(new Color(220, 245, 255));
        btnMatricula.setForeground(Color.BLACK);
        btnMatricula.setBorder(new LineBorder(celesteDark,1));
        f.gridx = 2; footer.add(btnMatricula, f);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(240,240,240));
        btnCerrar.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
        f.gridx = 3; footer.add(btnCerrar, f);

        f.gridx = 4; f.weightx = 1.0; f.anchor = GridBagConstraints.EAST;
        footer.add(new JLabel("Promedio Final: "), f);
        txtPromedio = new JTextField();
        txtPromedio.setEditable(false); txtPromedio.setColumns(8);
        txtPromedio.setBackground(new Color(230, 250, 255)); txtPromedio.setBorder(new LineBorder(celesteDark,1));
        f.gridx = 5; footer.add(txtPromedio, f);

        add(footer, BorderLayout.SOUTH);

        // Events
        btnCalcular.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { calcularPromedio(); } });
        btnReset.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { for (int i = 0; i < 5; i++) txtNotas[i].setText("0"); txtPromedio.setText(""); } });
        btnCerrar.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); } });
        btnMatricula.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { MatriculaFrame mf = new MatriculaFrame(txtCodigo.getText(), txtNombre.getText()); mf.setVisible(true); } });
    }

    private JLabel createHeaderLabel(String text) { JLabel lbl = new JLabel(text); lbl.setFont(new Font("Segoe UI", Font.BOLD, 14)); lbl.setForeground(new Color(3,169,244)); return lbl; }

    private void calcularPromedio() {
        double suma = 0; int cont = 0;
        for (int i = 0; i < 5; i++) {
            try { double n = Double.parseDouble(txtNotas[i].getText()); suma += n; cont++; } catch (NumberFormatException ex) { }
        }
        if (cont > 0) { txtPromedio.setText(String.format("%.2f", suma / cont)); } else { txtPromedio.setText("0.00"); }
    }
}
