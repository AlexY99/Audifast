package controlador;

import modelo.dto.AuditoriaDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.AuditorAuxiliarDAO;
import modelo.dao.AuditorDAO;
import modelo.dao.AuditoriaDAO;
import modelo.dao.ContactoAuditoriaDAO;
import modelo.dao.ProductoDAO;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditorDTO;
import modelo.dto.ContactoAuditoriaDTO;
import modelo.dto.ProductoDTO;
import modelo.entidades.Auditoria;

public class AuditoriaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String accion = request.getParameter("accion");

        if (accion.equals("Almacenar")) {
            almacenarAuditoria(request, response);
        } else if (accion.equals("Cancelar")) {
            cancelarAuditoria(request, response);
        } else if (accion.equals("Info")) {
            infoAuditoria(request, response);
        } else if (accion.equals("AlmacenarAuditorAuxiliar")) {
            AlmacenarAuditorAuxiliar(request, response);
        } else if (accion.equals("AlmacenarContacto")) {
            AlmacenarContacto(request, response);
        } else if (accion.equals("AlmacenarProducto")) {
            AlmacenarProducto(request, response);
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

    private void almacenarAuditoria(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            AuditoriaDAO dao = new AuditoriaDAO();
            AuditoriaDTO dto = new AuditoriaDTO();
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();
            dto.getEntidad().setCorreo_auditor_lider(correo);
            dto.getEntidad().setRfc_organizacion(request.getParameter("txtRFC"));
            dto.getEntidad().setFecha_registro(new Date());
            dao.create(dto);
            System.out.println("Creado->" + dto.toString());
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

            dto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
            dto = dao.read(dto);
            dao.delete(dto);
            request.setAttribute("mensaje", "Auditoría cancelada satisfactoriamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Inicio").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void infoAuditoria(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if( id == null)
            id =(String) request.getAttribute("id");
        HttpSession session = request.getSession();
        String correoAuditor = session.getAttribute("CorreoAuditor").toString();
        try {
            AuditorAuxiliarDAO daoAuxiliar = new AuditorAuxiliarDAO();
            AuditoriaDTO dto = new AuditoriaDTO();
            AuditoriaDAO dao = new AuditoriaDAO();

            AuditorDTO adto = new AuditorDTO();
            AuditorDTO adto1 = new AuditorDTO();
            AuditorDAO adao = new AuditorDAO();

            adto1.getEntidad().setCorreo(correoAuditor);
            adto1 = adao.read(adto1);

            dto.getEntidad().setId(Integer.parseInt(id));
            dto = dao.read(dto);
            
            List<AuditorAuxiliarDTO> dtos = null;
            dtos = daoAuxiliar.readAll(Integer.parseInt(id));
            ArrayList<AuditorDTO> dtosAuditores = new ArrayList<>();
            for (AuditorAuxiliarDTO axdto : dtos) {
                adto = new AuditorDTO();
                adto.getEntidad().setCorreo(axdto.getEntidad().getId().getCorreo());
                adto = adao.read(adto);
                System.out.println(adto);
                dtosAuditores.add(new AuditorDTO(adto.getEntidad()));
            }
            
            ContactoAuditoriaDAO daoContacto = new ContactoAuditoriaDAO();
            List<ContactoAuditoriaDTO> listaContactos = null;
            listaContactos = daoContacto.readAll(Integer.parseInt(id));
            
            ProductoDAO daoProducto = new ProductoDAO();
            List<ProductoDTO> listaProductos = null;
            listaProductos = daoProducto.readAll(Integer.parseInt(id));
                  
            request.setAttribute("listaProductos", listaProductos);
            request.setAttribute("listaContactos", listaContactos);
            request.setAttribute("auditoria", dto);
            request.setAttribute("auditor", adto1);
            request.setAttribute("auditoresAuxiliares", dtosAuditores);

            RequestDispatcher vista = request.getRequestDispatcher("auditoria.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AuditoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AlmacenarAuditorAuxiliar(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        String correoAuditorAux = request.getParameter("txtEmailAuditorAux");
        AuditorAuxiliarDTO dto = new AuditorAuxiliarDTO();
        AuditorAuxiliarDAO dao = new AuditorAuxiliarDAO();
        dto.getEntidad().getId().setCorreo(correoAuditorAux);
        dto.getEntidad().getId().setIdAuditoria(idAuditoria);
        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();
        AuditoriaDAO auDAO = new AuditoriaDAO();
        auditoriaDTO.getEntidad().setId(idAuditoria);
        auditoriaDTO = auDAO.read(auditoriaDTO);
        dto.getEntidad().setAuditoria(auditoriaDTO.getEntidad());
        System.out.println(dto);
        dao.create(dto);
        System.out.println("Creado->" + dto.toString());
        request.setAttribute("mensaje", "Auditor auxiliar registrado exitosamente");
        request.setAttribute("id", String.valueOf(idAuditoria));
        infoAuditoria(request, response);

    }

    private void AlmacenarContacto(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        String correo = request.getParameter("txtEmail");
        String nombre = request.getParameter("txtNombreContacto");
        String puesto = request.getParameter("txtPuesto");
        String telefono = request.getParameter("txtTelefono");
        ContactoAuditoriaDTO dto = new ContactoAuditoriaDTO();
        ContactoAuditoriaDAO dao = new ContactoAuditoriaDAO();
        dto.getEntidad().getId().setCorreo(correo);
        dto.getEntidad().setNombre(nombre);
        dto.getEntidad().setPuesto(puesto);
        dto.getEntidad().setTelefono(telefono);
        dto.getEntidad().getId().setIdAuditoria(idAuditoria);
        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();
        AuditoriaDAO auDAO = new AuditoriaDAO();
        auditoriaDTO.getEntidad().setId(idAuditoria);
        auditoriaDTO = auDAO.read(auditoriaDTO);
        dto.getEntidad().setAuditoria(auditoriaDTO.getEntidad());
        System.out.println(dto);
        dao.create(dto);
        System.out.println("Creado->" + dto.toString());
        request.setAttribute("mensaje", "Contacto de auditoria registrado exitosamente");
        request.setAttribute("id", String.valueOf(idAuditoria));
        infoAuditoria(request, response);
    }

    private void AlmacenarProducto(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        String clave = request.getParameter("txtClaveProducto");
        String descripcion = request.getParameter("txtDescripcion");
        ProductoDTO dto = new ProductoDTO();
        ProductoDAO dao = new ProductoDAO();
        dto.getEntidad().getId().setClave(clave);
        dto.getEntidad().setDescripcion(descripcion);
        dto.getEntidad().getId().setIdAuditoria(idAuditoria);
        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();
        AuditoriaDAO auDAO = new AuditoriaDAO();
        auditoriaDTO.getEntidad().setId(idAuditoria);
        auditoriaDTO = auDAO.read(auditoriaDTO);
        dto.getEntidad().setAuditoria(auditoriaDTO.getEntidad());
        System.out.println(dto);
        dao.create(dto);
        System.out.println("Creado->" + dto.toString());
        request.setAttribute("mensaje", "Producto de auditoria creado con exito");
    }

}
