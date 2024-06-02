package org.example.ordersservice;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class BaseTest {

    static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.1"))
            .withDatabaseName("test_database")
            .withUsername("user")
            .withPassword("password");

    static {
        container.start();
        System.setProperty("spring.r2dbc.url", "r2dbc:postgresql://" + container.getHost() + ":" + container.getFirstMappedPort() + "/test_database");
        System.setProperty("spring.r2dbc.username", container.getUsername());
        System.setProperty("spring.r2dbc.password", container.getPassword());
        System.setProperty("spring.flyway.url", container.getJdbcUrl());
        System.setProperty("eureka.client.enabled", "false");
        System.setProperty("spring.cloud.loadbalancer.enabled", "false");
    }

    @Autowired
    private ConnectionFactory connectionFactory;

    @BeforeEach
    void setup(@Value("classpath:insert-orders.sql") Resource script) {
        executeScript(script);
    }

    @AfterEach
    void tearDown(@Value("classpath:delete-orders.sql") Resource script) {
        executeScript(script);
    }


    private void executeScript(final Resource resource) {
        var populator = new ResourceDatabasePopulator();
        populator.addScript(resource);
        populator.populate(connectionFactory).block();
    }

}
