package org.example.ordersservice.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "external")
public class OrderServiceProps {
    private final String productServiceUrl;
    private final String productInfoPath;
}
