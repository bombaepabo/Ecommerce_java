package com.bombae.microservices.inventory_services.service;

import com.bombae.microservices.inventory_services.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public boolean isInStock(String skuCode,int quantity){
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }
}
