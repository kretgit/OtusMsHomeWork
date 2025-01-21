package ru.otus.ms.userservice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("user")
@Data
public class UserConfiguration {
    private int maxCount = 100;
    private int maxLenParam = 255;
}
