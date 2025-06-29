package com.acopl.microservice_user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = MicroserviceUserApplicationTests.class)
class MicroserviceUserApplicationTests {

	//creo que es necesario dejarlo
	@Test
	void contextLoads() {
		// Context loading is tested by Spring Boot automatically.
	}

	//este test es para que no falle al iniciar la aplicacion
	//si falla, es porque es probable que la BBDD no está activa

	@Test
    void mainRunsWithoutExceptions() {
        MicroserviceUserApplication.main(new String[] {});
		
    }
}
