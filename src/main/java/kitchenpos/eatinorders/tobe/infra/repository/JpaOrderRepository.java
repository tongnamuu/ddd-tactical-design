package kitchenpos.eatinorders.tobe.infra.repository;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.entity.Order;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends OrderRepository, JpaRepository<Order, UUID> {
}
