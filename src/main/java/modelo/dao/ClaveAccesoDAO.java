/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.ClaveAccesoDTO;
import modelo.dto.ProcesoActaDTO;
import modelo.entidades.Clave_Acceso;
import modelo.entidades.ProcesoActa;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;

/**
 *
 * @author azul-
 */
public class ClaveAccesoDAO {
    public void create(ClaveAccesoDTO dto){
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
    
    public void update(ClaveAccesoDTO dto){
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
    
    public boolean delete(ClaveAccesoDTO dto){
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
    
    public ClaveAccesoDTO read(ClaveAccesoDTO dto){
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
        return dto;
    }
    
    public ArrayList<ClaveAccesoDTO> ClavesAcceso(int idAuditoria) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Clave_Acceso> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Clave_Acceso c where c.auditoria.id = :id order by c.id");
            q.setParameter("id", idAuditoria);
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<ClaveAccesoDTO> dtos = new ArrayList<>();
        for (Clave_Acceso o : lista) {
            dtos.add(new ClaveAccesoDTO(o));
        }
        return dtos;
    }   
}
