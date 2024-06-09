package org.example.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ordersservice.storage.model.OrderStatus;
import org.example.ordersservice.storage.model.ProductLineItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalPrice;
    private List<ProductLineItem> productLineItems;
    private Address address;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
