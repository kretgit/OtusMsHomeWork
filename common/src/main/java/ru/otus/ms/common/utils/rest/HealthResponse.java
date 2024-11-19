package ru.otus.ms.common.utils.rest;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class HealthResponse {
    private final String status = "OK";
}
