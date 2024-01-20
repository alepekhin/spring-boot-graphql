package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Alex
 * @since 13.08.2023
 */
@Component
public class RequestInterceptor implements WebGraphQlInterceptor {

    @Value("${api-key}")
    private String apiKey;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        var headers = request.getHeaders().get("x-api-key");
        String key = headers != null ? headers.get(0) : "";
        if (apiKey.equals(key)) {
            return chain.next(request);
        } else {
            throw new SecurityException("Invalid x-api-key");
        }
    }

}
