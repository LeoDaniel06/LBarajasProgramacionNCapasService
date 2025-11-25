package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA {

    @Autowired
    private EntityManager entityManager;


    @Override
    public Result GetByIdMunicipio(int IdMunicipio) {
        Result result = new Result();

        try {
            TypedQuery<ColoniaJPA> queryColonias = entityManager.createQuery(
                    "FROM ColoniaJPA colonia WHERE colonia.MunicipioJPA.IdMunicipio = :idMunicipio",
                    ColoniaJPA.class
            );

            queryColonias.setParameter("idMunicipio", IdMunicipio);

            List<ColoniaJPA> colonias = queryColonias.getResultList();
            result.objects = (List<Object>) (List<?>) colonias;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
