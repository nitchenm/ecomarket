package com.acopl.microservice_user;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.acopl.microservice_user.controller.UserController;
import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.dto.UserDTO;
import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ActiveProfiles("test")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@duocuc.cl");
        user.setRol("USER");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@duocuc.cl");
        userDTO.setRol("USER");
    }

    @Test
    void testListAllUsers_withUsers() {
        when(userService.findall()).thenReturn(List.of(user));
        ResponseEntity<List<User>> response = userController.listAllUsers();
        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testListAllUsers_noUsers() {
        when(userService.findall()).thenReturn(List.of());
        ResponseEntity<List<User>> response = userController.listAllUsers();
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

    @Test
    void testFindAllSaleByUser() {
        when(userService.findAllSaleByUser(1L)).thenReturn(List.of(new SaleDTO()));
        ResponseEntity<List<SaleDTO>> response = userController.findAllSaleByUser(1L);
        assertEquals(200, response.getStatusCode().value());
    }
}