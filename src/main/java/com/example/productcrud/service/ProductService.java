package com.example.productcrud.service;

import com.example.productcrud.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
    ProductDTO getProductByName(String name);
    List<ProductDTO> getAllProducts();
}
