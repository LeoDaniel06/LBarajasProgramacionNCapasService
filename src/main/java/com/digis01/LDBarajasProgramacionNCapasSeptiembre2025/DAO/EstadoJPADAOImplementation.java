package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.EstadoJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByIdPais(int IdPais) {
        Result result = new Result();
        try {
            TypedQuery<EstadoJPA> queryEstados = entityManager.createQuery(
                    "FROM EstadoJPA estado WHERE estado.PaisJPA.IdPais = :idPais",
                    EstadoJPA.class
            );
            queryEstados.setParameter("idPais", IdPais);
            List<EstadoJPA> estados = queryEstados.getResultList();
            result.objects = (List<Object>) (List<?>) estados;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
