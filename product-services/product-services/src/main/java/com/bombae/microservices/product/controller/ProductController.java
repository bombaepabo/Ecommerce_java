package com.bombae.microservices.product.controller;

import com.bombae.microservices.product.dto.ProductRequest;
import com.bombae.microservices.product.dto.ProductResponse;
import com.bombae.microservices.product.model.Product;
import com.bombae.microservices.product.services.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse CreateProduct(@RequestBody ProductRequest productRequest){
     return productServices.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productServices.getAllProducts();
    }


}
