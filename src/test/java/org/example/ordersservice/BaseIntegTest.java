package org.example.ordersservice;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.example.ordersservice.testutils.TestConstants.*;

@SpringBootTest
public class BaseIntegTest extends BaseTest {

    @RegisterExtension
    protected static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void applyProperties(DynamicPropertyRegistry registry) {
        registry.add("external.product-service-url", wireMock::baseUrl);
    }

    protected void prepareStubForServiceUnavailable() {
        wireMock.stubFor(post(urlEqualTo(PRODUCT_INFO_PATH))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));
    }

    protected void prepareStubForSuccessWithTimeout() {
        var response = readSuccessfulResponse();
        wireMock.stubFor(post(urlEqualTo(PRODUCT_INFO_PATH))
                .willReturn(okJson(response)
                        .withFixedDelay(DELAY_MILLIS)));
    }

    protected void prepareStubForPartialSuccess() {
        var responseBody = readPartiallySuccessfulResponse();
        wireMock.stubFor(post(urlEqualTo(PRODUCT_INFO_PATH))
                .willReturn(okJson(responseBody)));
    }

    protected void prepareStubForSuccess() {
        var responseBody = readSuccessfulResponse();
        wireMock.stubFor(post(urlEqualTo(PRODUCT_INFO_PATH))
                .willReturn(okJson(responseBody)));
    }

    private static String readSuccessfulResponse() {
        return readFileToString("wiremock/success-response.json");
    }

    private static String readPartiallySuccessfulResponse() {
        return readFileToString("wiremock/partially-success-response.json");
    }

    private static String readFileToString(String filePath) {
        try {
            Path path = ResourceUtils.getFile("classpath:" + filePath).toPath();
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
