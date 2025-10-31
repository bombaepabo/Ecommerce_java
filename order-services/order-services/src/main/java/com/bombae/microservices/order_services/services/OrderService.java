package com.bombae.microservices.order_services.services;

import com.bombae.microservices.order_services.event.OrderPlacedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bombae.microservices.order_services.client.InventoryClient;
import com.bombae.microservices.order_services.dto.OrderRequest;
import com.bombae.microservices.order_services.model.Order;
import com.bombae.microservices.order_services.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    public void placeOrder(OrderRequest orderRequest) {
        // Validate quantity

        // Check stock
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(orderRequest.orderNumber() != null ? orderRequest.orderNumber() : UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantities(orderRequest.quantity());
            orderRepository.save(order);

            OrderPlacedEvent orderPlaceEvent = new OrderPlacedEvent();
            orderPlaceEvent.setOrderNumber(order.getOrderNumber());
            orderPlaceEvent.setEmail(orderRequest.userDetails().email());
            //orderPlaceEvent.setFirstName(orderRequest.userDetails().firstName());
            //orderPlaceEvent.setLastName(orderRequest.userDetails().lastname());
            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed",orderPlaceEvent);
            kafkaTemplate.send("order-placed",orderPlaceEvent);
            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed",orderPlaceEvent);
        } else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock");
        }
    }
}
