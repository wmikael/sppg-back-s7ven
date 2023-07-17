package br.ufma.sppg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Mapeia todas as rotas
                .allowedOrigins("http://localhost:5173") // Permite todas as origens
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                .allowCredentials(true) // Permite o envio de cookies
                .maxAge(3600); // Tempo máximo de cache das configurações CORS (em segundos)
    }
}
