package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex
 * @since 31.07.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    Integer id;
    String name;
    String username;
    String email;

}
