package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name= "VerificationToken")
public class VerificationTokenJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idtoken")
    private Long IdToken;
    
    @Column(name="token")
    private String Token;
    
    @Column(name="expirydate")
    private LocalDateTime ExpiryDate;
    
    @OneToOne 
    @JoinColumn(name = "idusuario") 
    private UsuarioJPA Usuario;
    
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="idusuario")
//    @JsonIgnore
//    public UsuarioJPA UsuarioJPA;

    public VerificationTokenJPA() {
    }

    public VerificationTokenJPA(String Token, UsuarioJPA Usuario, LocalDateTime ExpiryDate) {
        this.Token = Token;
        this.Usuario = Usuario;
        this.ExpiryDate = ExpiryDate;
    }

    public Long getIdToken() {
        return IdToken;
    }

    public void setIdToken(Long IdToken) {
        this.IdToken = IdToken;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

//    public UsuarioJPA getUsuarioJPA() {
//        return UsuarioJPA;
//    }
//
//    public void setUsuarioJPA(UsuarioJPA UsuarioJPA) {
//        this.UsuarioJPA = UsuarioJPA;
//    }

    public UsuarioJPA getUsuario() {
        return Usuario;
    }

    public void setUsuario(UsuarioJPA Usuario) {
        this.Usuario = Usuario;
    }
    
    public LocalDateTime getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(LocalDateTime ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }
}
