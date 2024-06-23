package kitchenpos.order.eatinorders.tobe.infra.repository;

import java.util.UUID;
import kitchenpos.order.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
