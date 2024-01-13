package com.example.demo.feature.user;

import com.example.demo.model.User;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Alex
 * @since 31.07.2023
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserQueryResolver {

    final UserService userService;

    @QueryMapping
    @Timed("demo_get_user_http")
    public User user(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.user(id);
    }

    @QueryMapping
    @Timed("demo_get_users_http")
    public List<User> users() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.users();
    }

    @SubscriptionMapping
    @Timed("demo_get_user_ws")
    public Mono<User> getUser(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUser(id);
    }

    @SubscriptionMapping
    @Timed("demo_get_user_ws")
    public Flux<User> getUsers() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUsers();
    }

}
