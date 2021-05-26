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
import modelo.dao.PlantillaAuditorDAO;
import modelo.dao.ProcesoActaDAO;
import modelo.dao.ProcesoDAO;
import modelo.dao.ProductoDAO;
import modelo.dao.RequisitoActaDAO;
import modelo.dao.RequisitoDAO;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditorDTO;
import modelo.dto.ContactoAuditoriaDTO;
import modelo.dto.PlantillaAuditorDTO;
import modelo.dto.ProcesoActaDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.ProcesoDTO;
import modelo.dto.RequisitoActaDTO;
import modelo.dto.RequisitoDTO;
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
        } else if (accion.equals("EliminarAuditorAuxiliar")) {
            EliminarAuditorAuxiliar(request, response);       
        } else if (accion.equals("EliminarContacto")) {
            EliminarContacto(request, response);
        } else if (accion.equals("EliminarProducto")) {
            EliminarProducto(request, response);
        } else if (accion.equals("SeleccionarActa")) {
            SeleccionarActa(request, response);
        } else if (accion.equals("EditarActa")) {
            EditarActa(request, response);
        } else if (accion.equals("EliminarActa")) {
            EliminarActa(request, response);
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
            AuditorDAO adao = new AuditorDAO();
            
            ProcesoActaDTO padto = new ProcesoActaDTO();
            ProcesoActaDAO padao = new ProcesoActaDAO();

            adto.getEntidad().setCorreo(correoAuditor);
            adto = adao.read(adto);
            request.setAttribute("auditor", adto);

            dto.getEntidad().setId(Integer.parseInt(id));
            dto = dao.read(dto);
            
            padto.getEntidad().setAuditoria(dto.getEntidad());
            List<ProcesoActaDTO> listaProcesoActa = padao.ProcesosActa(padto);
            ArrayList<ProcesoDTO> listaProcesos = new ArrayList<>();
            ProcesoDAO pdao = new ProcesoDAO();
            ProcesoDTO pdto ;
          
            if(!listaProcesoActa.isEmpty())
                for(ProcesoActaDTO padtoA: listaProcesoActa){
                    pdto = new ProcesoDTO();
                    pdto.getEntidad().setId(padtoA.getEntidad().getProceso().getId());
                    listaProcesos.add(pdao.read(pdto));
            }        
            List<AuditorAuxiliarDTO> dtos = null;
            dtos = daoAuxiliar.readAll(Integer.parseInt(id));
            
            PlantillaAuditorDAO pldao = new PlantillaAuditorDAO();
            
            ArrayList<PlantillaAuditorDTO> listaPlantillas = pldao.readAll(correoAuditor);
            
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
                  
            request.setAttribute("listaProcesoActa",listaProcesoActa);
            request.setAttribute("listaProcesos",listaProcesos);
            request.setAttribute("listaMisPlantillas",listaPlantillas);
            request.setAttribute("listaProductos", listaProductos);
            request.setAttribute("listaContactos", listaContactos);
            request.setAttribute("auditoria", dto);
            
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
        dto.getEntidad().getId().setCorreo_auditor(correoAuditorAux);
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
        request.setAttribute("id", String.valueOf(idAuditoria));
        infoAuditoria(request, response);
    }

    private void EliminarAuditorAuxiliar(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("txtCorreo");
        String id = request.getParameter("txtIdAuditoria");
        AuditorAuxiliarDAO dao = new AuditorAuxiliarDAO();
        AuditorAuxiliarDTO dto = new AuditorAuxiliarDTO();
        dto.getEntidad().getId().setCorreo_auditor(correo);
        dto.getEntidad().getId().setIdAuditoria(Integer.parseInt(id));
        dao.delete(dto);
        request.setAttribute("mensaje", "Auditor Auxiliar Eliminado");
        request.setAttribute("id", String.valueOf(id));
        infoAuditoria(request, response);
    }

    private void EliminarContacto(HttpServletRequest request, HttpServletResponse response) {
        String correo = request.getParameter("txtCorreo");
        String id = request.getParameter("txtIdAuditoria");
        ContactoAuditoriaDAO dao = new ContactoAuditoriaDAO();
        ContactoAuditoriaDTO dto = new ContactoAuditoriaDTO();
        dto.getEntidad().getId().setCorreo(correo);
        dto.getEntidad().getId().setIdAuditoria(Integer.parseInt(id));
        dao.delete(dto);
        request.setAttribute("mensaje", "Contacto Eliminado");
        request.setAttribute("id", String.valueOf(id));
        infoAuditoria(request, response);
    }

    private void EliminarProducto(HttpServletRequest request, HttpServletResponse response) {
        String clave = request.getParameter("txtClave");
        String id = request.getParameter("txtIdAuditoria");
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().getId().setClave(clave);
        dto.getEntidad().getId().setIdAuditoria(Integer.parseInt(id));
        dao.delete(dto);
        request.setAttribute("mensaje", "Producto Eliminado");
        request.setAttribute("id", String.valueOf(id));
        infoAuditoria(request, response);
    }

    private void SeleccionarActa(HttpServletRequest request, HttpServletResponse response) {
        int idPlantilla = Integer.parseInt(request.getParameter("selectedPlantilla"));
        PlantillaAuditorDTO plantillaDTO = new PlantillaAuditorDTO();
        PlantillaAuditorDAO plantillaDAO = new PlantillaAuditorDAO();
        plantillaDTO.getEntidad().setId(idPlantilla);
        plantillaDTO = plantillaDAO.read(plantillaDTO);
        ProcesoDAO pdao = new ProcesoDAO();
        ArrayList<ProcesoDTO> procesos = pdao.readAllPlantilla(plantillaDTO);
        int nProcesos = procesos.size();
        ProcesoActaDTO dto;
        ProcesoActaDAO dao = new ProcesoActaDAO();
        AuditorDAO Adao = new AuditorDAO();
        AuditorDTO Adto = new AuditorDTO();
        AuditoriaDAO Audao = new AuditoriaDAO();
        AuditoriaDTO Audto = new AuditoriaDTO();
        RequisitoActaDAO RAdao = new RequisitoActaDAO();
        RequisitoActaDTO RAdto = new RequisitoActaDTO();
        RequisitoDAO Rdao = new RequisitoDAO();
        List <RequisitoDTO> requisitos = null;
        int id, idA;
        idA = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        for(int i = 0; i < nProcesos; i++){
            dto = new ProcesoActaDTO();
            id = procesos.get(i).getEntidad().getId();
            Adto.getEntidad().setCorreo(request.getParameter("txtCorreoEncargadoProceso"+Integer.toString(id)));
            Adto = Adao.read(Adto);
            Audto.getEntidad().setId(idA);
            Audto = Audao.read(Audto);
            dto.getEntidad().setAuditor(Adto.getEntidad());
            dto.getEntidad().setProceso(procesos.get(i).getEntidad());
            dto.getEntidad().setAuditoria(Audto.getEntidad());
            dto.getEntidad().setPonderacion(Float.valueOf(request.getParameter("txtPonderacionProceso"+Integer.toString(id))));
            dto.getEntidad().setResultado(0);
            dao.create(dto);
            System.out.println(dto);
            requisitos = Rdao.Requisitos(id);
            
            for(RequisitoDTO r : requisitos){
                RAdto = new RequisitoActaDTO();
                RAdto.getEntidad().setProcesoActa(dto.getEntidad());
                RAdto.getEntidad().setRequisito(r.getEntidad());
                RAdao.create(RAdto);
                System.out.println(RAdto);
            }
        }
        request.setAttribute("mensaje", "Acta Creada");
        request.setAttribute("id", String.valueOf(idA));
        infoAuditoria(request, response);
    }

    private void EditarActa(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        int idP;
        float ponderacion;
        ProcesoActaDAO padao = new ProcesoActaDAO();
        ProcesoActaDTO padto = new ProcesoActaDTO();
        padto.getEntidad().setAuditoria(new Auditoria());
        padto.getEntidad().getAuditoria().setId(idAuditoria);
        List<ProcesoActaDTO> listaProcesoActa = padao.ProcesosActa(padto);
        for( ProcesoActaDTO pa : listaProcesoActa ){
            padto = new ProcesoActaDTO();
            idP = pa.getEntidad().getProceso().getId();
            ponderacion = Float.valueOf(request.getParameter("txtPonderacionProceso"+Integer.toString(idP)));
            padto.setEntidad(pa.getEntidad());
            padto.getEntidad().setPonderacion(ponderacion);
            padto.getEntidad().getAuditor().setCorreo(request.getParameter("txtCorreoEncargadoProceso"+Integer.toString(idP)));
            padao.update(padto);
        }
        request.setAttribute("mensaje", "Acta Editada");
        request.setAttribute("id", String.valueOf(idAuditoria));
        infoAuditoria(request, response);
    }

    private void EliminarActa(HttpServletRequest request, HttpServletResponse response) {
        int idAuditoria = Integer.parseInt(request.getParameter("txtIdAuditoria"));
        ProcesoActaDAO padao = new ProcesoActaDAO();
        ProcesoActaDTO padto = new ProcesoActaDTO();
        padto.getEntidad().setAuditoria(new Auditoria());
        padto.getEntidad().getAuditoria().setId(idAuditoria);
        List<ProcesoActaDTO> listaProcesoActa = padao.ProcesosActa(padto);
        for(ProcesoActaDTO dto : listaProcesoActa){
            padao.delete(dto);
        }
        request.setAttribute("mensaje", "Acta Eliminada");
        request.setAttribute("id", String.valueOf(idAuditoria));
        infoAuditoria(request, response);
    }
}
