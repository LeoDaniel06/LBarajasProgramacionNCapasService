package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.RESTCONTROLLER;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.ColoniaJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.EstadoJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.MunicipioJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.PaisJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.RolJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.VerificationTokenDAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.VerificationTokenJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.EmailService;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.VerificationEmailProducer;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class DemoRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;
    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    @Autowired
    private VerificationEmailProducer verificationEmailProducer;
    @Autowired
    private VerificationTokenDAOImplementation verificationTokenDAOImplementation;
    @Autowired
    private IUsuarioRepositoryDAO iUsuarioRepositoryDAO;
    @Autowired
    private EmailService emailService;

//-------------------------------GETALLUSSUARIOS-----------------------------------------------
    @GetMapping()
    public ResponseEntity<Result> getAllUsuarios() {
        Result result;
        try {
            result = usuarioJPADAOImplementation.GetAll();

            if (result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result = new Result();
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
//----------------------------------------GETALLROLES-----------------------------------------------------------

    @GetMapping("/roles")
    public ResponseEntity<Result> GetallRoles() {
        Result result = new Result();
        try {
            result = rolJPADAOImplementation.GETALL();
            if (result.objects == null || result.objects.isEmpty()) {
                result.correct = false;
                result.errorMessage = "No se encontraron usuarios";
                result.Status = 404;
            } else {
//                result.objects = 
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//----------------------------------------GETALLPAIS-----------------------------------------------------------------------

    @GetMapping("/paises")
    public ResponseEntity<Result> GetallPais() {
        Result result = new Result();
        try {
            result = paisJPADAOImplementation.GETALL();
            if (result.objects == null || result.objects.isEmpty()) {
                result.correct = false;
                result.errorMessage = "No se encontraron usuarios";
                result.Status = 404;
            } else {
//                result.objects = 
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//----------------------------------------GETESTADOBYPAIS---------------------------------------------------------------

    @GetMapping("/estados/{idPais}")
    public ResponseEntity<Result> GetCEstadobyid(@PathVariable("idPais") int idPais) {
        Result result = new Result();
        try {
            result = estadoJPADAOImplementation.GetByIdPais(idPais);
            if (result.objects == null) {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario";
                result.Status = 404;
            } else {
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//--------------------------------------GETMUNICIPIOSBYESTADO-------------------------------------

    @GetMapping("/municipios/{idEstado}")
    public ResponseEntity<Result> GetMunicipiobyidestado(@PathVariable("idEstado") int idEstado) {
        Result result = new Result();
        try {
            result = municipioJPADAOImplementation.GetByIdEstado(idEstado);
            if (result.objects == null) {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario";
                result.Status = 404;
            } else {
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//----------------------------------------GETCOLONIASBYMUNICIPIO--------------------------------------------------------

    @GetMapping("/colonias/{idMunicipio}")
    public ResponseEntity<Result> GetColoniabyid(@PathVariable("idMunicipio") int idMunicipio) {
        Result result = new Result();
        try {
            result = coloniaJPADAOImplementation.GetByIdMunicipio(idMunicipio);
            if (result.objects == null) {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario";
                result.Status = 404;
            } else {
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//----------------------------------------GETBYID----------------------------------------------------------------

    @GetMapping("/detail/{idUsuario}")
    public ResponseEntity<Result> GetUsuarioById(@PathVariable("idUsuario") int idUsuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.GetById(idUsuario);
            if (result.object == null) {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario";
                result.Status = 404;
            } else {
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//-------------------------------------DIRECCIONGETBYIDDIRECCION------------------------

    @GetMapping("/direccion/{idDireccion}")
    public ResponseEntity<Result> GetDIRECCIONByIdDIRECCION(@PathVariable("idDireccion") int idDireccion) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.GetDireccionBYIdDireccion(idDireccion);
            if (result.object == null) {
                result.correct = false;
                result.errorMessage = "No se encontró la direccion";
                result.Status = 404;
            } else {
                result.correct = true;
                result.Status = 200;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------ADD--------------------------------------------------

    @PostMapping("/add")
    public ResponseEntity<Result> addUsuario(@RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Add(usuarioJPA);
            if (result.correct) {
                UsuarioJPA nuevo = (UsuarioJPA) result.object;
                String token = UUID.randomUUID().toString();
                VerificationTokenJPA verificationToken = new VerificationTokenJPA(
                        token,
                        nuevo,
                        LocalDateTime.now().plusHours(24));
                verificationTokenDAOImplementation.Add(verificationToken);
                verificationEmailProducer.sendVerificationEmail(nuevo.getEmail(), token);

                result.Status = 201;
            } else {
                result.Status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------------------ADD DIRECCION------------------------------------------

    @PostMapping("/add-direccion/{idUsuario}")
    public ResponseEntity<Result> addDireccion(@PathVariable("idUsuario") int idUsuario, @RequestBody DireccionJPA direccionJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.AddDireccion(direccionJPA, idUsuario);
            if (result.correct) {
                result.Status = 201;
            } else {
                result.Status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------------------UPDATEIMAGEN-------------------------------------------

    @PatchMapping("/update-imagen/{idUsuario}")
    public ResponseEntity<Result> updateImagen(@PathVariable("idUsuario") int idUsuario, @RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.UpdateImagen(idUsuario, usuarioJPA.getImagen());
            if (result.correct) {
                result.Status = 200;
            } else {
                result.Status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------------------UPDATE USUARIO-------------------------------------------

    @PutMapping("/update")
    public ResponseEntity<Result> updateUsuario(@RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Update(usuarioJPA);
            if (result.correct) {
                result.Status = 200;
            } else {
                result.Status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------------------UPDATE DIRECCION-------------------------------------------

    @PutMapping("/update-direccion/{idUsuario}")
    public ResponseEntity<Result> UpddateDireccion(@PathVariable int idUsuario,
            @RequestBody DireccionJPA direccionJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.DireccionUPDATE(direccionJPA, idUsuario);
            if (result.correct) {
                result.Status = 400;
                return ResponseEntity.status(400).body(result);
            }
            result.Status = 200;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.status(500).body(result);
        }
    }
//---------------------------------------------DELETEUSUARIO---------------------------------------------

    @DeleteMapping("/delete-usuario/{idUsuario}")
    public ResponseEntity<Result> deleteUsuario(@PathVariable int idUsuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.DeleteUsuario(idUsuario);

            if (result.correct) {
                result.Status = 200;
            } else {
                result.Status = 404;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }

        return ResponseEntity.status(result.Status).body(result);
    }
//---------------------------------------DELETE DIRECCION---------------------------------------------------------

    @DeleteMapping("/delete-direccion/{idDireccion}")
    public ResponseEntity<Result> deletedIRECCION(@PathVariable int idDireccion) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.DeleteDireccion(idDireccion);

            if (result.correct) {
                result.Status = 200;
            } else {
                result.Status = 404;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }

        return ResponseEntity.status(result.Status).body(result);
    }
//----------------------------------------------BUSQUEDA DINAMICA----------------------------------------------------

    @PostMapping("/busqueda")
    public ResponseEntity<Result> BusquedaDinamica(@RequestBody UsuarioJPA usuariofiltro) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.BusquedaDinamica(usuariofiltro);
            if (!result.correct) {
                return ResponseEntity.status(400).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            return ResponseEntity.status(500).body(result);
        }
    }
//---------------------------------------UPDATE STATUS------------------------------------------------------------

    @PutMapping("/update-status/{idUsuario}/{status}")
    public ResponseEntity<Result> UpdateStatus(@PathVariable int idUsuario, @PathVariable int status) {
        Result result = usuarioJPADAOImplementation.UpdateStatus(idUsuario, status);
        try {
            if (!result.correct) {
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return ResponseEntity.ok(result);
    }
//------------------------------------------------CODIGO POSTAL-----------------------------------

    @GetMapping("/codigopostal/{codigoPostal}")
    public ResponseEntity<Result> GetInfoByCP(@PathVariable("codigoPostal") String codigoPostal) {
        Result result = new Result();

        try {
            if (codigoPostal.length() != 5 || !codigoPostal.matches("\\d+")) {
                result.correct = false;
                result.errorMessage = "El código postal debe ser de 5 números";
                return ResponseEntity.badRequest().body(result);
            }

            result = usuarioJPADAOImplementation.GetInfoByCP(codigoPostal);

            if (!result.correct) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }

            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error en el servidor";
            result.ex = ex;
            return ResponseEntity.internalServerError().body(result);
        }
    }
//--------------------------------VERIFICACION DE CORREO-----------------------------------------------------

    @GetMapping("/verificacion/confirmar")
    public ResponseEntity<Void> verificarCorreo(@RequestParam String token) {

        VerificationTokenJPA verificationToken
                = verificationTokenDAOImplementation.findByToken(token);

        if (verificationToken == null
                || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION,
                            "http://localhost:8081/usuario/verificacion-error")
                    .build();
        }

//        UsuarioJPA usuario = verificationToken.getUsuarioJPA();
        UsuarioJPA usuario = verificationToken.getUsuario();
        usuario.setIsVerified(1);
        usuarioJPADAOImplementation.UpdateVerification(usuario);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION,
                        "http://localhost:8081/usuario/verificado")
                .build();
    }
//-----------------------------------------REENVIO DE CORREO VERIFICACION------------------------------------------------------------

    @PostMapping("/reenviar-verificacion")
    public ResponseEntity<?> ReenviarCorrreo(@RequestParam String username) {

        UsuarioJPA usuario = iUsuarioRepositoryDAO.findByUserName(username);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no existe");
        }

        if (usuario.getIsVerified() == 1) {
            return ResponseEntity.ok("Usuario ya verificado");
        }

        VerificationTokenJPA tokenAnterior
                = verificationTokenDAOImplementation.findByUsuario(usuario);

        if (tokenAnterior != null) {
            verificationTokenDAOImplementation.delete(tokenAnterior);
        }

        String token = UUID.randomUUID().toString();

        VerificationTokenJPA nuevoToken = new VerificationTokenJPA(
                token,
                usuario,
                LocalDateTime.now().plusHours(24)
        );

        verificationTokenDAOImplementation.Add(nuevoToken);
        emailService.sendVerificationEmail(usuario.getEmail(), token);

        return ResponseEntity.ok("Correo enviado");
    }
}
