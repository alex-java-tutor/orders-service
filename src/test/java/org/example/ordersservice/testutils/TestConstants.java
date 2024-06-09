package org.example.ordersservice.testutils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class TestConstants {
    public static final String USER_ONE = "username1";
    public static final String USER_TWO = "username2";
    public static final LocalDateTime ORDER_ONE_DATE = LocalDateTime.of(2024, Month.MAY, 18, 10, 23, 54);
    public static final LocalDateTime ORDER_TWO_DATE = LocalDateTime.of(2024, Month.MAY, 19, 10, 23, 54);
    public static final LocalDateTime ORDER_THREE_DATE = LocalDateTime.of(2024, Month.MAY, 20, 10, 23, 54);
    public static final LocalDateTime ORDER_FOUR_DATE = LocalDateTime.of(2024, Month.MAY, 21, 10, 23, 54);
    public static final LocalDateTime ORDER_FIVE_DATE = LocalDateTime.of(2024, Month.MAY, 22, 10, 23, 54);
    public static final String CITY_ONE = "CityOne";
    public static final String CITY_TWO = "CityTwo";
    public static final String STREET_ONE = "StreetOne";
    public static final String STREET_TWO = "StreetTwo";
    public static final int HOUSE = 1;
    public static final Integer BLOCK = 1;
    public static final int APARTMENT = 1;
    public static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(70);
    public static final String PRODUCT_ONE_NAME = "One";
    public static final String PRODUCT_TWO_NAME = "Two";
    public static final String PRODUCT_THREE_NAME = "Three";
    public static final BigDecimal PRODUCT_ONE_PRICE = BigDecimal.valueOf(4);
    public static final BigDecimal PRODUCT_TWO_PRICE = BigDecimal.valueOf(3);
    public static final BigDecimal PRODUCT_THREE_PRICE = BigDecimal.valueOf(3);
    public static final Integer PRODUCT_ONE_QUANTITY = 10;
    public static final Integer PRODUCT_TWO_QUANTITY = 5;
    public static final Integer PRODUCT_THREE_QUANTITY = 5;
}
