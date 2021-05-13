package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.dto.EmpresaDTO;
import modelo.entidades.Organizacion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilidades.HibernateUtil;


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
    
    public boolean delete(EmpresaDTO dto){
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
        System.out.println(dto);
        return dto;
    }
    
    public ArrayList<EmpresaDTO> readAll(String correo){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<Organizacion> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Organizacion a where correo_auditor= :correoLider order by a.Id.rfc");
            q.setParameter("correoLider", correo);
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        ArrayList<EmpresaDTO> dtos = new ArrayList<>();
        for (Organizacion o : lista) {
            dtos.add(new EmpresaDTO(o));
        }
        return dtos;
    }

    /*public static void main (String args[]){
        Organizacion Empresa1 = new Organizacion();
        IdOrganizacion id1 = new IdOrganizacion();
        id1.setCorreo("alex@hotmail.com");
        id1.setRfc("CUPU800825569");
        Empresa1.setId(id1);
        EmpresaDTO EmpresaDTO1 = new EmpresaDTO(Empresa1);
        EmpresaDAO dao = new EmpresaDAO();
        System.out.println(dao.read(EmpresaDTO1));
    /*  Organizacion Empresa2 = new Organizacion();
        
        
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
    }      */
    
}

