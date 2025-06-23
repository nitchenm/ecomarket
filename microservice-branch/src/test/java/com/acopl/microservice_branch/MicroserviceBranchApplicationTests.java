package com.acopl.microservice_branch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroserviceBranchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainRunsWithoutExceptions() {
        MicroserviceBranchApplication.main(new String[] {});
    }
}