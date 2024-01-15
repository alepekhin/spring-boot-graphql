package com.example.demo.feature.user;

import com.example.demo.common.ClientFactory;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Внешний репозитарий данных
 * @author Alex
 * @since 14.01.2024
 */
@Component
@RequiredArgsConstructor
public class UserRepository {

    final ClientFactory clientFactory;

    public User getOneUser(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return clientFactory.getRepositoryClient()
                .documentName("user_get") // abbreviation of graphql-documents/user_get.graphql
                .variable("id", id)
                .retrieve("user")
                .toEntity(User.class)
                .toFuture()
                .get(2, TimeUnit.SECONDS);
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException, TimeoutException {
        return Arrays.stream(clientFactory.getRepositoryClient()
                .documentName("user_list")
                .retrieve("users.data")
                .toEntity(User[].class)
                .toFuture()
                .get(5, TimeUnit.SECONDS))
                .toList();
    }

}
