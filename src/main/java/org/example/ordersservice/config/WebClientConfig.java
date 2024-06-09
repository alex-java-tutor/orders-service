package org.example.ordersservice.config;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.config.props.OrderServiceProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final OrderServiceProps props;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(props.getProductServiceUrl())
                .build();
    }

}
