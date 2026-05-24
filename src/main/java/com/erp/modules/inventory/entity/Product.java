package com.erp.modules.inventory.entity;

import com.erp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String sku;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 100)
    private String category;

    private String brand;

    private String unit; // pcs, kg, liter, etc.

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal purchasePrice;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private Integer quantityOnHand;

    @Column(nullable = false)
    private Integer reorderLevel;

    private Integer reorderQuantity;

    private String warehouseLocation;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
