package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.dto.ProductRequestDto;
import com.ecommerce.product.model.dto.ProductResponseDto;
import com.ecommerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(ProductRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")).toResponse();
    }

    @Transactional
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }
        product.minusStock(newStock);
        productRepository.save(product);
    }
}
