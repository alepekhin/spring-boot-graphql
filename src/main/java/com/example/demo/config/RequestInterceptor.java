package com.example.demo.config;

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

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        var headers = request.getHeaders().get("x-api-key");
        String key = headers != null ? headers.get(0) : "";
        if ("xxx".equals(key)) {
            return chain.next(request);
        } else {
            throw new SecurityException("Invalid x-api-key");
        }
    }

}
