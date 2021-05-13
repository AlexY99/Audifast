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
import modelo.dao.EmpresaDAO;
import modelo.dto.EmpresaDTO;
import modelo.entidades.IdOrganizacion;

@WebServlet(name = "EmpresaServlet", urlPatterns = {"/EmpresaServlet"})
public class EmpresaServlet extends HttpServlet {

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

        if (accion.equals("almacenar")) {
            almacenarEmpresa(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarEmpresa(request, response);
        }else if (accion.equals("listaEmpresas")) {
            listaEmpresas(request, response);
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

    private void almacenarEmpresa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            EmpresaDAO dao = new EmpresaDAO();
            EmpresaDTO dto = new EmpresaDTO();
            IdOrganizacion id = new IdOrganizacion();
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();
            id.setCorreo(correo);
            id.setRfc(request.getParameter("txtRFC"));
            dto.getEntidad().setId(id);
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setGiro(request.getParameter("txtGiro"));
            dto.getEntidad().setDireccionF(request.getParameter("txtDireccionF"));
            dto.getEntidad().setDireccionO(request.getParameter("txtDireccionOp"));
            dao.create(dto);
            
            System.out.println("Creado->" + dto.toString());
            request.setAttribute("mensaje", "Empresa registrada exitosamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarEmpresa(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            EmpresaDAO dao = new EmpresaDAO();
            EmpresaDTO dto = new EmpresaDTO();
            IdOrganizacion id = new IdOrganizacion();
            //Si existe el parámetro new se crea, sino se actualiza
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();
            id.setCorreo(correo);
            id.setRfc(request.getParameter("rfc"));
            dto.getEntidad().setId(id);
            dto = dao.read(dto);
            System.out.println(dto);
            if( dto != null ){
                System.out.println("Empresa a Eliminar" + dto.toString());
                if( dao.delete(dto) ){
                    request.setAttribute("mensaje", "Empresa eliminada exitosamente");
                    getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
                }else{
                    request.setAttribute("mensaje", "Fallo al eliminar");
                    getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
                }
            }else{
                System.out.println("Empresa no encontrada");
                request.setAttribute("mensaje", "Fallo al eliminar");
                getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
            }     
            
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listaEmpresas(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        EmpresaDAO dao = new EmpresaDAO();
        try {
            ArrayList<EmpresaDTO> lista = dao.readAll(session.getAttribute("CorreoAuditor").toString());
            for (EmpresaDTO empresaDTO : lista) {
                System.out.println(empresaDTO);
            }
            request.setAttribute("listaDeEmpresas", lista);
            RequestDispatcher vista = request.getRequestDispatcher("listaDeEmpresas.jsp");
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(EmpresaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
