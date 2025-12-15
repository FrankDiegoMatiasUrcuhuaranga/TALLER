package Controladores;

import Datos.TesisDAO;
import Datos.TramitesDAO;
import Modelos.Evaluacion;
import Modelos.EvaluacionJurado; // Nuevo modelo necesario
import Modelos.Notificacion;
import Modelos.Sustentacion;
import Modelos.Tesis;
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

@WebServlet(name = "TeacherServlet", urlPatterns = {"/TeacherServlet"})
public class TeacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        TesisDAO tesisDAO = new TesisDAO();
        TramitesDAO tramitesDAO = new TramitesDAO();
        
        try {
            String codigoDocente = usuario.getCodigo();

            // 1. LISTAS PRINCIPALES
            List<Tesis> listaTesis = tesisDAO.getTesisPorDocente(codigoDocente);
            List<Sustentacion> listaSustentaciones = tramitesDAO.getSustentacionesPorJurado(codigoDocente);
            List<Evaluacion> historialEvaluaciones = tesisDAO.getEvaluacionesPorDocente(codigoDocente);
            List<Notificacion> notificaciones = tramitesDAO.getNotificacionesNoLeidas(codigoDocente);

            // 2. PREFETCH DE EVALUACIONES DE ASESOR (Para recalificar y para "Mis Estudiantes")
            for (Tesis t : listaTesis) {
                Evaluacion evalPrev = tesisDAO.getEvaluacionPorTesisYDocente(t.getIdTesis(), codigoDocente);
                if (evalPrev != null) {
                    // Clave: "eval_" + idTesis
                    request.setAttribute("eval_" + t.getIdTesis(), evalPrev);
                }
            }

            // 3. PREFETCH DE EVALUACIONES DE JURADO (Para "Ver Detalle" en Sustentaciones)
            // Necesitamos un método en TramitesDAO para esto. Asumiremos que existe o lo agregamos abajo.
            for (Sustentacion s : listaSustentaciones) {
                // Si ya evaluó, cargamos el detalle para mostrarlo en el modal
                if ("Evaluado".equals(s.getEstadoEvaluacionJurado())) {
                    EvaluacionJurado evalJury = tramitesDAO.getEvaluacionJuradoPorSustentacionYDocente(s.getIdSustentacion(), codigoDocente);
                    if (evalJury != null) {
                        // Clave: "evalJury_" + idSustentacion
                        request.setAttribute("evalJury_" + s.getIdSustentacion(), evalJury);
                    }
                }
            }

            // 4. ESTADÍSTICAS DESGLOSADAS PARA DASHBOARD
            int asesorPendientes = tesisDAO.contarTesisPorDocenteYEstado(codigoDocente, "PENDIENTE");
            int asesorAprobadas = tesisDAO.contarTesisPorDocenteYEstado(codigoDocente, "Aprobado");
            int juradoPendientes = tramitesDAO.contarSustentacionesPorJuradoYEstado(codigoDocente, "Pendiente");
            int juradoEvaluadas = tramitesDAO.contarSustentacionesPorJuradoYEstado(codigoDocente, "Evaluado");
            
            // Contar estudiantes únicos
            long totalEstudiantes = listaTesis.stream().map(Tesis::getCodigoEstudiante).distinct().count();

            // 5. ENVIAR AL JSP
            request.setAttribute("listaTesis", listaTesis);
            request.setAttribute("listaSustentaciones", listaSustentaciones);
            request.setAttribute("historialEvaluaciones", historialEvaluaciones);
            request.setAttribute("notificaciones", notificaciones);
            
            request.setAttribute("asesorPendientes", asesorPendientes);
            request.setAttribute("asesorAprobadas", asesorAprobadas);
            request.setAttribute("juradoPendientes", juradoPendientes);
            request.setAttribute("juradoEvaluadas", juradoEvaluadas);
            request.setAttribute("totalEstudiantes", totalEstudiantes);
            request.setAttribute("totalAsignadas", listaTesis.size()); // Total general

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=db_teacher");
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("teacher.jsp");
        dispatcher.forward(request, response);
    }
}