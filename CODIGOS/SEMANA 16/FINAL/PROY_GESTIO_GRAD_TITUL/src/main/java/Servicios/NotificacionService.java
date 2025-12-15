package Servicios;

import Datos.TramitesDAO;
import Datos.UsuarioDAO; // <-- Importar UsuarioDAO
import Modelos.Notificacion;
import java.sql.SQLException;
import java.util.List;

public class NotificacionService {

    private TramitesDAO tramitesDao;
    private UsuarioDAO usuarioDao;

    public NotificacionService() {
        this.tramitesDao = new TramitesDAO();
        this.usuarioDao = new UsuarioDAO();
    }

    public void notificarCambioEstado(String codigoUsuarioDestino, String nuevoEstado) {
        String mensaje = "Su trámite de titulación ha cambiado al estado: " + nuevoEstado;
        enviarNotificacionMulticanal(codigoUsuarioDestino, mensaje);
    }
    
    public void notificarDocumentoRecibido(String codigoUsuarioDestino, String tipoDoc) {
        String mensaje = "Hemos recibido su documento: " + tipoDoc + ". Está pendiente de validación.";
        enviarNotificacionMulticanal(codigoUsuarioDestino, mensaje);
    }

    // --- NUEVO: Notificar a todos los Administradores ---
    public void notificarAdministradores(String mensaje) {
        try {
            List<String> admins = usuarioDao.getCodigosAdmins();
            for (String codigoAdmin : admins) {
                // Crear notificación interna
                Notificacion noti = new Notificacion();
                noti.setCodigoUsuarioDestino(codigoAdmin);
                noti.setMensaje(mensaje);
                noti.setTipo("Sistema"); // Tipo alerta interna
                tramitesDao.crearNotificacion(noti);
            }
            // Log para depuración
            if (!admins.isEmpty()) {
                System.out.println("[NOTIFICACION ADMIN] Enviada a " + admins.size() + " administradores: " + mensaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al notificar administradores: " + e.getMessage());
        }
    }

    private void enviarNotificacionMulticanal(String codigoUsuario, String mensaje) {
        try {
            // 1. Guardar notificación de sistema
            Notificacion notiWeb = new Notificacion();
            notiWeb.setCodigoUsuarioDestino(codigoUsuario); 
            notiWeb.setMensaje(mensaje);
            notiWeb.setTipo("Sistema");
            tramitesDao.crearNotificacion(notiWeb);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al registrar notificaciones: " + e.getMessage());
        }
    }
}