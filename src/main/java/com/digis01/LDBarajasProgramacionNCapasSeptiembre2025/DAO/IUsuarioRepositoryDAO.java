package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryDAO extends JpaRepository<UsuarioJPA, Integer> {

    UsuarioJPA findByUserName (String userName);
}
