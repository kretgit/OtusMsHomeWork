package ru.otus.ms.kitchenservice.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.model.order.OrderStatus;
import ru.otus.ms.kitchenservice.KitchenService;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
@Slf4j
public class ActiveMqListener implements MessageListener {

    private final KitchenService kitchenService;

    public ActiveMqListener(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @Override
    @JmsListener(destination = "${kitchen.active-mq.consume-order-topic}")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Order order = (Order) objectMessage.getObject();
            if (OrderStatus.NEW.equals(order.getStatus())) {
                kitchenService.doOrder(order);
            }
        } catch (Exception e) {
            log.error("Ошбика приема пакета Active MQ: " + e);
        }

    }

}
