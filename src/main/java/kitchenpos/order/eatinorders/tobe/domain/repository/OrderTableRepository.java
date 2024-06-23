package kitchenpos.order.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.eatinorders.tobe.domain.entity.OrderTable;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    List<OrderTable> findAll();
}

