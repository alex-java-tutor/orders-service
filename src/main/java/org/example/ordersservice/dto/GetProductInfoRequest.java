package org.example.ordersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetProductInfoRequest {
    private Set<String> productNames;
}
