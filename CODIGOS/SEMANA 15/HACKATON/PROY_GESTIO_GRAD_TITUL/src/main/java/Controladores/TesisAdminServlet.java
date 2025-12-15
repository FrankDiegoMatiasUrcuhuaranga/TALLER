package Controladores;

import Datos.TesisDAO;
import Modelos.Tesis;
import Servicios.NotificacionService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "TesisAdminServlet", urlPatterns = {"/TesisAdminServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 15
)
public class TesisAdminServlet extends HttpServlet {
    
    private String fixedUploadPath;
    private NotificacionService notificacionService;
    
    @Override
    public void init() throws ServletException {
        // Configuración de ruta de subida (asegurarse de que coincida con tu entorno)
        fixedUploadPath = getServletContext().getInitParameter("ruta-tesis-uploads");
        if (fixedUploadPath == null) fixedUploadPath = "C:/tesis_uploads"; 
        File dir = new File(fixedUploadPath);
        if (!dir.exists()) dir.mkdirs();
        notificacionService = new NotificacionService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action"); 
        TesisDAO dao = new TesisDAO();
        
        // Mantener la pestaña activa en 'tesis-crud'
        session.setAttribute("flash_activeTab", "tesis-crud");

        if (action == null) {
            session.setAttribute("adminError", "Acción no especificada.");
            response.sendRedirect("AdminServlet");
            return;
        }

        try {
            switch (action) {
                case "crear":
                    handleCrearTesis(request, dao, session);
                    break;
                case "reasignar":
                    handleReasignarTesis(request, dao, session);
                    break;
                case "editar":
                    handleEditarTesis(request, dao, session);
                    break;
                case "eliminar":
                    handleEliminarTesis(request, dao, session);
                    break;
                // --- NUEVO CASO: VER DETALLE (APROBADO) ---
                case "ver_detalle_tesis":
                    handleVerDetalleTesis(request, dao, session);
                    break;
                default:
                    session.setAttribute("adminError", "Acción desconocida.");
                    break;
            }
        } catch (SQLException e) {
             if (e.getMessage().contains("foreign key constraint fails")) {
                session.setAttribute("adminError", "Error: No se puede eliminar, tiene registros asociados.");
            } else {
                e.printStackTrace();
                session.setAttribute("adminError", "Error BD: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("adminError", "Error inesperado: " + e.getMessage());
        }
        
        // Redirección final al panel administrativo
        response.sendRedirect("AdminServlet");
    }

    private void handleCrearTesis(HttpServletRequest request, TesisDAO dao, HttpSession session) 
            throws SQLException, IOException, ServletException {
        String titulo = request.getParameter("tesis_titulo");
        String codigoEstudiante = request.getParameter("alumno_id");
        String codigoDocente = request.getParameter("docente_id");
        
        Tesis nuevaTesis = new Tesis();
        nuevaTesis.setTitulo(titulo);
        nuevaTesis.setCodigoEstudiante(codigoEstudiante);
        
        // Lógica de estado inicial
        if (codigoDocente != null && !codigoDocente.isEmpty()) {
            nuevaTesis.setCodigoDocenteRevisor(codigoDocente);
            nuevaTesis.setEstado("En Proceso");
        } else {
            nuevaTesis.setCodigoDocenteRevisor(null);
            nuevaTesis.setEstado("Iniciado");
        }
        
        String dbPath = null;
        try {
            Part filePart = request.getPart("tesis_file");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
                fileName = fileName.replaceAll("\\s+", "_"); 
                filePart.write(fixedUploadPath + File.separator + fileName);
                dbPath = fileName;
            }
        } catch (Exception e) { throw new ServletException("Error upload", e); }
        
        nuevaTesis.setArchivoPath(dbPath);
        
        if (dao.insertarTesis(nuevaTesis)) {
            notificacionService.notificarCambioEstado(codigoEstudiante, "Plan de Tesis registrado: " + titulo);
            if (codigoDocente != null && !codigoDocente.isEmpty()) {
                notificacionService.notificarCambioEstado(codigoDocente, "Nueva tesis asignada: " + titulo);
            }
            session.setAttribute("adminMsg", "Tesis registrada exitosamente.");
        } else {
            session.setAttribute("adminError", "Error al registrar en BD.");
        }
    }
    
    private void handleReasignarTesis(HttpServletRequest request, TesisDAO dao, HttpSession session) throws SQLException {
        int idTesis = Integer.parseInt(request.getParameter("id_tesis"));
        String codigoDocente = request.getParameter("docente_id");
        // Si se asigna un docente, pasa a "En Proceso", si se quita, vuelve a "Iniciado" (salvo que ya esté aprobado, pero el botón no aparece si está aprobado)
        String nuevoEstado = (codigoDocente == null || codigoDocente.isEmpty()) ? "Iniciado" : "En Proceso";
        
        if (dao.actualizarAsignacion(idTesis, codigoDocente, nuevoEstado)) {
            session.setAttribute("adminMsg", "Asignación actualizada.");
        } else {
            session.setAttribute("adminError", "Error al actualizar asignación.");
        }
    }

    private void handleEditarTesis(HttpServletRequest request, TesisDAO dao, HttpSession session) throws SQLException, IOException, ServletException {
        int idTesis = Integer.parseInt(request.getParameter("id_tesis"));
        String titulo = request.getParameter("tesis_titulo");
        String existingFilePath = request.getParameter("existing_file_path");
        String dbPath = existingFilePath;
        
        try {
            Part filePart = request.getPart("tesis_file");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); 
                fileName = fileName.replaceAll("\\s+", "_");
                filePart.write(fixedUploadPath + File.separator + fileName);
                dbPath = fileName;
            }
        } catch (Exception e) { session.setAttribute("adminError", "Error archivo: " + e.getMessage()); return; }
        
        Tesis t = new Tesis();
        t.setIdTesis(idTesis); 
        t.setTitulo(titulo); 
        t.setArchivoPath(dbPath);
        
        if (dao.actualizarTesis(t)) session.setAttribute("adminMsg", "Tesis actualizada.");
        else session.setAttribute("adminError", "Error al actualizar BD.");
    }

    private void handleEliminarTesis(HttpServletRequest request, TesisDAO dao, HttpSession session) throws SQLException {
        int idTesis = Integer.parseInt(request.getParameter("id_tesis"));
        if (dao.eliminarTesis(idTesis)) session.setAttribute("adminMsg", "Tesis eliminada.");
        else session.setAttribute("adminError", "No se pudo eliminar.");
    }

    // --- MÉTODO NUEVO PARA VER DETALLE ---
    private void handleVerDetalleTesis(HttpServletRequest request, TesisDAO dao, HttpSession session) throws SQLException {
        int idTesis = Integer.parseInt(request.getParameter("id_tesis"));
        
        // NOTA: Asegúrate de que tu TesisDAO tenga un método getTesisPorId(int id).
        // Si no lo tiene, deberás agregarlo en el DAO.
        Tesis tesis = dao.getTesisPorId(idTesis);
        
        if (tesis != null) {
            session.setAttribute("tesisDetalle", tesis);
            session.setAttribute("flash_modalTesisDetalle", true); // Flag para abrir modal en JSP
        } else {
            session.setAttribute("adminError", "No se encontró la tesis solicitada.");
        }
    }
}