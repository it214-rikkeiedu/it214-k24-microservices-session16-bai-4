package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.UpdateProductRequest;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Áp dụng @Cacheable: Lần đầu gọi sẽ chạy xuống DB, các lần sau lấy trực tiếp từ Cache (Redis).
     */
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id) {
        log.info("Cache MISS! Lay thong tin san pham tu Database cho ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay san pham voi ID: " + id));
        return convertToDTO(product);
    }

    /**
     * Áp dụng chiến lược chọn: @CacheEvict (Xóa Cache)
     * Giải pháp tối ưu hàng đầu chống Race Condition cho hệ thống tỉ lệ Đọc:Ghi (100:1).
     */
    @CacheEvict(value = "products", key = "#id")
    public ProductDTO updateProduct(Long id, UpdateProductRequest request) {
        log.info("Cap nhat san pham trong DB va Tien hanh EVICT (Xoa) Cache cho ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay san pham de cap nhat voi ID: " + id));

        // Cập nhật thông tin mới từ Request
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }
}