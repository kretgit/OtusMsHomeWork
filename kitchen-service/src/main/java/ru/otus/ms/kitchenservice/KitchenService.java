package ru.otus.ms.kitchenservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.model.order.OrderStatus;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@Slf4j
public class KitchenService {

    private final KitchenConfiguration configuration;

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public KitchenService(KitchenConfiguration configuration) {
        this.configuration = configuration;
        this.executorService = configuration.getExecutorService();
        this.restTemplate = configuration.getResTemplate();
    }

    public Order prepareOrder(Order order) {
        log.info("Начинаю готовить новый заказ " + order.getId());

        Future<OrderStatus> task = executorService.submit(() -> {
            try {
                Thread.sleep(configuration.getPreparingTimeMs());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Заказ " + order.getId() + " готов");
            return OrderStatus.PROGRESS;
        });

        try {
            task.get();
            callOrderService();
            order.setStatus(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return order;

    }

    private void callOrderService() {
        log.info("Информация о готовом заказе отправлена в диспетчерскую");
    }
}
