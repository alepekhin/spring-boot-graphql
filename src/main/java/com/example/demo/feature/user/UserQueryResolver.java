package com.example.demo.feature.user;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Alex
 * @since 31.07.2023
 */
@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    final UserService userService;

    @QueryMapping
    public User user(@Argument Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userService.getUser(id);
    }

}
