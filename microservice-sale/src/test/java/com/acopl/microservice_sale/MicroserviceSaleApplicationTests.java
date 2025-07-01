package com.acopl.microservice_sale;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = MicroserviceSaleApplicationTests.class)
class MicroserviceSaleApplicationTests {

    @Test
    void contextLoads() {
        // Este test verifica que el contexto de Spring arranca correctamente.
    }
}