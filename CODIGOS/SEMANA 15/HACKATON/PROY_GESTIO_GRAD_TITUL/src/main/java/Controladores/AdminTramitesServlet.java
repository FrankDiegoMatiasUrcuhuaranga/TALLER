package Controladores;

import Datos.TramitesDAO;
import Modelos.DocumentoRequisito;
import Modelos.Sustentacion;
import Modelos.Usuario;
import Servicios.NotificacionService;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminTramitesServlet", urlPatterns = {"/AdminTramitesServlet"})
public class AdminTramitesServlet extends HttpServlet {

    private NotificacionService notificacionService;

    @Override
    public void init() throws ServletException {
        notificacionService = new NotificacionService();
    }
    
    // Método auxiliar para redirección limpia y seteo de tab activo
    private void redirigirAdmin(HttpServletResponse response, HttpSession session, String tab) throws IOException {
        session.setAttribute("flash_activeTab", tab);
        response.sendRedirect("AdminServlet"); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        TramitesDAO dao = new TramitesDAO();
        HttpSession session = request.getSession();
        
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;
        if (usuario == null || !"admin".equals(usuario.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // --- CARGA DE MODALES (POST para URL Limpia) ---
            // Soporta tipos de vista: 'docs', 'jury' y ahora 'final'
            if ("ver_detalle_tramite".equals(action)) {
                int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                String tipoVista = request.getParameter("tipo_vista"); 
                
                // Carga de datos necesarios para cualquier vista de detalle
                List<DocumentoRequisito> docs = dao.getDocumentosPorTramite(idTramite);
                Sustentacion sust = dao.getSustentacionPorTramite(idTramite);
                
                // Seteo de atributos de sesión para el modal
                session.setAttribute("flash_modalData", true);
                session.setAttribute("modalTramiteId", idTramite);
                session.setAttribute("listaDocumentosModal", docs);
                session.setAttribute("sustentacionActual", sust);
                session.setAttribute("param_type", tipoVista); // Aquí pasa 'final' si viene del botón nuevo
                
                redirigirAdmin(response, session, "tramites-gestion");
                return;
            }

            // --- ACCIÓN: VALIDAR DOCUMENTOS (Fase 1) ---
            if ("validar_documento".equals(action)) {
                int idDoc = Integer.parseInt(request.getParameter("id_documento"));
                int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                String estado = request.getParameter("estado");
                String observacion = request.getParameter("observacion");
                
                dao.actualizarEstadoDocumento(idDoc, estado, observacion);
                
                String codigoEstudiante = dao.getCodigoEstudiantePorTramite(idTramite);
                if (codigoEstudiante != null) {
                    notificacionService.notificarCambioEstado(codigoEstudiante, "Documento " + estado);
                }
                
                // Recargar modal para seguir editando
                reloadModalData(session, dao, idTramite, "docs");
                session.setAttribute("adminMsg", "Documento actualizado.");
                redirigirAdmin(response, session, "tramites-gestion");
                return; 

            // --- FASE 2: DESIGNAR JURADO (SOLO MIEMBROS) ---
            } else if ("designar_jurado".equals(action)) {
                int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                
                Sustentacion s = new Sustentacion();
                s.setIdTramite(idTramite);
                s.setCodigoMiembro1(request.getParameter("miembro1"));
                s.setCodigoMiembro2(request.getParameter("miembro2"));
                s.setCodigoMiembro3(request.getParameter("miembro3"));
                s.setCodigoSuplente(request.getParameter("suplente"));
                
                // Guardamos designación
                dao.guardarJurado(s); 
                
                // Notificaciones
                notificacionService.notificarCambioEstado(s.getCodigoMiembro1(), "Designado Presidente de Jurado");
                notificacionService.notificarCambioEstado(s.getCodigoMiembro2(), "Designado Secretario de Jurado");
                notificacionService.notificarCambioEstado(s.getCodigoMiembro3(), "Designado Vocal de Jurado");
                
                String codEst = dao.getCodigoEstudiantePorTramite(idTramite);
                notificacionService.notificarCambioEstado(codEst, "Designación de Jurado");

                // Avanzar flujo
                dao.avanzarFaseTramite(idTramite, "Designación de Jurado"); 
                
                session.setAttribute("adminMsg", "Jurado designado correctamente. Esperando evaluación.");
                reloadModalData(session, dao, idTramite, "jury");
                redirigirAdmin(response, session, "tramites-gestion");
                return;

            // --- FASE 3: PROGRAMAR FECHA (SOLO FECHA Y LUGAR) ---
            } else if ("programar_sustentacion".equals(action)) {
                int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                
                String fechaStr = request.getParameter("fecha_hora"); 
                String lugar = request.getParameter("lugar");
                // Conversión robusta de fecha
                Timestamp fechaHora = Timestamp.valueOf(fechaStr.replace("T", " ") + ":00");
                
                dao.programarFechaSustentacion(idTramite, fechaHora, lugar);
                
                String codEst = dao.getCodigoEstudiantePorTramite(idTramite);
                notificacionService.notificarCambioEstado(codEst, "Sustentación Programada");
                
                dao.avanzarFaseTramite(idTramite, "Sustentación Programada");
                
                session.setAttribute("adminMsg", "Fecha de sustentación programada.");
                reloadModalData(session, dao, idTramite, "jury");
                redirigirAdmin(response, session, "tramites-gestion");
                return;

            // --- ACCIÓN: CERRAR Y TITULAR ---
            } else if ("cerrar_sustentacion".equals(action)) {
                 int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                 Sustentacion sus = dao.getSustentacionPorTramite(idTramite);
                 
                 // Validación final de nota
                 if (sus != null && sus.getNotaFinal() >= 13.0) {
                     dao.actualizarResultadoSustentacion(sus.getIdSustentacion(), sus.getNotaFinal(), "Aprobado");
                     dao.avanzarFaseTramite(idTramite, "Titulado");
                     
                     String codEst = dao.getCodigoEstudiantePorTramite(idTramite);
                     notificacionService.notificarCambioEstado(codEst, "¡FELICITACIONES! Has aprobado la sustentación y obtenido el título.");
                     
                     session.setAttribute("adminMsg", "¡Estudiante Titulado con Éxito!");
                 } else {
                     session.setAttribute("adminError", "Nota insuficiente o faltan votos.");
                 }
                 redirigirAdmin(response, session, "tramites-gestion");
                 return;
                 
            // --- ACCIÓN: CONTROL MANUAL (FORCE UPDATE) ---
            } else if ("avanzar_fase".equals(action)) {
                int idTramite = Integer.parseInt(request.getParameter("id_tramite"));
                String fase = request.getParameter("nueva_fase");
                dao.avanzarFaseTramite(idTramite, fase);
                
                reloadModalData(session, dao, idTramite, "jury");
                session.setAttribute("adminMsg", "Fase actualizada manualmente a: " + fase);
                redirigirAdmin(response, session, "tramites-gestion");
                return;
                
            } else {
                session.setAttribute("adminError", "Acción no reconocida: " + action);
                redirigirAdmin(response, session, "tramites-gestion");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("adminError", "Error procesando solicitud: " + e.getMessage());
            response.sendRedirect("AdminServlet");
        }
    }
    
    // Helper para mantener el modal abierto tras una acción
    private void reloadModalData(HttpSession session, TramitesDAO dao, int idTramite, String type) throws java.sql.SQLException {
        session.setAttribute("flash_modalData", true);
        session.setAttribute("modalTramiteId", idTramite);
        session.setAttribute("listaDocumentosModal", dao.getDocumentosPorTramite(idTramite));
        session.setAttribute("sustentacionActual", dao.getSustentacionPorTramite(idTramite));
        session.setAttribute("param_type", type);
    }
}