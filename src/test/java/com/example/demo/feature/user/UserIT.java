package com.example.demo.feature.user;

import com.example.demo.feature.ITBase;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;

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
public class UserIT extends ITBase {

    @Test
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
    void users() throws ExecutionException, InterruptedException, TimeoutException {
        var result =  httpClient
                .documentName("get_all_users_http")
                .retrieve("users")
                .toEntity(User[].class)
                .doOnNext(r -> Arrays.stream(r).forEach(System.out::println))
                .toFuture()
                .get(5, TimeUnit.SECONDS);
        assertTrue(result.length > 0);
    }

    @Test
    void getUser() {
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
    void getUsers() {
        var result =  wsClient
                .documentName("get_all_users_ws")
                .retrieveSubscription("getUsers")
                .toEntity(User.class)
                .onErrorContinue((e, o) -> System.out.println(e.getMessage()))
                .doOnNext(System.out::println)
                .collectList()
                .block();
        assertTrue(result.size() > 0);
    }

}
