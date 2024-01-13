package com.example.demo.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
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

    /**
     * TimedAspect создает метрику для оценки времени выполнения
     * метода с аннотацией @Timed(имя метрики)
     * Значение метрики доступно по адресу /actuator/metrics/имя метрики
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public ApplicationRunner applicationRunner() throws IOException {
        Properties git = new Properties();
        git.load(resource.getInputStream());
        return args -> {
            log.info("branch {}, commit {}", git.getProperty("git.branch"), git.getProperty("git.commit.id.abbrev"));
        };
    }

    /**
     * TODO: Validate connection just after application start
     */

}
