package com.acopl.microservice_sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.acopl.microservice_sale.controller.SaleController;
import com.acopl.microservice_sale.dto.ProductDTO;
import com.acopl.microservice_sale.dto.SaleDTO;
import com.acopl.microservice_sale.service.SaleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SaleController.class)
public class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    //Hago un mock de mi servicio 
    @MockBean
    private SaleService saleService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createSaleControllerTest() throws JsonProcessingException, Exception{
        String uri = "/api/v1/sale/create";
        SaleDTO sale = new SaleDTO();
        sale.setClientID(1L);
        sale.setProductID(1L);
        sale.setTotal(1999.98F);
        sale.setDateTime(new Date());
        sale.setId(1L);

        when(saleService.saveSale(any(SaleDTO.class))).thenReturn(sale);


        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(sale)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productID").value("1"));
    }

    @Test
    public void findSaleByIdControllerTest() throws JsonProcessingException, Exception{
      String uri = "/api/v1/sale/1";

      SaleDTO sale = new SaleDTO();
      sale.setClientID(1L);
      sale.setProductID(1L);
      sale.setTotal(1999.98F);
      sale.setDateTime(new Date());
      sale.setId(1L);

      when(saleService.findById(any(Long.TYPE))).thenReturn(sale);

      mockMvc.perform(MockMvcRequestBuilders.get(uri)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value("1"));
               
    }

    @Test
    public void findAllSalesControllerTest() throws JsonProcessingException, Exception{
      String uri = "/api/v1/sale";

      SaleDTO sale = new SaleDTO();
      sale.setClientID(1L);
      sale.setProductID(1L);
      sale.setTotal(1999.98F);
      sale.setDateTime(new Date());
      sale.setId(1L);

      List<SaleDTO> saleList = saleService.findAllSales();
      saleList.add(sale);

      when(saleService.findAllSales()).thenReturn(saleList);

      mockMvc.perform(MockMvcRequestBuilders.get(uri)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
               
    }

    @Test
    public void deleteSaleByIdControllerTest() throws JsonProcessingException, Exception{
      String uri = "/api/v1/sale/1";
      Long id = 1L;

      doNothing().when(saleService).deleteSale(id);

      mockMvc.perform(delete(uri))
              .andExpect(status().isOk());

    }

    @Test
    public void findAllSaleByUserControllerTest() throws JsonProcessingException, Exception{
      String uri = "/api/v1/sale/search-by-id/1";

      List<SaleDTO> saleListDTO = new ArrayList<>();

      SaleDTO sale = new SaleDTO();
      sale.setClientID(1L);
      sale.setProductID(1L);
      sale.setTotal(1999.98F);
      sale.setDateTime(new Date());
      sale.setId(1L);
      
      saleListDTO.add(sale);

      when(saleService.findAllSaleByUser(sale.getClientID())).thenReturn(saleListDTO);
      
      mockMvc.perform(MockMvcRequestBuilders.get(uri)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
      
    }

    @Test
    public void findAllProductsBySale() throws JsonProcessingException, Exception{
      String uri = "/api/v1/sale/search-products-by-id/1";

      SaleDTO sale = new SaleDTO();
      sale.setClientID(1L);
      sale.setProductID(1L);
      sale.setTotal(1999.98F);
      sale.setDateTime(new Date());
      sale.setId(1L);

      ProductDTO product = new ProductDTO();
      product.setId(1L);
      product.setName("producto1");
      product.setPrice(200f);
      product.setQuantity(2);
      
      when(saleService.findAllProductsBySale(sale.getId())).thenReturn(product);

      mockMvc.perform(MockMvcRequestBuilders.get(uri)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(status().isOk());
    }
  
    public void findAllSaleControllerTest()throws JsonProcessingException, Exception{
        String uri = "/api/v1/sale";
        List<SaleDTO> saleList = new ArrayList<>();
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setClientID(1L);
        saleDTO.setProductID(1L);
        saleDTO.setTotal(1999.98F);
        saleDTO.setDateTime(new Date());
        saleDTO.setId(1L);

        saleList.add(saleDTO);

        when(saleService.findAllSales()).thenReturn(saleList);

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }


    
}
