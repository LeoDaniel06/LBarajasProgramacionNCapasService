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
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;
//-----------------------------------------------------GETALL----------------------------------------------------------------------

    @Override
    public Result GetAll() {

        Result result = new Result();
        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();
            List<Usuario> usuariosML = usuariosJPA.stream().map(usuario -> modelMapper.map(usuario, Usuario.class)).collect(Collectors.toList());
            result.objects = (List<Object>) (List<?>) usuariosML;
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
                usuarioJPA.getRolJPA();
                usuarioJPA.getDireccionesJPA();
                for (DireccionJPA direccion : usuarioJPA.getDireccionesJPA()) {
                    if (direccion.getColoniaJPA() != null) {
                        direccion.getColoniaJPA().getNombre();
                    }
                }
            }
            Usuario usuarioML = modelMapper.map(usuarioJPA, Usuario.class);
            result.object = usuarioML;
            result.correct = true;
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
            if (direccionJPA != null) {
                if (direccionJPA.getColoniaJPA() != null) {
                    direccionJPA.getColoniaJPA().getNombre();
                }
                if (direccionJPA.getUsuarioJPA() != null) {
                    direccionJPA.getUsuarioJPA().getIdUsuario();
                }
                Direccion direccionML = modelMapper.map(direccionJPA, Direccion.class);
                result.object = direccionML;
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontro ninguna direccion";
            }
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
    public Result Add(Usuario usuarioML) {
        Result result = new Result();
        try {
            UsuarioJPA usuarioJPA = modelMapper.map(usuarioML, UsuarioJPA.class);
            if (usuarioJPA.getDireccionesJPA() != null && !usuarioJPA.getDireccionesJPA().isEmpty()) {
                for (DireccionJPA direccion : usuarioJPA.getDireccionesJPA()) {
                    direccion.setUsuarioJPA(usuarioJPA);
                }
            }
            entityManager.persist(usuarioJPA);
            entityManager.flush();
            result.correct = true;
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
    public Result AddDireccion(Direccion direccionML, int idUsuario) {
        Result result = new Result();

        try {
            //Mapea ml a jpa
            DireccionJPA direccionJPA = modelMapper.map(direccionML, DireccionJPA.class);
            //Busca y asiga idusuario
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            direccionJPA.setUsuarioJPA(usuarioJPA);
            //busca y asigana colonia
            if (direccionML.getColonia() != null && direccionML.getColonia().getIdColonia() > 0) {
                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccionML.getColonia().getIdColonia());
                direccionJPA.setColoniaJPA(coloniaJPA);
            }
            //guaradar la direccion
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
    public Result Update(Usuario usuarioML) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioBase = entityManager.find(UsuarioJPA.class, usuarioML.getIdUsuario());
            if (usuarioBase == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }
            UsuarioJPA usuarioJPA = modelMapper.map(usuarioML, UsuarioJPA.class);
            usuarioJPA.setImagen(usuarioBase.getImagen());
            usuarioJPA.setDireccionesJPA(usuarioBase.getDireccionesJPA());
            if (usuarioML.getRol() != null) {
                usuarioJPA.setRolJPA(usuarioJPA.getRolJPA());
            } else {
                usuarioJPA.setRolJPA(usuarioBase.getRolJPA());
            }
            entityManager.merge(usuarioJPA);
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
    @Override
    @Transactional
    public Result DireccionUPDATE(Direccion direccionML, int idUsuario) {
        Result result = new Result();

        try {
            // Buscar la dirección existente
            DireccionJPA direccionBase = entityManager.find(DireccionJPA.class, direccionML.getIdDireccion());

            if (direccionBase == null) {
                result.correct = false;
                result.errorMessage = "Dirección no encontrada";
                return result;
            }

            // Mapear los nuevos datos del formulario
            DireccionJPA direccionJPA = modelMapper.map(direccionML, DireccionJPA.class);

            // Mantener la relación con el usuario
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            direccionJPA.setUsuarioJPA(usuarioJPA);

            // Mantener el mismo idDireccion (clave primaria)
            direccionJPA.setIdDireccion(direccionBase.getIdDireccion());

            // Actualizar la colonia si fue seleccionada
            if (direccionML.getColonia() != null && direccionML.getColonia().getIdColonia() > 0) {
                ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccionML.getColonia().getIdColonia());
                direccionJPA.setColoniaJPA(coloniaJPA);
            } else {
                direccionJPA.setColoniaJPA(direccionBase.getColoniaJPA());
            }

            // Guardar cambios
            entityManager.merge(direccionJPA);
            entityManager.flush();

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

//    -------------------------------------UPDATE IMAGEN--------------------------------------------------------------
    @Override
    @Transactional
    public Result UpdateImagen(int idUsuario, String NuevaImgenB64) {
        Result result = new Result();
        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, idUsuario);
            if (usuarioJPA != null) {
                usuarioJPA.setImagen(NuevaImgenB64);
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

    @Override
    @Transactional
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        try {
            int index = 0;
            for (Usuario usuarioML : usuarios) {
                UsuarioJPA usuarioJPA = modelMapper.map(usuarioML, UsuarioJPA.class);
                if (usuarioML.getFechaNacimiento() == null) {
                    usuarioJPA.setFechaNacimiento(null);
                }
                if (usuarioML.getRol() != null && usuarioML.getRol().getIdRol() > 0) {
                    RolJPA rol = entityManager.find(RolJPA.class, usuarioML.getRol().getIdRol());
                    usuarioJPA.setRolJPA(rol);
                } else {
                    usuarioJPA.setRolJPA(null);
                }
                entityManager.persist(usuarioJPA);
                if (index % 50 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                index++;
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
//------------------------------------------BUSQUEDA DINAMICA--------------------------------------------------------------------

    @Override
    public Result BusquedaDinamica(Usuario usuario) {
        Result result = new Result();

        try {
            String jpql = "SELECT u FROM UsuarioJPA u "
                    + "LEFT JOIN FETCH u.rol r "
                    + "LEFT JOIN FETCH u.direcciones d "
                    + "LEFT JOIN FETCH d.colonia c "
                    + "LEFT JOIN FETCH c.municipio m "
                    + "LEFT JOIN FETCH m.estado e "
                    + "LEFT JOIN FETCH e.pais p "
                    + "WHERE 1 = 1 ";

            // IF dinámico como tu procedure:
            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty()) {
                jpql += " AND LOWER(u.nombre) LIKE LOWER(:nombre) ";
            }

            if (usuario.getApellidoPat() != null && !usuario.getApellidoPat().isEmpty()) {
                jpql += " AND LOWER(u.apellidoPat) LIKE LOWER(:apellidoPat) ";
            }

            if (usuario.getApellidoMat() != null && !usuario.getApellidoMat().isEmpty()) {
                jpql += " AND LOWER(u.apellidoMat) LIKE LOWER(:apellidoMat) ";
            }

            if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
                jpql += " AND r.idRol = :idRol ";
            }

            jpql += " ORDER BY u.idUsuario ";

            TypedQuery<UsuarioJPA> query = entityManager.createQuery(jpql, UsuarioJPA.class);

            // Seteo dinámico EXACTO como tu SP
            if (usuario.getNombreUsuario() != null && !usuario.getNombreUsuario().isEmpty()) {
                query.setParameter("nombre", "%" + usuario.getNombreUsuario() + "%");
            }

            if (usuario.getApellidoPat() != null && !usuario.getApellidoPat().isEmpty()) {
                query.setParameter("apellidoPat", "%" + usuario.getApellidoPat() + "%");
            }

            if (usuario.getApellidoMat() != null && !usuario.getApellidoMat().isEmpty()) {
                query.setParameter("apellidoMat", "%" + usuario.getApellidoMat() + "%");
            }

            if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
                query.setParameter("idRol", usuario.getRol().getIdRol());
            }

            List<UsuarioJPA> usuariosJPA = query.getResultList();

            // Evitar duplicados por los JOIN FETCH
            Set<UsuarioJPA> setUsuarios = new LinkedHashSet<>(usuariosJPA);
            usuariosJPA = new ArrayList<>(setUsuarios);

            // Mapear tu ML exactamente igual que antes
            List<Usuario> usuariosML = new ArrayList<>();
            for (UsuarioJPA usuarioJPA : usuariosJPA) {
                Usuario u = modelMapper.map(usuarioJPA, Usuario.class);
                usuariosML.add(u);
            }

            result.objects = (List<Object>) (List<?>) usuariosML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
