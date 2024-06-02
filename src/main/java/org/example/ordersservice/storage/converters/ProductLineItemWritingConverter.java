package org.example.ordersservice.storage.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.example.ordersservice.exception.OrderServiceException;
import org.example.ordersservice.storage.model.ProductLineItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.http.HttpStatus;

import java.util.List;

@WritingConverter
@RequiredArgsConstructor
public class ProductLineItemWritingConverter implements Converter<List<ProductLineItem>, Json> {

    private final ObjectMapper objectMapper;

    @Override
    public Json convert(List<ProductLineItem> source) {
        try {
            return Json.of(objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            var msg = "Failed to convert ProductLineItems %s to JSON".formatted(source);
            throw new OrderServiceException(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
