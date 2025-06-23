package com.acopl.microservice_user;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.acopl.microservice_user.controller.UserController;
import com.acopl.microservice_user.dto.SaleDTO;
import com.acopl.microservice_user.model.User;
import com.acopl.microservice_user.service.UserService;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@duocuc.cl");
        user.setRol("USER");
    }

    /// DOCUEMTNAR LUEGO PARA QUE NO SE ME OLVIDE

    @Test
    void testListAllUsers_withUsers() {
        when(userService.findall()).thenReturn(List.of(user));
        ResponseEntity<List<User>> response = userController.listAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testListAllUsers_noUsers() {
        when(userService.findall()).thenReturn(List.of());
        ResponseEntity<List<User>> response = userController.listAllUsers();
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testSaveUser() {
        when(userService.save(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = userController.saveUser(user);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFindById_found() {
        when(userService.findById(1L)).thenReturn(user);
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFindById_notFound() {
        when(userService.findById(2L)).thenThrow(new RuntimeException());
        ResponseEntity<User> response = userController.findById(2L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateUser_found() {
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(1L, user);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void testUpdateUser_notFound() {
        when(userService.updateUser(eq(2L), any(User.class))).thenThrow(new RuntimeException());
        ResponseEntity<User> response = userController.updateUser(2L, user);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser_success() {
        doNothing().when(userService).deleteById(1L);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser_notFound() {
        doThrow(new RuntimeException()).when(userService).deleteById(2L);
        ResponseEntity<Void> response = userController.deleteUser(2L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAuthenticateById_success() {
        when(userService.authenticateById(1L, "test@duocuc.cl", "USER")).thenReturn(true);
        ResponseEntity<String> response = userController.authenticateById(1L, "test@duocuc.cl", "USER");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ID authentication successful", response.getBody());
    }

    @Test
    void testAuthenticateById_invalid() {
        when(userService.authenticateById(1L, "wrong@duocuc.cl", "USER")).thenReturn(false);
        ResponseEntity<String> response = userController.authenticateById(1L, "wrong@duocuc.cl", "USER");
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void testFindAllSaleByUser() {
        when(userService.findAllSaleByUser(1L)).thenReturn(List.of(new SaleDTO()));
        ResponseEntity<List<SaleDTO>> response = userController.findAllSaleByUser(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
}