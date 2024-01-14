package com.example.demo.common;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Alex
 * @since 31.07.2023
 */
public class HttpClientBase {

    public static HttpClientBase INSTANCE = new HttpClientBase();
    HttpGraphQlClient client;

    private HttpClientBase() {
        client = HttpGraphQlClient.builder(WebClient.create()).build();
    }

    public HttpGraphQlClient getHttpClient(String url) {
        return client.mutate().url(url).build();
    }

}
