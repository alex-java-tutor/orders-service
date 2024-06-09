package org.example.ordersservice.service;

import org.example.ordersservice.dto.CreateOrderRequest;
import org.example.ordersservice.dto.OrderResponse;
import org.example.ordersservice.dto.SortBy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductOrderService {

    Mono<OrderResponse> createOrder(CreateOrderRequest request, String username);

    Flux<OrderResponse> getOrdersOfUser(String username, SortBy sort, int from, int size);

}
