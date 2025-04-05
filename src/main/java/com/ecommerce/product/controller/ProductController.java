package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.dto.ProductRequestDto;
import com.ecommerce.product.model.dto.ProductResponseDto;
import com.ecommerce.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        Product savedProduct = productService.createProduct(requestDto);
        return ResponseEntity.ok(ProductResponseDto.fromEntity(savedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}