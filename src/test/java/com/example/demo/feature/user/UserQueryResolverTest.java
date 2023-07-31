package com.example.demo.feature.user;

import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alex
 * @since 31.07.2023
 */

// Warning: This is integration test

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserQueryResolverTest {

    @Autowired
    GraphQlTester tester;

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

        User result =tester.document(document)
                .execute()
                .path("user")
                .entity(User.class)
                .get();
        assertEquals(1, result.getId());
        System.out.println(result);
    }
}
