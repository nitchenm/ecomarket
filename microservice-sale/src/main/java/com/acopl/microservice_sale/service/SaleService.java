package com.acopl.microservice_sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acopl.microservice_sale.client.ClientProduct;
import com.acopl.microservice_sale.dto.ProductDTO;
import com.acopl.microservice_sale.dto.saleDTO;
import com.acopl.microservice_sale.model.Sale;
import com.acopl.microservice_sale.repository.ISaleRepository;


@Service
public class SaleService {

    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private ClientProduct clientProduct;

    public List<Sale> findAllSales(){
        return saleRepository.findAll();
    }

    public saleDTO findById(Long id){
        Sale newSale = saleRepository.findById(id).orElseThrow(()-> new RuntimeException("Sale not found"));

        saleDTO newSaleDTO = new saleDTO();

        newSaleDTO.setClientID(newSale.getClientId());
        newSaleDTO.setDateTime(newSale.getDateTime());
        newSaleDTO.setTotal(newSale.getTotal());
        newSaleDTO.setId(newSale.getId());
        newSaleDTO.setProductID(newSale.getProductId());


        return newSaleDTO;

    }
    
    public saleDTO saveSale(saleDTO saleDTO){
        Sale newSale = new Sale();

        newSale.setClientId(saleDTO.getClientID());
        newSale.setDateTime(saleDTO.getDateTime());
        newSale.setId(saleDTO.getId());
        newSale.setProductId(saleDTO.getProductID());
        newSale.setTotal(saleDTO.getTotal());

        Sale savedSale = saleRepository.save(newSale);

        saleDTO returningSale = new saleDTO();

        returningSale.setClientID(savedSale.getClientId());
        returningSale.setDateTime(savedSale.getDateTime());
        returningSale.setId(savedSale.getId());
        returningSale.setProductID(savedSale.getProductId());
        returningSale.setTotal(savedSale.getTotal());

        return returningSale;
    }

    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }

    public List<Sale> findAllSaleByUser(Long id){
        return saleRepository.findByClientId(id);
    }


   public ProductDTO findAllProductsBySale(Long saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

        return clientProduct.getProductsByIds(saleId);
    }


}
