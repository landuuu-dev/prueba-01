package com.cynthia.apiRest.apiRest.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EnvConfigTest {

    @Autowired
    private EnvConfig envConfig;

    @Test
    public void testEnvVarsLoaded() {
        assertNotNull(envConfig.getDbUrl());
        assertNotNull(envConfig.getDbUsername());
        assertNotNull(envConfig.getDbPassword());

        System.out.println("DB_URL: " + envConfig.getDbUrl());
        System.out.println("DB_USER: " + envConfig.getDbUsername());
        System.out.println("DB_PASS: " + envConfig.getDbPassword().replaceAll(".", "*")); // Oculta la contraseña
    }
}