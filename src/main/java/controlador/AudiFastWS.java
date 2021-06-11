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
import modelo.dao.ProductoDAO;
import modelo.dao.RequisitoActaDAO;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditorDTO;
import modelo.dto.AuditoriaDTO;
import modelo.dto.ContactoAuditoriaDTO;
import modelo.dto.ProcesoActaDTO;
import modelo.dto.ProductoDTO;
import modelo.dto.RequisitoActaDTO;
import org.json.JSONArray;
import org.json.JSONObject;

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

        String jsonResp = "{\"listaLideradas\":[";
        
        if(!listaByAuditor.isEmpty()){
            for (AuditoriaDTO adto : listaByAuditor) {
                jsonResp += "{\"id\":" + adto.getEntidad().getId()+",";
                jsonResp += "\"correo_auditor_lider\":\"" + adto.getEntidad().getCorreo_auditor_lider()+"\",";
                jsonResp += "\"fecha_registro\":\"" + adto.getEntidad().fecha()+"\",";
                jsonResp += "\"organizacion\":\""+adto.getEntidad().getOrganizacion().getNombre()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);   
        }
        jsonResp+="],\"listaAuxiliadas\":[";
       
        if(!listaWithAuditor.isEmpty()){
            for(AuditoriaDTO adto : listaWithAuditor){
                jsonResp += "{\"id\":" + adto.getEntidad().getId()+",";
                jsonResp += "\"correo_auditor_lider\":\"" + adto.getEntidad().getCorreo_auditor_lider()+"\",";
                jsonResp += "\"fecha_registro\":\"" + adto.getEntidad().fecha()+"\",";
                jsonResp += "\"organizacion\":\""+adto.getEntidad().getOrganizacion().getNombre()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp += "]}";
        return jsonResp;
    }
    
    @WebMethod(operationName = "infoAuditoria")
    public String infoAuditoria(@WebParam(name = "id") int id) {
            
        AuditoriaDTO dto = new AuditoriaDTO();
        AuditoriaDAO dao = new AuditoriaDAO();

        AuditorDTO dtoLider = new AuditorDTO();
        AuditorDAO daoLider = new AuditorDAO();

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
        
        jsonResp += "\"auditorLider\":{";
        jsonResp += "\"correo\":\""+dtoLider.getEntidad().getCorreo()+"\",";
        jsonResp += "\"nombre\":\""+dtoLider.getEntidad().getNombre()+"\",";
        jsonResp += "\"telefono\":\""+dtoLider.getEntidad().getTelefono()+"\"}}";

        return jsonResp;
    }
        
    @WebMethod(operationName = "auxiliaresAuditoria")
    public String auxiliaresAuditoria(@WebParam(name = "id") int id) {
            
        AuditorAuxiliarDAO daoAuxiliar = new AuditorAuxiliarDAO();
        List<AuditorAuxiliarDTO> listaAuxiliares = daoAuxiliar.readAll(id);

        String jsonResp = "[";
        
        if(!listaAuxiliares.isEmpty()){
            for (AuditorAuxiliarDTO axdto : listaAuxiliares) {
                AuditorDTO auxdto = new AuditorDTO();
                AuditorDAO auxdao = new AuditorDAO();
                auxdto.getEntidad().setCorreo(axdto.getEntidad().getId().getCorreo());
                auxdto = auxdao.read(auxdto);
                jsonResp += "{\"correo\":\"" + auxdto.getEntidad().getCorreo()+"\",";
                jsonResp += "\"nombre\":\"" + auxdto.getEntidad().getNombre()+"\",";
                jsonResp += "\"telefono\":\""+auxdto.getEntidad().getTelefono()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp+="]";
        return jsonResp;
    }
        
    @WebMethod(operationName = "contactosAuditoria")
    public String contactosAuditoria(@WebParam(name = "id") int id) {
            
        ContactoAuditoriaDAO daoContacto = new ContactoAuditoriaDAO();
        List<ContactoAuditoriaDTO> listaContactos = daoContacto.readAll(id);
        String jsonResp ="[";
  
        if(!listaContactos.isEmpty()){
            for (ContactoAuditoriaDTO cdto : listaContactos) {
                jsonResp += "{\"correo\":\"" +cdto.getEntidad().getId().getCorreo()+"\",";
                jsonResp += "\"nombre\":\"" + cdto.getEntidad().getNombre()+"\",";
                jsonResp += "\"puesto\":\"" + cdto.getEntidad().getPuesto()+"\",";
                jsonResp += "\"telefono\":\""+cdto.getEntidad().getTelefono()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp+="]";
        return jsonResp;
    }
        
    @WebMethod(operationName = "productosAuditoria")
    public String productosAuditoria(@WebParam(name = "id") int id) {
            
        ProductoDAO daoProducto = new ProductoDAO();
        List<ProductoDTO> listaProductos = daoProducto.readAll(id);
        String jsonResp = "[";

        if(!listaProductos.isEmpty()){
            for (ProductoDTO prdto : listaProductos) {
                jsonResp += "{\"clave\":\""+prdto.getEntidad().getId().getClave()+"\",";
                jsonResp += "\"nombre\":\"" + prdto.getEntidad().getDescripcion()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp+="]";
        return jsonResp;
    }
    
    @WebMethod(operationName = "procesosActaAuditoria")
    public String procesosActaAuditoria(@WebParam(name = "id") int id) {
        ProcesoActaDAO padao = new ProcesoActaDAO();
        String jsonResp ="[";

        List<ProcesoActaDTO> listaProcesoActa = padao.ProcesosActa(id);

        if(!listaProcesoActa.isEmpty()){
            for(ProcesoActaDTO padtoA: listaProcesoActa){
                jsonResp += "{\"id\":" + padtoA.getEntidad().getId()+",";
                jsonResp += "\"descripcion\":\"" + padtoA.getEntidad().getProceso().getDescripcion()+"\",";
                jsonResp += "\"ponderacion\":" + padtoA.getEntidad().getPonderacion()+",";
                jsonResp += "\"evaluado\":" + padtoA.getEntidad().getEvaluado()+",";
                jsonResp += "\"resultado\":"+padtoA.getEntidad().getResultado()+",";
                jsonResp += "\"encargado\":\""+padtoA.getEntidad().getAuditor().getNombre()+"\",";
                if(padtoA.getEntidad().getObservaciones()==null)
                    jsonResp += "\"observaciones\":\"\",";
                else
                    jsonResp += "\"observaciones\":\""+padtoA.getEntidad().getObservaciones()+"\",";
                jsonResp += "\"correo_encargado\":\""+padtoA.getEntidad().getAuditor().getCorreo()+"\"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp+="]";
        return jsonResp;
    }
    
    @WebMethod(operationName = "requisitosProcesoActa")
    public String requisitosProcesoActa(@WebParam(name = "id") int id) {
        RequisitoActaDAO radao = new RequisitoActaDAO();
        String jsonResp ="[";

        List<RequisitoActaDTO> listaRequisitosActa = radao.RequisitosActa(id);
            
        if(!listaRequisitosActa.isEmpty()){
            for(RequisitoActaDTO radtoA: listaRequisitosActa){
                jsonResp += "{\"id\":" + radtoA.getEntidad().getId()+",";
                jsonResp += "\"descripcion\":\"" + radtoA.getEntidad().getRequisito().getDescripcion()+"\",";
                jsonResp += "\"clave_norma\":\"" + radtoA.getEntidad().getRequisito().getClave_norma()+"\",";
                jsonResp += "\"cumplimiento\":" + radtoA.getEntidad().getCumplimiento()+"},";
            }
            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
        }
        jsonResp+="]";
        return jsonResp;
    }
    
    //metodo para registro de evaluacion de requisitos de proceso acta y observaciones del proceso acta
    @WebMethod(operationName = "evaluacionProceso")
    public String evaluacionProceso(@WebParam(name = "json") String json) {
        ProcesoActaDTO padto = new ProcesoActaDTO();
        ProcesoActaDAO padao = new ProcesoActaDAO();
        RequisitoActaDTO radto;
        RequisitoActaDAO radao = new RequisitoActaDAO();
         
        JSONObject evaluacion = new JSONObject(json);
        JSONObject procesoEvaluacion = evaluacion.getJSONObject("procesoEvaluacion");
        int idProceso = procesoEvaluacion.getInt("id");
        String observaciones = procesoEvaluacion.getString("observaciones");
        
        JSONArray requisitosEvaluacion = evaluacion.getJSONArray("requisitosEvaluacion");
        int puntosTotales = requisitosEvaluacion.length()*2;
        int puntosObtenidos = 0;
        
        for (int i = 0; i < requisitosEvaluacion.length(); i++){
            JSONObject requisitoEvaluacion = requisitosEvaluacion.getJSONObject(i);
            int idRequisito = requisitoEvaluacion.getInt("id");
            int cumplimiento = requisitoEvaluacion.getInt("cumplimiento");         
            radto = new RequisitoActaDTO();
            radto.getEntidad().setId(idRequisito);
            radto = radao.read(radto);
            radto.getEntidad().setCumplimiento(cumplimiento);
            radao.update(radto);
            puntosObtenidos += cumplimiento;
        }
        
        padto.getEntidad().setId(idProceso);
        padto = padao.read(padto);
        float ponderacion = padto.getEntidad().getPonderacion();
                
        float resultado = ((float)puntosObtenidos/puntosTotales)*ponderacion;
        
        padto.getEntidad().setEvaluado(true);
        padto.getEntidad().setObservaciones(observaciones);
        padto.getEntidad().setResultado(resultado);
        padao.update(padto);
        
        String jsonResp ="Ok";
        return jsonResp;
    }
    
    //metodo para finalizar evaluacion de auditoria
    
    public static void main(String[] args) {
        AudiFastWS ws = new  AudiFastWS();
        ws.procesosActaAuditoria(1);
    }
}
