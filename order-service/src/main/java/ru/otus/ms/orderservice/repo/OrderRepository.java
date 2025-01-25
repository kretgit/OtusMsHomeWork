package ru.otus.ms.orderservice.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findAllByCreatedAfter(OffsetDateTime created, Pageable pageable);

    @Query(value = "select 'ORD_' || nextval('orders_id_seq')", nativeQuery = true)
    String getNextOrderId();
}
