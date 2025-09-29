package com.figuras.vista;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagenPixelArt {

    /**
     * Crea un rectángulo en estilo pixel-art dentro del lienzo dado.
     */
    public static BufferedImage rectangulo(int w, int h, int pixel) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(230, 245, 255));
        g.fillRect(0, 0, w, h);
        // marco
        g.setColor(new Color(25, 118, 210));
        for (int x = pixel; x < w - pixel; x += pixel) {
            g.fillRect(x, pixel, pixel - 1, pixel - 1); // borde superior
            g.fillRect(x, h - 2 * pixel, pixel - 1, pixel - 1); // borde inferior
        }
        for (int y = 2 * pixel; y < h - 2 * pixel; y += pixel) {
            g.fillRect(pixel, y, pixel - 1, pixel - 1); // borde izq
            g.fillRect(w - 2 * pixel, y, pixel - 1, pixel - 1); // borde der
        }
        // relleno ajedrezado
        g.setColor(new Color(144, 202, 249));
        for (int y = 2 * pixel; y < h - 2 * pixel; y += pixel) {
            for (int x = 2 * pixel; x < w - 2 * pixel; x += pixel) {
                if (((x + y) / pixel) % 2 == 0) {
                    g.fillRect(x, y, pixel - 1, pixel - 1);
                }
            }
        }
        g.dispose();
        return img;
    }

    public static BufferedImage circulo(int w, int h, int pixel) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(255, 241, 224));
        g.fillRect(0, 0, w, h);
        int cx = w / 2; int cy = h / 2; int r = Math.min(w, h) / 2 - 2 * pixel;
        g.setColor(new Color(244, 67, 54));
        // píxeles del borde (círculo "pixelado")
        for (int y = -r; y <= r; y += pixel) {
            for (int x = -r; x <= r; x += pixel) {
                int dx = x; int dy = y;
                if (Math.abs(dx * dx + dy * dy - r * r) <= r * pixel) {
                    g.fillRect(cx + dx, cy + dy, pixel - 1, pixel - 1);
                }
            }
        }
        // relleno disperso
        g.setColor(new Color(255, 138, 128));
        for (int y = -r + pixel; y < r - pixel; y += pixel) {
            for (int x = -r + pixel; x < r - pixel; x += pixel) {
                if (dx2(x, y) < (r - pixel) * (r - pixel) && ((x + y) / pixel) % 2 == 0) {
                    g.fillRect(cx + x, cy + y, pixel - 1, pixel - 1);
                }
            }
        }
        g.dispose();
        return img;
    }

    public static BufferedImage triangulo(int w, int h, int pixel) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(236, 253, 220));
        g.fillRect(0, 0, w, h);
        // triángulo isósceles centrado
        int margin = 2 * pixel;
        int x1 = margin, y1 = h - margin;
        int x2 = w - margin, y2 = h - margin;
        int x3 = w / 2, y3 = margin;
        // Borde pixelado
        g.setColor(new Color(56, 142, 60));
        trazarLineaPixelada(g, x1, y1, x2, y2, pixel);
        trazarLineaPixelada(g, x2, y2, x3, y3, pixel);
        trazarLineaPixelada(g, x3, y3, x1, y1, pixel);
        // Relleno cuadriculado
        g.setColor(new Color(165, 214, 167));
        for (int y = y3 + pixel; y < y1 - pixel; y += pixel) {
            int xa = interpolaX(x3, y3, x1, y1, y);
            int xb = interpolaX(x3, y3, x2, y2, y);
            if (xa > xb) { int tmp = xa; xa = xb; xb = tmp; }
            for (int x = xa; x < xb; x += pixel) {
                if (((x + y) / pixel) % 2 == 0) g.fillRect(x, y, pixel - 1, pixel - 1);
            }
        }
        g.dispose();
        return img;
    }

    // ==== utilidades internas ====
    private static int dx2(int x, int y) { return x * x + y * y; }

    private static void trazarLineaPixelada(Graphics2D g, int x1, int y1, int x2, int y2, int p) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? p : -p;
        int sy = y1 < y2 ? p : -p;
        int err = dx - dy;
        int x = x1, y = y1;
        while (true) {
            g.fillRect(x, y, p - 1, p - 1);
            if (x == x2 && y == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 < dx) { err += dx; y += sy; }
        }
    }

    private static int interpolaX(int x1, int y1, int x2, int y2, int y) {
        if (y2 == y1) return Math.min(x1, x2);
        double t = (y - y1) / (double)(y2 - y1);
        return (int)Math.round(x1 + t * (x2 - x1));
    }
}
