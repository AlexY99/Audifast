/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.AuditoriaDTO;
import modelo.dto.ContactoAuditoriaDTO;
import modelo.entidades.ContactoAuditoria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;

/**
 *
 * @author azul-
 */
public class ContactoAuditoriaDAO {
    
    public void create(ContactoAuditoriaDTO dto){
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
    
    public void update(ContactoAuditoriaDTO dto){
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
    
    public void delete(ContactoAuditoriaDTO dto){
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
    
    public ContactoAuditoriaDTO read(ContactoAuditoriaDTO dto){
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
    
    public List<ContactoAuditoriaDTO> readAll(int idAuditoria){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<ContactoAuditoria> lista = null;
        ArrayList<ContactoAuditoriaDTO> dtos = new ArrayList<>();
        try{
            transaction.begin();
            Query q = session.createQuery("from ContactoAuditoria a where idAuditoria= :idAuditoria order by a.id.correo");
            q.setParameter("idAuditoria", idAuditoria );
            lista = q.list();
            transaction.commit();
            for (ContactoAuditoria a : lista) {
                dtos.add(new ContactoAuditoriaDTO(a));
            }
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dtos;
    }
    
    /*public static void main (String []args){
        
        ContactoAuditoriaDTO dto = new ContactoAuditoriaDTO();
        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();
        AuditoriaDAO auDAO = new AuditoriaDAO();
        auditoriaDTO.getEntidad().setId(1);
        auditoriaDTO = auDAO.read(auditoriaDTO);
        dto.getEntidad().getId().setCorreo("luis@empresa.com");
        dto.getEntidad().getId().set(1);
    }*/
    
}
