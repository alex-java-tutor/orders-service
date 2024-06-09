package org.example.ordersservice.client;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.config.props.OrderServiceProps;
import org.example.ordersservice.dto.GetProductInfoRequest;
import org.example.ordersservice.dto.GetProductInfoResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final WebClient webClient;
    private final OrderServiceProps props;

    public Mono<GetProductInfoResponse> getProductInfo(GetProductInfoRequest request) {
        return webClient.post()
                .uri(props.getProductInfoPath())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GetProductInfoResponse.class);
    }

}
