package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProcesoDTO;
import modelo.dto.RequisitoDTO;
import modelo.entidades.Requisito;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;


public class RequisitoDAO {
    
    public void create(RequisitoDTO dto){
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
    
    public void update(RequisitoDTO dto){
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
    
    public boolean delete(RequisitoDTO dto){
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
            return false;
        }
        return true;
    }
    
    public RequisitoDTO read(RequisitoDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getId()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public ArrayList<RequisitoDTO> readAllProceso(ProcesoDTO proceso){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Requisito> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("select distinct r from Requisito r inner join fetch p.proceso where p.proceso = :proc");
            q.setParameter("proc",proceso.getEntidad());
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<RequisitoDTO> dtos = new ArrayList<>();
        for (Requisito o : lista) {
            dtos.add(new RequisitoDTO(o));
        }
        return dtos;
    }
    
    public List<RequisitoDTO> Requisitos(int idProceso) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Requisito> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Requisito r where r.proceso.id = :id order by r.id");
            q.setParameter("id", idProceso);
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<RequisitoDTO> dtos = new ArrayList<>();
        for (Requisito o : lista) {
            dtos.add(new RequisitoDTO(o));
        }
        return dtos;
    }
    
}

