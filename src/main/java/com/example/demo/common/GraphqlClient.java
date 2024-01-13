package com.example.demo.common;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Alex
 * @since 31.07.2023
 */
@Component
public class GraphqlClient {

    HttpGraphQlClient client;

    @PostConstruct
    void init() {
        client = HttpGraphQlClient.builder(WebClient.create()).build();
    }

    public HttpGraphQlClient getClient(String url) {
        return client.mutate().url(url).build();
    }

}
