package com.acopl.microservice_branch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acopl.microservice_branch.dto.BranchDTO;
import com.acopl.microservice_branch.service.BranchService;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> listAllBranches() {
        List<BranchDTO> branches = branchService.findAll();
        return branches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(branches);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> saveBranch(@RequestBody BranchDTO newBranch) {
        BranchDTO branch = branchService.save(newBranch);
        return ResponseEntity.status(HttpStatus.CREATED).body(branch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> findById(@PathVariable Long id) {
        try {
            BranchDTO branch = branchService.findById(id);
            return ResponseEntity.ok(branch);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        try {
            BranchDTO updatedBranch = branchService.updateBranch(id, branchDTO);
            return ResponseEntity.ok(updatedBranch);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        try {
            branchService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}