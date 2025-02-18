package ru.otus.ms.orderservice;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.CommonController;
import ru.otus.ms.common.model.order.Order;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
    public Order createNewOrder(@RequestBody CreateOrderRq rq, @RequestHeader HttpHeaders httpHeaders) {
        log.info("catch request headers:\n{}", httpHeaders);
        return orderService.createOrder(rq);
    }

    @GetMapping("recent")
    public List<Order> getRecentOrders() {
        return orderService.getRecentOrders();
    }

    @PostMapping("update")
    public void updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
    }
}
