package com.cynthia.apiRest.apiRest.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EnvConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
    @PostConstruct
    public void loadEnv() {

        Dotenv dotenv = Dotenv.configure().load();


        // Validar variables obligatorias
        List<String> requiredVars = List.of("SPRING_DATASOURCE_URL", "SPRING_DATASOURCE_USERNAME", "SPRING_DATASOURCE_PASSWORD", "JWT_SECRET");
        for (String var : requiredVars) {
            if (dotenv.get(var) == null) {
                throw new IllegalStateException("Falta la variable requerida en .env: " + var);
            }
        }

        // Establecer propiedades del sistema solo si no están ya definidas
        dotenv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
    }
}