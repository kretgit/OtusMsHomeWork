package ru.otus.ms.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.ms.common.model.notification.NotificationMessage;


@Service
@Slf4j
public class NotificationService {

    public void sendMessage(NotificationMessage notificationMessage) {
        log.info("new message found: {}", notificationMessage);
    }

}
