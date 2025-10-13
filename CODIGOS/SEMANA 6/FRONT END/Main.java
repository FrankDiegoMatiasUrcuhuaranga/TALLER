package notasapp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotasFrame frame = new NotasFrame();
            frame.setVisible(true);
        });
    }
}
