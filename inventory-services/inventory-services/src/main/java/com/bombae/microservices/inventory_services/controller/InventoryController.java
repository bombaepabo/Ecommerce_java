package com.bombae.microservices.inventory_services.controller;


import com.bombae.microservices.inventory_services.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    public final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam("skuCode") String skuCode,
                             @RequestParam("quantity") Integer quantity){
        return inventoryService.isInStock(skuCode, quantity);
    }
}
