package com.microservice_product;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservice_product.assembler.ProductModelAssembler;
import com.microservice_product.controller.ProductControllerV2;
import com.microservice_product.dto.ProductDTO;
import com.microservice_product.service.ProductService;


@ExtendWith(MockitoExtension.class)
class ProductControllerV2Test {

    @Mock
    private ProductService productService;

    @Mock
    private ProductModelAssembler assembler;

    @InjectMocks
    private ProductControllerV2 productControllerV2;

    private ProductDTO productDTO;
    private EntityModel<ProductDTO> entityModel;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Patata");
        productDTO.setQuantity(20);
        productDTO.setPrice(2000);

        entityModel = EntityModel.of(productDTO);
    }

    @Test
    void testListAllProducts_withProducts() {
        when(productService.findAll()).thenReturn(List.of(productDTO));
        when(assembler.toModel(productDTO)).thenReturn(entityModel);

        ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> response = productControllerV2.listAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getContent().isEmpty());
    }

    @Test
    void testListAllProducts_noProducts() {
        when(productService.findAll()).thenReturn(List.of());

        ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> response = productControllerV2.listAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindById_found() {
        when(productService.findById(1L)).thenReturn(productDTO);
        when(assembler.toModel(productDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<ProductDTO>> response = productControllerV2.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO, response.getBody().getContent());
    }

    @Test
    void testFindById_notFound() {
        when(productService.findById(2L)).thenThrow(new RuntimeException());

        ResponseEntity<EntityModel<ProductDTO>> response = productControllerV2.findById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSaveProduct() {
        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(productDTO);
        when(assembler.toModel(productDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<ProductDTO>> response = productControllerV2.saveProduct(productDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDTO, response.getBody().getContent());
    }

    @Test
    void testUpdateProduct_found() {
        when(productService.findById(eq(1L))).thenReturn(productDTO);
        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(productDTO);
        when(assembler.toModel(productDTO)).thenReturn(entityModel);

        ResponseEntity<EntityModel<ProductDTO>> response = productControllerV2.updateProduct(1L, productDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody().getContent());
    }

    @Test
    void testUpdateProduct_notFound() {
        when(productService.findById(eq(2L))).thenThrow(new RuntimeException());

        ResponseEntity<EntityModel<ProductDTO>> response = productControllerV2.updateProduct(2L, productDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_success() {
        doNothing().when(productService).deleteById(1L);

        ResponseEntity<Void> response = productControllerV2.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_notFound() {
        doThrow(new RuntimeException()).when(productService).deleteById(2L);

        ResponseEntity<Void> response = productControllerV2.deleteProduct(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
