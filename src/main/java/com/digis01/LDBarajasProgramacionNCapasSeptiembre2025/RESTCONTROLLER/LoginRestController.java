package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.RESTCONTROLLER;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DTO.LoginRequest;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DTO.TokenResponse;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.JPA.UsuarioJPA;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.JwtService;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class LoginRestController {

    @Autowired
    private IUsuarioRepositoryDAO usuarioRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsJPAService userDetailsJPAService;

    public LoginRestController(AuthenticationManager authenticationManager,
                               JwtService jwtService, 
                               UserDetailsJPAService userDetailsJPAService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsJPAService = userDetailsJPAService;
    }

    // -------------------------------
    // LOGIN: genera el token
    // -------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsJPAService.loadUserByUsername(request.getUsername());
        UsuarioJPA usuario = usuarioRepository.findByUserName(request.getUsername());

        String token = jwtService.generateToken(userDetails, usuario.getIdUsuario());
        return ResponseEntity.ok(new TokenResponse(token));
    }


    // -------------------------------
    // NUEVO ENDPOINT
    // Obtiene información del token
    // -------------------------------
    @GetMapping("/user-info")
    public Map<String, Object> obtenerInfo(@RequestHeader("Authorization") String token) {

        // quitar "Bearer "
        token = token.replace("Bearer ", "");

        Claims claims = jwtService.extractAllClaims(token);

        Map<String, Object> data = new HashMap<>();
        data.put("idUsuario", claims.get("idUsuario", Integer.class));
        data.put("role", claims.get("Authorities", String.class));
        data.put("username", claims.getSubject());

        return data;
    }
}