package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.RESTCONTROLLER;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.Result;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo")
public class DemoRestController {
 
    @GetMapping("saludo")
    public String Saludo( @RequestParam("Nombre") String nombre){
        
        return "Hola "+ nombre;
    }
    
    @GetMapping("division")
    public ResponseEntity Division(@RequestParam("NumeroUno")int numeroUno, @RequestParam("NumeroDos")int numeroDos){
        Result result = new Result();
        try {
            if(numeroDos == 0){
                result.correct = false;
                result.errorMessage = "Syntax Error";
                result.Status = 400;
            } else{
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
    public ResponseEntity Multiplicacion(@RequestParam("NumeroUno") int numeroUno, @RequestParam("NumeroDos")int numeroDos){
    Result result = new Result();
        try {
            if(numeroDos == 0){
                result.correct = false;
                result.errorMessage = "No puede multiplicar por 0";
                result.Status = 400;
            } else{
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
    public ResponseEntity Suma(@RequestParam("Numero")List<Integer> numeros){
        Result result =new Result();
        int total = 0;
        try {
            for(Integer num : numeros){
                total +=num;
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
}
