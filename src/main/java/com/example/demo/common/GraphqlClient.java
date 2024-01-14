package com.example.demo.common;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Alex
 * @since 31.07.2023
 */
public class GraphqlClient {

    public static GraphqlClient INSTANCE = new GraphqlClient();
    HttpGraphQlClient client;

    private GraphqlClient() {
        client = HttpGraphQlClient.builder(WebClient.create()).build();
    }

    public HttpGraphQlClient getHttpClient(String url) {
        return client.mutate().url(url).build();
    }

}
