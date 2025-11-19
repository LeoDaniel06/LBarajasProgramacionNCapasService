package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.EstadoJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.ML.Estado;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.ML.Result;
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
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetByIdPais(int IdPais) {
        Result result = new Result();
        try {
            TypedQuery<EstadoJPA> queryEstados = entityManager.createQuery(
                    "FROM EstadoJPA estado WHERE estado.PaisJPA.IdPais = :idPais", EstadoJPA.class);
            queryEstados.setParameter("idPais", IdPais);
            List<EstadoJPA> estadosJPA = queryEstados.getResultList();
            List<Estado> estadosML = new ArrayList<>();
            for (EstadoJPA estadoJPA : estadosJPA) {
                Estado estado = modelMapper.map(estadoJPA, Estado.class);
                estadosML.add(estado);
            }
            result.objects = (List<Object>) (List<?>) estadosML;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
