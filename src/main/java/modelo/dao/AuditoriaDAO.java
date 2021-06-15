package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dto.AuditoriaDTO;
import utilidades.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import modelo.dto.AuditorDTO;
import modelo.entidades.Auditoria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class AuditoriaDAO {

    private Connection con;

    public void create(AuditoriaDTO dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void update(AuditoriaDTO dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void delete(AuditoriaDTO dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.delete(dto.getEntidad());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public AuditoriaDTO read(AuditoriaDTO dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(), dto.getEntidad().getId()));
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return dto;
    }

    public ArrayList<AuditoriaDTO> readAllByAuditor(AuditorDTO auditor) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String correo = auditor.getEntidad().getCorreo();
        List<Auditoria> lista = null;
        try {
            transaction.begin();
            Query q = session.createQuery("from Auditoria a where a.correo_auditor_lider = :correoLider order by a.fecha_registro");
            q.setParameter("correoLider", correo);
            lista = q.list();
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        ArrayList<AuditoriaDTO> dtos = new ArrayList<>();
        for (Auditoria a : lista) {
            dtos.add(new AuditoriaDTO(a));
        }
        return dtos;
    }

    public ArrayList<AuditoriaDTO> readAllWithAuditor(AuditorDTO auditor) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String correo = auditor.getEntidad().getCorreo();
        List<Auditoria> lista = null;
        try {
            transaction.begin();
            Query q = session.createQuery("select distinct a from Auditoria a join a.auditoresAuxiliares aux where aux.id.correo_auditor = :correoAuditor order by a.fecha_registro");
            q.setParameter("correoAuditor", correo);
            lista = q.list();
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        ArrayList<AuditoriaDTO> dtos = new ArrayList<>();
        for (Auditoria a : lista) {
            dtos.add(new AuditoriaDTO(a));
        }
        return dtos;
    }

    public Connection obtenerConexion() throws SQLException {
        Context ic;
        String url = "jdbc:mysql://localhost:3306/AudiFast?serverTimezone=America/Mexico_City&amp";
        String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        con = null;
        //String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(mySqlDriver);
            con = DriverManager.getConnection(url, "root", "root");
            if (con != null) {
                System.out.println("Conexion Establecida ...");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error al conectar " + ex);
            ex.printStackTrace();
        }
        
        return con;
    }
    
    /*public static void main(String [] args){
        AuditoriaDAO p = new AuditoriaDAO();
        try {
            p.obtenerConexion();
        } catch (SQLException ex) {
            Logger.getLogger(AuditoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/


}
