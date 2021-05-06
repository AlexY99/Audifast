package controlador;
import modelo.dao.AuditorDAO;
import modelo.dto.AuditorDTO;
import utilidades.Utilerias;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "AuditorServlet", urlPatterns = {"/AuditorServlet"})
public class AuditorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        String accion = request.getParameter("accion");

        if(accion.equals("listaAuditores"))
            listaAuditores(request,response);
        else if(accion.equals("Eliminar"))
            eliminarAuditor(request,response);
        else if(accion.equals("Almacenar"))
            almacenarAuditor(request,response);
        else if(accion.equals("Actualizar"))
            actualizarAuditor(request,response);
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

    private void listaAuditores(HttpServletRequest request, HttpServletResponse response) {
        AuditorDAO dao = new AuditorDAO();
        try {
            Collection lista = dao.readAll();
            request.setAttribute("listaAuditores",lista);
            RequestDispatcher vista = request.getRequestDispatcher("listaAuditores.jsp");
            vista.forward(request, response);
        } catch (ServletException | IOException  ex) {
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    private void almacenarAuditor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            AuditorDAO dao = new AuditorDAO();
            AuditorDTO dto = new AuditorDTO();
            if(request.getParameter("id")!=null&&!request.getParameter("id").isEmpty()){
                dto.getEntidad().setIdAuditor(Integer.parseInt(request.getParameter("id")));
                dto.getEntidad().setNombre(request.getParameter("txtNombre"));
                dto.getEntidad().setPaterno(request.getParameter("txtPaterno"));
                dto.getEntidad().setMaterno(request.getParameter("txtMaterno"));
                dto.getEntidad().setEmail(request.getParameter("txtEmail"));
                Part archivo = request.getPart("txtImagen");
                if (archivo != null && !archivo.getSubmittedFileName().isEmpty()){
                    dto.getEntidad().setImagen(archivo.getInputStream().readAllBytes());
                }else{
                    dto.getEntidad().setImagen(dao.read(dto).getEntidad().getImagen());
                }
                dto.getEntidad().setNombre(request.getParameter("txtNombreAuditor"));
                dto.getEntidad().setClaveAuditor(request.getParameter("txtClaveAuditor"));
                dao.update(dto);
                request.setAttribute("mensaje", "Auditor actualizado exitosamente");
                getServletContext().getRequestDispatcher("/AuditorServlet?accion=listaAuditores").forward(request, response);
            }else{
                dto.getEntidad().setNombre(request.getParameter("txtNombre"));
                dto.getEntidad().setPaterno(request.getParameter("txtPaterno"));
                dto.getEntidad().setMaterno(request.getParameter("txtMaterno"));
                dto.getEntidad().setEmail(request.getParameter("txtEmail"));
                dto.getEntidad().setNombreAuditor(request.getParameter("txtNombreAuditor"));
                dto.getEntidad().setClaveAuditor(request.getParameter("txtClaveAuditor"));

                Part archivo = request.getPart("txtImagen");
                if (archivo != null && !archivo.getSubmittedFileName().isEmpty()){
                    dto.getEntidad().setImagen(archivo.getInputStream().readAllBytes());
                }else{
                    File img = new File(getServletContext().getRealPath("/img/marc.jpg"));
                    FileInputStream fis = new FileInputStream(img);
                    dto.getEntidad().setImagen(fis.readAllBytes());
                }
                dao.create(dto);
                request.setAttribute("mensaje", "Auditor creado exitosamente");
                Utilerias.enviarEmail(request.getParameter("txtEmail"),"Bienvenido a Escuela Web","Bienvenido a la Escuela Web, tu nombre de usuario es "+request.getParameter("txtNombreAuditor"));
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } catch (IOException | ServletException  ex) {
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }


    private void eliminarAuditor(HttpServletRequest request, HttpServletResponse response) {
        AuditorDAO dao = new AuditorDAO();
        AuditorDTO dto = new AuditorDTO();
        HttpSession session = request.getSession();
        dto.getEntidad().setIdAuditor(Integer.parseInt(request.getParameter("id")));
        try{
            dto = dao.read(dto);
            dao.delete(dto);
            request.setAttribute("mensaje","Auditor eliminado satisfactoriamente");
            if(dto.getEntidad().getNombreAuditor().equals(session.getAttribute("nombreAuditor"))){
                session.invalidate();
                response.sendRedirect("index.jsp");
            }else{
                listaAuditores(request,response);
            }
            
        }catch(IOException ex){
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void actualizarAuditor(HttpServletRequest request, HttpServletResponse response) {
        AuditorDAO dao = new AuditorDAO();
        AuditorDTO dto = new AuditorDTO();
        dto.getEntidad().setIdAuditor(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.read(dto);
            request.setAttribute("usuario", dto);
            RequestDispatcher vista = request.getRequestDispatcher("usuarioForm.jsp");
            vista.forward(request,response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
