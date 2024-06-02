package org.example.ordersservice.storage.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.example.ordersservice.exception.OrderServiceException;
import org.example.ordersservice.storage.model.ProductLineItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@ReadingConverter
@RequiredArgsConstructor
public class ProductLineItemReadingConverter implements Converter<Json, List<ProductLineItem>> {

    private final ObjectMapper objectMapper;

    @Override
    public List<ProductLineItem> convert(Json source) {
        try {
            var typeReference = new TypeReference<List<ProductLineItem>>(){};
            return objectMapper.readValue(source.asArray(), typeReference);
        } catch (IOException e) {
            var msg = "Failed to convert JSON %s to ProductLineItem".formatted(source.asString());
            throw new OrderServiceException(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
