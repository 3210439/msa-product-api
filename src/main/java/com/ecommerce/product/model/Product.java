package com.ecommerce.product.model;

import com.ecommerce.product.model.common.BaseEntity;
import lombok.Data;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    private Integer stock;
}
