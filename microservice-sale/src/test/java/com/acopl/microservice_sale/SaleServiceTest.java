package com.acopl.microservice_sale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acopl.microservice_sale.model.Sale;
import com.acopl.microservice_sale.repository.ISaleRepository;
import com.acopl.microservice_sale.service.SaleService;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    
    @InjectMocks
    private SaleService saleService;
    @Mock
    private ISaleRepository saleRepository;

    @Test
    public void testFindAll(){

        //Defino mi comportamiento del mock, le digo que cuando el repo haga findAll
        //me devuelva una lista con un objeto que defino aqui
        when(saleRepository.findAll()).thenReturn(List.of(new Sale((long)1,new Date(),50,(long)1,(long)1)));

        //Llamo findAll
        List<Sale> saleList = saleService.findAllSales();

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
        Sale saleFound = saleService.findById(id);

        //Verifico que no sea nulo
        assertNotNull(saleFound);

        //Confirmo que lo que me devolvio fue lo que defini en mi mock
        assertEquals((long)1, saleFound.getId());
    }

    @Test
    public void testSave(){
        Sale sale = new Sale((long)1, new Date(), 50, (long)1, (long)1);

        when(saleRepository.save(sale)).thenReturn(sale);

        Sale savedSale = saleService.saveSale(sale);

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


}
