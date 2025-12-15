package Controladores;

import Datos.TesisDAO;
import Datos.TramitesDAO;
import Datos.UsuarioDAO;
import Modelos.DocumentoRequisito;
import Modelos.Notificacion;
import Modelos.Sustentacion;
import Modelos.Tesis;
import Modelos.Tramite;
import Modelos.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        Usuario adminUser = (Usuario) session.getAttribute("usuarioLogueado");
        
        // --- LOGICA DE FLASH SCOPE (URL LIMPIA) ---
        
        // 1. Mensajes
        if (session.getAttribute("adminMsg") != null) {
            request.setAttribute("adminMsg", session.getAttribute("adminMsg"));
            session.removeAttribute("adminMsg");
        }
        if (session.getAttribute("adminError") != null) {
            request.setAttribute("adminError", session.getAttribute("adminError"));
            session.removeAttribute("adminError");
        }
        
        // 2. Tab Activa
        if (session.getAttribute("flash_activeTab") != null) {
            request.setAttribute("activeTab", session.getAttribute("flash_activeTab"));
            session.removeAttribute("flash_activeTab");
        } else {
            request.setAttribute("activeTab", "dashboard");
        }

        // 3. Modales
        if (session.getAttribute("flash_modalData") != null) {
            request.setAttribute("openModalDocs", true);
            request.setAttribute("modalTramiteId", session.getAttribute("modalTramiteId"));
            request.setAttribute("listaDocumentosModal", session.getAttribute("listaDocumentosModal"));
            request.setAttribute("sustentacionActual", session.getAttribute("sustentacionActual"));
            request.setAttribute("param_type", session.getAttribute("param_type"));
            
            // Limpiar todo para que al recargar (F5) no se abra de nuevo
            session.removeAttribute("flash_modalData");
            session.removeAttribute("modalTramiteId");
            session.removeAttribute("listaDocumentosModal");
            session.removeAttribute("sustentacionActual");
            session.removeAttribute("param_type");
        }

        // --- Carga de Datos Normal ---
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        TesisDAO tesisDAO = new TesisDAO();
        TramitesDAO tramitesDAO = new TramitesDAO();
        
        try {
            List<Usuario> listaDocentes = usuarioDAO.listarPorRol("docente");
            List<Usuario> listaEstudiantes = usuarioDAO.listarPorRol("alumno");
            
            int totalDocentes = listaDocentes.size();
            int totalEstudiantes = listaEstudiantes.size();
            List<String> estadosProceso = List.of("Iniciado", "En Proceso", "Necesita Correcciones");
            int tesisEnProceso = tesisDAO.contarTesisPorEstados(estadosProceso);
            List<String> estadosCompletado = List.of("Aprobado");
            int tesisCompletadas = tesisDAO.contarTesisPorEstados(estadosCompletado);
            
            List<Tesis> listaTotalTesis = tesisDAO.getAllTesisConNombres();
            List<Tramite> listaTramites = tramitesDAO.listarTodosLosTramites();
            List<Notificacion> notificaciones = tramitesDAO.getNotificacionesNoLeidas(adminUser.getCodigo());
            
            request.setAttribute("notificaciones", notificaciones);
            request.setAttribute("listaDocentes", listaDocentes);
            request.setAttribute("listaEstudiantes", listaEstudiantes);
            request.setAttribute("listaDocentesDropdown", listaDocentes);
            request.setAttribute("totalDocentes", totalDocentes);
            request.setAttribute("totalEstudiantes", totalEstudiantes);
            request.setAttribute("tesisEnProceso", tesisEnProceso);
            request.setAttribute("tesisCompletadas", tesisCompletadas);
            request.setAttribute("listaTotalTesis", listaTotalTesis);
            request.setAttribute("listaTramites", listaTramites);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=admin_load_failed");
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }
}