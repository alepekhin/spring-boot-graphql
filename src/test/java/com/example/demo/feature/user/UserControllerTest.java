package com.example.demo.feature.user;

import com.example.demo.model.User;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex
 * @since 31.07.2023
 */

// Warning: This is integration test

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Value("${local.server.port}")
    private int serverPort;

    @Value("${api-key}")
    String apiKey;

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    WebSocketGraphQlTester webSocketGraphQlTester;

    @PostConstruct
    void init() throws URISyntaxException {
        // we have to build WebSocketGraphQlTester manually, there is no autowired
        URI url = new URI("ws://localhost:" + serverPort + "/ws") ;
        webSocketGraphQlTester =
                WebSocketGraphQlTester.builder(url, new ReactorNettyWebSocketClient())
                .header("x-api-key", apiKey)
                .build();
    }

    @Test
    public void user() {
        String document = """
                    query {
                    user(id: 1) {
                                    id
                                    name
                                    username
                                    email
                                    }
                    }
    
                    """;

        var tester1 = httpGraphQlTester.mutate().header("x-api-key","xxx").build();

        User result =tester1.document(document)
                .execute()
                .path("user")
                .entity(User.class)
                .get();
        assertEquals(1, result.getId());
    }

    @Test
    public void user_negative_no_api_key() {
        String document = """
                    query {
                    user(id: "aa") {
                                    id
                                    name
                                    username
                                    email
                                    }
                    }
    
                    """;
        Throwable thrown = Assertions.assertThrows(Throwable.class, () -> {
            User result = httpGraphQlTester.document(document)
                    .execute()
                    .path("user")
                    .entity(User.class)
                    .get();
        }, "Exception was expected");
        assertTrue(thrown.getMessage().contains("500"));
    }

    @Test
    public void user_negative_invalid_id() {
        String document = """
                    query {
                    user(id: "aa") {
                                    id
                                    name
                                    username
                                    email
                                    }
                    }
    
                    """;
        var tester1 = httpGraphQlTester.mutate().header("x-api-key","xxx").build();
        Throwable thrown = Assertions.assertThrows(Throwable.class, () -> {
            User result =tester1.document(document)
                    .execute()
                    .path("user")
                    .entity(User.class)
                    .get();
        }, "Exception was expected");
        assertTrue(thrown.getMessage().contains("aa"));
    }

    @Test
    public void users() {
        String document = """
                    query Users {
                        users {
                            id
                            name
                            username
                            email
                        }
                    }
                """;

        var tester1 = httpGraphQlTester.mutate().header("x-api-key","xxx").build();

        var result = tester1.document(document)
                .execute()
                .path("users")
                .entityList(User.class)
                .get();
        assertTrue(result.size() > 0);
    }

    @Test
    public void getUser() {
        String document = """
                    subscription {
                        getUser(id: 1) {
                            id
                            name
                            username
                            email
                        }
                    }
                """;

        var result = webSocketGraphQlTester.document(document)
                .executeSubscription()
                .toFlux("getUser", User.class)
                .blockLast();
        assertEquals(1, result.getId());
    }

    @Test
    public void getUsers() {
        String document = """
                    subscription {
                        getUsers {
                            id
                            name
                            username
                            email
                        }
                    }
                """;

        var result = webSocketGraphQlTester.document(document)
                .executeSubscription()
                .toFlux("getUsers", User.class)
                .collectList()
                .block();
        assertTrue(result.size() > 0);
    }

}

