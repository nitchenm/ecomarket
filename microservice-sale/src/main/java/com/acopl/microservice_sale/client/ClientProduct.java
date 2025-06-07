package com.acopl.microservice_sale.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acopl.microservice_sale.dto.ProductDTO;

@FeignClient(name = "msvc-product", url = "localhost:8060")
public interface ClientProduct {

    @GetMapping("api/v1/product/search-by-id/{id}")
    List<ProductDTO> getProductsByIds(@RequestParam List<Long> ids);

}
