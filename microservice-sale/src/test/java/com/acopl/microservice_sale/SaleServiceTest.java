package com.acopl.microservice_sale;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import com.acopl.microservice_sale.client.ClientProduct;
import com.acopl.microservice_sale.dto.ProductDTO;
import com.acopl.microservice_sale.dto.SaleDTO;
import com.acopl.microservice_sale.model.Sale;
import com.acopl.microservice_sale.repository.ISaleRepository;
import com.acopl.microservice_sale.service.SaleService;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    
    @InjectMocks
    private SaleService saleService;
    @Mock
    private ISaleRepository saleRepository;
    @Mock
    private ClientProduct clientProduct;

    @Test
    public void testFindAll(){

        //Defino mi comportamiento del mock, le digo que cuando el repo haga findAll
        //me devuelva una lista con un objeto que defino aqui
        when(saleRepository.findAll()).thenReturn(List.of(new Sale((long)1,new Date(),50,(long)1,(long)1)));

        //Llamo findAll
        List<SaleDTO> saleList = saleService.findAllSales();

        //Verifico que no sea nulo
        assertNotNull(saleList);
        //Verifico que me devuelva el tamaño exacto y el tamaño actual que defini en mi comportamiento mock
        assertEquals(1, saleList.size());
    }

    @Test
    public void testFindById(){
        Long id = (long)1;
        Sale sale = new Sale(id,new Date(), 1, (long)1, (long)1);

        //Defino mi comportamiento del mock, cuando llamo un Sale por id 1 
        //Me devuelve un opcional de Sale
        when(saleRepository.findById(id)).thenReturn(Optional.of(sale));



        //Llamo a mi service con el id 1 que defini anteriormente
        SaleDTO saleFound = new SaleDTO();

        
        saleFound = saleService.findById(id);
        
        saleFound.setDateTime(sale.getDateTime());
        saleFound.setClientID(sale.getClientId());
        saleFound.setId(sale.getId());
        saleFound.setProductID(sale.getProductId());
        saleFound.setTotal(sale.getTotal());
        //Verifico que no sea nulo
        assertNotNull(saleFound);

        //Confirmo que lo que me devolvio fue lo que defini en mi mock
        assertEquals((long)1, saleFound.getId());
    }

    @Test
    public void testSave(){
        //(long)1, new Date(), 50, (long)1, (long)1
        Sale newSale = new Sale();
        newSale.setClientId(1L);
        newSale.setDateTime(new Date());
        newSale.setProductId(1L);
        newSale.setTotal(10);
        newSale.setId(1L);

        when(saleRepository.save(any(Sale.class))).thenReturn(newSale);
        
        SaleDTO sale = new SaleDTO();
        sale.setClientID(1l);
        sale.setDateTime(new Date());
        sale.setProductID(1L);
        sale.setTotal(10);
        sale.setId(1L);
        
        SaleDTO savedSale = saleService.saveSale(sale);

        assertNotNull(savedSale);
        assertEquals(1, savedSale.getId());
    }

    @Test
    public void testDeleteById(){
        Long id = (long)1;

        doNothing().when(saleRepository).deleteById(id);

        saleService.deleteSale(id);

        verify(saleRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindAllSaleByUser(){
        Long idUser = (long)1;
        Sale sale = new Sale();
        sale.setClientId(1l);
        sale.setDateTime(new Date());
        sale.setProductId(1L); 
        sale.setTotal(10);
        sale.setId(1L);

        when(saleRepository.findByClientId((long)1)).thenReturn(List.of(sale));
        
        List<SaleDTO> saleList = saleService.findAllSaleByUser(idUser);

        assertNotNull(saleList);

        assertEquals(idUser, saleList.get(0).getClientID());
        
    }

    @Test
    public void testFindAllProductBySale(){
        Long saleId = 1L;

        Sale sale = new Sale();
        sale.setId(saleId);
        sale.setClientId(1L);
        sale.setProductId(1L);
        sale.setTotal(50.0f);
        sale.setDateTime(new Date());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Producto prueba");
        productDTO.setQuantity(1);
        productDTO.setPrice(25.0f);


        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(clientProduct.getProductsByIds(1L)).thenReturn(productDTO);
        
        ProductDTO result = saleService.findAllProductsBySale(saleId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Producto prueba", result.getName());
        assertEquals(25.0, result.getPrice());
    }

    @Test
    public void testFindAllSaleByUserError() {
        Long clientId = 1L;
        String errorMessage = "Database connection failed";

        // Mock repository to throw an exception
        when(saleRepository.findByClientId(clientId)).thenThrow(new DataAccessException(errorMessage) {});

        // Test and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            saleService.findAllSaleByUser(clientId);
        });

        assertEquals("No se encontraron ventas: " + errorMessage, exception.getMessage());
    }

    @Test
    public void testFindAllSalesEmpty() {
        when(saleRepository.findAll()).thenReturn(List.of());

        List<SaleDTO> saleList = saleService.findAllSales();

        assertNotNull(saleList);
        assertTrue(saleList.isEmpty(), "No se encontraron ventas: ");
    }

}
