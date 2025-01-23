package ru.otus.ms.orderservice.repo;

import org.mapstruct.Mapper;
import ru.otus.ms.common.model.order.Order;
import ru.otus.ms.common.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderEntity toDb(Order order);

    default OffsetDateTime toDb(LocalDateTime created) {
        return DateTimeUtils.localToOffset(created);
    }

    Order toWeb(OrderEntity orderEntity);

    default LocalDateTime toWeb(OffsetDateTime created) {
        return DateTimeUtils.offsetToLocal(created);
    }

}
