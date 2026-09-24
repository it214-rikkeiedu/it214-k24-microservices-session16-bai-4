package com.example.ecommerce.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateProductRequest {
    
    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String name;

    @NotNull(message = "Gia san pham khong duoc de trong")
    @Min(value = 0, message = "Gia san pham phai lon hon hoac bang 0")
    private BigDecimal price;

    @NotNull(message = "So luong ton kho khong duoc de trong")
    @Min(value = 0, message = "So luong ton kho phai lon hon hoac bang 0")
    private Integer stock;

    private String description;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}