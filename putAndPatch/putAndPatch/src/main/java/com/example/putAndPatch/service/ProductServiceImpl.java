package com.example.putAndPatch.service;

import com.example.putAndPatch.entity.Product;
import com.example.putAndPatch.repository.ProductRepository;
import java.lang.reflect.Field;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired ProductRepository productRepository;

  @Override
  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(int id, Product product) {

    Product existingProduct =
        productRepository.findById(id).orElseThrow(EmptyStackException::new);
    existingProduct.setProductType(product.getProductType());
    existingProduct.setName(product.getName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setPrice(product.getPrice());

    return productRepository.save(existingProduct);
  }

  @Override
  public Product updateProductUsingPatch(int id, Map<String, Object> product) {
    Product existingProduct =
        productRepository.findById(id).orElseThrow(EmptyStackException::new);
    product.forEach(
        (key, value) -> {
          Field field = ReflectionUtils.findField(Product.class, key);
          field.setAccessible(true);
          ReflectionUtils.setField(field, existingProduct, value);
        });
    return productRepository.save(existingProduct);

/*      Optional<Product> existingProduct = productRepository.findById(id);

      if (existingProduct.isPresent()) {
          product.forEach((key, value) -> {Field field = ReflectionUtils.findField(Product.class, key);
              field.setAccessible(true);
              ReflectionUtils.setField(field, existingProduct.get(), value);
          });
          return productRepository.save(existingProduct.get());
      }
      return null;*/
  }
}
