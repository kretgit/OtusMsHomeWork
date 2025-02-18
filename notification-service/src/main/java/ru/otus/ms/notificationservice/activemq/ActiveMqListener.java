package ru.otus.ms.notificationservice.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.notificationservice.NotificationService;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
@Slf4j
public class ActiveMqListener implements MessageListener {

    private final NotificationService notificationService;

    public ActiveMqListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @JmsListener(destination = "${notification.active-mq.consume-order-topic}")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Order order = (Order) objectMessage.getObject();
            notificationService.createdAndSendOrderMessage(order);
        } catch (Exception e) {
            log.error("Ошбика приема пакета Active MQ: " + e);
        }

    }

}
