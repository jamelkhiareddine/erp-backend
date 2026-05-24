package com.erp.modules.inventory.repository;

import com.erp.modules.inventory.entity.Product;
import com.erp.modules.inventory.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "p.sku LIKE CONCAT('%',:query,'%') OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%',:query,'%'))")
    Page<Product> searchProducts(String query, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.quantityOnHand <= p.reorderLevel AND p.status = 'ACTIVE'")
    List<Product> findLowStockProducts();
}
