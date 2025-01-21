package ru.otus.ms.kitchenservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class KitchenServiceIntegrationTest {
    @Test
    void contextRefreshed() {
        log.info("kitchen-service context refreshed");
    }
}
