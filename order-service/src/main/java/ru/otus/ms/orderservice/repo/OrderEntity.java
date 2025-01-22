package ru.otus.ms.orderservice.repo;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.otus.ms.common.model.order.OrderDetails;
import ru.otus.ms.common.model.order.OrderStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OrderEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Type(type = "jsonb")
    @Column(name = "details")
    private OrderDetails details;

    @Column(name = "amount")
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created")
    private OffsetDateTime created;
}
