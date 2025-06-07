package com.acopl.microservice_sale.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

    private Long id;
    private String name;
    private int quantity;
    private float price;

}
