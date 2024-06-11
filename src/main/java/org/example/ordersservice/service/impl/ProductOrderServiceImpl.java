package org.example.ordersservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.client.ProductClient;
import org.example.ordersservice.dto.CreateOrderRequest;
import org.example.ordersservice.dto.GetProductInfoRequest;
import org.example.ordersservice.dto.OrderResponse;
import org.example.ordersservice.dto.SortBy;
import org.example.ordersservice.mapper.OrderMapper;
import org.example.ordersservice.service.ProductOrderService;
import org.example.ordersservice.storage.repository.ProductOrderRepository;
import org.example.ordersservice.utils.HashUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository repository;
    private final OrderMapper mapper;
    private final ProductClient client;
    private final HashUtils hashUtils;

    @Override
    public Mono<OrderResponse> createOrder(CreateOrderRequest request, String username) {
        var infoRequest = new GetProductInfoRequest(request.getNameToQuantity().keySet());
        return client
                .getProductInfo(infoRequest) // Call to Product Service (WebClient)
                .map(response -> mapper.mapToOrder(request, username, response)) // Map To ProductOrder
                .flatMap(repository::save) // Save to Repository (R2DBC)
                .map(mapper::mapToResponse); // Map to OrderResponse
    }

    @Override
    public Flux<OrderResponse> getOrdersOfUser(String username, SortBy sort, int from, int size) {
        var pageable = PageRequest.of(from, size)
                .withSort(sort.getSort());
        return repository.findAllByUsername(username, pageable)
                .map(mapper::mapToResponse);
    }

    @Override
    public Mono<OrderResponse> findOrderById(String orderId) {
        return repository.findById(hashUtils.idOf(orderId))
                .map(mapper::mapToResponse);
    }
}
