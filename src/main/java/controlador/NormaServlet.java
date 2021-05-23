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
import modelo.dao.NormaDAO;
import modelo.dto.AuditorDTO;
import modelo.dto.NormaDTO;

@WebServlet(name = "NormaServlet", urlPatterns = {"/NormaServlet"})
public class NormaServlet extends HttpServlet {

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
            almacenarNorma(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarNorma(request, response);
        }else if (accion.equals("listaNormas")) {
            listaNormas(request, response);
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

    private void almacenarNorma(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            NormaDAO dao = new NormaDAO();
            NormaDTO dto = new NormaDTO();
            
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();

            AuditorDTO adto = new AuditorDTO();
            adto.getEntidad().setCorreo(correo);
            dto.getEntidad().setClave(request.getParameter("txtClave"));
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setAuditor(adto.getEntidad());
            dao.create(dto);
            
            System.out.println("Creado->" + dto.toString());
            request.setAttribute("mensaje", "Norma registrada exitosamente");
            getServletContext().getRequestDispatcher("/NormaServlet?accion=listaNormas").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarNorma(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            NormaDAO dao = new NormaDAO();
            NormaDTO dto = new NormaDTO();

            String idNorma = request.getParameter("id");
            dto.getEntidad().setId(Integer.parseInt(idNorma));
            dto = dao.read(dto);
            if( dto != null ){
                System.out.println("Norma a Eliminar" + dto.toString());
                if( dao.delete(dto) ){
                    request.setAttribute("mensaje", "Norma eliminada exitosamente");
                    getServletContext().getRequestDispatcher("/NormaServlet?accion=listaNormas").forward(request, response);
                }else{
                    request.setAttribute("mensaje", "Fallo al eliminar");
                    getServletContext().getRequestDispatcher("/NormaServlet?accion=listaNormas").forward(request, response);                }
            }else{
                System.out.println("Norma no encontrada");
                request.setAttribute("mensaje", "Fallo al eliminar");
                getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
            }     
            
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listaNormas(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        NormaDAO dao = new NormaDAO();
        AuditorDTO adto = new AuditorDTO();
        adto.getEntidad().setCorreo(session.getAttribute("CorreoAuditor").toString());
        try {
            ArrayList<NormaDTO> lista = dao.readAllAuditor(adto);
            for (NormaDTO empresaDTO : lista) {
                System.out.println(empresaDTO);
            }
            request.setAttribute("listaDeNormas", lista);
            RequestDispatcher vista = request.getRequestDispatcher("listaDeNormas.jsp");
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(NormaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
