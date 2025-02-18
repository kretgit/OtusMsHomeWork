package ru.otus.ms.kitchenservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.ms.common.exception.CommonException;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.model.order.OrderStatus;
import ru.otus.ms.common.utils.DateTimeUtils;
import ru.otus.ms.common.utils.rest.RestUtils;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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


    public void doOrder(Order order) {
        checkOrderBeforePreparing(order);
        checkOrderIngredientsAvailable(order);
        prepareOrder(order);
    }

    private void checkOrderBeforePreparing(Order order) {
        if (order.getStatus() != OrderStatus.NEW) {
            throw new CommonException("Принимаются только заказы в статусе новый");
        }

        long diffInHours = DateTimeUtils.getTimeDiff(order.getCreated(), LocalDateTime.now(), TimeUnit.HOURS);
        if (diffInHours > configuration.getOrderMaxHoursAge()) {
            throw new CommonException("Заказ слишком старый для готовки");
        }
    }

    private void checkOrderIngredientsAvailable(Order order) {
        String id = order.getId();
        log.info("Проверяем наличие продуктов по заказу " + id);

        Future<OrderStatus> task = executorService.submit(() -> {
            try {
                Thread.sleep(configuration.getCheckingTimeMs());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Для заказ " + id + " все продукты в наличии");
            return OrderStatus.PROGRESS;
        });

        try {
            OrderStatus status = task.get();
            order.setStatus(status);
            onOrderChange(order);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void prepareOrder(Order order) {
        String id = order.getId();
        log.info("Начинаю готовить заказ " + id);

        Future<OrderStatus> task = executorService.submit(() -> {
            try {
                Thread.sleep(configuration.getPreparingTimeMs());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Заказ " + id + " готов");
            return OrderStatus.DONE;
        });

        try {
            OrderStatus status = task.get();
            order.setStatus(status);
            onOrderChange(order);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void onOrderChange(Order order) {
        callOrderService(order);
        log.info("Информация об изменении в заказе " + order.getId() + " отправлена в диспетчерскую");
    }

    private void callOrderService(Order order) {
        String url = RestUtils.composeUrl(configuration.getOrderServiceUrl(), "update");
        RestUtils.sendHttpRequest(restTemplate, HttpMethod.POST, url, null,
                ParameterizedTypeReference.forType(Void.class), order);
    }
}
