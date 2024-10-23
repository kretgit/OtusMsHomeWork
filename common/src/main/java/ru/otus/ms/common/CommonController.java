package ru.otus.ms.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.utils.rest.HealthResponse;

@RestController
public class CommonController {

    @GetMapping("health")
    public HealthResponse getHealthResponse() {
        return new HealthResponse();
    }

}
