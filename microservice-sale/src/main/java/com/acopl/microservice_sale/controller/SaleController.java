package com.acopl.microservice_sale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acopl.microservice_sale.dto.ProductDTO;
import com.acopl.microservice_sale.model.Sale;
import com.acopl.microservice_sale.service.SaleService;




@Controller
@RequestMapping("/api/v1/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<Sale> saveSale(@RequestBody Sale sale) {
        Sale newSale = saleService.saveSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }

    @GetMapping("{id}")
    public ResponseEntity<Sale> findById(@PathVariable Long id) {
        try {
            Sale saleFound = saleService.findById(id);
            return ResponseEntity.ok(saleFound);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Sale>> findAllSales() {
        List<Sale> saleList = saleService.findAllSales();
        return saleList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(saleList);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(Long id){
        try {
            saleService.deleteSale(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<List<Sale>> findAllSaleByUser(@PathVariable Long id) {
        saleService.findAllSaleByUser(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/search-products-by-id/{id}")
    public ResponseEntity<List<ProductDTO>> findAllProductsBySale(@PathVariable Long id){
        List<ProductDTO> productsBySale = saleService.findAllProductsBySale(id);
        return ResponseEntity.ok(productsBySale);
    }
    
    
}
