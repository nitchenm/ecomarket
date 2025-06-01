package com.microservice_product.controller;

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

import com.microservice_product.model.Product;
import com.microservice_product.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>>findAll(){
        List<Product> products = productService.findAll();

        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){

        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        
        Product newProduct = productService.saveProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        try {
            Product productToUpdate = productService.findById(id);

            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setQuantity(updatedProduct.getQuantity());
            productToUpdate.setPrice(updatedProduct.getPrice());

            productService.saveProduct(productToUpdate);

            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable Long id){
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> getStockById(@PathVariable Long id){
        try {
            int productStock = productService.getStockById(id);
            return ResponseEntity.ok(productStock);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
