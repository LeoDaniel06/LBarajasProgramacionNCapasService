package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.VerificationTokenJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VerificationTokenDAOImplementation {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void Add(VerificationTokenJPA token) {
        entityManager.persist(token);
    }

    @Transactional
    public void delete(VerificationTokenJPA token) {
        entityManager.remove(
                entityManager.contains(token) ? token : entityManager.merge(token)
        );
    }

    public VerificationTokenJPA findByToken(String token) {

        try {
            return entityManager.createQuery(
                    "SELECT t FROM VerificationTokenJPA t WHERE t.Token = :token",
                    VerificationTokenJPA.class)
                    .setParameter("token", token).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public VerificationTokenJPA findByUsuario(UsuarioJPA usuario) {
        return entityManager.createQuery(
                "FROM VerificationTokenJPA v WHERE v.Usuario = :usuario",
                VerificationTokenJPA.class)
                .setParameter("usuario", usuario)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
