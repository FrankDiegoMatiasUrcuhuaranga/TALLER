package Modelos;

import java.sql.Timestamp;

/**
 * Modelo para la tabla 'evaluaciones_jurado'.
 * Similar a Evaluacion, pero linkeada a la Sustentación.
 */
public class EvaluacionJurado {

    private int idEvaluacionJurado;
    private int idSustentacion;
    private String codigoJurado;
    private String observaciones;
    private Timestamp fechaEvaluacion;
    
    // Rúbrica de 38 items
    private double item1, item2, item3, item4, item5, item6, item7, item8, item9, item10;
    private double item11, item12, item13, item14, item15, item16, item17, item18, item19, item20;
    private double item21, item22, item23, item24, item25, item26, item27, item28, item29, item30;
    private double item31, item32, item33, item34, item35, item36, item37, item38;
    
    private double puntajeTotal;
    private String condicion;

    public EvaluacionJurado() {}

    // Getters y Setters Básicos
    public int getIdEvaluacionJurado() { return idEvaluacionJurado; }
    public void setIdEvaluacionJurado(int idEvaluacionJurado) { this.idEvaluacionJurado = idEvaluacionJurado; }

    public int getIdSustentacion() { return idSustentacion; }
    public void setIdSustentacion(int idSustentacion) { this.idSustentacion = idSustentacion; }

    public String getCodigoJurado() { return codigoJurado; }
    public void setCodigoJurado(String codigoJurado) { this.codigoJurado = codigoJurado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Timestamp getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Timestamp fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }

    public double getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(double puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }

    // Setters para Items (Generados simplificados para ahorrar espacio, en IDE generar todos)
    public void setItem1(double i) { this.item1 = i; } public double getItem1() { return item1; }
    public void setItem2(double i) { this.item2 = i; } public double getItem2() { return item2; }
    public void setItem3(double i) { this.item3 = i; } public double getItem3() { return item3; }
    public void setItem4(double i) { this.item4 = i; } public double getItem4() { return item4; }
    public void setItem5(double i) { this.item5 = i; } public double getItem5() { return item5; }
    public void setItem6(double i) { this.item6 = i; } public double getItem6() { return item6; }
    public void setItem7(double i) { this.item7 = i; } public double getItem7() { return item7; }
    public void setItem8(double i) { this.item8 = i; } public double getItem8() { return item8; }
    public void setItem9(double i) { this.item9 = i; } public double getItem9() { return item9; }
    public void setItem10(double i) { this.item10 = i; } public double getItem10() { return item10; }
    public void setItem11(double i) { this.item11 = i; } public double getItem11() { return item11; }
    public void setItem12(double i) { this.item12 = i; } public double getItem12() { return item12; }
    public void setItem13(double i) { this.item13 = i; } public double getItem13() { return item13; }
    public void setItem14(double i) { this.item14 = i; } public double getItem14() { return item14; }
    public void setItem15(double i) { this.item15 = i; } public double getItem15() { return item15; }
    public void setItem16(double i) { this.item16 = i; } public double getItem16() { return item16; }
    public void setItem17(double i) { this.item17 = i; } public double getItem17() { return item17; }
    public void setItem18(double i) { this.item18 = i; } public double getItem18() { return item18; }
    public void setItem19(double i) { this.item19 = i; } public double getItem19() { return item19; }
    public void setItem20(double i) { this.item20 = i; } public double getItem20() { return item20; }
    public void setItem21(double i) { this.item21 = i; } public double getItem21() { return item21; }
    public void setItem22(double i) { this.item22 = i; } public double getItem22() { return item22; }
    public void setItem23(double i) { this.item23 = i; } public double getItem23() { return item23; }
    public void setItem24(double i) { this.item24 = i; } public double getItem24() { return item24; }
    public void setItem25(double i) { this.item25 = i; } public double getItem25() { return item25; }
    public void setItem26(double i) { this.item26 = i; } public double getItem26() { return item26; }
    public void setItem27(double i) { this.item27 = i; } public double getItem27() { return item27; }
    public void setItem28(double i) { this.item28 = i; } public double getItem28() { return item28; }
    public void setItem29(double i) { this.item29 = i; } public double getItem29() { return item29; }
    public void setItem30(double i) { this.item30 = i; } public double getItem30() { return item30; }
    public void setItem31(double i) { this.item31 = i; } public double getItem31() { return item31; }
    public void setItem32(double i) { this.item32 = i; } public double getItem32() { return item32; }
    public void setItem33(double i) { this.item33 = i; } public double getItem33() { return item33; }
    public void setItem34(double i) { this.item34 = i; } public double getItem34() { return item34; }
    public void setItem35(double i) { this.item35 = i; } public double getItem35() { return item35; }
    public void setItem36(double i) { this.item36 = i; } public double getItem36() { return item36; }
    public void setItem37(double i) { this.item37 = i; } public double getItem37() { return item37; }
    public void setItem38(double i) { this.item38 = i; } public double getItem38() { return item38; }
}