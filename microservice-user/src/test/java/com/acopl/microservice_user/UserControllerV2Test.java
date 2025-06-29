package com.acopl.microservice_user;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.acopl.microservice_user.assembler.UserModelAssembler;
import com.acopl.microservice_user.controller.UserControllerV2;
import com.acopl.microservice_user.dto.UserDTO;
import com.acopl.microservice_user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerV2Test {

    @Mock
    private UserService userService;

    @Mock
    private UserModelAssembler assembler;

    @InjectMocks
    private UserControllerV2 userControllerV2;

    private UserDTO userDTO;
    private EntityModel<UserDTO> entityModel;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@duocuc.cl");
        userDTO.setRol("USER");

        entityModel = EntityModel.of(userDTO);
    }

    @Test
    void testListAllUsers_withUsers() {
        when(userService.findall()).thenReturn(List.of(userDTO));
        when(assembler.toModel(userDTO)).thenReturn(entityModel);

        ResponseEntity<CollectionModel<EntityModel<UserDTO>>> response = userControllerV2.listAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getContent().isEmpty());
    }

    @Test
    void testListAllUsers_noUsers() {
        when(userService.findall()).thenReturn(List.of());

        ResponseEntity<CollectionModel<EntityModel<UserDTO>>> response = userControllerV2.listAllUsers();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindById_found() {
        when(userService.findById(1L)).thenReturn(userDTO);
        when(assembler.toModel(userDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<UserDTO>> response = userControllerV2.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO, response.getBody().getContent());
    }

    @Test
    void testFindById_notFound() {
        when(userService.findById(2L)).thenThrow(new RuntimeException());

        ResponseEntity<EntityModel<UserDTO>> response = userControllerV2.findById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSaveUser() {
        when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        when(assembler.toModel(userDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<UserDTO>> response = userControllerV2.saveUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody().getContent());
    }

    @Test
    void testUpdateUser_found() {
        when(userService.findById(eq(1L))).thenReturn(userDTO);
        when(userService.saveUser(any(UserDTO.class))).thenReturn(userDTO);
        when(assembler.toModel(userDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<UserDTO>> response = userControllerV2.updateUser(1L, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody().getContent());
    }

    @Test
    void testUpdateUser_notFound() {
        when(userService.findById(eq(2L))).thenThrow(new RuntimeException());

        ResponseEntity<EntityModel<UserDTO>> response = userControllerV2.updateUser(2L, userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser_success() {
        doNothing().when(userService).deleteById(1L);

        ResponseEntity<Void> response = userControllerV2.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUser_notFound() {
        doThrow(new RuntimeException()).when(userService).deleteById(2L);

        ResponseEntity<Void> response = userControllerV2.deleteUser(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}