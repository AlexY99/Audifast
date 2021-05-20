package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.AuditorDTO;
import modelo.dto.NormaDTO;
import modelo.entidades.Norma;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;


public class NormaDAO {
    
    public void create(NormaDTO dto){
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
    
    public void update(NormaDTO dto){
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
    
    public boolean delete(NormaDTO dto){
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
    
    public NormaDTO read(NormaDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getClave()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        System.out.println(dto);
        return dto;
    }
    
    public ArrayList<NormaDTO> readAll(AuditorDTO auditor){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Norma> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("select distinct n from Norma n join n.auditor where n.auditor = :audit");
            q.setParameter("audit",auditor.getEntidad());
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<NormaDTO> dtos = new ArrayList<>();
        for (Norma o : lista) {
            dtos.add(new NormaDTO(o));
        }
        return dtos;
    }
}

