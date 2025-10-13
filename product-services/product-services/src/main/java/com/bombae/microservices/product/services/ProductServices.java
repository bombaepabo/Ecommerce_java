package com.bombae.microservices.product.services;

import com.bombae.microservices.product.dto.ProductRequest;
import com.bombae.microservices.product.dto.ProductResponse;
import com.bombae.microservices.product.model.Product;
import com.bombae.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServices {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product Create successfully");
        return new ProductResponse(product.getId(),
                product.getName(), product.getDescription(),
                product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().
                stream().
                map(product ->new ProductResponse(product.getId(),
                        product.getName(), product.getDescription(),
                        product.getPrice())).toList();
    }
}
