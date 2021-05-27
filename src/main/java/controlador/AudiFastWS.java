package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.dao.AuditorAuxiliarDAO;
import modelo.dao.AuditorDAO;
import modelo.dao.AuditoriaDAO;
import modelo.dao.ContactoAuditoriaDAO;
import modelo.dao.ProcesoActaDAO;
import modelo.dao.ProcesoDAO;
import modelo.dao.ProductoDAO;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditorDTO;
import modelo.dto.AuditoriaDTO;
import modelo.dto.ContactoAuditoriaDTO;
import modelo.dto.ProcesoActaDTO;
import modelo.dto.ProcesoDTO;
import modelo.dto.ProductoDTO;

@WebService(serviceName = "AudiFastWS")
public class AudiFastWS {

    @WebMethod(operationName = "iniciarSesion")
    public String iniciarSesion(@WebParam(name = "correo") String correo,@WebParam(name = "password") String password) {
        AuditorDTO dto = new AuditorDTO();
        AuditorDAO dao = new AuditorDAO();
        dto.getEntidad().setCorreo(correo);
        dto.getEntidad().setPswd(password);
        String msj = dao.login(dto);
        //Si el correo contraseña son validos
        if (correo.equalsIgnoreCase(msj)) {
            return msj;
        } else {
            return "INVALID";
        }
    }
    
    @WebMethod(operationName = "listasAuditorias")
    public String listasAuditorias(@WebParam(name = "correo") String correo) {
        AuditorDTO dto = new AuditorDTO();
        dto.getEntidad().setCorreo(correo);
        
        AuditoriaDAO dao = new AuditoriaDAO();
        
        ArrayList<AuditoriaDTO> listaByAuditor = dao.readAllByAuditor(dto);
        ArrayList<AuditoriaDTO> listaWithAuditor = dao.readAllWithAuditor(dto);

        String jsonResp = "{\"listaLideradas\":{";
        
        for (AuditoriaDTO adto : listaByAuditor) {
            jsonResp += "\""+adto.getEntidad().getId()+"\":{";
            jsonResp += "\"fecha_registro\":\"" + adto.getEntidad().fecha()+"\",";
             jsonResp += "\"organizacion\":\""+adto.getEntidad().getOrganizacion().getNombre()+"\"},";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);   
        jsonResp+="},\"listaAuxiliadas\":{";
       
        for(AuditoriaDTO adto : listaWithAuditor){
            jsonResp += "\""+adto.getEntidad().getId()+"\":{";
            jsonResp += "\"correo_auditor_lider\":\"" + adto.getEntidad().getCorreo_auditor_lider()+"\",";
            jsonResp += "\"fecha_registro\":\"" + adto.getEntidad().fecha()+"\",";
            jsonResp += "\"organizacion\":\""+adto.getEntidad().getOrganizacion().getNombre()+"\"},";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp += "}}";
        return jsonResp;
    }
    
    @WebMethod(operationName = "infoAuditoria")
    public String infoAuditoria(@WebParam(name = "id") int id) {
            
        AuditoriaDTO dto = new AuditoriaDTO();
        AuditoriaDAO dao = new AuditoriaDAO();

        AuditorDTO dtoLider = new AuditorDTO();
        AuditorDAO daoLider = new AuditorDAO();

        ProcesoActaDTO padto = new ProcesoActaDTO();
        ProcesoActaDAO padao = new ProcesoActaDAO();

        dto.getEntidad().setId(id);
        dto = dao.read(dto);

        String jsonResp = "{\"organizacion\":{";
        jsonResp += "\"rfc\":\""+dto.getEntidad().getOrganizacion().getId().getRfc()+"\",";
        jsonResp += "\"nombre\":\""+dto.getEntidad().getOrganizacion().getNombre()+"\",";
        jsonResp += "\"giro\":\""+dto.getEntidad().getOrganizacion().getGiro()+"\",";
        jsonResp += "\"direccion_operacion\":\""+dto.getEntidad().getOrganizacion().getDireccionO()+"\",";
        jsonResp += "\"direccion_fiscal\":\""+dto.getEntidad().getOrganizacion().getDireccionF()+"\"},";
        
        dtoLider.getEntidad().setCorreo(dto.getEntidad().getCorreo_auditor_lider());
        dtoLider = daoLider.read(dtoLider);
        
        jsonResp += "{\"auditorLider\":{";
        jsonResp += "\"correo\":\""+dtoLider.getEntidad().getCorreo()+"\",";
        jsonResp += "\"nombre\":\""+dtoLider.getEntidad().getNombre()+"\",";
        jsonResp += "\"telefono\":\""+dtoLider.getEntidad().getTelefono()+"\"},";
        
        
        AuditorAuxiliarDAO daoAuxiliar = new AuditorAuxiliarDAO();
        List<AuditorAuxiliarDTO> listaAuxiliares = daoAuxiliar.readAll(id);

        jsonResp += "{\"auditoresAuxiliares\":{";
        
        ArrayList<AuditorDTO> dtosAuditores = new ArrayList<>();
        for (AuditorAuxiliarDTO axdto : listaAuxiliares) {
            AuditorDTO auxdto = new AuditorDTO();
            AuditorDAO auxdao = new AuditorDAO();
            auxdto.getEntidad().setCorreo(axdto.getEntidad().getId().getCorreo());
            auxdto = auxdao.read(auxdto);
            jsonResp += "\""+auxdto.getEntidad().getCorreo()+"\":{";
            jsonResp += "\"nombre\":\"" + auxdto.getEntidad().getNombre()+"\",";
            jsonResp += "\"telefono\":\""+auxdto.getEntidad().getTelefono()+"\"},";
        }
        
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp+="},\"contactos\":{";
        
        ContactoAuditoriaDAO daoContacto = new ContactoAuditoriaDAO();
        List<ContactoAuditoriaDTO> listaContactos = daoContacto.readAll(id);

        for (ContactoAuditoriaDTO cdto : listaContactos) {
            jsonResp += "\""+cdto.getEntidad().getId().getCorreo()+"\":{";
            jsonResp += "\"nombre\":\"" + cdto.getEntidad().getNombre()+"\",";
            jsonResp += "\"puesto\":\"" + cdto.getEntidad().getPuesto()+"\",";
            jsonResp += "\"telefono\":\""+cdto.getEntidad().getTelefono()+"\"},";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp+="},\"productos\":{";
        
        ProductoDAO daoProducto = new ProductoDAO();
        List<ProductoDTO> listaProductos = daoProducto.readAll(id);
        
        for (ProductoDTO prdto : listaProductos) {
            jsonResp += "\""+prdto.getEntidad().getId().getClave()+"\":{";
            jsonResp += "\"nombre\":\"" + prdto.getEntidad().getDescripcion()+"\"},";
        }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp+="},\"procesosActa\":{";

        padto.getEntidad().setAuditoria(dto.getEntidad());
        List<ProcesoActaDTO> listaProcesoActa = padao.ProcesosActa(padto);
        ProcesoDAO pdao = new ProcesoDAO();

        if(!listaProcesoActa.isEmpty())
            for(ProcesoActaDTO padtoA: listaProcesoActa){
                ProcesoDTO pdto = new ProcesoDTO();
                pdto.getEntidad().setId(padtoA.getEntidad().getProceso().getId());
                pdto = pdao.read(pdto);
                jsonResp += "\""+pdto.getEntidad().getId()+"\":{";
                jsonResp += "\"descripcion\":\"" + pdto.getEntidad().getDescripcion()+"\",";
                jsonResp += "\"ponderacion\":\"" + padtoA.getEntidad().getPonderacion()+"\",";
                jsonResp += "\"encargado\":\""+padtoA.getEntidad().getAuditor().getNombre()+"\"},";
                //añadir listaProcesos a json
            }
        jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        jsonResp+="}}";
        return jsonResp;
    }
    

        
   
}
