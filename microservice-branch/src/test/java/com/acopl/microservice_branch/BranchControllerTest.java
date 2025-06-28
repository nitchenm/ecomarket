package com.acopl.microservice_branch;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

import com.acopl.microservice_branch.controller.BranchController;
import com.acopl.microservice_branch.dto.BranchDTO;
import com.acopl.microservice_branch.service.BranchService;
import com.acopl.microservice_branch.assembler.BranchModelAssembler;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @Mock
    private BranchModelAssembler assembler;

    @InjectMocks
    private BranchController branchController;

    private BranchDTO branchDTO;
    private EntityModel<BranchDTO> branchEntityModel;

    @BeforeEach
    void setUp() {
        branchDTO = new BranchDTO();
        branchDTO.setId(1L);
        branchDTO.setName("Sucursal");
        branchDTO.setAddress("Calle 123");
        branchDTO.setCity("Ciudad");
        branchDTO.setCountry("País");

        branchEntityModel = EntityModel.of(branchDTO);
    }

    @Test
    void testListAllBranches_withBranches() {
        when(branchService.findAll()).thenReturn(List.of(branchDTO));
        when(assembler.toModel(branchDTO)).thenReturn(branchEntityModel);

        ResponseEntity<CollectionModel<EntityModel<BranchDTO>>> response = branchController.listAllBranches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getContent().isEmpty());
        assertTrue(response.getBody().getContent().stream()
            .anyMatch(entity -> entity.getContent().equals(branchDTO)));
    }

    @Test
    void testListAllBranches_noBranches() {
        when(branchService.findAll()).thenReturn(List.of());

        ResponseEntity<CollectionModel<EntityModel<BranchDTO>>> response = branchController.listAllBranches();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindById_found() {
        when(branchService.findById(1L)).thenReturn(branchDTO);
        when(assembler.toModel(branchDTO)).thenReturn(branchEntityModel);

        ResponseEntity<EntityModel<BranchDTO>> response = branchController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(branchDTO, response.getBody().getContent());
    }

    @Test
    void testFindById_notFound() {
        when(branchService.findById(2L)).thenThrow(new RuntimeException());

        ResponseEntity<EntityModel<BranchDTO>> response = branchController.findById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSaveBranch() {
        when(branchService.save(any(BranchDTO.class))).thenReturn(branchDTO);

        ResponseEntity<BranchDTO> response = branchController.saveBranch(branchDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(branchDTO, response.getBody());
    }

    @Test
    void testUpdateBranch_found() {
        when(branchService.updateBranch(eq(1L), any(BranchDTO.class))).thenReturn(branchDTO);

        ResponseEntity<BranchDTO> response = branchController.updateBranch(1L, branchDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(branchDTO, response.getBody());
    }

    @Test
    void testUpdateBranch_notFound() {
        when(branchService.updateBranch(eq(2L), any(BranchDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<BranchDTO> response = branchController.updateBranch(2L, branchDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBranch_success() {
        doNothing().when(branchService).deleteById(1L);

        ResponseEntity<Void> response = branchController.deleteBranch(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteBranch_notFound() {
        doThrow(new RuntimeException()).when(branchService).deleteById(2L);

        ResponseEntity<Void> response = branchController.deleteBranch(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}