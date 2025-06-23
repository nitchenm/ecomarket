package com.acopl.microservice_branch;

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

import com.acopl.microservice_branch.controller.BranchController;
import com.acopl.microservice_branch.model.Branch;
import com.acopl.microservice_branch.service.BranchService;

class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private Branch branch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branch = new Branch();
        branch.setId(1L);
        branch.setName("Sucursal");
        branch.setAddress("Calle 123");
        branch.setCity("Ciudad");
        branch.setCountry("País");
    }

    @Test
    void testListAllBranches_withBranches() {
        when(branchService.findAll()).thenReturn(List.of(branch));
        ResponseEntity<List<Branch>> response = branchController.listAllBranches();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testListAllBranches_noBranches() {
        when(branchService.findAll()).thenReturn(List.of());
        ResponseEntity<List<Branch>> response = branchController.listAllBranches();
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testSaveBranch() {
        when(branchService.save(any(Branch.class))).thenReturn(branch);
        ResponseEntity<Branch> response = branchController.saveBranch(branch);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(branch, response.getBody());
    }

    @Test
    void testFindById_found() {
        when(branchService.findById(1L)).thenReturn(branch);
        ResponseEntity<Branch> response = branchController.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(branch, response.getBody());
    }

    @Test
    void testFindById_notFound() {
        when(branchService.findById(2L)).thenThrow(new RuntimeException());
        ResponseEntity<Branch> response = branchController.findById(2L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBranch_success() {
        when(branchService.updateBranch(eq(1L), any(Branch.class))).thenReturn(branch);
        ResponseEntity<Branch> response = branchController.updateBranch(1L, branch);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(branch, response.getBody());
    }

    @Test
    void testUpdateBranch_notFound() {
        when(branchService.updateBranch(eq(2L), any(Branch.class))).thenThrow(new RuntimeException());
        ResponseEntity<Branch> response = branchController.updateBranch(2L, branch);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteBranch_success() {
        doNothing().when(branchService).deleteById(1L);
        ResponseEntity<Void> response = branchController.deleteBranch(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteBranch_notFound() {
        doThrow(new RuntimeException()).when(branchService).deleteById(2L);
        ResponseEntity<Void> response = branchController.deleteBranch(2L);
        assertEquals(404, response.getStatusCodeValue());
    }
}