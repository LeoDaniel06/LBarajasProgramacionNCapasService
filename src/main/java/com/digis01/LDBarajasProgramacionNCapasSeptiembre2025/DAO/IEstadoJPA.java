package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;

public interface IEstadoJPA {
    Result GetByIdPais(int IdPais);
}
