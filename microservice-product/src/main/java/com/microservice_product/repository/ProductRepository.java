package com.microservice_product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice_product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
