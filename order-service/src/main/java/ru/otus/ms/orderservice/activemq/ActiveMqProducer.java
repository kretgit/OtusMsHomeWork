package ru.otus.ms.orderservice.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.otus.ms.common.model.order.Order;

@Component
@Slf4j
public class ActiveMqProducer {

    private final ActiveMqConfiguration activeMqConfiguration;

    private final JmsTemplate jmsTemplate;

    public ActiveMqProducer(ActiveMqConfiguration activeMqConfiguration, JmsTemplate jmsTemplate) {
        this.activeMqConfiguration = activeMqConfiguration;
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(Order order) {
        try {
            log.debug("Попытка отправки сообщения в ActiveMq");
            jmsTemplate.convertAndSend(activeMqConfiguration.getProduceOrderTopic(), order);
        } catch (Exception e) {
            log.error("Ошбика отправки пакета Active MQ: " + e);
        }
    }
}
