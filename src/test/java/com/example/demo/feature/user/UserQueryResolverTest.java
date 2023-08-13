package com.example.demo.feature.user;

import com.example.demo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Alex
 * @since 31.07.2023
 */

// Warning: This is integration test

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserQueryResolverTest {

    @Autowired
    HttpGraphQlTester tester;

    @Test
    public void queryUser() {
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

        var tester1 = tester.mutate().header("x-api-key","xxx").build();

        User result =tester1.document(document)
                .execute()
                .path("user")
                .entity(User.class)
                .get();
        assertEquals(1, result.getId());
        System.out.println(result);
    }

    @Test
    public void queryUser_negative_no_api_key() {
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
            User result =tester.document(document)
                    .execute()
                    .path("user")
                    .entity(User.class)
                    .get();
        }, "Exception was expected");
        assertTrue(thrown.getMessage().contains("500"));
    }

    @Test
    public void queryUser_negative_invalid_id() {
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
        var tester1 = tester.mutate().header("x-api-key","xxx").build();
        Throwable thrown = Assertions.assertThrows(Throwable.class, () -> {
            User result =tester1.document(document)
                    .execute()
                    .path("user")
                    .entity(User.class)
                    .get();
        }, "Exception was expected");
        assertTrue(thrown.getMessage().contains("aa"));
    }

}
