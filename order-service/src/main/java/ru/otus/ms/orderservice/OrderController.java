package ru.otus.ms.orderservice;

import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.CommonController;
import ru.otus.ms.common.model.order.Order;

import java.util.Map;

@RestController
public class OrderController extends CommonController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateOrderRq {
        @NonNull
        private Map<String, Integer> items;
    }

    @PostMapping("create")
    public Order createNewOrder(@RequestBody CreateOrderRq rq) {
        return orderService.createOrder(rq);
    }
}
