package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="COLONIA")
public class ColoniaJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idcolonia")
    private int IdColonia;
    
    @Column(name="nombre")
    private String Nombre;
    
    @Column(name="codigopostal")
    private String CodigoPostal;
    
    @ManyToOne
    @JoinColumn(name="idmunicipio")
    public MunicipioJPA MunicipioJPA;


    public MunicipioJPA getMunicipioJPA() {
        return MunicipioJPA;
    }

    public void setMunicipioJPA(MunicipioJPA MunicipioJPA) {
        this.MunicipioJPA = MunicipioJPA;
    } 
    
}
