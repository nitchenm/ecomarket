package com.acopl.microservice_user;

import java.util.List;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.acopl.microservice_user.controller.UserController;
import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.dto.UserDTO;
import com.acopl.microservice_user.service.UserService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private SaleDTO saleDTO;

    
    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@duocuc.cl");
        userDTO.setRol("USER");

        saleDTO = new SaleDTO();
    }

    @SuppressWarnings("null")
    @Test
    void testListAllUsers_withUsers() {
        when(userService.findall()).thenReturn(List.of(userDTO));
        ResponseEntity<List<UserDTO>> response = userController.listAllUsers();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testListAllUsers_noUsers() {
        when(userService.findall()).thenReturn(List.of());
        ResponseEntity<List<UserDTO>> response = userController.listAllUsers();
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testFindById_found() {
        when(userService.findById(1L)).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.findById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void testFindById_notFound() {
        when(userService.findById(2L)).thenThrow(new RuntimeException());
        ResponseEntity<UserDTO> response = userController.findById(2L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testSaveUser() {
        when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.saveUser(userDTO);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void testUpdateUser_found() {
        when(userService.findById(1L)).thenReturn(userDTO);
        when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.updateUser(1L, userDTO);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void testUpdateUser_notFound() {
        when(userService.findById(2L)).thenThrow(new RuntimeException());
        ResponseEntity<UserDTO> response = userController.updateUser(2L, userDTO);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteUser_success() {
        doNothing().when(userService).deleteById(1L);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testDeleteUser_notFound() {
        doThrow(new RuntimeException()).when(userService).deleteById(2L);
        ResponseEntity<Void> response = userController.deleteUser(2L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testAuthenticateById_success() {
        when(userService.authenticateById(1L, "test@duocuc.cl", "USER")).thenReturn(true);
        ResponseEntity<String> response = userController.authenticateById(1L, "test@duocuc.cl", "USER");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("ID authentication successful", response.getBody());
    }

    @Test
    void testAuthenticateById_invalid() {
        when(userService.authenticateById(1L, "wrong@duocuc.cl", "USER")).thenReturn(false);
        ResponseEntity<String> response = userController.authenticateById(1L, "wrong@duocuc.cl", "USER");
        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid credentials", response.getBody());
    }

    // --- TESTS PARA EL MÉTODO DE VENTAS POR USUARIO ---

    @SuppressWarnings("null")
    @Test
    void testFindAllSaleByUser_success() {
        when(userService.findAllSaleByUser(1L)).thenReturn(List.of(saleDTO));
        ResponseEntity<List<SaleDTO>> response = userController.findAllSaleByUser(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @SuppressWarnings("null")
    @Test
    void testFindAllSaleByUser_emptyList() {
        when(userService.findAllSaleByUser(1L)).thenReturn(List.of());
        ResponseEntity<List<SaleDTO>> response = userController.findAllSaleByUser(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testFindAllSaleByUser_notFound() {
        when(userService.findAllSaleByUser(2L)).thenThrow(new RuntimeException());
        ResponseEntity<List<SaleDTO>> response = userController.findAllSaleByUser(2L);
        assertEquals(404, response.getStatusCode().value());
    }
}