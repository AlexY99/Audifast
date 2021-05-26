/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.ProcesoActaDTO;
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
public class ProcesoActaDAO {
    public void create(ProcesoActaDTO dto){
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
            System.out.println(he);
        }
    }
    
    public void update(ProcesoActaDTO dto){
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
    
    public boolean delete(ProcesoActaDTO dto){
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
    
    public ProcesoActaDTO read(ProcesoActaDTO dto){
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
    
    /*public ArrayList<ProcesoActaDTO> readAllPlantilla(PlantillaAuditorDTO plantilla){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<ProcesoActa> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("select distinct p from ProcesoActa p where p.plantilla = :plant");
            q.setParameter("plant",plantilla.getEntidad());
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<ProcesoActaDTO> dtos = new ArrayList<>();
        for (ProcesoActa o : lista) {
            dtos.add(new ProcesoActaDTO(o));
        }
        return dtos;
    }*/
    

    public List<ProcesoActaDTO> ProcesosActa(ProcesoActaDTO padto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<ProcesoActa> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from ProcesoActa p where p.auditoria.id = :id order by p.id");
            q.setParameter("id", padto.getEntidad().getAuditoria().getId());
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<ProcesoActaDTO> dtos = new ArrayList<>();
        for (ProcesoActa o : lista) {
            dtos.add(new ProcesoActaDTO(o));
        }
        for(ProcesoActaDTO pDTO1 : dtos){
            System.out.println(pDTO1);
        }
        return dtos;
    }

}
