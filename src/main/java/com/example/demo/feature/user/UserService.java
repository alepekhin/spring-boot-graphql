package com.example.demo.feature.user;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Методы получения данных -
 * два для Http Graphql client
 * и два для Web socket Graphql client
 * 
 * @author Alex
 * @since 31.07.2023
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    User user(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return userRepository.getOneUser(id);
    }

    List<User> users() throws ExecutionException, InterruptedException, TimeoutException {
        return userRepository.getAllUsers();
    }

    Mono<User> getUser(Integer id) throws ExecutionException, InterruptedException, TimeoutException {
        return Mono.just(userRepository.getOneUser(id));

    }

    Flux<User> getUsers() throws ExecutionException, InterruptedException, TimeoutException {
        return Flux.fromIterable(userRepository.getAllUsers());
    }

}
