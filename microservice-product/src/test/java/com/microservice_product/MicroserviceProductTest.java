package com.microservice_product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


import com.microservice_product.model.Product;
import com.microservice_product.repository.ProductRepository;
import com.microservice_product.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class MicroserviceProductTest {


    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testFindAll(){
        when(productRepository.findAll()).thenReturn(List.of(new Product((long)1, "Patata", 20, 2000)));

        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertEquals(1, products.size());
    }

    @Test
    public void testFindById(){
        Long id = (long)1;
        Product product = new Product(id, "Patata", 20, 2000);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product found = productService.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }


    @Test
    public void testSave(){
        Product product = new Product((long)1, "Patata", 20,2000);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        assertEquals("Patata", savedProduct.getName());
    }

    @Test
    public void testDeleteById(){
        Long id = (long)1;
        doNothing().when(productRepository).deleteById(id);

        productService.deleteById(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}
