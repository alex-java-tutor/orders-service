package org.example.ordersservice.testutils;

import org.example.ordersservice.dto.Address;
import org.example.ordersservice.dto.CreateOrderRequest;
import org.example.ordersservice.storage.model.ProductLineItem;

import java.util.List;
import java.util.Map;

import static org.example.ordersservice.testutils.TestConstants.*;

public class TestDataProvider {

    public static CreateOrderRequest createOrderRequest() {
        return CreateOrderRequest.builder()
                .address(Address.builder()
                        .city(CITY_ONE)
                        .street(STREET_ONE)
                        .house(HOUSE)
                        .block(BLOCK)
                        .apartment(APARTMENT)
                        .build())
                .nameToQuantity(Map.of(
                        PRODUCT_ONE_NAME, PRODUCT_CREATE_ONE_QUANTITY,
                        PRODUCT_TWO_NAME, PRODUCT_CREATE_TWO_QUANTITY,
                        PRODUCT_THREE_NAME, PRODUCT_CREATE_THREE_QUANTITY
                )).build();
    }

    public static CreateOrderRequest createOrderInvalidRequest() {
        return CreateOrderRequest.builder()
                .address(Address.builder()
                        .city("")
                        .street("")
                        .house(-1)
                        .apartment(-1)
                        .block(-1)
                        .build())
                .nameToQuantity(Map.of(
                        PRODUCT_ONE_NAME, PRODUCT_CREATE_ONE_QUANTITY,
                        PRODUCT_TWO_NAME, PRODUCT_CREATE_TWO_QUANTITY,
                        PRODUCT_THREE_NAME, PRODUCT_CREATE_THREE_QUANTITY
                )).build();
    }

    public static ProductLineItem firstExisting() {
        return ProductLineItem.builder()
                .productName(PRODUCT_ONE_NAME)
                .price(PRODUCT_ONE_PRICE)
                .quantity(PRODUCT_ONE_QUANTITY)
                .build();
    }

    public static ProductLineItem secondExisting() {
        return ProductLineItem.builder()
                .productName(PRODUCT_TWO_NAME)
                .price(PRODUCT_TWO_PRICE)
                .quantity(PRODUCT_TWO_QUANTITY)
                .build();
    }

    public static ProductLineItem thirdExisting() {
        return ProductLineItem.builder()
                .productName(PRODUCT_THREE_NAME)
                .price(PRODUCT_THREE_PRICE)
                .quantity(PRODUCT_THREE_QUANTITY)
                .build();
    }

    public static List<ProductLineItem> existingItems() {
        return List.of(
                firstExisting(),
                secondExisting(),
                thirdExisting()
        );
    }

    public static ProductLineItem firstCreatedItem() {
        return ProductLineItem.builder()
                .productName(PRODUCT_ONE_NAME)
                .price(PRODUCT_CREATE_ONE_PRICE)
                .quantity(PRODUCT_CREATE_ONE_QUANTITY)
                .build();
    }

    public static ProductLineItem secondCreatedItem() {
        return ProductLineItem.builder()
                .productName(PRODUCT_TWO_NAME)
                .price(PRODUCT_CREATE_TWO_PRICE)
                .quantity(PRODUCT_CREATE_TWO_QUANTITY)
                .build();
    }

    public static ProductLineItem thirdCreatedItem() {
        return ProductLineItem.builder()
                .productName(PRODUCT_THREE_NAME)
                .price(PRODUCT_CREATE_THREE_PRICE)
                .quantity(PRODUCT_CREATE_THREE_QUANTITY)
                .build();
    }

    public static List<ProductLineItem> createdItems() {
        return List.of(
                firstCreatedItem(),
                secondCreatedItem(),
                thirdCreatedItem()
        );
    }
}
