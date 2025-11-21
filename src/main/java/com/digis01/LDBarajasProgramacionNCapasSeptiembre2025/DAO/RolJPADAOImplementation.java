package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GETALL() {
        Result result = new Result();
        try {
            TypedQuery<RolJPA> queryrol = entityManager.createQuery("FROM RolJPA", RolJPA.class);
            List<RolJPA> rolesJPA = queryrol.getResultList();
            result.objects = (List<Object>) (List<?>) rolesJPA;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
