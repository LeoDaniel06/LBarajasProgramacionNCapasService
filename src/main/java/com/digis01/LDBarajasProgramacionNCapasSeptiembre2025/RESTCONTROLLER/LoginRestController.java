package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.RESTCONTROLLER;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DTO.LoginRequest;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.DTO.TokenResponse;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.JwtService;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class LoginRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsJPAService userDetailsJPAService;

    public LoginRestController(AuthenticationManager authenticationManager,
            JwtService jwtService, UserDetailsJPAService userDetailsJPAService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsJPAService = userDetailsJPAService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
        (request.getUsername(),request.getPassword()));
        
        UserDetails userDetails = userDetailsJPAService.loadUserByUsername(request.getUsername());
        
        String token = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
