package kitchenpos.order.common.tobe.domain.infra.repository;

import kitchenpos.order.common.tobe.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
}
