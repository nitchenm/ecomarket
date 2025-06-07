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

import com.acopl.microservice_branch.model.Branch;
import com.acopl.microservice_branch.service.BranchService;

@RestController
//contiene la url
@RequestMapping("/api/v1/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> listAllBranches() {
        List<Branch> branches = branchService.findAll();
        return branches.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(branches);
    }

    @PostMapping
    public ResponseEntity<Branch> saveBranch(@RequestBody Branch newBranch) {
        Branch branch = branchService.save(newBranch);
        return ResponseEntity.status(HttpStatus.CREATED).body(branch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> findById(@PathVariable Long id) {
        try {
            Branch branch = branchService.findById(id);
            return ResponseEntity.ok(branch);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        try {
            Branch updatedBranch = branchService.updateBranch(id, branch);
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
