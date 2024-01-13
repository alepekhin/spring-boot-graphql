package com.example.demo.feature.user;

import com.example.demo.common.GraphqlClient;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Alex
 * @since 31.07.2023
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${graphql.client.url}")
    String url;

    final GraphqlClient client;

    public User user(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return client.getClient(url)
                .documentName("user_get")
                .variable("id", id)
                .retrieve("user")
                .toEntity(User.class)
                .toFuture()
                .get(2, TimeUnit.SECONDS);

    }

    public Mono<User> getUser(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return Mono.just(client.getClient(url)
                .documentName("user_get")
                .variable("id", id)
                .retrieve("user")
                .toEntity(User.class)
                .toFuture()
                .get(2, TimeUnit.SECONDS));

    }

    public List<User> users() throws ExecutionException, InterruptedException, TimeoutException {
        return client.getClient(url)
                .documentName("user_list")
                .retrieve("users")
                .toEntity(UsersResponse.class)
                .toFuture()
                .get(5, TimeUnit.SECONDS)
                .getData();
    }

    public Flux<User> getUsers() throws ExecutionException, InterruptedException, TimeoutException {
        var users = client.getClient(url)
                .documentName("user_list")
                .retrieve("users")
                .toEntity(UsersResponse.class)
                .toFuture()
                .get(5, TimeUnit.SECONDS)
                .getData();
        return Flux.fromIterable(users);
    }

}
