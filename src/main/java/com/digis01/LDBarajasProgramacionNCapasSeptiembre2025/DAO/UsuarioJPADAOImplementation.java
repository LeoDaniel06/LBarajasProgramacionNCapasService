package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.ColoniaJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.RolJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

//-----------------------------------------------------GETALL----------------------------------------------------------------------
    @Override
    public Result GetAll() {

        Result result = new Result();
        try {
            TypedQuery<UsuarioJPA> queryUsuario
                    = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuarios = queryUsuario.getResultList();
            result.objects = (List<Object>) (List<?>) usuarios;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

//------------------------------------------------GET BY ID -------------------------------------------------------------------
    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            if (usuarioJPA != null) {
                Hibernate.initialize(usuarioJPA.RolJPA);
                Hibernate.initialize(usuarioJPA.DireccionesJPA);
                for (DireccionJPA direccion : usuarioJPA.DireccionesJPA) {
                    if (direccion.ColoniaJPA != null) {
                        Hibernate.initialize(direccion.ColoniaJPA);
                    }
                }
                result.object = usuarioJPA;
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

//------------------------------------------------DIRECCION GETBYID DIRECCION--------------------------------------------------
    @Override
    @Transactional
    public Result GetDireccionBYIdDireccion(int idDireccion) {
        Result result = new Result();
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, idDireccion);
            if (direccionJPA == null) {
                result.correct = false;
                result.errorMessage = "No se encontró ninguna dirección";
                return result;
            }
            if (direccionJPA.ColoniaJPA != null) {
                Hibernate.initialize(direccionJPA.ColoniaJPA);
            }
            if (direccionJPA.UsuarioJPA != null) {
                Hibernate.initialize(direccionJPA.UsuarioJPA);
            }
            result.object = direccionJPA;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

//------------------------------------------------ADD--------------------------------------------------------------------------
    @Override
    @Transactional
    public Result Add(UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            if (usuarioJPA.DireccionesJPA != null && !usuarioJPA.DireccionesJPA.isEmpty()) {
                usuarioJPA.DireccionesJPA.get(0).UsuarioJPA = usuarioJPA;
            }
            entityManager.persist(usuarioJPA);
            entityManager.flush();
            result.correct = true;
            result.object = usuarioJPA;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
//    -------------------------------------------ADDDIRECCION----------------------------------------------------------

    @Override
    @Transactional
    public Result AddDireccion(DireccionJPA direccionJPA, int idUsuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            direccionJPA.UsuarioJPA = usuarioJPA;

            if (direccionJPA.ColoniaJPA != null && direccionJPA.ColoniaJPA.getIdColonia() > 0) {
                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccionJPA.ColoniaJPA.getIdColonia());
                direccionJPA.ColoniaJPA = coloniaJPA;
            }
            entityManager.persist(direccionJPA);
            entityManager.flush();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

//    -----------------------------------------UPDATE------------------------------------------------------------------
    @Override
    @Transactional
    public Result Update(UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            UsuarioJPA usuarioBase = entityManager.find(UsuarioJPA.class, usuarioJPA.getIdUsuario());
            if (usuarioBase == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }
            usuarioBase.setUserName(usuarioJPA.getUserName());
            usuarioBase.setNombre(usuarioJPA.getNombre());
            usuarioBase.setApellidoPat(usuarioJPA.getApellidoPat());
            usuarioBase.setApellidoMat(usuarioJPA.getApellidoMat());
            usuarioBase.setEmail(usuarioJPA.getEmail());
            usuarioBase.setPassword(usuarioJPA.getPassword());
            usuarioBase.setSexo(usuarioJPA.getSexo());
            usuarioBase.setTelefono(usuarioJPA.getTelefono());
            usuarioBase.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
            usuarioBase.setCelular(usuarioJPA.getCelular());
            usuarioBase.setCurp(usuarioJPA.getCurp());
            if (usuarioJPA.getImagen() != null) {
                usuarioBase.setImagen(usuarioJPA.getImagen());
            }
            if (usuarioJPA.RolJPA != null) {
                usuarioBase.RolJPA = usuarioJPA.RolJPA;
            }
            usuarioBase.DireccionesJPA = usuarioBase.DireccionesJPA;
            entityManager.merge(usuarioBase);
            entityManager.flush();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }
//    --------------------------UPDATEDIRECCION ------------------------------------
//
//    @Override
//    @Transactional
//    public Result DireccionUPDATE(DireccionJPA direccionJPA, int idUsuario) {
//        Result result = new Result();
//
//        try {
//            DireccionJPA direccionBase = entityManager.find(DireccionJPA.class, direccionJPA.getIdDireccion());
//            if (direccionBase == null) {
//                result.correct = false;
//                result.errorMessage = "Dirección no encontrada";
//                return result;
//            }
//            direccionBase.setCalle(direccionJPA.getCalle());
//            direccionBase.setNumeroInterior(direccionJPA.getNumeroInterior());
//            direccionBase.setNumeroExterior(direccionJPA.getNumeroExterior());
//            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
//            direccionBase.setUsuarioJPA(usuarioJPA);
//            if (direccionJPA.getColoniaJPA() != null && direccionJPA.getColoniaJPA().getIdColonia() > 0) {
//                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccionJPA.getColoniaJPA().getIdColonia());
//                direccionBase.setColoniaJPA(coloniaJPA);
//            }
//            entityManager.merge(direccionBase);
//            entityManager.flush();
//            result.correct = true;
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getMessage();
//            result.ex = ex;
//        }
//        return result;
//    }
//    -------------------------------------UPDATE IMAGEN--------------------------------------------------------------
    @Override
    @Transactional
    public Result UpdateImagen(int idUsuario, String NuevaImagenB64) {
        Result result = new Result();
        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            if (usuarioJPA != null) {
                usuarioJPA.setImagen(NuevaImagenB64);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }

//    -------------------------------------DELETE USUARIO---------------------------------------------------
    @Override
    @Transactional
    public Result DeleteUsuario(int idUsuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            if (usuarioJPA != null) {
                entityManager.remove(usuarioJPA);
                entityManager.flush();
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no existe";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
//    -----------------------------------DELETE DIRECCION-------------------------------------------------------

    @Override
    @Transactional
    public Result DeleteDireccion(int idDireccion) {
        Result result = new Result();
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, idDireccion);
            if (direccionJPA != null) {
                entityManager.remove(direccionJPA);
                entityManager.flush();
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Direccion no existe";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
//-----------------------------------------------CARGA MASIVA-----------------------------------------------
//
//    @Override
//    @Transactional
//    public Result AddAll(List<UsuarioJPA> usuarios) {
//        Result result = new Result();
//
//        try {
//            int index = 0;
//
//            for (UsuarioJPA usuarioJPA : usuarios) {
//
//                // Validar fecha nula
//                if (usuarioJPA.getFechaNacimiento() == null) {
//                    usuarioJPA.setFechaNacimiento(null);
//                }
//
//                // Rol → Buscarlo en BD si viene con ID
//                if (usuarioJPA.getRolJPA() != null && usuarioJPA.getRolJPA().getIdRol() > 0) {
//                    RolJPA rol = entityManager.find(RolJPA.class, usuarioJPA.getRolJPA().getIdRol());
//                    usuarioJPA.setRolJPA(rol);
//                } else {
//                    usuarioJPA.setRolJPA(null);
//                }
//
//                // Guardar usuario
//                entityManager.persist(usuarioJPA);
//
//                // Optimización por lotes
//                if (index % 50 == 0) {
//                    entityManager.flush();
//                    entityManager.clear();
//                }
//
//                index++;
//                result.correct = true;
//            }
//
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//        }
//
//        return result;
//    }
////------------------------------------------BUSQUEDA DINAMICA--------------------------------------------------------------------
//
//    @Override
//    public Result BusquedaDinamica(UsuarioJPA usuario) {
//        Result result = new Result();
//        
//        try {
//            String jpql = "SELECT u FROM UsuarioJPA u "
//                    + "LEFT JOIN FETCH u.rolJPA r "
//                    + "LEFT JOIN FETCH u.direccionesJPA d "
//                    + "LEFT JOIN FETCH d.coloniaJPA c "
//                    + "LEFT JOIN FETCH c.municipioJPA m "
//                    + "LEFT JOIN FETCH m.estadoJPA e "
//                    + "LEFT JOIN FETCH e.paisJPA p "
//                    + "WHERE 1 = 1 ";
//
//            // ---- FILTROS ----
//            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
//                jpql += " AND LOWER(u.nombre) LIKE LOWER(:nombre) ";
//            }
//
//            if (usuario.getApellidoPat() != null && !usuario.getApellidoPat().isEmpty()) {
//                jpql += " AND LOWER(u.apellidoPaterno) LIKE LOWER(:apellidoPat) ";
//            }
//
//            if (usuario.getApellidoMat() != null && !usuario.getApellidoMat().isEmpty()) {
//                jpql += " AND LOWER(u.apellidoMaterno) LIKE LOWER(:apellidoMat) ";
//            }
//
//            if (usuario.getRolJPA() != null && usuario.getRolJPA().getIdRol() != 0) {
//                jpql += " AND r.idRol = :idRol ";
//            }
//
//            jpql += " ORDER BY u.idUsuario ";
//
//            TypedQuery<UsuarioJPA> query = entityManager.createQuery(jpql, UsuarioJPA.class);
//
//            // ---- SETEO DE PARÁMETROS ----
//            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
//                query.setParameter("nombre", "%" + usuario.getNombre() + "%");
//            }
//
//            if (usuario.getApellidoPat() != null && !usuario.getApellidoPat().isEmpty()) {
//                query.setParameter("apellidoPat", "%" + usuario.getApellidoPat() + "%");
//            }
//
//            if (usuario.getApellidoMat() != null && !usuario.getApellidoMat().isEmpty()) {
//                query.setParameter("apellidoMat", "%" + usuario.getApellidoMat() + "%");
//            }
//
//            if (usuario.getRolJPA() != null && usuario.getRolJPA().getIdRol() != 0) {
//                query.setParameter("idRol", usuario.getRolJPA().getIdRol());
//            }
//
//            List<UsuarioJPA> usuariosJPA = query.getResultList();
//
//            // Evita duplicados por JOIN FETCH
//            Set<UsuarioJPA> setUsuarios = new LinkedHashSet<>(usuariosJPA);
//            usuariosJPA = new ArrayList<>(setUsuarios);
//
//            // Ahora asignamos directamente las entidades JPA
//            result.objects = (List<Object>) (List<?>) usuariosJPA;
//            result.correct = true;
//
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//        }
//
//        return result;
//    }
}
