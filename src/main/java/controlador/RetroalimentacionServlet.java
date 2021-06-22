package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.AuditoriaDAO;
import modelo.dao.ClaveAccesoDAO;
import modelo.dto.AuditoriaDTO;
import modelo.dto.ClaveAccesoDTO;

@WebServlet(name = "RetroalimentacionServlet", urlPatterns = {"/RetroalimentacionServlet"})
public class RetroalimentacionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String accion = request.getParameter("accion");

        if (accion.equals("Retroalimentacion")) {
            Retroalimentacion(request, response);
        } else if (accion.equals("infoRetroalimentacion")) {
            infoRetroalimentacion(request, response);
        } else if (accion.equals("AlmacenarClave")) {
            AlmacenarClave(request, response);
        } else if (accion.equals("EliminarClave")) {
            EliminarClave(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void Retroalimentacion(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        String correo = "";
        String clave = "";
        int idAuditoria = 0;
        if (session.getAttribute("CorreoAuditor") != null && request.getParameter("txtIdAuditoria") != null ) {
            correo = (String) session.getAttribute("CorreoAuditor");
            request.setAttribute("CorreoAuditor", correo);
            request.setAttribute("Invitado", false);
            idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
            AuditoriaDTO dto = new AuditoriaDTO();
            AuditoriaDAO dao = new AuditoriaDAO();
            dto.getEntidad().setId(idAuditoria);
            dto = dao.read(dto);
            boolean permisoEdicion;
            if (dto.getEntidad().getCorreo_auditor_lider().equals(correo)) {
                permisoEdicion = true;
            } else {
                permisoEdicion = false;
            }
            request.setAttribute("permisoEdicion", permisoEdicion);
            request.setAttribute("auditoria", dto);
            infoRetroalimentacion(request, response);
        } else {
            //Validacion de claves de acceso para que haga esto
            correo = request.getParameter("correo");
            clave = request.getParameter("clave");
            ClaveAccesoDAO daoC = new ClaveAccesoDAO();
            ClaveAccesoDTO dtoC = new ClaveAccesoDTO();
            dtoC.getEntidad().setClave(clave);
            dtoC = daoC.read(dtoC);
            if (dtoC.getEntidad() == null) {
                request.setAttribute("mensaje", "Clave de Acceso o Correo Incorrecto");
                RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
                try {
                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (IOException | ServletException ex) {
                    Logger.getLogger(RetroalimentacionServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(!dtoC.getEntidad().getCorreo().equals(correo)) {
                request.setAttribute("mensaje", "Clave de Acceso o Correo Incorrecto");
                RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
                try {
                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (IOException | ServletException ex) {
                    Logger.getLogger(RetroalimentacionServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else {
                idAuditoria = dtoC.getEntidad().getAuditoria().getId();

                session.setAttribute("CorreoAuditor", correo);
                session.setAttribute("Invitado", true);
                request.setAttribute("CorreoAuditor", correo);
                request.setAttribute("Invitado", true);
                AuditoriaDTO dto = new AuditoriaDTO();
                AuditoriaDAO dao = new AuditoriaDAO();
                dto.getEntidad().setId(idAuditoria);
                dto = dao.read(dto);
                boolean permisoEdicion;
                if (dto.getEntidad().getCorreo_auditor_lider().equals(correo)) {
                    permisoEdicion = true;
                } else {
                    permisoEdicion = false;
                }
                request.setAttribute("permisoEdicion", permisoEdicion);
                request.setAttribute("auditoria", dto);
                infoRetroalimentacion(request, response);
            }
        }
    }

    private void infoRetroalimentacion(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getAttribute("auditoria"));
        AuditoriaDTO dto = new AuditoriaDTO();
        dto = (AuditoriaDTO) request.getAttribute("auditoria");
        int idAuditoria = dto.getEntidad().getId();
        ClaveAccesoDAO daoClave = new ClaveAccesoDAO();
        ArrayList<ClaveAccesoDTO> claves = new ArrayList();
        claves = daoClave.ClavesAcceso(idAuditoria);
        request.setAttribute("Claves", claves);
        RequestDispatcher vista = request.getRequestDispatcher("retroalimentacion.jsp");;
        try {
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(RetroalimentacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AlmacenarClave(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        ClaveAccesoDTO dtoClave = new ClaveAccesoDTO();
        HttpSession session = request.getSession();
        AuditoriaDTO dto = new AuditoriaDTO();
        AuditoriaDAO dao = new AuditoriaDAO();
        ClaveAccesoDAO daoClave = new ClaveAccesoDAO();
        boolean invitado = (boolean) session.getAttribute("Invitado");
        String correo = session.getAttribute("CorreoAuditor").toString();
        String correoClave = request.getParameter("txtCorreo");
        String clave = request.getParameter("txtClaveAcceso");

        dtoClave.getEntidad().setClave(clave);
        dtoClave.getEntidad().setCorreo(correoClave);
        dto.getEntidad().setId(idAuditoria);
        dto = dao.read(dto);
        dtoClave.getEntidad().getAuditoria().setId(idAuditoria);
        daoClave.create(dtoClave);
        boolean permisoEdicion;
        if (dto.getEntidad().getCorreo_auditor_lider().equals(correo)) {
            permisoEdicion = true;
        } else {
            permisoEdicion = false;
        }

        request.setAttribute("Invitado", invitado);
        request.setAttribute("permisoEdicion", permisoEdicion);
        request.setAttribute("auditoria", dto);
        infoRetroalimentacion(request, response);
    }

    private void EliminarClave(HttpServletRequest request, HttpServletResponse response) {
        String clave = request.getParameter("txtClave");
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        ClaveAccesoDTO dtoC = new ClaveAccesoDTO();
        ClaveAccesoDAO daoC = new ClaveAccesoDAO();
        AuditoriaDTO dto = new AuditoriaDTO();
        AuditoriaDAO dao = new AuditoriaDAO();
        dto.getEntidad().setId(idAuditoria);
        dto = dao.read(dto);
        dtoC.getEntidad().setClave(clave);
        dtoC = daoC.read(dtoC);
        daoC.delete(dtoC);
        HttpSession session = request.getSession();
        String correo = session.getAttribute("CorreoAuditor").toString();
        boolean invitado = (boolean) session.getAttribute("Invitado");
        boolean permisoEdicion;
        if (dto.getEntidad().getCorreo_auditor_lider().equals(correo)) {
            permisoEdicion = true;
        } else {
            permisoEdicion = false;
        }
        request.setAttribute("auditoria", dto);
        request.setAttribute("Invitado", invitado);
        request.setAttribute("permisoEdicion", permisoEdicion);
        infoRetroalimentacion(request, response);
    }

}
