package com.example.demo.feature.user;

import com.example.demo.feature.MetricNames;
import com.example.demo.model.User;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
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
 * Выполнение запросов, описанных в schema.graphqls
 * Два для type Query и два для type Subscription
 *
 * @author Alex
 * @since 31.07.2023
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @QueryMapping
    @Timed(MetricNames.ONE_USER_HTTP)
    public User user(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.user(id);
    }

    @QueryMapping
    @Timed(MetricNames.ALL_USERS_HTTP)
    public List<User> users() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.users();
    }

    @SubscriptionMapping
    @Timed(MetricNames.ONE_USER_WS)
    public Mono<User> getUser(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUser(id);
    }

    @SubscriptionMapping
    @Timed(MetricNames.ALL_USERS_WS)
    public Flux<User> getUsers() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUsers();
    }

}
