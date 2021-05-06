package controlador;
import modelo.dao.AuditorDAO;
import modelo.dto.AuditorDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AuditorServlet", urlPatterns = {"/AuditorServlet"})
public class AuditorServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
      
        String accion = request.getParameter("accion");

        if(accion.equals("Login"))
            login(request,response);
        else if(accion.equals("Logout"))
            logout(request,response);    
        else if(accion.equals("Almacenar"))
            almacenarAuditor(request,response);
        else if(accion.equals("Eliminar"))
            eliminarAuditor(request,response);
        else if(accion.equals("InfoAuditor"))
            infoAuditor(request,response);
        
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

    private void listaAuditorias(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Se inicio sesion :3");
        //AuditoriaDAO dao = new AuditoriaDAO();
        //try {
            //Collection lista = dao.readAllAuditor(request.getParameter(auditor));
            //request.setAttribute("listaAuditorias",lista);
            //RequestDispatcher vista = request.getRequestDispatcher("listaAuditorias.jsp");
            //vista.forward(request, response);
        //} catch (ServletException | IOException  ex) {
           //Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        //}   
    }
    
    private void almacenarAuditor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            AuditorDAO dao = new AuditorDAO();
            AuditorDTO dto = new AuditorDTO();
            //Si existe el parámetro new se crea, sino se actualiza
            if(request.getParameter("update")!=null&&!request.getParameter("update").isEmpty()){
                dto.getEntidad().setCorreo(request.getParameter("txtCorreo"));
                dto.getEntidad().setNombre(request.getParameter("txtNombre"));
                dto.getEntidad().setPswd(request.getParameter("txtPswd"));
                dto.getEntidad().setTelefono(request.getParameter("txtTelefono"));
                dao.update(dto);
                request.setAttribute("mensaje", "Auditor actualizado exitosamente");
                getServletContext().getRequestDispatcher("/AuditorServlet?Inicio").forward(request, response);
            }else{
                dto.getEntidad().setCorreo(request.getParameter("txtCorreo"));
                dto.getEntidad().setNombre(request.getParameter("txtNombre"));
                dto.getEntidad().setPswd(request.getParameter("txtPswd"));
                dto.getEntidad().setTelefono(request.getParameter("txtTelefono"));
                dao.create(dto);
                request.setAttribute("mensaje", "Auditor creado exitosamente");
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
        dto.getEntidad().setCorreo(request.getParameter("txtCorreo"));
        dto = dao.read(dto);
        dao.delete(dto);
        request.setAttribute("mensaje","Auditor eliminado satisfactoriamente");
        if(dto.getEntidad().getCorreo().equals(session.getAttribute("txtCorreo"))){
            logout(request,response);
        }else{
            listaAuditorias(request,response);
        }
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");
        try {
            AuditorDTO dto = new AuditorDTO();
            AuditorDAO dao = new AuditorDAO();
            dto.getEntidad().setCorreo(correo);
            dto.getEntidad().setPswd(password);
            String msj = dao.login(dto);
            //Si el correo contraseña son validos
            if (correo.equalsIgnoreCase(msj)) {
                HttpSession session = request.getSession();
                session.setAttribute("CorreoAuditor", msj);       
                response.sendRedirect("AuditorServlet?accion=listaAuditorias");
            } else {
                request.setAttribute("mensaje",msj);
                RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
                vista.forward(request, response);
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            session.removeAttribute("CorreoAuditor");
            if (session.getAttribute("CorreoAuditor") == null) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                session.invalidate();
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Expires", "0");
                response.setDateHeader("Expires", -1);   
                response.sendRedirect("index.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void infoAuditor(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");
        try {
            AuditorDTO dto = new AuditorDTO();
            AuditorDAO dao = new AuditorDAO();
            dto.getEntidad().setCorreo(correo);
            dto = dao.read(dto);
            request.setAttribute("Auditor",dto);
            RequestDispatcher vista = request.getRequestDispatcher("infoAuditor.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
