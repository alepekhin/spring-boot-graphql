package com.example.demo.feature.user;

import com.example.demo.common.GraphqlClient;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    final GraphqlClient client;

    public User getUser(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return client.getClient().documentName("user_get")
                .variable("id", id)
                .retrieve("user").toEntity(User.class).toFuture().get(2, TimeUnit.SECONDS);

    }

}
