package org.example.ordersservice.storage.repository;

import org.example.ordersservice.BaseTest;
import org.example.ordersservice.config.R2DBCConfig;
import org.example.ordersservice.storage.model.ProductOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.example.ordersservice.testutils.TestConstants.*;

@Import({R2DBCConfig.class})
@DataR2dbcTest
@ImportAutoConfiguration({JacksonAutoConfiguration.class})
public class ProductRepositoryTest extends BaseTest {

    @Autowired
    private ProductOrderRepository repository;

    @Test
    void findAllByCreatedBy_returnsCorrectSortedByDateDesc() {
        var pageable = PageRequest.of(0, 10)
                .withSort(Sort.by(Sort.Direction.DESC, "createdAt"));
        Flux<ProductOrder> orders = repository.findAllByUsername(USER_ONE, pageable);
        StepVerifier.create(orders)
                .expectNextMatches(order ->
                        order.getCreatedAt().equals(ORDER_FIVE_DATE) &&
                        order.getUsername().equals(USER_ONE))
                .expectNextMatches(order ->
                        order.getCreatedAt().equals(ORDER_THREE_DATE) &&
                                order.getUsername().equals(USER_ONE))
                .expectNextMatches(order ->
                        order.getCreatedAt().equals(ORDER_ONE_DATE) &&
                                order.getUsername().equals(USER_ONE))
                .verifyComplete();
    }

    @Test
    void findAllByCreatedBy_returnsCorrectSortedByDateAsc() {
        var pageable = PageRequest.of(0, 10)
                .withSort(Sort.by(Sort.Direction.ASC, "createdAt"));
        Flux<ProductOrder> orders = repository.findAllByUsername(USER_TWO, pageable);
        StepVerifier.create(orders)
                .expectNextMatches(order ->
                        order.getCreatedAt().equals(ORDER_TWO_DATE) &&
                                order.getUsername().equals(USER_TWO))
                .expectNextMatches(order ->
                        order.getCreatedAt().equals(ORDER_FOUR_DATE) &&
                                order.getUsername().equals(USER_TWO))
                .verifyComplete();
    }

    @Test
    void findAllByUsername_returnsEmptyFlux_whenUserHasNoOrders() {
        var pageable = PageRequest.of(0, 10)
                .withSort(Sort.by(Sort.Direction.ASC, "createdAt"));
        Flux<ProductOrder> flux = repository.findAllByUsername("unknown", pageable);
        StepVerifier.create(flux)
                .expectNextCount(0)
                .verifyComplete();
    }

}
