package ru.otus.ms.kitchenservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ConfigurationProperties("kitchen")
@Data
public class KitchenConfiguration {

    private long preparingTimeMs = 10_000;

    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public RestTemplate getResTemplate() {
        return new RestTemplate();
    }
}
