package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.tobe.domain.entity.OrderTable;
import kitchenpos.order.common.tobe.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ClearOrderTable {
    OrderTable execute(UUID orderTableId);
}

@Service
class DefaultClearOrderTable implements ClearOrderTable {
    private final OrderTableRepository orderTableRepository;

    DefaultClearOrderTable(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public OrderTable execute(UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                                                    .orElseThrow(() -> new IllegalArgumentException("주문 테이블이 존재하지 않습니다."));
        orderTable.clearTable();
        return orderTableRepository.save(orderTable);
    }
}
