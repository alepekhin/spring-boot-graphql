package com.example.demo.common;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Alex
 * @since 15.01.2024
 */
@Component
@Getter
public class ClientFactory {

    @Value("${repository_url}")
    private String url;

    private HttpGraphQlClient repositoryClient;

    @PostConstruct
    void init() {
        repositoryClient = HttpGraphQlClient.builder(WebClient.create(url)).build();
    }

}
