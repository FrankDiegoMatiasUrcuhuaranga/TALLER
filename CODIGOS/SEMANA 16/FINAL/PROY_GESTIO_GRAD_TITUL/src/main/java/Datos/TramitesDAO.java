package Datos;

import Modelos.DocumentoRequisito;
import Modelos.EvaluacionJurado;
import Modelos.Notificacion;
import Modelos.Sustentacion;
import Modelos.Tramite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TramitesDAO {

    public Tramite getTramitePorEstudiante(String codigoEstudiante) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        Tramite tramite = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM tramites WHERE codigo_estudiante = ? LIMIT 1");
            stmt.setString(1, codigoEstudiante);
            rs = stmt.executeQuery();
            if (rs.next()) {
                tramite = new Tramite();
                tramite.setIdTramite(rs.getInt("id_tramite"));
                tramite.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                tramite.setEstadoActual(rs.getString("estado_actual"));
                tramite.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                tramite.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return tramite;
    }

    public int iniciarTramite(String codigoEstudiante) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("INSERT INTO tramites (codigo_estudiante, estado_actual) VALUES (?, 'Iniciado')", java.sql.Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, codigoEstudiante);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1); 
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return -1;
    }
    
    public List<Tramite> listarTodosLosTramites() throws SQLException {
        List<Tramite> lista = new ArrayList<>();
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            String sql = "SELECT t.*, CONCAT(u.nombres, ' ', u.apellidos) as nombre_completo " +
                         "FROM tramites t JOIN usuarios u ON t.codigo_estudiante = u.codigo ORDER BY t.fecha_actualizacion DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tramite t = new Tramite();
                t.setIdTramite(rs.getInt("id_tramite"));
                t.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                t.setNombreEstudiante(rs.getString("nombre_completo"));
                t.setEstadoActual(rs.getString("estado_actual"));
                t.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                t.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
                lista.add(t);
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return lista;
    }
    
    public boolean avanzarFaseTramite(int idTramite, String nuevoEstado) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("UPDATE tramites SET estado_actual = ? WHERE id_tramite = ?");
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idTramite);
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }
    
    public String getCodigoEstudiantePorTramite(int idTramite) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT codigo_estudiante FROM tramites WHERE id_tramite = ?");
            stmt.setInt(1, idTramite);
            rs = stmt.executeQuery();
            if (rs.next()) return rs.getString(1);
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return null;
    }

    public List<DocumentoRequisito> getDocumentosPorTramite(int idTramite) throws SQLException {
        List<DocumentoRequisito> lista = new ArrayList<>();
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM documentos_requisito WHERE id_tramite = ?");
            stmt.setInt(1, idTramite);
            rs = stmt.executeQuery();
            while (rs.next()) {
                DocumentoRequisito doc = new DocumentoRequisito();
                doc.setIdDocumento(rs.getInt("id_documento"));
                doc.setIdTramite(rs.getInt("id_tramite"));
                doc.setTipoDocumento(rs.getString("tipo_documento"));
                doc.setRutaArchivo(rs.getString("ruta_archivo"));
                doc.setEstadoValidacion(rs.getString("estado_validacion"));
                doc.setObservacionRechazo(rs.getString("observacion_rechazo"));
                doc.setFechaSubida(rs.getTimestamp("fecha_subida"));
                lista.add(doc);
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return lista;
    }

    public boolean subirDocumento(DocumentoRequisito doc) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sqlCheck = "SELECT id_documento FROM documentos_requisito WHERE id_tramite = ? AND tipo_documento = ?";
            PreparedStatement check = conn.prepareStatement(sqlCheck);
            check.setInt(1, doc.getIdTramite());
            check.setString(2, doc.getTipoDocumento());
            ResultSet rs = check.executeQuery();
            
            if (rs.next()) {
                String sqlUpdate = "UPDATE documentos_requisito SET ruta_archivo = ?, estado_validacion = 'Pendiente', observacion_rechazo = NULL, fecha_subida = CURRENT_TIMESTAMP WHERE id_documento = ?";
                stmt = conn.prepareStatement(sqlUpdate);
                stmt.setString(1, doc.getRutaArchivo());
                stmt.setInt(2, rs.getInt("id_documento"));
            } else {
                String sqlInsert = "INSERT INTO documentos_requisito (id_tramite, tipo_documento, ruta_archivo, estado_validacion) VALUES (?, ?, ?, 'Pendiente')";
                stmt = conn.prepareStatement(sqlInsert);
                stmt.setInt(1, doc.getIdTramite());
                stmt.setString(2, doc.getTipoDocumento());
                stmt.setString(3, doc.getRutaArchivo());
            }
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }
    
    public boolean actualizarEstadoDocumento(int idDocumento, String estado, String observacion) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sql = "UPDATE documentos_requisito SET estado_validacion = ?, observacion_rechazo = ? WHERE id_documento = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, estado);
            stmt.setString(2, observacion);
            stmt.setInt(3, idDocumento);
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }

    public void crearNotificacion(Notificacion noti) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sql = "INSERT INTO notificaciones (codigo_usuario_destino, mensaje, tipo) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, noti.getCodigoUsuarioDestino());
            stmt.setString(2, noti.getMensaje());
            stmt.setString(3, noti.getTipo());
            stmt.executeUpdate();
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }
    
    public List<Notificacion> getNotificacionesNoLeidas(String codigoUsuario) throws SQLException {
        List<Notificacion> lista = new ArrayList<>();
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            String sql = "SELECT * FROM notificaciones WHERE codigo_usuario_destino = ? AND leido = FALSE ORDER BY fecha_envio DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigoUsuario);
            rs = stmt.executeQuery();
            while(rs.next()) {
                Notificacion n = new Notificacion();
                n.setIdNotificacion(rs.getInt("id_notificacion"));
                n.setMensaje(rs.getString("mensaje"));
                n.setTipo(rs.getString("tipo"));
                n.setFechaEnvio(rs.getTimestamp("fecha_envio"));
                lista.add(n);
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return lista;
    }

    // --- FASE 2: GUARDAR SOLO JURADO (NUEVO) ---
    public boolean guardarJurado(Sustentacion s) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sqlCheck = "SELECT id_sustentacion FROM sustentaciones WHERE id_tramite = ?";
            PreparedStatement check = conn.prepareStatement(sqlCheck);
            check.setInt(1, s.getIdTramite());
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                // Actualizar solo jurados, NO tocar fecha ni lugar
                String sqlUpdate = "UPDATE sustentaciones SET codigo_miembro1=?, codigo_miembro2=?, codigo_miembro3=?, codigo_suplente=? WHERE id_tramite=?";
                stmt = conn.prepareStatement(sqlUpdate);
                stmt.setString(1, s.getCodigoMiembro1());
                stmt.setString(2, s.getCodigoMiembro2());
                stmt.setString(3, s.getCodigoMiembro3());
                stmt.setString(4, s.getCodigoSuplente());
                stmt.setInt(5, s.getIdTramite());
            } else {
                // Crear con fecha dummy (para cumplir con BD)
                String sqlInsert = "INSERT INTO sustentaciones (id_tramite, codigo_miembro1, codigo_miembro2, codigo_miembro3, codigo_suplente, fecha_hora, resultado_defensa) VALUES (?, ?, ?, ?, ?, '2025-01-01 00:00:00', 'Pendiente')";
                stmt = conn.prepareStatement(sqlInsert);
                stmt.setInt(1, s.getIdTramite());
                stmt.setString(2, s.getCodigoMiembro1());
                stmt.setString(3, s.getCodigoMiembro2());
                stmt.setString(4, s.getCodigoMiembro3());
                stmt.setString(5, s.getCodigoSuplente());
            }
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }

    // --- FASE 3: PROGRAMAR FECHA ---
    public boolean programarFechaSustentacion(int idTramite, Timestamp fecha, String lugar) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sql = "UPDATE sustentaciones SET fecha_hora = ?, lugar_enlace = ? WHERE id_tramite = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, fecha);
            stmt.setString(2, lugar);
            stmt.setInt(3, idTramite);
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }

    public Sustentacion getSustentacionPorTramite(int idTramite) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        Sustentacion sus = null;
        try {
            conn = Conexion.getConnection();
            String sql = "SELECT s.*, " +
                         "CONCAT(m1.nombres, ' ', m1.apellidos) as nombre_m1, " +
                         "CONCAT(m2.nombres, ' ', m2.apellidos) as nombre_m2, " +
                         "CONCAT(m3.nombres, ' ', m3.apellidos) as nombre_m3, " +
                         "CONCAT(sup.nombres, ' ', sup.apellidos) as nombre_sup, " +
                         "(SELECT puntaje_total FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = s.codigo_miembro1) as puntaje1, " +
                         "(SELECT puntaje_total FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = s.codigo_miembro2) as puntaje2, " +
                         "(SELECT puntaje_total FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = s.codigo_miembro3) as puntaje3, " +
                         "CONCAT(u.nombres, ' ', u.apellidos) as nombre_estudiante, " +
                         "t.titulo as titulo_tesis " +
                         "FROM sustentaciones s " +
                         "LEFT JOIN usuarios m1 ON s.codigo_miembro1 = m1.codigo " +
                         "LEFT JOIN usuarios m2 ON s.codigo_miembro2 = m2.codigo " +
                         "LEFT JOIN usuarios m3 ON s.codigo_miembro3 = m3.codigo " +
                         "LEFT JOIN usuarios sup ON s.codigo_suplente = sup.codigo " +
                         "JOIN tramites tr ON s.id_tramite = tr.id_tramite " +
                         "JOIN usuarios u ON tr.codigo_estudiante = u.codigo " +
                         "LEFT JOIN tesis t ON t.codigo_estudiante = u.codigo " +
                         "WHERE s.id_tramite = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTramite);
            rs = stmt.executeQuery();
            if (rs.next()) {
                sus = new Sustentacion();
                sus.setIdSustentacion(rs.getInt("id_sustentacion"));
                sus.setIdTramite(rs.getInt("id_tramite"));
                sus.setCodigoMiembro1(rs.getString("codigo_miembro1"));
                sus.setNombreMiembro1(rs.getString("nombre_m1"));
                sus.setCodigoMiembro2(rs.getString("codigo_miembro2"));
                sus.setNombreMiembro2(rs.getString("nombre_m2"));
                sus.setCodigoMiembro3(rs.getString("codigo_miembro3"));
                sus.setNombreMiembro3(rs.getString("nombre_m3"));
                sus.setCodigoSuplente(rs.getString("codigo_suplente"));
                sus.setNombreSuplente(rs.getString("nombre_sup"));
                sus.setFechaHora(rs.getTimestamp("fecha_hora"));
                sus.setLugarEnlace(rs.getString("lugar_enlace"));
                sus.setResultadoDefensa(rs.getString("resultado_defensa"));
                sus.setNombreEstudiante(rs.getString("nombre_estudiante"));
                sus.setTituloTesis(rs.getString("titulo_tesis") != null ? rs.getString("titulo_tesis") : "Tesis en proceso");
                
                double p1 = rs.getObject("puntaje1") != null ? rs.getDouble("puntaje1") : -1;
                double p2 = rs.getObject("puntaje2") != null ? rs.getDouble("puntaje2") : -1;
                double p3 = rs.getObject("puntaje3") != null ? rs.getDouble("puntaje3") : -1;
                
                // Setear booleanos
                sus.setTieneVotoM1(p1 >= 0);
                sus.setTieneVotoM2(p2 >= 0);
                sus.setTieneVotoM3(p3 >= 0);
                
                // --- NUEVO: Setear puntajes individuales ---
                sus.setPuntaje1(p1 >= 0 ? p1 : 0);
                sus.setPuntaje2(p2 >= 0 ? p2 : 0);
                sus.setPuntaje3(p3 >= 0 ? p3 : 0);
                
                if (p1 >= 0 && p2 >= 0 && p3 >= 0) {
                    double promedio = (p1 + p2 + p3) / 3.0;
                    sus.setNotaFinal(promedio);
                } else {
                    sus.setNotaFinal(-1); 
                }
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return sus;
    }
    
    public List<Sustentacion> getSustentacionesPorJurado(String codigoDocente) throws SQLException {
        List<Sustentacion> lista = new ArrayList<>();
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            // CORRECCIÓN AQUÍ: Agregué 't.archivo_path' al SELECT
            String sql = "SELECT s.*, " +
                         "CONCAT(u.nombres, ' ', u.apellidos) as nombre_estudiante, " +
                         "t.titulo as titulo_tesis, " +
                         "t.archivo_path, " + // <--- CAMPO AGREGADO
                         "CONCAT(m1.nombres, ' ', m1.apellidos) as nombre_m1, " +
                         "CONCAT(m2.nombres, ' ', m2.apellidos) as nombre_m2, " +
                         "CONCAT(m3.nombres, ' ', m3.apellidos) as nombre_m3, " +
                         "CONCAT(sup.nombres, ' ', sup.apellidos) as nombre_sup, " +
                         "(SELECT COUNT(*) FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = ?) as ya_evaluo " +
                         "FROM sustentaciones s " +
                         "JOIN tramites tr ON s.id_tramite = tr.id_tramite " +
                         "JOIN usuarios u ON tr.codigo_estudiante = u.codigo " +
                         "LEFT JOIN tesis t ON t.codigo_estudiante = u.codigo " +
                         "LEFT JOIN usuarios m1 ON s.codigo_miembro1 = m1.codigo " +
                         "LEFT JOIN usuarios m2 ON s.codigo_miembro2 = m2.codigo " +
                         "LEFT JOIN usuarios m3 ON s.codigo_miembro3 = m3.codigo " +
                         "LEFT JOIN usuarios sup ON s.codigo_suplente = sup.codigo " +
                         "WHERE s.codigo_miembro1 = ? OR s.codigo_miembro2 = ? OR s.codigo_miembro3 = ? OR s.codigo_suplente = ? " +
                         "ORDER BY s.fecha_hora DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigoDocente);
            stmt.setString(2, codigoDocente);
            stmt.setString(3, codigoDocente);
            stmt.setString(4, codigoDocente);
            stmt.setString(5, codigoDocente);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Sustentacion sus = new Sustentacion();
                sus.setIdSustentacion(rs.getInt("id_sustentacion"));
                sus.setIdTramite(rs.getInt("id_tramite"));
                sus.setCodigoMiembro1(rs.getString("codigo_miembro1"));
                sus.setCodigoMiembro2(rs.getString("codigo_miembro2"));
                sus.setCodigoMiembro3(rs.getString("codigo_miembro3"));
                sus.setCodigoSuplente(rs.getString("codigo_suplente"));
                
                sus.setNombreMiembro1(rs.getString("nombre_m1"));
                sus.setNombreMiembro2(rs.getString("nombre_m2"));
                sus.setNombreMiembro3(rs.getString("nombre_m3"));
                sus.setNombreSuplente(rs.getString("nombre_sup"));
                
                sus.setFechaHora(rs.getTimestamp("fecha_hora"));
                sus.setLugarEnlace(rs.getString("lugar_enlace"));
                sus.setNombreEstudiante(rs.getString("nombre_estudiante"));
                sus.setTituloTesis(rs.getString("titulo_tesis") != null ? rs.getString("titulo_tesis") : "Tesis en proceso");
                
                // CORRECCIÓN AQUÍ: Mapear el archivo al objeto (asegúrate de haber actualizado Sustentacion.java antes)
                sus.setArchivoPath(rs.getString("archivo_path"));

                int evaluado = rs.getInt("ya_evaluo");
                sus.setEstadoEvaluacionJurado(evaluado > 0 ? "Evaluado" : "Pendiente");
                lista.add(sus);
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return lista;
    }

    public boolean guardarEvaluacionJurado(EvaluacionJurado eval) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sql = "INSERT INTO evaluaciones_jurado (id_sustentacion, codigo_jurado, observaciones, " +
                         "item_1, item_2, item_3, item_4, item_5, item_6, item_7, item_8, item_9, item_10, " +
                         "item_11, item_12, item_13, item_14, item_15, item_16, item_17, item_18, item_19, item_20, " +
                         "item_21, item_22, item_23, item_24, item_25, item_26, item_27, item_28, item_29, item_30, " +
                         "item_31, item_32, item_33, item_34, item_35, item_36, item_37, item_38, " +
                         "puntaje_total, condicion) VALUES (?, ?, ?, " +
                         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                         "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                         "?, ?, ?, ?, ?, ?, ?, ?, " +
                         "?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, eval.getIdSustentacion());
            stmt.setString(2, eval.getCodigoJurado());
            stmt.setString(3, eval.getObservaciones());
            stmt.setDouble(4, eval.getItem1()); stmt.setDouble(5, eval.getItem2()); stmt.setDouble(6, eval.getItem3());
            stmt.setDouble(7, eval.getItem4()); stmt.setDouble(8, eval.getItem5()); stmt.setDouble(9, eval.getItem6());
            stmt.setDouble(10, eval.getItem7()); stmt.setDouble(11, eval.getItem8()); stmt.setDouble(12, eval.getItem9());
            stmt.setDouble(13, eval.getItem10()); stmt.setDouble(14, eval.getItem11()); stmt.setDouble(15, eval.getItem12());
            stmt.setDouble(16, eval.getItem13()); stmt.setDouble(17, eval.getItem14()); stmt.setDouble(18, eval.getItem15());
            stmt.setDouble(19, eval.getItem16()); stmt.setDouble(20, eval.getItem17()); stmt.setDouble(21, eval.getItem18());
            stmt.setDouble(22, eval.getItem19()); stmt.setDouble(23, eval.getItem20()); stmt.setDouble(24, eval.getItem21());
            stmt.setDouble(25, eval.getItem22()); stmt.setDouble(26, eval.getItem23()); stmt.setDouble(27, eval.getItem24());
            stmt.setDouble(28, eval.getItem25()); stmt.setDouble(29, eval.getItem26()); stmt.setDouble(30, eval.getItem27());
            stmt.setDouble(31, eval.getItem28()); stmt.setDouble(32, eval.getItem29()); stmt.setDouble(33, eval.getItem30());
            stmt.setDouble(34, eval.getItem31()); stmt.setDouble(35, eval.getItem32()); stmt.setDouble(36, eval.getItem33());
            stmt.setDouble(37, eval.getItem34()); stmt.setDouble(38, eval.getItem35()); stmt.setDouble(39, eval.getItem36());
            stmt.setDouble(40, eval.getItem37()); stmt.setDouble(41, eval.getItem38());
            stmt.setDouble(42, eval.getPuntajeTotal());
            stmt.setString(43, eval.getCondicion());
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }
    
    public int contarSustentacionesPorJuradoYEstado(String codigoJurado, String estadoEvaluacion) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            String sql;
            
            if ("Pendiente".equals(estadoEvaluacion)) {
                sql = "SELECT COUNT(*) FROM sustentaciones s " +
                      "WHERE (s.codigo_miembro1=? OR s.codigo_miembro2=? OR s.codigo_miembro3=?) " +
                      "AND NOT EXISTS (SELECT 1 FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = ?)";
            } else {
                sql = "SELECT COUNT(*) FROM sustentaciones s " +
                      "WHERE (s.codigo_miembro1=? OR s.codigo_miembro2=? OR s.codigo_miembro3=?) " +
                      "AND EXISTS (SELECT 1 FROM evaluaciones_jurado ej WHERE ej.id_sustentacion = s.id_sustentacion AND ej.codigo_jurado = ?)";
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigoJurado);
            stmt.setString(2, codigoJurado);
            stmt.setString(3, codigoJurado);
            stmt.setString(4, codigoJurado);
            
            rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
            
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return 0;
    }
    
    public EvaluacionJurado getEvaluacionJuradoPorSustentacionYDocente(int idSustentacion, String codigoJurado) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
        EvaluacionJurado eval = null;
        try {
            conn = Conexion.getConnection();
            String sql = "SELECT * FROM evaluaciones_jurado WHERE id_sustentacion = ? AND codigo_jurado = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSustentacion);
            stmt.setString(2, codigoJurado);
            rs = stmt.executeQuery();
            if (rs.next()) {
                eval = new EvaluacionJurado();
                eval.setIdEvaluacionJurado(rs.getInt("id_evaluacion_jurado"));
                eval.setIdSustentacion(rs.getInt("id_sustentacion"));
                eval.setCodigoJurado(rs.getString("codigo_jurado"));
                eval.setObservaciones(rs.getString("observaciones")); 
                eval.setPuntajeTotal(rs.getDouble("puntaje_total"));
                eval.setCondicion(rs.getString("condicion"));
                eval.setFechaEvaluacion(rs.getTimestamp("fecha_evaluacion"));
                
                eval.setItem1(rs.getDouble("item_1")); eval.setItem2(rs.getDouble("item_2")); eval.setItem3(rs.getDouble("item_3"));
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return eval;
    }
    
    public boolean actualizarResultadoSustentacion(int idSustentacion, double notaFinal, String resultado) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            String sql = "UPDATE sustentaciones SET nota_final = ?, resultado_defensa = ? WHERE id_sustentacion = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, notaFinal);
            stmt.setString(2, resultado);
            stmt.setInt(3, idSustentacion);
            return stmt.executeUpdate() > 0;
        } finally { Conexion.close(stmt); Conexion.close(conn); }
    }
    
    public Tramite getTramitePorEstudianteFull(String codigoEstudiante) throws SQLException {
        Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null; Tramite t = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM tramites WHERE codigo_estudiante = ? LIMIT 1");
            stmt.setString(1, codigoEstudiante);
            rs = stmt.executeQuery();
            if(rs.next()) {
                t = new Tramite();
                t.setIdTramite(rs.getInt("id_tramite"));
                t.setCodigoEstudiante(rs.getString("codigo_estudiante"));
                t.setEstadoActual(rs.getString("estado_actual"));
            }
        } finally { Conexion.close(rs); Conexion.close(stmt); Conexion.close(conn); }
        return t;
    }
}