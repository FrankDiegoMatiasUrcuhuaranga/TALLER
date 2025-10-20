package Modelo;

public class Estudiante {
    private int id;
    private String nombreCompleto;

    public Estudiante(int id, String nombres, String apellidos) {
        this.id = id;
        this.nombreCompleto = nombres + " " + apellidos;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        // Esto es lo que se mostrará en el JComboBox
        return nombreCompleto;
    }
}