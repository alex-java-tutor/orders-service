package org.example.ordersservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ordersservice.storage.converters.ProductLineItemReadingConverter;
import org.example.ordersservice.storage.converters.ProductLineItemWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.util.List;

@Configuration
@EnableR2dbcAuditing
public class R2DBCConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ObjectMapper mapper) {
        var converters = List.of(
                new ProductLineItemReadingConverter(mapper),
                new ProductLineItemWritingConverter(mapper)
        );
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }

}
