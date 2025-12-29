package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Result RegistrarUsuario(UsuarioJPA usuarioJPA){
        usuarioJPA.setPassword(passwordEncoder.encode(usuarioJPA.getPassword()));
        usuarioJPA.setIsVerified(0);
        usuarioJPA.setStatus(1);
        return usuarioJPADAOImplementation.Add(usuarioJPA);
    }
    
}
