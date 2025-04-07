package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.dto.ProductRequestDto;
import com.ecommerce.product.model.dto.ProductResponseDto;
import com.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateProduct_Success() {
    // Given
    ProductRequestDto requestDto = ProductRequestDto.builder()
        .name("Test Product")
        .price("1000")
        .stock(10)
        .build();

    Product savedProduct = Product.builder()
        .name("Test Product")
        .price("1000")
        .stock(10)
        .build();

    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    // When
    Product result = productService.createProduct(requestDto);

    // Then
    assertNotNull(result);
    assertEquals("Test Product", result.getName());
    assertEquals("1000", result.getPrice());
    assertEquals(10, result.getStock());
    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  void testGetProductById_Success() throws Exception {
    // Given
    Long productId = 1L;
    Product product = Product.builder()
        .name("Test Product")
        .price("1000")
        .stock(5)
        .build();
    java.lang.reflect.Field idField = Product.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(product, productId);

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // When
    ProductResponseDto result = productService.getProductById(productId);

    // Then
    assertNotNull(result);
    assertEquals(productId, result.getId());
    assertEquals("Test Product", result.getName());
    assertEquals("1000", result.getPrice());
    assertEquals(5, result.getStock());
    verify(productRepository, times(1)).findById(productId);
  }

  @Test
  void testGetProductById_NotFound() {
    // Given
    Long productId = 1L;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productService.getProductById(productId);
    });
    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(productId);
  }

  @Test
  void testUpdateStock_Success() throws Exception {
    Long productId = 1L;
    int quantity = 3;
    Product product = Product.builder()
        .name("Test Product")
        .price("1000")
        .stock(10)
        .build();

    Product spiedProduct = spy(product);

    when(productRepository.findById(productId)).thenReturn(Optional.of(spiedProduct));

    productService.updateStock(productId, quantity);

    verify(spiedProduct).minusStock(quantity);
    assertEquals(7, spiedProduct.getStock()); // 10 - 3 = 7
    verify(productRepository, times(1)).findById(productId);
  }

  @Test
  void testUpdateStock_InsufficientStock() throws Exception {
    // Given
    Long productId = 1L;
    int quantity = 15;
    Product product = Product.builder()
        .name("Test Product")
        .price("1000")
        .stock(10)
        .build();
    java.lang.reflect.Field idField = Product.class.getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(product, productId);

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productService.updateStock(productId, quantity);
    });
    assertEquals("재고가 부족합니다.", exception.getMessage());
    verify(productRepository, times(1)).findById(productId);
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  void testUpdateStock_ProductNotFound() {
    // Given
    Long productId = 1L;
    int quantity = 5;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // When & Then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productService.updateStock(productId, quantity);
    });
    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(productId);
    verify(productRepository, never()).save(any(Product.class));
  }
}