package Frame;

import Modelo.Asistencia;
import Modelo.AsistenciaDAO;
import Modelo.Estudiante;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

// =================================================================================
// CLASE PRINCIPAL: PantallaAsistencia
// =================================================================================
public class PantallaAsistencia extends JFrame {

    private final AsistenciaDAO dao = new AsistenciaDAO(); 
    private JComboBox<Estudiante> cmbEstudiantes;         
    private int estudianteSeleccionadoId = -1;
    // CORRECCIÓN 1: Convertir StatusBar a atributo de la clase
    private StatusBar sb; 
    
    Login vistalogin = new Login();
    private String rolUsuario;
    private List<List<List<JCheckBox>>> dataChecks = new ArrayList<>();
    private JTabbedPane tabs; 

    public PantallaAsistencia(String rol) {
        this.rolUsuario = rol.toLowerCase();
        initComponents();
        setTitle("Sistema de Asistencia - Rol: " + rol);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        cargarEstudiantesYAsistencias();
        
        pack();
    }

    // ... (El resto de la clase RolEditableTableModel y métodos de ayuda se mantiene igual)

    private class RolEditableTableModel extends DefaultTableModel {
        private boolean isEditable;
        public RolEditableTableModel(TableModel model, boolean editable) {
            super(convertTableToVector(model), convertColumnNamesToVector(model));
            this.isEditable = editable;
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            return isEditable;
        }
    }
    @SuppressWarnings("unchecked")
    private static Vector<Vector> convertTableToVector(TableModel model) {
        Vector<Vector> data = new Vector<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Vector<Object> row = new Vector<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.add(model.getValueAt(i, j));
            }
            data.add(row);
        }
        return data;
    }
    @SuppressWarnings("unchecked")
    private static Vector<String> convertColumnNamesToVector(TableModel model) {
        Vector<String> columnNames = new Vector<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnNames.add(model.getColumnName(i));
        }
        return columnNames;
    }

    // =================================================================================
    // EL MÉTODO initComponents() CORREGIDO
    // =================================================================================
    private void initComponents() {
        getContentPane().setBackground(Color.WHITE);
        
        cmbEstudiantes = new JComboBox<>();
        PantallaAsistencia.HeaderPanel header = new PantallaAsistencia.HeaderPanel("Registro de Asistencia", cmbEstudiantes);
        add(header, BorderLayout.NORTH);

        // Uso de la variable de instancia (atributo sb)
        sb = new PantallaAsistencia.StatusBar();
        // CORRECCIÓN 1: La línea ahora usa el atributo de la clase
        sb.setStatus("Sesión activa: " + rolUsuario); 
        add(sb, BorderLayout.SOUTH);

        tabs = new JTabbedPane();
        tabs.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        boolean isEditable = "maestro".equals(rolUsuario) || "docente".equals(rolUsuario);

        for (int ciclo = 1; ciclo <= 4; ciclo++) {
            JPanel cicloPanel = new JPanel(new BorderLayout());
            cicloPanel.setBackground(Color.WHITE);

            JPanel semanasPanel = new JPanel();
            semanasPanel.setLayout(new BoxLayout(semanasPanel, BoxLayout.Y_AXIS));
            semanasPanel.setBackground(Color.WHITE);

            List<List<JCheckBox>> semanasChecks = new ArrayList<>();
            for (int s = 1; s <= 8; s++) { 
                JPanel semanaRow = new JPanel(new BorderLayout()); // ESTE ES EL CONTENEDOR DE FILA
                semanaRow.setBackground(new Color(245,245,247));
                semanaRow.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(6,6,6,6),
                        BorderFactory.createLineBorder(new Color(200,200,200))));

                JLabel lblSemana = new JLabel("Semana " + s);
                lblSemana.setPreferredSize(new Dimension(90, 30));
                lblSemana.setFont(new Font("Segoe UI", Font.BOLD, 13));
                semanaRow.add(lblSemana, BorderLayout.WEST); // COMPONENTE 0 (WEST)

                JPanel diasPanel = new JPanel(new GridLayout(1,5,8,8));
                diasPanel.setBackground(new Color(245,245,247));
                String[] dias = {"Lun","Mar","Mie","Jue","Vie"};
                List<JCheckBox> diasChecks = new ArrayList<>();
                
                for (String d : dias) {
                    JCheckBox cb = new JCheckBox(d);
                    cb.setEnabled(isEditable); 
                    diasChecks.add(cb);
                    diasPanel.add(cb);
                }
                semanaRow.add(diasPanel, BorderLayout.CENTER); // COMPONENTE 1 (CENTER)

                JLabel lblPct = new JLabel("0%");
                lblPct.setPreferredSize(new Dimension(70,30));
                semanaRow.add(lblPct, BorderLayout.EAST); // COMPONENTE 2 (EAST) -> El porcentaje es el tercer componente de semanaRow.

                // Listener para actualizar el porcentaje de la semana (Lógica original)
                ItemListener checkListener = ev -> {
                    int present = 0;
                    for (JCheckBox cbox : diasChecks) if (cbox.isSelected()) present++;
                    int pct = Math.round((present / 5.0f) * 100);
                    lblPct.setText(pct + "%");
                };

                for (JCheckBox cb : diasChecks) {
                    cb.addItemListener(checkListener);
                }

                semanasChecks.add(diasChecks);
                semanasPanel.add(semanaRow);
            }

            dataChecks.add(semanasChecks);
            JScrollPane sp = new JScrollPane(semanasPanel);
            sp.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
            cicloPanel.add(sp, BorderLayout.CENTER);
            tabs.addTab("Ciclo " + ciclo, cicloPanel);
        }

        add(tabs, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setEnabled(isEditable); 
        btnGuardar.addActionListener(e -> guardarAsistenciasEnBD());
        JButton btnResumen = new JButton("Ver resumen (pastel)");
        btnResumen.addActionListener(e -> mostrarResumen());
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> {
            dispose();
            vistalogin.setVisible(true);
        });
        
        bottom.add(btnVolver); 
        if(isEditable) bottom.add(btnGuardar); 
        bottom.add(btnResumen);
        add(bottom, BorderLayout.SOUTH);
    }
    
    private void cargarEstudiantesYAsistencias() {
        List<Estudiante> estudiantes = dao.obtenerEstudiantes();
        for (Estudiante e : estudiantes) {
            cmbEstudiantes.addItem(e);
        }
        
        cmbEstudiantes.addActionListener(e -> {
        Estudiante seleccionado = (Estudiante) cmbEstudiantes.getSelectedItem();
        if (seleccionado != null) {
            estudianteSeleccionadoId = seleccionado.getId();
                List<Asistencia> asistencias = dao.obtenerAsistenciasPorEstudiante(seleccionado.getId());
                actualizarCheckboxes(asistencias);
                // Actualizar la barra de estado con el nombre del estudiante
                if (sb != null) {
                    sb.setStatus("Sesión activa: " + rolUsuario + " | Estudiante: " + seleccionado.getNombreCompleto());
                }
                setTitle("Sistema de Asistencia - Estudiante: " + seleccionado.getNombreCompleto() + " | Rol: " + rolUsuario);
            } else {
                estudianteSeleccionadoId = -1;
                limpiarCheckboxes();
                setTitle("Sistema de Asistencia - Rol: " + rolUsuario);
            }
        });

        if (!estudiantes.isEmpty()) {
            cmbEstudiantes.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron estudiantes en la base de datos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            limpiarCheckboxes();
        }
    }
    
    private void actualizarCheckboxes(List<Asistencia> asistencias) {
        limpiarCheckboxes(); 
        
        int semanaGlobal = 1; 
        
        for (int ciclo = 0; ciclo < dataChecks.size(); ciclo++) {
            List<List<JCheckBox>> semanasChecks = dataChecks.get(ciclo);
            
            for (int semana = 0; semana < semanasChecks.size(); semana++) {
                
                for (Asistencia a : asistencias) {
                    if (a.getSemana() == semanaGlobal) { 
                        List<JCheckBox> diasChecks = semanasChecks.get(semana);
                        
                        diasChecks.get(0).setSelected(a.isLunes());
                        diasChecks.get(1).setSelected(a.isMartes());
                        diasChecks.get(2).setSelected(a.isMiercoles());
                        diasChecks.get(3).setSelected(a.isJueves());
                        diasChecks.get(4).setSelected(a.isViernes());
                        
                        // Forzamos el recalculo del porcentaje
                        for (JCheckBox cb : diasChecks) cb.doClick(); 
                    }
                }
                semanaGlobal++;
            }
        }
        tabs.setSelectedIndex(0); 
    }
    
    /**
     * Deselecciona todos los checkboxes y pone el porcentaje a 0%.
     * CORRECCIÓN 2: Se accede al padre (semanaRow) y al componente 2 (lblPct).
     */
    private void limpiarCheckboxes() {
        for (List<List<JCheckBox>> semanas : dataChecks) {
            for (List<JCheckBox> dias : semanas) {
                // El JPanel diasPanel (padre de los JCheckBoxes)
                JPanel diasPanel = (JPanel) dias.get(0).getParent(); 
                
                // El JPanel semanaRow (padre de diasPanel)
                JPanel semanaRow = (JPanel) diasPanel.getParent(); 
                
                // El lblPct es el tercer componente (índice 2) de semanaRow (después de lblSemana y diasPanel)
                // Usamos getComponent(2) ya que el BorderLayout lo agrega en orden: WEST (0), CENTER (1), EAST (2).
                // VERIFICACIÓN: lblSemana (0), diasPanel (1), lblPct (2).
                JLabel lblPct = (JLabel) semanaRow.getComponent(2); 
                
                for (JCheckBox cb : dias) {
                    // Para no disparar el listener, deshabilitamos la acción de click
                    ItemListener[] listeners = cb.getItemListeners();
                    for(ItemListener l : listeners) cb.removeItemListener(l);
                    
                    cb.setSelected(false);
                    
                    // Restauramos el listener
                    for(ItemListener l : listeners) cb.addItemListener(l);
                }
                // Recalcular el porcentaje para asegurar que se ponga a 0%
                lblPct.setText("0%");
            }
        }
    }
    private void guardarAsistenciasEnBD() {
    if (estudianteSeleccionadoId == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante para guardar la asistencia.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // **NOTA:** Necesitas el ID del curso. Asumiremos 1, o debes obtenerlo de la base de datos.
    // Usaremos un ID_CURSO fijo (1) para la demostración. En una app real, este valor se obtendría del contexto del maestro/materia.
    final int ID_CURSO_DEFAULT = 1; 

    int semanaGlobal = 1; 
    boolean exito = true;
    int registrosGuardados = 0;

    // Iteramos sobre Ciclos (pestañas) y Semanas
    for (List<List<JCheckBox>> semanas : dataChecks) {
        for (List<JCheckBox> dias : semanas) {
            
            // Recopilar el estado de los 5 días
            boolean lunes = dias.get(0).isSelected();
            boolean martes = dias.get(1).isSelected();
            boolean miercoles = dias.get(2).isSelected();
            boolean jueves = dias.get(3).isSelected();
            boolean viernes = dias.get(4).isSelected();

            // Llamar al DAO para guardar la semana actual
            if (!dao.guardarAsistenciaSemanal(
                    estudianteSeleccionadoId, 
                    semanaGlobal, 
                    lunes, martes, miercoles, jueves, viernes,
                    ID_CURSO_DEFAULT)) 
            {
                exito = false; // Si falla el guardado de una semana, marcamos error
            } else {
                registrosGuardados++;
            }
            semanaGlobal++;
        }
    }

    if (exito) {
        JOptionPane.showMessageDialog(this, 
            String.format("✅ Asistencia guardada correctamente para el estudiante %d. Total: %d semanas.", 
                estudianteSeleccionadoId, registrosGuardados), 
            "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "❌ Ocurrió un error al guardar una o más semanas de asistencia.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
    }
}
    // ... (HeaderPanel, PieChartPanel, StatusBar y main se mantienen igual o con la corrección del constructor de HeaderPanel)

    public static class HeaderPanel extends JPanel {
        private String title;
        private JComboBox<Estudiante> cmbEstudiantes;

        public HeaderPanel(String title, JComboBox<Estudiante> cmbEstudiantes) {
            this.title = title;
            this.cmbEstudiantes = cmbEstudiantes;
            setOpaque(false);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(0, 70));
            
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            rightPanel.setOpaque(false);
            
            if (cmbEstudiantes != null) {
                JLabel lbl = new JLabel("Estudiante:");
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
                rightPanel.add(lbl);
                cmbEstudiantes.setPreferredSize(new Dimension(200, 30));
                rightPanel.add(cmbEstudiantes);
            }
            
            add(rightPanel, BorderLayout.EAST);
        }
        @Override
        protected void paintComponent(Graphics g) {
            // Lógica de pintura del encabezado...
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth();
            int h = getHeight();

            Color c1 = new Color(13, 110, 253);
            Color c2 = new Color(220, 53, 69);
            GradientPaint gp = new GradientPaint(0, 0, c1, w, 0, c2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            int iconSize = Math.min(48, h - 16);
            int ix = 16;
            int iy = (h - iconSize) / 2;

            g2.setColor(new Color(240, 240, 245));
            g2.fillRoundRect(ix, iy, iconSize, iconSize, 6, 6);
            g2.setColor(new Color(13, 110, 253));
            g2.fillRect(ix + 6, iy + 6, iconSize - 12, iconSize - 12);
            g2.setColor(new Color(220, 53, 69));
            g2.drawLine(ix + 8, iy + 6, ix + 8, iy + iconSize - 6);
            g2.drawLine(ix + iconSize - 8, iy + 6, ix + iconSize - 8, iy + iconSize - 6);

            if (title != null && !title.isEmpty()) {
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int tx = ix + iconSize + 12;
                int ty = (h + fm.getAscent()) / 2 - 4;
                g2.drawString(title, tx, ty);
            }

            g2.dispose();
        }
    }
    
    public static class PieChartPanel extends JPanel {
        private Map<String, Double> data;
        private Color[] palette = {
            new Color(13, 110, 253),  
            new Color(100, 149, 237), 
            new Color(220, 53, 69),   
            new Color(255, 99, 132)   
        };

        public PieChartPanel(Map<String, Double> data) {
            this.data = data;
            setPreferredSize(new Dimension(420,300));
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Lógica de pintura del gráfico de pastel...
            super.paintComponent(g);
            if (data == null || data.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int size = Math.min(w, h) - 80;
            int x = (w - size)/2, y = 20;

            double total = 0;
            for (Double v : data.values()) total += v;

            double start = 90; // Empezar arriba (90 grados)
            int i = 0;
            for (Map.Entry<String, Double> e : data.entrySet()) {
                double angle = e.getValue() / total * 360.0;
                g2.setColor(palette[i % palette.length]);
                g2.fillArc(x, y, size, size, (int)Math.round(start), (int)Math.round(-angle)); // -angle para dibujar en sentido horario
                start -= angle;
                i++;
            }

            // Lógica de Leyenda
            int lx = 10, ly = y + size + 10;
            i = 0;
            for (Map.Entry<String, Double> e : data.entrySet()) {
                g2.setColor(palette[i % palette.length]);
                g2.fillRect(lx, ly + i*18, 12, 12);
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g2.drawString(String.format("%s (%.1f%%)", e.getKey(), e.getValue()), lx + 18, ly + 12 + i*18);
                i++;
            }
            g2.dispose();
        }
    }

    public static class StatusBar extends JPanel {

        private JLabel lblTime;
        private JLabel lblStatus;

        public StatusBar() {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(0,28));
            setBackground(new Color(240,240,245));
            setBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(220,220,225)));

            lblStatus = new JLabel("    Estado: Desconectado");
            lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            add(lblStatus, BorderLayout.WEST);

            lblTime = new JLabel();
            lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
            add(lblTime, BorderLayout.EAST);

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        lblTime.setText("    " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "    ");
                    });
                }
            },0,1000);
        }

        public void setStatus(String s) {
            lblStatus.setText("    " + s);
        }
    }

    private void mostrarResumen() {
        Map<String, Double> resumen = new HashMap<>();
        
        for (int i = 0; i < dataChecks.size(); i++) {
            int semanas = dataChecks.get(i).size();
            int totalDias = semanas * 5;
            int presentes = 0;
            
            for (int s = 0; s < semanas; s++) {
                for (JCheckBox cb : dataChecks.get(i).get(s)) {
                    if (cb.isSelected()) {
                        presentes++;
                    }
                }
            }
            double pct = (totalDias == 0) ? 0 : (presentes * 100.0 / totalDias);
            resumen.put("Ciclo " + (i+1), pct);
        }
        
        PantallaAsistencia.PieChartPanel pie = new PantallaAsistencia.PieChartPanel(resumen);
        JOptionPane.showMessageDialog(this, pie, "Resumen de asistencia (por ciclo)", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PantallaAsistencia("Maestro").setVisible(true); 
        });
    }
}