package com.microservice_product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice_product.controller.ProductController;
import com.microservice_product.dto.ProductDTO;
import com.microservice_product.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testSaveProduct() throws Exception{
        ProductDTO productDTO = new ProductDTO((long)1, "Patata", 20, 2000);
        ProductDTO savedProductDTO = new ProductDTO((long)1, "Patata", 20, 2000);

        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(savedProductDTO);

        mockMvc.perform(post("/api/v1/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(productDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Patata"))
        .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    public void testFindById() throws Exception {
        
        Long id = (long) 1;
        ProductDTO productDTO = new ProductDTO(id, "Patata", 20, 2000);

        when(productService.findById(id)).thenReturn(productDTO);

        
        mockMvc.perform(get("/api/v1/product/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Patata"))
            .andExpect(jsonPath("$.quantity").value(20))
            .andExpect(jsonPath("$.price").value(2000));
    }


    @Test
    public void testFindAll_WithProducts() throws Exception {
        List<ProductDTO> productList = List.of(
            new ProductDTO((long)1, "Patata", 20, 2000),
            new ProductDTO((long)2, "Manzana", 10, 1500)
        );

        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/product"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Patata"))
            .andExpect(jsonPath("$[1].name").value("Manzana"));
    }


    @Test
    public void testFindAll_NoProducts() throws Exception {
        when(productService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/product"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateProduct_Success() throws Exception {
        Long id = (long)1;

        ProductDTO inputDTO = new ProductDTO(id, "Patata Actualizada", 50, 2500);
        ProductDTO existingDTO = new ProductDTO(id, "Patata", 20, 2000);

        // aca se simula que se encuentra el producto existente
        when(productService.findById(id)).thenReturn(existingDTO);
        // y aca se simula que el producto se guarda correctamente 
        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(inputDTO);

        mockMvc.perform(put("/api/v1/product/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(inputDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Patata Actualizada"))
            .andExpect(jsonPath("$.quantity").value(50))
            .andExpect(jsonPath("$.price").value(2500.0));
    }

    @Test
    public void testUpdateProduct_NotFound() throws Exception {
        Long id = (long)99;

        ProductDTO updatedDTO = new ProductDTO(id, "No existe", 0, 0);

        // Simular que el producto no se encuentra
        when(productService.findById(id)).thenThrow(new RuntimeException("Product not found"));

        mockMvc.perform(put("/api/v1/product/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteById_Success() throws Exception {
        Long id = 1L;

        // No hace falta simular nada si no lanza excepción
        doNothing().when(productService).deleteById(id);

        mockMvc.perform(delete("/api/v1/product/{id}", id))
            .andExpect(status().isNoContent());
    }


    @Test
    public void testDeleteById_NotFound() throws Exception {
        Long id = 99L;

        doThrow(new RuntimeException("Product not found")).when(productService).deleteById(id);

        mockMvc.perform(delete("/api/v1/product/{id}", id))
            .andExpect(status().isNotFound());
    }


    @Test
    public void testGetStockById_Success() throws Exception {
        Long id = 1L;

        when(productService.getStockById(id)).thenReturn(35);

        mockMvc.perform(get("/api/v1/product/{id}/stock", id))
            .andExpect(status().isOk())
            .andExpect(content().string("35"));
    }


    @Test
    public void testGetStockById_NotFound() throws Exception {
        Long id = 99L;

        when(productService.getStockById(id)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/v1/product/{id}/stock", id))
            .andExpect(status().isNotFound());
    }


    @Test
    public void testGetProductBySearchById() throws Exception {
        Long id = 1L;
        ProductDTO dto = new ProductDTO(id, "Patata", 10, 1999);

        when(productService.getProductsByIds(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/product/Search-by-id/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Patata"))
            .andExpect(jsonPath("$.quantity").value(10))
            .andExpect(jsonPath("$.price").value(1999.0));
    }

}
