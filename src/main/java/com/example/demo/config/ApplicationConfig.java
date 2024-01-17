package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Alex
 * @since 06.08.2023
 */
@Configuration
@Slf4j
public class ApplicationConfig {

    @Value("classpath:/git.properties")
    Resource resource;

    @Bean
    public ApplicationRunner applicationRunner() throws IOException {
        Properties git = new Properties();
        git.load(resource.getInputStream());
        return args -> {
            log.info("branch {}, commit {}", git.getProperty("git.branch"), git.getProperty("git.commit.id.abbrev"));
        };
    }

}
