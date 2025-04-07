package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.dto.ProductRequestDto;
import com.ecommerce.product.model.dto.ProductResponseDto;
import com.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

  private MockMvc mockMvc;
  @Mock
  private ProductService productService;
  @InjectMocks
  private ProductController productController;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testCreateProduct() throws Exception {
    // Given
    ProductRequestDto requestDto = ProductRequestDto.builder()
        .name("test product1")
        .stock(5)
        .price("1000")
        .build();

    Product product = Product.builder()
        .name("test product1")
        .stock(5)
        .price("1000")
        .build();


    when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(product);

    // When & Then
    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("test product1"))
        .andExpect(jsonPath("$.price").value("1000"))
        .andExpect(jsonPath("$.stock").value(5));
  }

  @Test
  void testGetProduct() throws Exception {
    // Given
    Long productId = 1L;
    ProductResponseDto responseDto = ProductResponseDto.builder()
        .id(productId)
        .name("test product1")
        .stock(3)
        .price("1000")
        .build();

    when(productService.getProductById(productId)).thenReturn(responseDto);

    // When & Then
    mockMvc.perform(get("/products/{id}", productId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("test product1"))
        .andExpect(jsonPath("$.price").value("1000"))
        .andExpect(jsonPath("$.stock").value(3));
  }
}