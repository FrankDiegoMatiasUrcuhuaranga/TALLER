package Controladores;

import Datos.TesisDAO; 
import Datos.TramitesDAO; 
import Modelos.Evaluacion; 
import Modelos.Tesis; 
import Modelos.Usuario;
import Modelos.Tramite; 
import Modelos.Sustentacion; // <-- IMPORTANTE
import Modelos.Notificacion; 
import Modelos.DocumentoRequisito; 
import java.io.IOException;
import java.sql.SQLException; 
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Carga los datos para el portal del estudiante.
 * ACTUALIZADO: Carga datos de sustentación para mostrar jurados y fecha.
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/StudentServlet"})
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Mensajes Flash
        if (session.getAttribute("studentMsg") != null) {
            request.setAttribute("studentMsg", session.getAttribute("studentMsg"));
            session.removeAttribute("studentMsg");
        }
        if (session.getAttribute("studentError") != null) {
            request.setAttribute("studentError", session.getAttribute("studentError"));
            session.removeAttribute("studentError");
        }
        
        TesisDAO tesisDAO = new TesisDAO();
        TramitesDAO tramitesDAO = new TramitesDAO();
        
        try {
            String codigoEstudiante = usuario.getCodigo();

            // 1. DATOS DE TESIS
            Tesis tesis = tesisDAO.getTesisPorEstudiante(codigoEstudiante);
            Evaluacion evaluacion = null;
            List<Evaluacion> historial = null;
            
            if (tesis != null) {
                evaluacion = tesisDAO.getUltimaEvaluacionPorTesis(tesis.getIdTesis());
                historial = tesisDAO.getHistorialEvaluaciones(tesis.getIdTesis());
            }
            
            // 2. DATOS DE TRÁMITE
            Tramite tramite = tramitesDAO.getTramitePorEstudiante(codigoEstudiante);
            List<DocumentoRequisito> listaDocumentos = null;
            Sustentacion sustentacion = null; // <-- NUEVO
            
            if (tramite == null) {
                tramitesDAO.iniciarTramite(codigoEstudiante);
                tramite = tramitesDAO.getTramitePorEstudiante(codigoEstudiante);
            }
            
            if (tramite != null) {
                listaDocumentos = tramitesDAO.getDocumentosPorTramite(tramite.getIdTramite());
                // Recuperar datos de sustentación si existen (para mostrar jurados y fecha)
                sustentacion = tramitesDAO.getSustentacionPorTramite(tramite.getIdTramite());
            }
            
            // 3. NOTIFICACIONES
            List<Notificacion> notificaciones = tramitesDAO.getNotificacionesNoLeidas(codigoEstudiante);
            
            // --- Setear Atributos ---
            request.setAttribute("tesis", tesis);
            request.setAttribute("evaluacion", evaluacion);
            request.setAttribute("historial", historial);
            
            request.setAttribute("tramiteActual", tramite);
            request.setAttribute("listaDocumentos", listaDocumentos);
            request.setAttribute("sustentacion", sustentacion); // <-- ENVIAMOS SUSTENTACIÓN
            request.setAttribute("notificaciones", notificaciones);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=db_student");
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("student.jsp");
        dispatcher.forward(request, response);
    }
}