package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para las operaciones de Estudiantes y Asistencias.
 */
public class AsistenciaDAO {

    /**
     * Obtiene la lista de todos los estudiantes de la tabla Estudiantes.
     * @return Lista de objetos Estudiante.
     */
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id_estudiante, nombres, apellidos FROM Estudiantes ORDER BY apellidos, nombres";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_estudiante");
                String nombres = rs.getString("nombres");
                String apellidos = rs.getString("apellidos");
                estudiantes.add(new Estudiante(id, nombres, apellidos));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener estudiantes: " + e.getMessage());
            // En un entorno de producción, aquí se lanzaría una excepción
        }
        return estudiantes;
    }
    
    /**
     * Obtiene todos los registros de asistencia para un estudiante dado, filtrado por el año 2025.
     * Nota: La semana de la BD (1-52) se mapeará en la UI con la lógica de 4 ciclos x 8 semanas.
     * @param idEstudiante ID del estudiante.
     * @return Lista de objetos Asistencia.
     */
    public List<Asistencia> obtenerAsistenciasPorEstudiante(int idEstudiante) {
        List<Asistencia> asistencias = new ArrayList<>();
        // El WHERE anio = 2025 es un ejemplo basado en tus datos de prueba.
        String sql = "SELECT id_curso, semana, anio, lunes, martes, miercoles, jueves, viernes " +
                     "FROM Asistencias WHERE id_estudiante = ? AND anio = 2025 ORDER BY semana";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Mapeo directo de columnas boolean de la BD a campos boolean de Asistencia
                    asistencias.add(new Asistencia(
                        idEstudiante,
                        rs.getInt("id_curso"),
                        rs.getInt("semana"),
                        rs.getInt("anio"),
                        rs.getBoolean("lunes"),
                        rs.getBoolean("martes"),
                        rs.getBoolean("miercoles"),
                        rs.getBoolean("jueves"),
                        rs.getBoolean("viernes")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener asistencias para el estudiante " + idEstudiante + ": " + e.getMessage());
        }
        return asistencias;
    }
    
    /**
     * Ejemplo de método para guardar o actualizar un registro de asistencia.
     * (No requerido por el prompt, pero incluido para la funcionalidad de 'Guardar')
     */
    // ... dentro de la clase AsistenciaDAO

/**
 * Guarda o actualiza un registro de asistencia semanal. 
 * Realiza un UPSERT (UPDATE si existe, INSERT si no existe) basado en la clave (id_estudiante, semana, anio).
 */
public boolean guardarAsistenciaSemanal(int idEstudiante, int semana, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, int idCurso) {
    // Usamos el año actual para la inserción/actualización
    String updateSql = "UPDATE Asistencias SET lunes=?, martes=?, miercoles=?, jueves=?, viernes=? " +
                       "WHERE id_estudiante=? AND id_curso=? AND semana=? AND anio=YEAR(CURRENT_DATE())";
    
    String insertSql = "INSERT INTO Asistencias (id_estudiante, id_curso, semana, lunes, martes, miercoles, jueves, viernes, anio) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, YEAR(CURRENT_DATE()))";
    
    try (Connection con = ConexionBD.obtenerConexion()) {
        con.setAutoCommit(false); // Iniciar transacción

        // 1. Intentar actualizar (UPDATE)
        try (PreparedStatement psUpdate = con.prepareStatement(updateSql)) {
            psUpdate.setBoolean(1, lunes);
            psUpdate.setBoolean(2, martes);
            psUpdate.setBoolean(3, miercoles);
            psUpdate.setBoolean(4, jueves);
            psUpdate.setBoolean(5, viernes);
            psUpdate.setInt(6, idEstudiante);
            psUpdate.setInt(7, idCurso);
            psUpdate.setInt(8, semana);
            
            if (psUpdate.executeUpdate() > 0) {
                con.commit();
                return true; // Actualización exitosa
            }
        }
        
        // 2. Si no se actualizó, intentar insertar (INSERT)
        try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
            psInsert.setInt(1, idEstudiante);
            psInsert.setInt(2, idCurso); 
            psInsert.setInt(3, semana);
            psInsert.setBoolean(4, lunes);
            psInsert.setBoolean(5, martes);
            psInsert.setBoolean(6, miercoles);
            psInsert.setBoolean(7, jueves);
            psInsert.setBoolean(8, viernes);
            
            if (psInsert.executeUpdate() > 0) {
                con.commit();
                return true; // Inserción exitosa
            }
        }

        con.rollback(); // Si algo falló en ambos pasos, revertir
        return false;
            
    } catch (SQLException e) {
        System.err.println("❌ Error al guardar/actualizar asistencia: " + e.getMessage());
        return false;
    }
}
}