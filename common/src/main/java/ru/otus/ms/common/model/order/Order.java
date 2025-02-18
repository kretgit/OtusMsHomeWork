package ru.otus.ms.common.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order implements Serializable {
    private String id;
    private OrderDetails details;
    private int amount;
    private OrderStatus status;
    private LocalDateTime created;
}
