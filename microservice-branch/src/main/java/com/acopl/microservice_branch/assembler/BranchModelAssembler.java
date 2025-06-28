package com.acopl.microservice_branch.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.acopl.microservice_branch.controller.BranchController;
import com.acopl.microservice_branch.dto.BranchDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BranchModelAssembler implements RepresentationModelAssembler<BranchDTO, EntityModel<BranchDTO>> {

    @Override
    public EntityModel<BranchDTO> toModel(BranchDTO branch) {
        return EntityModel.of(branch,
            linkTo(methodOn(BranchController.class).findById(branch.getId())).withSelfRel(),
            linkTo(methodOn(BranchController.class).listAllBranches()).withRel("branches")
        );
    }
}