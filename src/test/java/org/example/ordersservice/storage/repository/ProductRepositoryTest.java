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

@Import({R2DBCConfig.class})
@DataR2dbcTest
@ImportAutoConfiguration({JacksonAutoConfiguration.class})
public class ProductRepositoryTest extends BaseTest {

    @Autowired
    private ProductOrderRepository repository;

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
