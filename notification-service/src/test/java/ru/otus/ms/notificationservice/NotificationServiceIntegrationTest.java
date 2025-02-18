package ru.otus.ms.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class NotificationServiceIntegrationTest {
    @Test
    void contextRefreshed() {
        log.info("notification context refreshed");
    }
}
