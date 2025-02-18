package ru.otus.ms.kitchenservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.common.CommonController;
import ru.otus.ms.common.model.order.Order;

@RestController
public class KitchenController extends CommonController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @PostMapping("prepare")
    public void doOrder(@RequestBody Order order) {
        kitchenService.doOrder(order);
    }
}
