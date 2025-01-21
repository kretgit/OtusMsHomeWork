package ru.otus.ms.common.model.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    private String id;
    private Map<FoodType, Integer> details;
    private int amount;
    private OrderStatus status;
    private LocalDateTime created;
}
