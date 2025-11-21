package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.MunicipioJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByIdEstado(int IdEstado) {
        Result result = new Result();
        try {
            TypedQuery<MunicipioJPA> queryMunicipios = entityManager.createQuery(
                    "FROM MunicipioJPA municipio WHERE municipio.EstadoJPA.IdEstado = :idEstado",
                    MunicipioJPA.class
            );
            queryMunicipios.setParameter("idEstado", IdEstado);
            List<MunicipioJPA> municipios = queryMunicipios.getResultList();
            // Regresamos directamente las entidades JPA
            result.objects = (List<Object>) (List<?>) municipios;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
