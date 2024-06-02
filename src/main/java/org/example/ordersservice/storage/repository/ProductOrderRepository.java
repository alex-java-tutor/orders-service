package org.example.ordersservice.storage.repository;

import org.example.ordersservice.storage.model.ProductOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductOrderRepository extends ReactiveCrudRepository<ProductOrder, Long> {

    Flux<ProductOrder> findAllByUsername(String username, Pageable pageable);

}
