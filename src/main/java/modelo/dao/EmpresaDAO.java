package modelo.dao;

import java.util.List;
import modelo.dto.EmpresaDTO;
import modelo.entidades.IdOrganizacion;
import modelo.entidades.Organizacion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author azul-
 */
public class EmpresaDAO {
    
    public void create(EmpresaDTO dto){
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
    
    public void update(EmpresaDTO dto){
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
    
    public void delete(EmpresaDTO dto){
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
    
    public EmpresaDTO read(EmpresaDTO dto){
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
    
    public List<EmpresaDTO> readAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<EmpresaDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Organizacion a order by a.Id.rfc");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
  
    public static void main (String args[]){
        Organizacion Empresa1 = new Organizacion();
        Organizacion Empresa2 = new Organizacion();
        
        
        IdOrganizacion id1 = new IdOrganizacion();
        IdOrganizacion id2 = new IdOrganizacion();
        id1.setCorreo("pepito@gmail.com");
        id1.setRfc("ASJ-140318-5L6");
        id2.setCorreo("juanito@gmail.com");
        id2.setRfc("ASJ-140318-5L6");
        
        
        //Empresa1.setCorreo_auditor("pepito@gmail.com");
        Empresa1.setDireccionF("Granja de las Lomas");
        Empresa1.setDireccionO("Polanco");
        Empresa1.setGiro("Gallos de pelea");
        Empresa1.setNombre("Gallitos Rudos S.A de C.V");
        Empresa1.setId(id1);
        //Empresa1.setRfc("ASJ-140318-5L6");
        
        //Empresa2.setCorreo_auditor("juanito@gmail.com");
        Empresa2.setDireccionF("Granja de las Lomas");
        Empresa2.setDireccionO("Polanco");
        Empresa2.setGiro("Gallos de pelea");
        Empresa2.setNombre("Gallitos Rudos S.A de C.V");
        Empresa2.setId(id2);
        //Empresa2.setRfc("ASJ-140318-5L6");
        
        EmpresaDTO EmpresaDTO1 = new EmpresaDTO(Empresa1);
        EmpresaDTO EmpresaDTO2 = new EmpresaDTO(Empresa2);
        EmpresaDAO dao = new EmpresaDAO();
        
        dao.create(EmpresaDTO1);
        dao.create(EmpresaDTO2);
        System.out.println(dao.readAll());
        EmpresaDTO1.getEntidad().setGiro("Gallos pa Comer");
        dao.update(EmpresaDTO1);
        System.out.println(dao.read(EmpresaDTO1));
        dao.delete(EmpresaDTO1);
        System.out.println(dao.readAll());
        dao.delete(EmpresaDTO2);
    }
        
    
}

