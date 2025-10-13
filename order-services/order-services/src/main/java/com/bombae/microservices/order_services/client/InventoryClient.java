package com.bombae.microservices.order_services.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {

    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam("sku_code") String sku_code,
                      @RequestParam("quantity") Integer quantity);
}
