package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import java.util.List;



public interface IUsuarioJPA{
    
    Result GetAll();
    Result Add(UsuarioJPA usuarioJPA);
    Result Update(UsuarioJPA usuarioJPA);
    Result DireccionUPDATE(DireccionJPA direccionJPA, int idUsuario);
    Result AddDireccion(DireccionJPA direccionJPA, int idUsuario);
    Result DeleteUsuario(int idUsuario);
    Result DeleteDireccion(int idDireccion);
    Result UpdateImagen(int idUsuario, String NuevaImgenB64);
    Result GetById(int idUsuario);
    Result GetDireccionBYIdDireccion(int idDireccion);
//    Result AddAll(List<UsuarioJPA> usuarios);
//    Result BusquedaDinamica(UsuarioJPA usuario);
}
