/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author azul-
 */
@Entity
@Table(name = "requisito_acta", schema = "public")
public class RequisitoActa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cumplimiento;
    
    @ManyToOne
    @JoinColumn(name = "idProcesoActa", referencedColumnName = "id")
    private ProcesoActa procesoActa;
    
    @OneToOne
    @JoinColumn(name = "idRequisito", referencedColumnName = "id")
    private Requisito requisito;

    public RequisitoActa() {
        procesoActa = new ProcesoActa();
        requisito = new Requisito();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(int cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public ProcesoActa getProcesoActa() {
        return procesoActa;
    }

    public void setProcesoActa(ProcesoActa procesoActa) {
        this.procesoActa = procesoActa;
    }

    public Requisito getRequisito() {
        return requisito;
    }

    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }
    
}
