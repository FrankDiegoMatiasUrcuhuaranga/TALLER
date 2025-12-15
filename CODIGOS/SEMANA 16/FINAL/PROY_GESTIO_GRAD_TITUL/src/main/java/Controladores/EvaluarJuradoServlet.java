package Controladores;

import Datos.TramitesDAO;
import Modelos.EvaluacionJurado;
import Modelos.Usuario;
import Servicios.NotificacionService; // <-- Importar Servicio
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EvaluarJuradoServlet", urlPatterns = {"/EvaluarJuradoServlet"})
public class EvaluarJuradoServlet extends HttpServlet {

    private NotificacionService notificacionService;

    @Override
    public void init() throws ServletException {
        notificacionService = new NotificacionService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null || !"docente".equals(usuario.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int idSustentacion = Integer.parseInt(request.getParameter("id_sustentacion"));
            String observaciones = request.getParameter("comentarios_revision");
            
            EvaluacionJurado eval = new EvaluacionJurado();
            eval.setIdSustentacion(idSustentacion);
            eval.setCodigoJurado(usuario.getCodigo());
            eval.setObservaciones(observaciones);

            double puntajeTotal = 0;
            for (int i = 1; i <= 38; i++) {
                double val = 0;
                try { val = Double.parseDouble(request.getParameter("item_" + i)); } catch(Exception e){}
                puntajeTotal += val;
                // Setear valor mediante reflexión para ahorrar 38 líneas de código
                try { eval.getClass().getMethod("setItem" + i, double.class).invoke(eval, val); } catch(Exception ex){}
            }

            String condicion;
            if (puntajeTotal >= 25) condicion = "Aprobado";
            else if (puntajeTotal >= 13) condicion = "Aprobado con observaciones menores";
            else condicion = "Desaprobado con observaciones mayores";
            
            eval.setPuntajeTotal(puntajeTotal);
            eval.setCondicion(condicion);
            
            TramitesDAO dao = new TramitesDAO();
            boolean exito = dao.guardarEvaluacionJurado(eval);
            
            if (exito) {
                // --- NUEVO: Notificar al Admin ---
                String msgAdmin = "El Jurado " + usuario.getNombreCompleto() + " ha emitido su voto para la Sustentación #" + idSustentacion + ". Nota: " + puntajeTotal;
                notificacionService.notificarAdministradores(msgAdmin);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("TeacherServlet?tab=jurado");
    }
}