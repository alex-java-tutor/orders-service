package org.example.ordersservice.client;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.config.props.OrderServiceProps;
import org.example.ordersservice.dto.GetProductInfoRequest;
import org.example.ordersservice.dto.GetProductInfoResponse;
import org.example.ordersservice.exception.OrderServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.concurrent.TimeoutException;

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
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new OrderServiceException("Product Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE)))
                .bodyToMono(GetProductInfoResponse.class)
                .timeout(props.getDefaultTimeout()) // java.util.concurrent.TimeoutException
                .retryWhen(
                        Retry.backoff(props.getRetryCount(), props.getRetryBackoff())
                                .jitter(props.getRetryJitter())
                                .filter(t -> {
                                    return t instanceof OrderServiceException || t instanceof TimeoutException;
                                })
                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                                    var msg = "Failed to fetch info from Product Service after max retry attempts";
                                    throw new OrderServiceException(msg, HttpStatus.SERVICE_UNAVAILABLE);
                                })
                );

    }

}
