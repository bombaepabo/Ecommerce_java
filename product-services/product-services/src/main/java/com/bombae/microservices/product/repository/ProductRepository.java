package com.bombae.microservices.product.repository;

import com.bombae.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product,String> {
}
