package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    // Giả lập database trong memory
    private final Map<Long, Product> database = new ConcurrentHashMap<>();

    public ProductRepository() {
        // Khởi tạo một số dữ liệu mẫu ban đầu
        database.put(1L, new Product(1L, "iPhone 15 Pro Max", new BigDecimal("1200"), 100, "Apple Premium Smartphone"));
        database.put(2L, new Product(2L, "Samsung Galaxy S24 Ultra", new BigDecimal("1100"), 80, "Samsung Flagship phone"));
    }

    public Optional<Product> findById(Long id) {
        // Giả lập độ trễ kết nối DB
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        return Optional.ofNullable(database.get(id));
    }

    public Product save(Product product) {
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        database.put(product.getId(), product);
        return product;
    }
}