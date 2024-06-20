package org.example.ordersservice.config;

import lombok.RequiredArgsConstructor;
import org.example.ordersservice.config.props.OrderServiceProps;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final OrderServiceProps props;

    @LoadBalanced
    @Bean
    public WebClient.Builder loadBalancedBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder loadBalancedBuilder) {
        return loadBalancedBuilder
                .baseUrl(props.getProductServiceUrl())
                .build();
    }
}
