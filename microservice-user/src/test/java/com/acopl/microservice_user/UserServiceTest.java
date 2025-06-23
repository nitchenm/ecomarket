package com.acopl.microservice_user;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acopl.microservice_user.client.clientSale;
import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.repository.UserRepository;
import com.acopl.microservice_user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private clientSale clientSale;

    @InjectMocks
    private UserService userService;

    private User user;

    // IMPORTANTE DOCUEMNTAR TODO ESTO PARA QUE NO SE ME OLVIDE DESPUESSSS
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@duocuc.cl");
        user.setRol("USER");
    }

    //////// PARA EL METODO findall
    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.findall();
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    //////// PARA EL METODO save 
    @Test
    void testSave() {
        when(userRepository.save(user)).thenReturn(user);
        User saved = userService.save(user);
        assertNotNull(saved);
        assertEquals("Test User", saved.getName());
    }

    //////// PARA EL METODO deleteById 
    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    //////// PARA EL METODO updateUser //////////
    @Test
    void testUpdateUser() {
        User updated = new User();
        updated.setName("Nuevo nombre");
        updated.setEmail("otromaild@duocuc.cl");
        updated.setRol("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updated);

        assertEquals("Nuevo nombre", result.getName());
        assertEquals("otromaild@duocuc.cl", result.getEmail());
        assertEquals("ADMIN", result.getRol());
        verify(userRepository).save(any(User.class));
    }


    //////// PARA EL METEDO authenticateById //////////
    /// NO SACAR PORQUE ESTO HACE QUE LOGRE EL 100%
    @Test
    void testAuthenticateById_true() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "test@duocuc.cl", "USER");
        assertTrue(authenticated);
    }

    @Test
    void testAuthenticateById_false_wrongEmail() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "otro@duocuc.cl", "USER");
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_false_wrongRol() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "test@duocuc.cl", "ADMIN");
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_userNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        boolean authenticated = userService.authenticateById(99L, "test@duocuc.cl", "USER");
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_emailNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, null, "USER");
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_rolNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "test@duocuc.cl", null);
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_emailAndRolNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, null, null);
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_userEmailNull() {
        user.setEmail(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "test@duocuc.cl", "USER");
        assertFalse(authenticated);
    }

    @Test
    void testAuthenticateById_userRolNull() {
        user.setRol(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean authenticated = userService.authenticateById(1L, "test@duocuc.cl", "USER");
        assertFalse(authenticated);
    }

    //////// PARA EL METODO findById
    @Test
    void testFindAllSaleByUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<SaleDTO> sales = List.of(new SaleDTO());
        when(clientSale.findAllSaleByUser(1L)).thenReturn(sales);

        List<SaleDTO> result = userService.findAllSaleByUser(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

}