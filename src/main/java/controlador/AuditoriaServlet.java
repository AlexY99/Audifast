package controlador;
import modelo.dto.AuditoriaDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.AuditoriaDAO;

public class AuditoriaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
      
        String accion = request.getParameter("accion");

        if(accion.equals("Almacenar"))
            almacenarAuditoria(request,response);
        else if(accion.equals("Cancelar"))
            cancelarAuditoria(request,response);
        else if(accion.equals("InfoAuditoria"))
            infoAuditoria(request,response);
        
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
    
    private void almacenarAuditoria(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            AuditoriaDAO dao = new AuditoriaDAO();
            AuditoriaDTO dto = new AuditoriaDTO();
            //Si existe el parámetro new se crea, sino se actualiza
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();
            dto.getEntidad().setCorreo_auditor_lider(correo);
            dto.getEntidad().setRfc_organizacion(request.getParameter("txtRFC"));            
            dao.create(dto);
            System.out.println("Creado->"+dto.toString());
            request.setAttribute("mensaje", "Auditoría creada exitosamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void cancelarAuditoria(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuditoriaDAO dao = new AuditoriaDAO();
            AuditoriaDTO dto = new AuditoriaDTO();
            HttpSession session = request.getSession();
            dto.getEntidad().setId(Integer.parseInt(request.getParameter("IDAuditoria")));
            dto = dao.read(dto);
            dao.delete(dto);
            request.setAttribute("mensaje","Auditoría eliminada satisfactoriamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    private void infoAuditoria(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("AuditoriaID");
        try{
            AuditoriaDTO dto = new AuditoriaDTO();
            AuditoriaDAO dao = new AuditoriaDAO();
            dto.getEntidad().setId(Integer.parseInt("ID"));
            dto = dao.read(dto);
            request.setAttribute("Auditoria",dto);
            RequestDispatcher vista = request.getRequestDispatcher("Auditoria.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
