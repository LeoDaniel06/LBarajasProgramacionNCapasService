package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name= "Usuario")
public class UsuarioJPA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idusuario")
    private int idUsuario;
    
    @Column(name="username")
    private String UserName;
    
    @Column(name="nombre")
    private String Nombre;
    
    @Column(name="apellidopat")
    private String ApellidoPat;
    
    @Column(name="apellidomat")
    private String ApellidoMat;
    
    @Column(name="email")
    private String Email;
    
    @Column(name="password")
    private String Password;
    
    @Column(name="fechanacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    
    @Column(name="sexo")
    private String Sexo;
    
    @Column(name="telefono")
    private String Telefono;
    
    @Column(name="celular")
    private String Celular;
    
    @Column(name="curp")
    private String Curp;
    
    @Column(name="imagen")
    private String Imagen;
    
    @ManyToOne
    @JoinColumn(name="idrol")
    public RolJPA RolJPA;
    
    @OneToMany(mappedBy = "UsuarioJPA",cascade = CascadeType.ALL, orphanRemoval = true)
    public List<DireccionJPA> DireccionesJPA = new ArrayList();

//    public int getIdUsuario() {
//        return idUsuario;
//    }
//
//    public void setIdUsuario(int idUsuario) {
//        this.idUsuario = idUsuario;
//    }
//
//    public String getUserName() {
//        return UserName;
//    }
//
//    public void setUserName(String UserName) {
//        this.UserName = UserName;
//    }
//
//    public String getNombre() {
//        return Nombre;
//    }
//
//    public void setNombre(String Nombre) {
//        this.Nombre = Nombre;
//    }
//
//    public String getApellidoPat() {
//        return ApellidoPat;
//    }
//
//    public void setApellidoPat(String ApellidoPat) {
//        this.ApellidoPat = ApellidoPat;
//    }
//
//    public String getApellidoMat() {
//        return ApellidoMat;
//    }
//
//    public void setApellidoMat(String ApellidoMat) {
//        this.ApellidoMat = ApellidoMat;
//    }
//
//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String Email) {
//        this.Email = Email;
//    }
//
//    public String getPassword() {
//        return Password;
//    }
//
//    public void setPassword(String Password) {
//        this.Password = Password;
//    }
//
//    public Date getFechaNacimiento() {
//        return FechaNacimiento;
//    }
//
//    public void setFechaNacimiento(Date FechaNacimiento) {
//        this.FechaNacimiento = FechaNacimiento;
//    }
//
//    public String getSexo() {
//        return Sexo;
//    }
//
//    public void setSexo(String Sexo) {
//        this.Sexo = Sexo;
//    }
//
//    public String getTelefono() {
//        return Telefono;
//    }
//
//    public void setTelefono(String Telefono) {
//        this.Telefono = Telefono;
//    }
//
//    public String getCelular() {
//        return Celular;
//    }
//
//    public void setCelular(String Celular) {
//        this.Celular = Celular;
//    }
//
//    public String getCurp() {
//        return Curp;
//    }
//
//    public void setCurp(String Curp) {
//        this.Curp = Curp;
//    }
//
//    public String getImagen() {
//        return Imagen;
//    }
//
//    public void setImagen(String Imagen) {
//        this.Imagen = Imagen;
//    }

    public RolJPA getRolJPA() {
        return RolJPA;
    }

    public void setRolJPA(RolJPA RolJPA) {
        this.RolJPA = RolJPA;
    }

    public List<DireccionJPA> getDireccionesJPA() {
        return DireccionesJPA;
    }

    public void setDireccionesJPA(List<DireccionJPA> DireccionesJPA) {
        this.DireccionesJPA = DireccionesJPA;
    }

    
}
