package com.acopl.microservice_sale;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MicroserviceSaleApplicationTests {

	@Test
    void contextLoads() {
    }

    @Test
    void mainRunsWithoutExceptions() {
        MicroserviceSaleApplication.main(new String[] {});
    }
}
