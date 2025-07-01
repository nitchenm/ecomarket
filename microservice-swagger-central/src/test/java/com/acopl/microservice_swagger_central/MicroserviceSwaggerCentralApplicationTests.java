package com.acopl.microservice_swagger_central;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = MicroserviceSwaggerCentralApplication.class)
class MicroserviceSwaggerCentralApplicationTests {

    @Test
    void contextLoads() {
        // Context loading is tested by Spring Boot automatically.
    }
}