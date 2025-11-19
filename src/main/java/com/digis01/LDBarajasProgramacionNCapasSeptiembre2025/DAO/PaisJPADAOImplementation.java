package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.PaisJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GETALL() {
        Result result = new Result();
        try {
            TypedQuery<PaisJPA> querypais = entityManager.createQuery("FROM PaisJPA", PaisJPA.class);
            List<PaisJPA> paisesJPA = querypais.getResultList();
            List<Pais> paisesML = new ArrayList<>();
            for (PaisJPA paisJPA : paisesJPA) {

                Pais pais = modelMapper.map(paisJPA, Pais.class);
                paisesML.add(pais);
            }
            result.objects = (List<Object>) (List<?>) paisesML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
