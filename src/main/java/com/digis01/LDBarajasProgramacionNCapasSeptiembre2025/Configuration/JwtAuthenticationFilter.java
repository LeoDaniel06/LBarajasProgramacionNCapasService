package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Configuration;

import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.JwtService;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsJPAService userDetailsJPAService;
    private final TokenRateLimiter rateLimiter;

    public JwtAuthenticationFilter(JwtService jwtService,
            UserDetailsJPAService userDetailsJPAService,
            TokenRateLimiter rateLimiter) {
        this.jwtService = jwtService;
        this.userDetailsJPAService = userDetailsJPAService;
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth/")
                || path.startsWith("/api/usuario/verificacion/")
                || path.startsWith("/api/usuario/reenviar-verificacion")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        boolean isSensitiveOperation
                = path.contains("/api/usuario/add")
                || path.contains("/api/usuario/update")
                || path.contains("/api/usuario/delete-usuario")
                || path.contains("/api/usuario/update-status")
                || path.contains("/api/usuario/add-direccion")
                || path.contains("/api/usuario/update-imagen")
                || path.contains("/api/usuario/update-direccion")
                || path.contains("/api/usuario/delete-direccion")
                || path.contains("/api/usuario/busqueda")
                || request.getMethod().equals("DELETE");

        if (isSensitiveOperation) {
            if (!rateLimiter.isAllowed(token)) {
                SecurityContextHolder.clearContext();
                response.setStatus(440);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"error\": \"rate_limit\", \"message\": \"Sesión bloqueada por exceso de solicitudes\"}"
                );
                return;
            }
        }

        String username = jwtService.extractUsername(token);

        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsJPAService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
