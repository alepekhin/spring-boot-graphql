package com.example.demo.feature.user;

import com.example.demo.common.HttpClientBase;
import com.example.demo.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
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
public class UserRepository {

    @Value("${repository_url}")
    String url;

    HttpGraphQlClient repositoryClient;

    @PostConstruct
    void init() {
        repositoryClient = HttpClientBase.INSTANCE.getHttpClient(url);
    }

    public User getOneUser(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return repositoryClient
                .documentName("user_get") // abbreviation of graphql-documents/user_get.graphql
                .variable("id", id)
                .retrieve("user")
                .toEntity(User.class)
                .toFuture()
                .get(2, TimeUnit.SECONDS);
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException, TimeoutException {
        return Arrays.stream(repositoryClient
                .documentName("user_list")
                .retrieve("users.data")
                .toEntity(User[].class)
                .toFuture()
                .get(5, TimeUnit.SECONDS))
                .toList();
    }

}
