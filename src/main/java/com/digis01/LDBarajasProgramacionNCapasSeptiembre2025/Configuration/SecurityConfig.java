package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Configuration;

import org.springframework.context.annotation.Configuration;
import com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final UserDetailsJPAService userDetailsJPAService;

    public SecurityConfig(UserDetailsJPAService userDetailsJPAService) {
        this.userDetailsJPAService = userDetailsJPAService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(
                        "/api/usuario/**",
                        "/api/usuario/roles/**",
                        "/api/usuario/paises/**",
                        "/api/usuario/estados/**",
                        "/api/usuario/municipios/**",
                        "/api/usuario/colonias/**",
                        "/api/usuario/add/**",
                        "/api/usuario/add-direccion/**",
                        "/api/usuario/update-imagen/**",
                        "/api/usuario/update/**",
                        "/api/usuario/update-direccion/**",
                        "/api/usuario/delete-usuario/**",
                        "/api/usuario/delete-direccion/**",
                        "/api/usuario/busqueda/**",
                        "/api/usuario/update-status/**",
                        "/api/usuario/codigopostal/**",
                        "/api/usuario/detail/**",
                        "/api/usuario/direccion/**"
                ).hasRole("ADMINISTRADOR")
                .requestMatchers("/api/usuario/detail/**",
                        "/api/usuario/roles/**",
                        "/api/usuario/paises/**",
                        "/api/usuario/estados/**",
                        "/api/usuario/municipios/**",
                        "/api/usuario/colonias/**",
                        "/api/usuario/add-direccion/**",
                        "/api/usuario/update-imagen/**",
                        "/api/usuario/update-direccion/**",
                        "/api/usuario/delete-direccion/**",
                        "/api/usuario/codigopostal/**",
                        "/api/usuario/direccion/**")
                .hasAnyRole("ASISTENTE", "GERENTE", "SUBGERENTE", "COLABORADOR")
                .anyRequest().authenticated()
                )
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081")); // tu cliente
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
