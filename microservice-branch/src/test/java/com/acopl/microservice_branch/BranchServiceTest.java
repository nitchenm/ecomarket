package com.acopl.microservice_branch;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.acopl.microservice_branch.model.Branch;
import com.acopl.microservice_branch.repository.BranchRepository;
import com.acopl.microservice_branch.service.BranchService;

class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

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
    void testFindAll() {
        when(branchRepository.findAll()).thenReturn(List.of(branch));
        List<Branch> result = branchService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindById_found() {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        Branch result = branchService.findById(1L);
        assertEquals(branch, result);
    }

    @Test
    void testFindById_notFound() {
        when(branchRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> branchService.findById(2L));
    }

    @Test
    void testSave() {
        when(branchRepository.save(branch)).thenReturn(branch);
        Branch result = branchService.save(branch);
        assertEquals(branch, result);
    }

    @Test
    void testDeleteById_success() {
        when(branchRepository.existsById(1L)).thenReturn(true);
        doNothing().when(branchRepository).deleteById(1L);
        branchService.deleteById(1L);
        verify(branchRepository).deleteById(1L);
    }

    @Test
    void testDeleteById_notFound() {
        when(branchRepository.existsById(2L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> branchService.deleteById(2L));
    }

    @Test
    void testUpdateBranch_success() {
        Branch updated = new Branch();
        updated.setName("Nueva");
        updated.setAddress("Nueva Dir");
        updated.setCity("Nueva Ciudad");
        updated.setCountry("Nuevo País");

        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenAnswer(inv -> inv.getArgument(0));

        Branch result = branchService.updateBranch(1L, updated);

        assertEquals("Nueva", result.getName());
        assertEquals("Nueva Dir", result.getAddress());
        assertEquals("Nueva Ciudad", result.getCity());
        assertEquals("Nuevo País", result.getCountry());
    }

    @Test
    void testUpdateBranch_notFound() {
        when(branchRepository.findById(2L)).thenReturn(Optional.empty());
        Branch updated = new Branch();
        assertThrows(RuntimeException.class, () -> branchService.updateBranch(2L, updated));
    }
}