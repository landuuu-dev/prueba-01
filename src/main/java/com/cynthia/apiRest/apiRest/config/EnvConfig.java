package com.cynthia.apiRest.apiRest.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EnvConfig {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        // Validar variables obligatorias
        List<String> requiredVars = List.of("DB_URL", "DB_USERNAME", "DB_PASSWORD", "JWT_SECRET");
        for (String var : requiredVars) {
            if (dotenv.get(var) == null) {
                throw new IllegalStateException("Falta la variable requerida en .env: " + var);
            }
        }

        dotenv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
    }
}