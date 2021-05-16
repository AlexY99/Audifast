/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.AuditorAuxiliarDTO;
import modelo.dto.AuditoriaDTO;
import modelo.dto.EmpresaDTO;
import modelo.entidades.AuditorAuxiliar;
import modelo.entidades.Organizacion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;

/**
 *
 * @author azul-
 */
public class AuditorAuxiliarDAO {
    
    public void create(AuditorAuxiliarDTO dto){
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
    
    public void update(AuditorAuxiliarDTO dto){
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
    
    public void delete(AuditorAuxiliarDTO dto){
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
    
    public AuditorAuxiliarDTO read(AuditorAuxiliarDTO dto){
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
    
    public List<AuditorAuxiliarDTO> readAll(int idAuditoria){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<AuditorAuxiliar> lista = null;
        ArrayList<AuditorAuxiliarDTO> dtos = new ArrayList<>();
        try{
            transaction.begin();
            Query q = session.createQuery("from AuditorAuxiliar a where idAuditoria= :idAuditoria order by a.id.correo_auditor");
            q.setParameter("idAuditoria", idAuditoria );
            lista = q.list();
            transaction.commit();
            for (AuditorAuxiliar a : lista) {
                dtos.add(new AuditorAuxiliarDTO(a));
            }
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dtos;
    }
    
    /*public static void main (String []args){
        
        AuditorAuxiliarDTO dto = new AuditorAuxiliarDTO();
        AuditorAuxiliarDAO dao = new AuditorAuxiliarDAO();
        AuditorAuxiliar auAux = new AuditorAuxiliar();
        AuditoriaDAO AudDao = new AuditoriaDAO();
        AuditoriaDTO AuDTO = new AuditoriaDTO();
        AuDTO.getEntidad().setId(1);
        auAux.getId().setIdAuditoria(1);
        auAux.getId().setCorreo("pepe@hotmail.com");       
        auAux.setAuditoria(AudDao.read(AuDTO).getEntidad());
        dto.setEntidad(auAux);
        dao.create(dto);
            
        List<AuditorAuxiliarDTO> dtos =  new AuditorAuxiliarDAO().readAll(2);
        for (AuditorAuxiliarDTO dto : dtos) {
            System.out.println(dto);
        }
        
    }*/
    
}
