package com.ecommerce.product.model;

import com.ecommerce.product.model.common.BaseEntity;
import com.ecommerce.product.model.dto.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    public Product(String name, String price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public ProductResponseDto toResponse() {
        return ProductResponseDto.builder()
            .id(this.id)
            .name(this.name)
            .price(this.price)
            .stock(this.stock)
            .build();
    }

    public void minusStock(Integer minusValue) {
        this.stock = this.stock - minusValue;
    }
}