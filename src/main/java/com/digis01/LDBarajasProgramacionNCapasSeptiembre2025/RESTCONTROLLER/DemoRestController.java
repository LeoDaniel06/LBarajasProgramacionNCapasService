package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.RESTCONTROLLER;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.ColoniaJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.EstadoJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.PaisJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.RolJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.DireccionJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("saludo")
    public String Saludo(@RequestParam("Nombre") String nombre) {

        return "Hola " + nombre;
    }

    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
        Result result = new Result();
        try {
            if (numeroDos == 0) {
                result.correct = false;
                result.errorMessage = "Syntax Error";
                result.Status = 400;
            } else {
                int division = numeroUno / numeroDos;
                result.object = division;
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

    @GetMapping("multiplicacion")
    public ResponseEntity Multiplicacion(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos") int numeroDos) {
        Result result = new Result();
        try {
            if (numeroDos == 0) {
                result.correct = false;
                result.errorMessage = "No puede multiplicar por 0";
                result.Status = 400;
            } else {
                int multiplicacion = numeroUno * numeroDos;
                result.object = multiplicacion;
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

    @GetMapping("suma")
    public ResponseEntity Suma(@RequestParam("Numero") List<Integer> numeros) {
        Result result = new Result();
        int total = 0;
        try {
            for (Integer num : numeros) {
                total += num;
            }
            result.object = total;
            result.correct = true;
            result.Status = 200;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.Status = 500;
        }
        return ResponseEntity.status(result.Status).body(result);
//        return ResponseEntity.ok(total);
    }
//-------------------------------GETALLUSSUARIOS-----------------------------------------------
    @GetMapping
    public ResponseEntity<Result> GetallUsuarios() {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.GetAll();
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
//---------------------------------ADD--------------------------------------------------
    @PostMapping("/add")
    public ResponseEntity<Result> addUsuario(@RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Add(usuarioJPA);
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
//---------------------------------------------ADD DIRECCION------------------------------------------
    @PostMapping("/add-direccion/{idUsuario}")
    public ResponseEntity<Result> addDireccion(@PathVariable("idUsuario") int idUsuario,@RequestBody DireccionJPA direccionJPA) {
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
    public ResponseEntity<Result> updateImagen(@PathVariable("idUsuario") int idUsuario,@RequestBody UsuarioJPA usuarioJPA) {
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
}
