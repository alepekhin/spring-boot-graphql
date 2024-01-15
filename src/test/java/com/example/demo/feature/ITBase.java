package com.example.demo.feature;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

/**
 * Базовый класс для интеграционных тестов
 *
 * @author Alex
 * @since 13.01.2024
 */
public abstract class ITBase {

    String serviceHost = "localhost:8080";
    protected static WebSocketGraphQlClient wsClient;
    protected static HttpGraphQlClient httpClient;
    protected static WebClient metricsClient;

    public ITBase() {
        httpClient = HttpGraphQlClient
                .builder(WebClient.create("http://" + serviceHost + "/graphql"))
                .header("x-api-key", "xxx")
                .build();
        wsClient = WebSocketGraphQlClient
                .builder("ws://" + serviceHost + "/ws", new ReactorNettyWebSocketClient())
                .header("x-api-key", "xxx")
                .build();
        metricsClient = WebClient.create("http://" + serviceHost + "/actuator/prometheus");
    }


}
