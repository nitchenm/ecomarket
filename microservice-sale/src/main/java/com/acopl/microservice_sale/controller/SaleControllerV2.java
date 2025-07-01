package com.acopl.microservice_sale.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acopl.microservice_sale.assembler.SaleModelAssembler;
import com.acopl.microservice_sale.dto.SaleDTO;
import com.acopl.microservice_sale.service.SaleService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/sales")
@Tag(name = "Ventas HATEOAS", description = "Operaciones HATEOAS para ventas")
public class SaleControllerV2 {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SaleDTO>>> listAllSales() {
        List<SaleDTO> sales = saleService.findAllSales();
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<SaleDTO>> saleModels = sales.stream()
            .map(assembler::toModel)
            .toList();
        return ResponseEntity.ok(
            CollectionModel.of(saleModels,
                linkTo(methodOn(SaleControllerV2.class).listAllSales()).withSelfRel()
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SaleDTO>> findById(@PathVariable Long id) {
        try {
            SaleDTO sale = saleService.findById(id);
            return ResponseEntity.ok(assembler.toModel(sale));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<SaleDTO>> saveSale(@RequestBody SaleDTO newSale) {
        SaleDTO sale = saleService.saveSale(newSale);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(assembler.toModel(sale));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
