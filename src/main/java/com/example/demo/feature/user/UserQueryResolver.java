package com.example.demo.feature.user;

import com.example.demo.model.User;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

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
    @Timed("get_user_time")
    public User user(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUser(id);
    }

    @QueryMapping
    @Timed("get_user_list_time")
    public List<User> users() throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUsers();
    }

}
