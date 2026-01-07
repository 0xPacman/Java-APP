package com.journalintime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Test de démarrage de l'application Spring Boot.
 */
@SpringBootTest(classes = com.journalintime.infrastructure.config.ApplicationConfig.class)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class JournalIntimeApplicationTests {

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
    }
}
