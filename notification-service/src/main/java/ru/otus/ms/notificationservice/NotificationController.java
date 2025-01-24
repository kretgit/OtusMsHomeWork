package ru.otus.ms.notificationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.CommonController;
import ru.otus.ms.common.model.order.Order;

@RestController
@RequiredArgsConstructor
public class NotificationController extends CommonController {

    private final NotificationService notificationService;

    @PostMapping("send")
    public void sendOrderNotification(@RequestBody Order order) {
        notificationService.createdAndSendOrderMessage(order);
    }

}
