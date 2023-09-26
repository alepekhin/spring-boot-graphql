package com.example.demo.feature.user;

import com.example.demo.model.User;
import lombok.Data;

import java.util.List;

/**
 * @author Alex
 * @since 26.09.2023
 */
@Data
public class UsersResponse {

    List<User> data;

}
