package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.ML.Direccion;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.ML.Usuario;
import java.util.List;



public interface IUsuarioJPA{
    
    Result GetAll();
    Result Add(Usuario usuarioML);
    Result Update(Usuario usuarioML);
    Result DireccionUPDATE(Direccion direccionML, int idUsuario);
    Result AddDireccion(Direccion direccionML, int idUsuario);
    Result DeleteUsuario(int idUsuario);
    Result DeleteDireccion(int idDireccion);
    Result UpdateImagen(int idUsuario, String NuevaImgenB64);
    Result GetById(int idUsuario);
    Result GetDireccionBYIdDireccion(int idDireccion);
    Result AddAll(List<Usuario>usuarios);
    Result BusquedaDinamica(Usuario usuario);
}
