package com.photo

import org.junit.jupiter.api.AfterAll
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {
    static final GenericContainer REDIS_CONTAINER

    static {
        REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379);

        REDIS_CONTAINER.start();

        // Set system properties for Redis connection
        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
        System.setProperty("file.path", "/path/to/file");

        // Set up the application context for the tests
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "0"));
        context = app.run();
    }

    static ConfigurableApplicationContext context;

    @AfterAll
    static void afterAll() {
        // Stop the Redis container and shut down the application context
        REDIS_CONTAINER.stop();
        context.close();
    }

}
