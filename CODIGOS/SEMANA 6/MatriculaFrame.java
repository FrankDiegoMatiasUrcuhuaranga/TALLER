package notasapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatriculaFrame extends JFrame {
    private JTextField txtCodigo, txtNombre, txtCarrera, txtCiclo;
    private JComboBox<String> comboCursos;
    private DefaultTableModel model;
    private JTable table;
    private JButton btnMatricular, btnVolver;

    public MatriculaFrame(String codigo, String nombre) {
        setTitle("Matrícula - NotasApp");
        setSize(680,420);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color celeste = new Color(179, 229, 252);
        Color celesteDark = new Color(3, 169, 244);
        Color fondo = Color.WHITE;
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(fondo);
        GridBagConstraints h = new GridBagConstraints();
        h.insets = new Insets(8,12,8,12);
        h.gridx = 0; h.gridy = 0; h.anchor = GridBagConstraints.WEST;
        JLabel title = new JLabel("Formulario de Matrícula");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(celesteDark);
        header.add(title,h);

        // Info fields
        JPanel info = new JPanel(new GridBagLayout());
        info.setBackground(fondo);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; info.add(new JLabel("Código:"), c);
        c.gridx = 1; txtCodigo = new JTextField(codigo); txtCodigo.setEditable(false); txtCodigo.setBackground(celeste); txtCodigo.setColumns(10); txtCodigo.setBorder(new LineBorder(celesteDark,1)); info.add(txtCodigo,c);
        c.gridx = 0; c.gridy = 1; info.add(new JLabel("Nombre:"), c);
        c.gridx = 1; txtNombre = new JTextField(nombre); txtNombre.setColumns(20); txtNombre.setBackground(celeste); txtNombre.setBorder(new LineBorder(celesteDark,1)); info.add(txtNombre,c);
        c.gridx = 0; c.gridy = 2; info.add(new JLabel("Carrera/Programa:"), c);
        c.gridx = 1; txtCarrera = new JTextField("Ingeniería de Sistemas"); txtCarrera.setColumns(20); txtCarrera.setBorder(new LineBorder(celesteDark,1)); info.add(txtCarrera,c);
        c.gridx = 0; c.gridy = 3; info.add(new JLabel("Ciclo/Semestre:"), c);
        c.gridx = 1; txtCiclo = new JTextField("1"); txtCiclo.setColumns(5); txtCiclo.setBorder(new LineBorder(celesteDark,1)); info.add(txtCiclo,c);

        // Courses combo and matriculate button
        c.gridx = 0; c.gridy = 4; info.add(new JLabel("Cursos disponibles:"), c);
        String[] cursos = new String[] { "CURS-01 – Matemática", "CURS-02 – Comunicación", "CURS-03 – Física", "CURS-04 – Inglés", "CURS-05 – Programación" };
        c.gridx = 1; comboCursos = new JComboBox<>(cursos); info.add(comboCursos,c);

        add(header, BorderLayout.NORTH);
        add(info, BorderLayout.WEST);

        // Table of matriculated courses
        model = new DefaultTableModel(new Object[]{"Código", "Curso"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Footer buttons
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnMatricular = new JButton("Matricular");
        btnVolver = new JButton("Volver");
        footer.add(btnMatricular);
        footer.add(btnVolver);
        add(footer, BorderLayout.SOUTH);

        // Events
        btnMatricular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = (String) comboCursos.getSelectedItem();
                if (sel != null) {
                    String[] parts = sel.split(" – ", 2);
                    String code = parts[0].trim();
                    String name = parts.length > 1 ? parts[1].trim() : "";
                    model.addRow(new Object[]{code, name});
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
