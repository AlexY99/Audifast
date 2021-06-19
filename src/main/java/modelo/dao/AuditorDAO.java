package modelo.dao;

import modelo.dto.AuditorDTO;
import utilidades.HibernateUtil;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.hibernate.result.ResultSetOutput;

public class AuditorDAO {
    public void create(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.save(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public void update(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.update(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public void delete(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.delete(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public AuditorDTO read(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getCorreo()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List<AuditorDTO> readAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<AuditorDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Auditor a order by a.Correo");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public String login(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String msj = "";
        try{
            transaction.begin();
            ProcedureCall call = session.createStoredProcedureCall( "sp_login" );
            call.registerParameter("correoLogin",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getCorreo());
            call.registerParameter("pswdLogin",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getPswd());
            ResultSetOutput rs = (ResultSetOutput)call.getOutputs().getCurrent();            
            msj = (String) rs.getSingleResult();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive())
                transaction.rollback();
        }
        return msj;
    } 
          
    public String getCorreoAuditor(AuditorDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String msj = "";
        try{
            transaction.begin();
            ProcedureCall call = session.createStoredProcedureCall( "sp_correoAuditor" );
            call.registerParameter("user",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getCorreo());
            ResultSetOutput rs = (ResultSetOutput)call.getOutputs().getCurrent();            
            msj = (String) rs.getSingleResult();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive())
                transaction.rollback();
        }
        return msj;
    } 
/*    
    public static void main (String args[]){
        Auditor auditor1 = new Auditor();
        Auditor auditor2 = new Auditor();
        
        auditor1.setCorreo("pepito@gmail.com");
        auditor1.setNombre("Pepito");
        auditor1.setPswd("pinga");
        auditor1.setTelefono("55555555");
        
        auditor2.setCorreo("juanito@gmail.com");
        auditor2.setNombre("Juanito");
        auditor2.setPswd("pingon");
        auditor2.setTelefono("55555556");
        AuditorDTO dto1 = new AuditorDTO(auditor1);
        AuditorDTO dto2 = new AuditorDTO(auditor2);
        
        AuditorDAO dao = new AuditorDAO();
        
        dao.create(dto1);
        dao.create(dto2);
        
    }
 */   
}
