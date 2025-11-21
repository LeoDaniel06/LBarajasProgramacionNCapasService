package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="DIRECCION")
public class DireccionJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="iddireccion")
    private Integer IdDireccion;
    
    @Column(name="calle")
    private String Calle;
    
    @Column(name="numerointerior")
    private String NumeroInterior;
    
    @Column(name="numeroexterior")
    private String NumeroExterior;
    
    @ManyToOne
    @JoinColumn(name="idcolonia")
    public ColoniaJPA ColoniaJPA;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idusuario")
    @JsonIgnore
    public UsuarioJPA UsuarioJPA;

    public Integer getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(Integer IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }   
}
