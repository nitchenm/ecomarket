package com.acopl.microservice_branch;

import com.acopl.microservice_branch.assembler.BranchModelAssembler;
import com.acopl.microservice_branch.dto.BranchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.*;

class BranchModelAssemblerTest {

    private BranchModelAssembler assembler;
    private BranchDTO branchDTO;

    @BeforeEach
    void setUp() {
        assembler = new BranchModelAssembler();
        branchDTO = new BranchDTO();
        branchDTO.setId(1L);
        branchDTO.setName("Sucursal Centro");
        branchDTO.setAddress("Av. Principal 123");
        branchDTO.setCity("Santiago");
        branchDTO.setCountry("Chile");
    }

    @Test
    void testToModel_addsLinks() {
        EntityModel<BranchDTO> model = assembler.toModel(branchDTO);

        assertNotNull(model.getContent());
        assertEquals(branchDTO, model.getContent());

        // Verifica que el self link esté presente
        Link selfLink = model.getLink("self").orElse(null);
        assertNotNull(selfLink);
        assertTrue(selfLink.getHref().contains("/api/v1/branches/1"));

        // Verifica que el link de branches esté presente
        Link branchesLink = model.getLink("branches").orElse(null);
        assertNotNull(branchesLink);
        assertTrue(branchesLink.getHref().contains("/api/v1/branches"));
    }
}