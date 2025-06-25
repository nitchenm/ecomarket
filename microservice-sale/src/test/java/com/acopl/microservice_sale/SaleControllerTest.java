package com.acopl.microservice_sale;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.acopl.microservice_sale.controller.SaleController;
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

    

    /*
     * @Test
         public void createProduct() throws Exception {
            String uri = "/products";
            Product product = new Product();
            product.setId("3");
            product.setName("Ginger");
            
            String inputJson = super.mapToJson(product);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
               .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
            
            int status = mvcResult.getResponse().getStatus();
            assertEquals(201, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "Product is created successfully");
         }
     */
    
}
