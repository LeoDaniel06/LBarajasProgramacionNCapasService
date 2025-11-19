package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.ML.Colonia;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetByIdMunicipio(int IdMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<ColoniaJPA> querycolonias = entityManager.createQuery(
                    "FROM ColoniaJPA colonia WHERE colonia.MunicipioJPA.IdMunicipio = :idMunicipio", ColoniaJPA.class);
            querycolonias.setParameter("idMunicipio", IdMunicipio);
            List<ColoniaJPA> coloniasJPA = querycolonias.getResultList();
            List<Colonia> coloniasML = new ArrayList<>();
            for (ColoniaJPA coloniaJPA : coloniasJPA) {
                Colonia colonia = modelMapper.map(coloniaJPA, Colonia.class);
                coloniasML.add(colonia);
            }
            result.objects = (List<Object>) (List<?>) coloniasML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
