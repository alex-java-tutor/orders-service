package org.example.ordersservice.service.impl;

import org.example.ordersservice.BaseIntegTest;
import org.example.ordersservice.dto.OrderResponse;
import org.example.ordersservice.exception.OrderServiceException;
import org.example.ordersservice.service.ProductOrderService;
import org.example.ordersservice.storage.model.ProductLineItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Comparator;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.example.ordersservice.testutils.TestConstants.*;
import static org.example.ordersservice.testutils.TestDataProvider.*;

/**
 *     1. Создание заказа - успех, когда все товары есть в наличии
 *     2. Создание заказа - ошибка, когда часть товаров отсутствует
 *     3. Создание заказа - ошибка при таймауте от Product Service
 *     4. Создание заказа - ошибка, когда Product Service недоступен.
 *     5. Получение заказов - пустой Flux, когда у пользователя нет заказов
 *     6. Получение заказов - корректный Flux, когда у пользователя есть заказы
 */
class ProductOrderServiceImplTest extends BaseIntegTest {

    @Autowired
    protected ProductOrderService productOrderService;

    @Test
    void createOrder_returnsError_onTimeout() {
        prepareStubForSuccessWithTimeout();

        var request = createOrderRequest();
        Mono<OrderResponse> mono = productOrderService.createOrder(request, USER_ONE);

        StepVerifier.create(mono)
                .expectError(OrderServiceException.class)
                .verify();

        wireMock.verify(6, postRequestedFor(urlEqualTo(PRODUCT_INFO_PATH)));
    }

    @Test
    void createOrder_createsOrder_whenAllProductsAreAvailable() {
        // Orders Service -> Product Service | Intercepted by WireMock -> Success
        prepareStubForSuccess();

        var request = createOrderRequest();

        Mono<OrderResponse> mono = productOrderService.createOrder(request, USER_ONE);
        StepVerifier.create(mono)
                .expectNextMatches(orderResponse -> {
                    assertThat(orderResponse.getAddress()).isEqualTo(request.getAddress());
                    var productItems = new ArrayList<>(orderResponse.getProductLineItems());
                    productItems.sort(Comparator.comparing(ProductLineItem::getPrice));

                    assertThat(productItems)
                            .map(ProductLineItem::getProductName)
                            .containsExactly(PRODUCT_ONE_NAME, PRODUCT_TWO_NAME, PRODUCT_THREE_NAME);

                    assertThat(productItems)
                            .map(ProductLineItem::getQuantity)
                            .containsExactly(PRODUCT_CREATE_ONE_QUANTITY, PRODUCT_CREATE_TWO_QUANTITY, PRODUCT_CREATE_THREE_QUANTITY);

                    return orderResponse.getOrderId() != null;
                })
                .verifyComplete();

        wireMock.verify(1, postRequestedFor(urlEqualTo(PRODUCT_INFO_PATH)));
    }

}