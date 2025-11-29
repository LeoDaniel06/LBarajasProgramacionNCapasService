package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService {

    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO;

    public UserDetailsJPAService(IUsuarioRepositoryDAO iUsuarioRepositoryDAO) {
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UsuarioJPA usuario = iUsuarioRepositoryDAO.findByUserName(userName);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no existe");
        }
        boolean enabled = usuario.getStatus() == 1;
        String rol = usuario.RolJPA.getNombre();
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(rol)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!enabled)
                .build();
    }
}
