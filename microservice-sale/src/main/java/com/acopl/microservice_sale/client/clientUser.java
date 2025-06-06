package com.acopl.microservice_sale.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-user", url = "localhost:8050")
public interface clientUser {

   
}
