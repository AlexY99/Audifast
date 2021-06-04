package controlador;

import modelo.dto.PlantillaAuditorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import modelo.dao.NormaDAO;
import modelo.dao.PlantillaAuditorDAO;
import modelo.dao.ProcesoDAO;
import modelo.dao.RequisitoDAO;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditorDTO;
import modelo.dto.NormaDTO;
import modelo.dto.ProcesoDTO;
import modelo.dto.RequisitoDTO;

public class PlantillaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");        
        String accion = request.getParameter("accion");

        if (accion.equals("Almacenar")) 
            almacenarPlantilla(request, response);
        else if (accion.equals("Eliminar")) 
            eliminarPlantilla(request, response);
        else if (accion.equals("Editar")) 
            editarPlantilla(request, response);
        else if (accion.equals("AlmacenarProceso")) 
            almacenarProceso(request, response);
        else if (accion.equals("AlmacenarRequisito")) 
            almacenarRequisito(request, response);      
        else if (accion.equals("EliminarProceso")) 
            eliminarProceso(request, response);
        else if (accion.equals("EliminarRequisito")) 
            eliminarRequisito(request, response);   
        else if (accion.equals("getProcesosPlantilla")) 
            getProcesosPlantilla(request, response);   
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

    private void almacenarPlantilla(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            
            HttpSession session = request.getSession();
            String correo = session.getAttribute("CorreoAuditor").toString();
            
            PlantillaAuditorDAO dao = new PlantillaAuditorDAO();
            PlantillaAuditorDTO dto = new PlantillaAuditorDTO();
            
            AuditorDTO adto = new AuditorDTO();
            AuditorDAO adao = new AuditorDAO();
            adto.getEntidad().setCorreo(correo);
            adto = adao.read(adto);
            
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setAuditor(adto.getEntidad());
            dao.create(dto);
            
            System.out.println("Creado->" + dto.toString());
            request.setAttribute("mensaje", "Plantilla creada exitosamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Plantillas").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(PlantillaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarPlantilla(HttpServletRequest request, HttpServletResponse response) {
        try {
            PlantillaAuditorDAO dao = new PlantillaAuditorDAO();
            PlantillaAuditorDTO dto = new PlantillaAuditorDTO();

            dto.getEntidad().setId(Integer.parseInt(request.getParameter("id")));
            dto = dao.read(dto);
            dao.delete(dto);
            request.setAttribute("mensaje", "Plantilla eliminada satisfactoriamente");
            getServletContext().getRequestDispatcher("/AuditorServlet?accion=Plantillas").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(PlantillaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editarPlantilla(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        if(id == null)
            id =(String) request.getAttribute("id");
        try {
            PlantillaAuditorDTO dto = new PlantillaAuditorDTO();
            PlantillaAuditorDAO dao = new PlantillaAuditorDAO();

            dto.getEntidad().setId(Integer.parseInt(id));
            dto = dao.read(dto);
            
            ProcesoDAO pdao = new ProcesoDAO();
            ArrayList<ProcesoDTO> procesos = pdao.readAllPlantilla(dto);
            
            NormaDAO ndao = new NormaDAO();
            
            AuditorDTO adto = new AuditorDTO();
            adto.getEntidad().setCorreo(session.getAttribute("CorreoAuditor").toString());
            
            ArrayList<NormaDTO> normas = ndao.readAllAuditor(adto);
            
            request.setAttribute("plantilla",dto);
            request.setAttribute("listaProcesos", procesos);
            request.setAttribute("listaNormas", normas);
            request.setAttribute("numProcesos",procesos.size());
            
            
            RequestDispatcher vista = request.getRequestDispatcher("plantilla.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(PlantillaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarProceso(HttpServletRequest request, HttpServletResponse response) {
        String nombre = request.getParameter("txtProcesoNombre");
        String idPlantilla = request.getParameter("idPlantilla");
        ProcesoDTO dto = new ProcesoDTO();
        ProcesoDAO dao = new ProcesoDAO();
        
        PlantillaAuditorDTO auxDTO = new PlantillaAuditorDTO();
        auxDTO.getEntidad().setId(Integer.parseInt(idPlantilla));
        dto.getEntidad().setPlantilla(auxDTO.getEntidad());
        dto.getEntidad().setDescripcion(nombre);
        dao.create(dto);
        
        System.out.println("Creado->" + dto.toString());
        request.setAttribute("id",idPlantilla);
        editarPlantilla(request, response);
    }


    private void eliminarProceso(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("idProceso");
        String idPlantilla = request.getParameter("idPlantilla");
        ProcesoDTO dto = new ProcesoDTO();
        ProcesoDAO dao = new ProcesoDAO();
        
        dto.getEntidad().setId(Integer.parseInt(id));
        dao.delete(dto);
        
        request.setAttribute("mensaje", "Proceso Eliminado");
        request.setAttribute("id", idPlantilla);
        editarPlantilla(request, response);
    }
    
    private void almacenarRequisito(HttpServletRequest request, HttpServletResponse response) {
        String idPlantilla = request.getParameter("idPlantilla");
        String claveNorma = request.getParameter("txtClaveNorma");
        String descripcion = request.getParameter("txtDescripcion");
        String idProceso = request.getParameter("idProceso");
        RequisitoDTO dto = new RequisitoDTO();
        RequisitoDAO dao = new RequisitoDAO();
        
        ProcesoDTO auxpDTO = new ProcesoDTO();
        auxpDTO.getEntidad().setId(Integer.parseInt(idProceso));
        
        dto.getEntidad().setClave_norma(claveNorma);
        dto.getEntidad().setProceso(auxpDTO.getEntidad());
        dto.getEntidad().setDescripcion(descripcion);
        
        dao.create(dto);
        
        System.out.println("Creado->" + dto.toString());
        request.setAttribute("id",idPlantilla);
        editarPlantilla(request, response);
    }


    private void eliminarRequisito(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("idRequisito");
        String idPlantilla = request.getParameter("idPlantilla");

        RequisitoDTO dto = new RequisitoDTO();
        RequisitoDAO dao = new RequisitoDAO();
        
        dto.getEntidad().setId(Integer.parseInt(id));
        dao.delete(dto);
        
        request.setAttribute("mensaje", "Requisito Eliminado");
        request.setAttribute("id",idPlantilla);
        editarPlantilla(request, response);
    }

    private void getProcesosPlantilla(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        
        String idAuditoria = request.getParameter("idAuditoria");
        String idPlantilla = request.getParameter("idPlantilla");
        
        PlantillaAuditorDTO dto = new PlantillaAuditorDTO();
        dto.getEntidad().setId(Integer.parseInt(idPlantilla));
        
        ProcesoDAO pdao = new ProcesoDAO();
        ArrayList<ProcesoDTO> procesos = pdao.readAllPlantilla(dto);
        
        AuditorAuxiliarDAO daoAuxiliar = new AuditorAuxiliarDAO();
        List<AuditorAuxiliarDTO> dtos = daoAuxiliar.readAll(Integer.parseInt(idAuditoria));

        AuditorDTO adto = new AuditorDTO();
        AuditorDAO adao = new AuditorDAO();
        
        adto.getEntidad().setCorreo(session.getAttribute("CorreoAuditor").toString());
        adto = adao.read(adto);
        
        String jsonResp = "{\"auditores\":{\"";
        
        jsonResp += adto.getEntidad().getCorreo()+"\":\""+adto.getEntidad().getNombre()+"\",";
        
        for (AuditorAuxiliarDTO axdto : dtos) {
            jsonResp += "\"";
            adto = new AuditorDTO();
            adto.getEntidad().setCorreo(axdto.getEntidad().getId().getCorreo());
            adto = adao.read(adto);
            jsonResp += adto.getEntidad().getCorreo()+"\":\""+adto.getEntidad().getNombre()+"\",";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);   
        jsonResp+="},\"procesos\":{";
       
        for(ProcesoDTO pdto : procesos){
            jsonResp += "\"";
            jsonResp+=pdto.getEntidad().getId()+"\":\""+pdto.getEntidad().getDescripcion()+"\",";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp += "}}";
        //mandarlista de auditores auxiliares de la auditoria
        
        System.out.println(jsonResp);
        
        response.setContentType("text/plain");
        response.setStatus(200);
        PrintWriter out = response.getWriter();

        out.println(jsonResp);
        out.flush();
        out.close();

    }
    
}
