package org.example.ordersservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordersservice.dto.CreateOrderRequest;
import org.example.ordersservice.dto.OrderResponse;
import org.example.ordersservice.service.ProductOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "ProductOrderController", description = "REST API for orders processing.")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/product-orders")
public class ProductOrderController {

    public static final String USER_HEADER = "X-User-Name";

    private final ProductOrderService service;

    @Operation(
            summary = "${api.submit-order.summary}",
            description = "${api.submit-order.description}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.response.submitOk}"),
            @ApiResponse(
                    responseCode = "400",
                    description = "{api.response.submitBadRequest}",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "${api.response.submitNotFound}",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "${api.response.submitInternalError}",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderResponse> submitProductOrder(
            @RequestBody @Valid CreateOrderRequest request,
            @RequestHeader(USER_HEADER) String username
    ) {
        log.info("Received POST request to submit order: {}, by user: {}", request, username);
        return service.createOrder(request, username);
    }
}
