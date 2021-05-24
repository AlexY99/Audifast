package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.PlantillaAuditorDTO;
import modelo.dto.ProcesoDTO;
import modelo.entidades.Proceso;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;


public class ProcesoDAO {
    
    public void create(ProcesoDTO dto){
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
    
    public void update(ProcesoDTO dto){
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
    
    public boolean delete(ProcesoDTO dto){
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
    
    public ProcesoDTO read(ProcesoDTO dto){
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
        System.out.println(dto);
        return dto;
    }
    
    public ArrayList<ProcesoDTO> readAllPlantilla(PlantillaAuditorDTO plantilla){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Proceso> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("select distinct p from Proceso p where p.plantilla = :plant");
            q.setParameter("plant",plantilla.getEntidad());
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<ProcesoDTO> dtos = new ArrayList<>();
        for (Proceso o : lista) {
            dtos.add(new ProcesoDTO(o));
        }
        return dtos;
    }
    
}

