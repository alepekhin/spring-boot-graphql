package com.example.demo.feature.user;

import com.example.demo.feature.ITBase;
import com.example.demo.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex
 * @since 13.01.2024
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIT extends ITBase {

    // ****************************
    // Тесты http graphql client
    // ****************************
    @Test
    @Order(10)
    void user() throws ExecutionException, InterruptedException, TimeoutException {
        var result = httpClient
                .documentName("get_one_user_http")
                .variable("id", 1)
                .retrieve("user")
                .toEntity(User.class)
                .doOnNext(System.out::println)
                .toFuture()
                .get(5, TimeUnit.SECONDS);
        assertEquals(1, result.getId());
    }

    @Test
    @Order(20)
    void users() throws ExecutionException, InterruptedException, TimeoutException {
        var result = httpClient
                .documentName("get_all_users_http")
                .retrieve("users")
                .toEntity(User[].class)
                .doOnNext(r -> Arrays.stream(r).forEach(System.out::println))
                .toFuture()
                .get(5, TimeUnit.SECONDS);
        assertTrue(result.length > 0);
    }

    // ****************************
    // Тесты websocket graphql client
    // ****************************
    @Test
    @Order(30)
    void getUser_blocked() {
        var result = wsClient
                .documentName("get_one_user_ws")
                .variable("id", 1)
                .retrieveSubscription("getUser")
                .toEntity(User.class)
                .onErrorContinue((e, o) -> System.out.println(e.getMessage()))
                .doOnNext(System.out::println)
                .blockLast();
        assertEquals(1, result.getId());
    }

    @Test
    @Order(40)
    void getUser_not_blocked() {
        var expected = User.builder()
                .id(1)
                .name("Leanne Graham")
                .username("Bret")
                .email("Sincere@april.biz")
                .build();
        StepVerifier.create(wsClient
                        .documentName("get_one_user_ws")
                        .variable("id", 1)
                        .retrieveSubscription("getUser")
                        .toEntity(User.class)
                        .onErrorContinue((e, o) -> System.out.println(e.getMessage())))
                .expectSubscription()
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    @Order(50)
    void getUsers_blocked() {
        var result = wsClient
                .documentName("get_all_users_ws")
                .retrieveSubscription("getUsers")
                .toEntity(User.class)
                .onErrorContinue((e, o) -> System.out.println(e.getMessage()))
                .doOnNext(System.out::println)
                .collectList()
                .block();
        assertTrue(result.size() > 0);
    }

    @Test
    @Order(50)
    void getUsers_not_blocked() {
        var expected = User.builder()
                .id(10)
                .name("Clementina DuBuque")
                .username("Moriah.Stanton")
                .email("Rey.Padberg@karina.biz")
                .build();
        StepVerifier.create(wsClient
                        .documentName("get_all_users_ws")
                        .retrieveSubscription("getUsers")
                        .toEntity(User.class))
                .expectSubscription()
                .expectNextCount(9)
                .expectNext(expected)
                .verifyComplete();
    }

    // ****************************
    // Тесты метрик
    // ****************************

    static final String ONE_USER_HTTP = "graphql_field_name=\"user\"";
    static final String ALL_USERS_HTTP = "graphql_field_name=\"users\"";
    static final String ONE_USER_WS = "graphql_field_name=\"getUser\"";
    static final String ALL_USERS_WS = "graphql_field_name=\"getUsers\"";



    @Test
    @Order(100)
    void testMetrics() throws ExecutionException, InterruptedException {
        String metrics = metricsClient.get().retrieve().toEntity(String.class).toFuture().get().getBody();
        assertTrue(metrics.contains(ONE_USER_HTTP));
        assertTrue(metrics.contains(ALL_USERS_HTTP));
        assertTrue(metrics.contains(ONE_USER_WS));
        assertTrue(metrics.contains(ALL_USERS_WS));
        // print metrics
        Arrays.stream(metrics.split("\n")).forEach(r -> {
            if ((r.contains(ONE_USER_HTTP) || r.contains(ALL_USERS_HTTP)
                    || r.contains(ONE_USER_WS) || r.contains(ALL_USERS_WS))
            ) {
                System.out.println(r);
            }
        });
    }

}
