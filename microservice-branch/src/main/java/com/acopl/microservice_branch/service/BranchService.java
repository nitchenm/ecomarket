package com.acopl.microservice_branch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acopl.microservice_branch.model.Branch;
import com.acopl.microservice_branch.repository.BranchRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    //obtiene todas las sucursales
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    //obtiene una sucursal por id
    public Branch findById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    //guarda una nueva sucursal
    public Branch save(Branch branch) {
        // ¿Validaciones? ¿Evitar duplicados?
        return branchRepository.save(branch);
    }

    //elimina una sucursal por id
    public void deleteById(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    //actualiza una sucursal
    public Branch updateBranch(Long id, Branch updatedBranch) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));

        existingBranch.setName(updatedBranch.getName());
        existingBranch.setAddress(updatedBranch.getAddress());
        existingBranch.setCity(updatedBranch.getCity());
        existingBranch.setCountry(updatedBranch.getCountry());

        return branchRepository.save(existingBranch);
    }
}
