package com.figuras.modelo;
import java.time.LocalDateTime;
public class ConversionEntry {
    private String categoria, desde, hacia, autor;
    private double entrada, resultado;
    private LocalDateTime fechaHora;
    public ConversionEntry(String categoria, String desde, String hacia, double entrada, double resultado, String autor) {
        this.categoria=categoria; this.desde=desde; this.hacia=hacia; this.entrada=entrada; this.resultado=resultado; this.autor=autor; this.fechaHora=LocalDateTime.now();
    }
    public String getCategoria(){return categoria;}
    public String getDesde(){return desde;}
    public String getHacia(){return hacia;}
    public double getEntrada(){return entrada;}
    public double getResultado(){return resultado;}
    public String getAutor(){return autor;}
    public LocalDateTime getFechaHora(){return fechaHora;}
}
