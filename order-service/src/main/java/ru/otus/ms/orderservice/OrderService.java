package ru.otus.ms.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.ms.common.exception.CommonException;
import ru.otus.ms.common.model.order.FoodType;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.model.order.OrderDetails;
import ru.otus.ms.common.model.order.OrderStatus;
import ru.otus.ms.common.utils.Integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    public Order createOrder(OrderController.CreateOrderRq rq) {
        Order newOrder;

        try {
           newOrder = checkRequestAndCreateOrder(rq);
        } catch (CommonException e) {
            sendNotification(e.getMessage());
            return null;
        }

        sendNotification(Integration.toJson(newOrder));
        return newOrder;
    }

    private Order checkRequestAndCreateOrder(OrderController.CreateOrderRq rq) throws CommonException {
        Map<String, Integer> requestItems = rq.getItems();

        if (requestItems.size() == 0 || FoodType.maxItemsExceeded(requestItems.size())) {
            throw new CommonException("Слишком много элементов в заказе");
        }

        Map<FoodType, Integer> items = new HashMap<>();

        rq.getItems().forEach((k, v) -> {
            FoodType type = FoodType.findType(k);

            if (type == null) {
                throw new CommonException("В меню отсутствует " + k);
            }

            if (v > type.getMaxItems()) {
                throw new CommonException(String.format("Превышено максимальное число позиций %s (%s шт.)",
                        type.name(), type.getMaxItems()));
            }

            items.put(type, v);
        });

        String orderId = getNextOrderId();
        int amount = countAmount(items);

        return Order.builder()
                .id(orderId)
                .details(new OrderDetails(items))
                .amount(amount)
                .status(OrderStatus.NEW)
                .created(LocalDateTime.now())
                .build();
    }

    private void sendNotification(String message) {
        log.error(message);
    }

    private String getNextOrderId() {
        return UUID.randomUUID().toString();
    }

    private int countAmount(Map<FoodType, Integer> details) {
        return details.entrySet().stream().mapToInt(e -> e.getValue() * e.getKey().getAmount()).sum();
    }


}
