package com.example.putAndPatch.controller;

import com.example.putAndPatch.entity.Product;
import com.example.putAndPatch.service.ProductServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired ProductServiceImpl productService;

  @GetMapping("/getAll")
  public List<Product> getProducts() {
    return productService.getProducts();
  }

  @PostMapping("/post")
  public Product saveProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  @PutMapping("/{id}")
  public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
    return productService.updateProduct(id, product);
  }

  @PatchMapping("/{id}")
  public Product patchProduct(@PathVariable int id, @RequestBody Map<String, Object> updatedValues) {
    return productService.updateProductUsingPatch(id, updatedValues);
  }
}
