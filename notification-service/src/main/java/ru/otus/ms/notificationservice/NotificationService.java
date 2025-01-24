package ru.otus.ms.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.otus.ms.common.exception.CommonException;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.utils.DateTimeUtils;
import ru.otus.ms.common.utils.Integration;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class NotificationService {

    public void createdAndSendOrderMessage(Order order) {
        String message = getMessageDataByOrder(order);
        if (ObjectUtils.isEmpty(message)) {
            log.error(Integration.toJson(order));
            throw new CommonException("Неизвестный статус, либо пустое собщение");
        }

        log.info(" * * * * * * * * * * * * * * * * * *");
        log.info("\n" + message);
    }

    private String getMessageDataByOrder(Order order) {
        String id = order.getId();

        switch (order.getStatus()) {
            case NEW:
                return String.format("Уважаемый клиент!\nПолучен заказ %s\nОжидайте оповещений", id);
            case PROGRESS:
                return String.format("Уважаемый клиент!\nВаш заказ %s передан уже готорят\nПожалуйста, подождите", id);
            case DONE:
                long durationSec = DateTimeUtils.getTimeDiff(order.getCreated(), LocalDateTime.now(), TimeUnit.SECONDS);
                return String.format("Уважаемый клиент!\nВаш заказ %s готов\nДлительность ожидания составила %s секунд" +
                        "\nСумма к оплате %s рублей\nПриятного аппетита!", id, durationSec, order.getAmount());
            case CANCEL:
                return "Уважаемый клиент!\nВаш заказ отменен\nПриносим извинения за доставленные неудобства...";
            default:
                return null;
        }

    }

}
