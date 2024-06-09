package org.example.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInfo {
    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
}
