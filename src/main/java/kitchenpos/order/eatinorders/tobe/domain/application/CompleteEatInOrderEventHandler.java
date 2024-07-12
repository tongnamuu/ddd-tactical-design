package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.tobe.domain.entity.OrderTable;
import kitchenpos.order.common.tobe.domain.repository.OrderTableRepository;
import org.springframework.stereotype.Component;

public interface CompleteEatInOrderEventHandler {
    void clearOrderTable(OrderTable orderTable);
}

@Component
class DefaultCompleteEatInOrderEventHandler implements CompleteEatInOrderEventHandler {
    private final OrderTableRepository orderTableRepository;

    DefaultCompleteEatInOrderEventHandler(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void clearOrderTable(OrderTable orderTable) {
        orderTable.setOccupied(false);
        orderTable.setNumberOfGuests(0);
        orderTableRepository.save(orderTable);
    }
}

