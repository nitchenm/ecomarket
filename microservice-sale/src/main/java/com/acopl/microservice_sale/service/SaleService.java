package com.acopl.microservice_sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acopl.microservice_sale.client.ClientProduct;
import com.acopl.microservice_sale.dto.ProductDTO;
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

    public Sale findById(Long id){
        return saleRepository.findById(id).orElseThrow(()-> new RuntimeException("Sale not found"));
    }
    
    public Sale saveSale(Sale sale){
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }

    public List<Sale> findAllSaleByUser(Long id){
        return saleRepository.findByClientId(id);
    }


    public List<ProductDTO> findAllProductsBySale(Long id){
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found."));

        List<ProductDTO> productsBySale = clientProduct.findAllProductsBySale(id);

        return productsBySale;
    }

}
