package ru.otus.ms.userservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserServiceIntegrationTest {
    @Test
    void contextRefreshed() {
        log.info("user-service context refreshed");
    }
}
