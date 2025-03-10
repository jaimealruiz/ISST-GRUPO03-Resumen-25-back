package com.isst.resumenes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Ejemplo de stack dde archivo de configuracion. permite sin nada a la lista
    //Falta impleemtnar JWT para el resto de los endpoint
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register", "/auth/login", "/resumenes/ejemplo").permitAll()  
                .anyRequest().authenticated()  
            );

        return http.build();
    }
}
