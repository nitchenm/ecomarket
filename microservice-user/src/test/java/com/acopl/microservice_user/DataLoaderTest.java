package com.acopl.microservice_user;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.repository.UserRepository;

@ActiveProfiles("test")
class DataLoaderTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    void testRunInsertsUsers() throws Exception {
        MockitoAnnotations.openMocks(this);

        dataLoader.run();

        // Verifica que se hayan guardado 3 usuarios
        verify(userRepository, times(3)).save(any(User.class));
    }
}