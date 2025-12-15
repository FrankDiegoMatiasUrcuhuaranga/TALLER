package Modelos;

import java.sql.Timestamp;

public class Sustentacion {
    private int idSustentacion;
    private int idTramite;
    
    // Códigos de los docentes
    private String codigoMiembro1; // Presidente
    private String codigoMiembro2; // Secretario
    private String codigoMiembro3; // Vocal
    private String codigoSuplente; 
    
    private Timestamp fechaHora;
    private String lugarEnlace;
    private double notaFinal; 
    private String resultadoDefensa; 
    private String observaciones;
    
    // --- Campos Auxiliares para Visualización ---
    private String nombreMiembro1;
    private String nombreMiembro2;
    private String nombreMiembro3;
    private String nombreSuplente;
    
    // --- Estado de Votación ---
    private boolean tieneVotoM1;
    private boolean tieneVotoM2;
    private boolean tieneVotoM3;
    
    // --- Puntajes Individuales ---
    private double puntaje1;
    private double puntaje2;
    private double puntaje3;
    
    // Datos del Estudiante/Tesis
    private String nombreEstudiante;
    private String tituloTesis;
    private String estadoEvaluacionJurado;
    
    // --- NUEVO CAMPO: Ruta del Archivo ---
    private String archivoPath; 

    public Sustentacion() {}

    // ... (Tus Getters y Setters existentes se mantienen igual) ...

    public int getIdSustentacion() { return idSustentacion; }
    public void setIdSustentacion(int idSustentacion) { this.idSustentacion = idSustentacion; }

    public int getIdTramite() { return idTramite; }
    public void setIdTramite(int idTramite) { this.idTramite = idTramite; }

    public String getCodigoMiembro1() { return codigoMiembro1; }
    public void setCodigoMiembro1(String codigoMiembro1) { this.codigoMiembro1 = codigoMiembro1; }

    public String getCodigoMiembro2() { return codigoMiembro2; }
    public void setCodigoMiembro2(String codigoMiembro2) { this.codigoMiembro2 = codigoMiembro2; }

    public String getCodigoMiembro3() { return codigoMiembro3; }
    public void setCodigoMiembro3(String codigoMiembro3) { this.codigoMiembro3 = codigoMiembro3; }

    public String getCodigoSuplente() { return codigoSuplente; }
    public void setCodigoSuplente(String codigoSuplente) { this.codigoSuplente = codigoSuplente; }

    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }

    public String getLugarEnlace() { return lugarEnlace; }
    public void setLugarEnlace(String lugarEnlace) { this.lugarEnlace = lugarEnlace; }

    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }

    public String getResultadoDefensa() { return resultadoDefensa; }
    public void setResultadoDefensa(String resultadoDefensa) { this.resultadoDefensa = resultadoDefensa; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getNombreMiembro1() { return nombreMiembro1; }
    public void setNombreMiembro1(String nombreMiembro1) { this.nombreMiembro1 = nombreMiembro1; }

    public String getNombreMiembro2() { return nombreMiembro2; }
    public void setNombreMiembro2(String nombreMiembro2) { this.nombreMiembro2 = nombreMiembro2; }

    public String getNombreMiembro3() { return nombreMiembro3; }
    public void setNombreMiembro3(String nombreMiembro3) { this.nombreMiembro3 = nombreMiembro3; }

    public String getNombreSuplente() { return nombreSuplente; }
    public void setNombreSuplente(String nombreSuplente) { this.nombreSuplente = nombreSuplente; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public String getTituloTesis() { return tituloTesis; }
    public void setTituloTesis(String tituloTesis) { this.tituloTesis = tituloTesis; }

    public String getEstadoEvaluacionJurado() { return estadoEvaluacionJurado; }
    public void setEstadoEvaluacionJurado(String estadoEvaluacionJurado) { this.estadoEvaluacionJurado = estadoEvaluacionJurado; }
    
    public boolean isTieneVotoM1() { return tieneVotoM1; }
    public void setTieneVotoM1(boolean tieneVotoM1) { this.tieneVotoM1 = tieneVotoM1; }

    public boolean isTieneVotoM2() { return tieneVotoM2; }
    public void setTieneVotoM2(boolean tieneVotoM2) { this.tieneVotoM2 = tieneVotoM2; }

    public boolean isTieneVotoM3() { return tieneVotoM3; }
    public void setTieneVotoM3(boolean tieneVotoM3) { this.tieneVotoM3 = tieneVotoM3; }
    
    public double getPuntaje1() { return puntaje1; }
    public void setPuntaje1(double puntaje1) { this.puntaje1 = puntaje1; }

    public double getPuntaje2() { return puntaje2; }
    public void setPuntaje2(double puntaje2) { this.puntaje2 = puntaje2; }

    public double getPuntaje3() { return puntaje3; }
    public void setPuntaje3(double puntaje3) { this.puntaje3 = puntaje3; }

    // --- NUEVOS GETTER Y SETTER ---
    public String getArchivoPath() { return archivoPath; }
    public void setArchivoPath(String archivoPath) { this.archivoPath = archivoPath; }

    public String getRolJurado(String miCodigo) {
        if (miCodigo.equals(codigoMiembro1)) return "Miembro 1 (Presidente)";
        if (miCodigo.equals(codigoMiembro2)) return "Miembro 2 (Secretario)";
        if (miCodigo.equals(codigoMiembro3)) return "Miembro 3 (Vocal)";
        if (miCodigo.equals(codigoSuplente)) return "Miembro Suplente";
        return "Desconocido";
    }
}