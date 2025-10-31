package com.bombae.microservices.order_services.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderRequest(
        Long id,                       // optional
        String orderNumber,             // optional
         String skuCode,       // required
         BigDecimal price,      // required
         @JsonProperty("quantity") Integer quantity,
        UserDetails userDetails// required, maps JSON "quantity" -> quantity
){
    public record UserDetails(String email,String firstName,String lastname){

    }


}