package com.figuras.modelo;

public class Circulo implements Figura {
    private double radio;

    public Circulo(double radio) { this.radio = radio; }

    public double getRadio() { return radio; }

    @Override
    public double calcularArea() { return Math.PI * radio * radio; }

    @Override
    public double calcularPerimetro() { return 2 * Math.PI * radio; }
}
