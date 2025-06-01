package com.microservice_product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice_product.model.Product;
import com.microservice_product.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).get();
    }

    public Product saveProduct(Product newProduct){
        return productRepository.save(newProduct);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
    
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));

        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setQuantity(updatedProduct.getQuantity());
        productToUpdate.setPrice(updatedProduct.getPrice());

        return productRepository.save(productToUpdate);
    }

    public void deleteById(Long id){

        productRepository.deleteById(id);
    }

    public int getStockById(Long id){
        Product productToConsult = productRepository.findById(id).get();

        return productToConsult.getQuantity();
    }

}
