package com.example.putAndPatch.service;

import com.example.putAndPatch.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getProducts();

    Product saveProduct(Product product);


    Product updateProduct(int id, Product product);

    Product updateProductUsingPatch(int id, Map<String, Object> product);
}
