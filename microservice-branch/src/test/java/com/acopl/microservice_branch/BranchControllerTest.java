package com.acopl.microservice_branch;

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
import org.springframework.http.ResponseEntity;

import com.acopl.microservice_branch.controller.BranchController;
import com.acopl.microservice_branch.dto.BranchDTO;
import com.acopl.microservice_branch.service.BranchService;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private BranchDTO branchDTO;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        branchDTO = new BranchDTO();
        branchDTO.setId(1L);
        branchDTO.setName("Sucursal");
        branchDTO.setAddress("Calle 123");
        branchDTO.setCity("Ciudad");
        branchDTO.setCountry("País");
    }

    @SuppressWarnings("null")
    @Test
    void testListAllBranches_withBranches() {
        when(branchService.findAll()).thenReturn(List.of(branchDTO));
        ResponseEntity<List<BranchDTO>> response = branchController.listAllBranches();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testListAllBranches_noBranches() {
        when(branchService.findAll()).thenReturn(List.of());
        ResponseEntity<List<BranchDTO>> response = branchController.listAllBranches();
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testFindById_found() {
        when(branchService.findById(1L)).thenReturn(branchDTO);
        ResponseEntity<BranchDTO> response = branchController.findById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(branchDTO, response.getBody());
    }

    @Test
    void testFindById_notFound() {
        when(branchService.findById(2L)).thenThrow(new RuntimeException());
        ResponseEntity<BranchDTO> response = branchController.findById(2L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testSaveBranch() {
        when(branchService.save(any(BranchDTO.class))).thenReturn(branchDTO);
        ResponseEntity<BranchDTO> response = branchController.saveBranch(branchDTO);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(branchDTO, response.getBody());
    }

    @Test
    void testUpdateBranch_found() {
        when(branchService.updateBranch(eq(1L), any(BranchDTO.class))).thenReturn(branchDTO);
        ResponseEntity<BranchDTO> response = branchController.updateBranch(1L, branchDTO);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(branchDTO, response.getBody());
    }

    @Test
    void testUpdateBranch_notFound() {
        when(branchService.updateBranch(eq(2L), any(BranchDTO.class))).thenThrow(new RuntimeException());
        ResponseEntity<BranchDTO> response = branchController.updateBranch(2L, branchDTO);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testDeleteBranch_success() {
        doNothing().when(branchService).deleteById(1L);
        ResponseEntity<Void> response = branchController.deleteBranch(1L);
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testDeleteBranch_notFound() {
        doThrow(new RuntimeException()).when(branchService).deleteById(2L);
        ResponseEntity<Void> response = branchController.deleteBranch(2L);
        assertEquals(404, response.getStatusCode().value());
    }
}