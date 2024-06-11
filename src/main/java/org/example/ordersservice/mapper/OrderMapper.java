package org.example.ordersservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.dto.*;
import org.example.ordersservice.exception.OrderServiceException;
import org.example.ordersservice.storage.model.OrderStatus;
import org.example.ordersservice.storage.model.ProductLineItem;
import org.example.ordersservice.storage.model.ProductOrder;
import org.example.ordersservice.utils.HashUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final HashUtils hashUtils;

    public ProductOrder mapToOrder(CreateOrderRequest request,
                                   String username,
                                   GetProductInfoResponse infoResponse) {
        var infos = infoResponse.getProductInfos();
        throwIfHasUnavailableProducts(infos);

        BigDecimal totalPrice = getTotalPrice(infos);
        List<ProductLineItem> productLineItems = getProductLineItems(request, infos);

        return ProductOrder.builder()
                .house(request.getAddress().getHouse())
                .city(request.getAddress().getCity())
                .street(request.getAddress().getStreet())
                .block(request.getAddress().getBlock())
                .apartment(request.getAddress().getApartment())
                .username(username)
                .status(OrderStatus.NEW)
                .totalPrice(totalPrice)
                .productLineItems(productLineItems)
                .build();
    }

    public OrderResponse mapToResponse(ProductOrder order) {
        return OrderResponse.builder()
                .orderId(hashUtils.hashOf(order.getId()))
                .totalPrice(order.getTotalPrice())
                .productLineItems(order.getProductLineItems())
                .address(Address.builder()
                        .city(order.getCity())
                        .street(order.getStreet())
                        .house(order.getHouse())
                        .block(order.getBlock())
                        .apartment(order.getApartment())
                        .build())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private static List<ProductLineItem> getProductLineItems(CreateOrderRequest request, List<ProductInfo> infos) {
        return infos.stream()
                .map(info -> {
                    var nameToQuantity = request.getNameToQuantity();
                    int quantity = nameToQuantity.get(info.getName());
                    return ProductLineItem.builder()
                            .productName(info.getName())
                            .price(info.getPrice())
                            .quantity(quantity)
                            .build();
                })
                .toList();
    }

    private static BigDecimal getTotalPrice(List<ProductInfo> infos) {
        return infos.stream()
                .map(ProductInfo::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void throwIfHasUnavailableProducts(List<ProductInfo> infos) {
        boolean hasUnavailable = infos.stream().anyMatch(info -> !info.getIsAvailable());
        if (hasUnavailable) {
            var msg = "Cannot create order, cause some products are not available, %s".formatted(infos);
            throw new OrderServiceException(msg, HttpStatus.NOT_FOUND);
        }
    }

}
