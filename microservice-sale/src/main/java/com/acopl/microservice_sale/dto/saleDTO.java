package com.acopl.microservice_sale.dto;

import java.util.Date;

import lombok.Data;

@Data
public class saleDTO {
    private Long id;
    private Date dateTime;
    private float total;
    private Long clientID;
}
