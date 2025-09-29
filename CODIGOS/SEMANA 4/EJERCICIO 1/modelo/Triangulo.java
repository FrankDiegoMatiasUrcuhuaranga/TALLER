package com.figuras.modelo;

public class Triangulo implements Figura {
    private double a, b, c; // lados

    public Triangulo(double a, double b, double c) {
        this.a = a; this.b = b; this.c = c;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    @Override
    public double calcularArea() {
        double s = (a + b + c) / 2.0; // semiperímetro
        return Math.sqrt(Math.max(0, s * (s - a) * (s - b) * (s - c))); // Herón
    }

    @Override
    public double calcularPerimetro() { return a + b + c; }
}
