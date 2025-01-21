package ru.otus.ms.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class OrderServiceIntegrationTest {
    @Test
    void contextRefreshed() {
        log.info("Order-service context refreshed");
    }
}
