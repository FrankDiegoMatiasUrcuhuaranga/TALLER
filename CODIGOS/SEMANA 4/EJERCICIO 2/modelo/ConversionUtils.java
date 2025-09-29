package com.figuras.modelo;
public class ConversionUtils {
    public static double convertirLongitud(String desde, String hacia, double valor) {
        double metros = switch (desde) {
            case "km" -> valor * 1000.0;
            case "m" -> valor;
            case "cm" -> valor / 100.0;
            default -> throw new IllegalArgumentException("Unidad no soportada: " + desde);
        };
        return switch (hacia) {
            case "km" -> metros / 1000.0;
            case "m" -> metros;
            case "cm" -> metros * 100.0;
            default -> throw new IllegalArgumentException("Unidad no soportada: " + hacia);
        };
    }
    public static double convertirMasa(String desde, String hacia, double valor) {
        double gramos = switch (desde) {
            case "kg" -> valor * 1000.0;
            case "g" -> valor;
            default -> throw new IllegalArgumentException("Unidad no soportada: " + desde);
        };
        return switch (hacia) {
            case "kg" -> gramos / 1000.0;
            case "g" -> gramos;
            default -> throw new IllegalArgumentException("Unidad no soportada: " + hacia);
        };
    }
    public static double convertirTemperatura(String desde, String hacia, double valor) {
        if (desde.equals(hacia)) return valor;
        if (desde.equals("C") && hacia.equals("F")) return valor * 9.0/5.0 + 32.0;
        if (desde.equals("F") && hacia.equals("C")) return (valor - 32.0) * 5.0/9.0;
        throw new IllegalArgumentException("Unidad no soportada: " + desde + " -> " + hacia);
    }
}
