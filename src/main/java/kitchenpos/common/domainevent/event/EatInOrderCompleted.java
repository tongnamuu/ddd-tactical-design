package kitchenpos.common.domainevent.event;

import kitchenpos.order.common.tobe.domain.entity.OrderTable;

import java.util.UUID;

public class EatInOrderCompleted {
    private final UUID orderTableId;

    public EatInOrderCompleted(OrderTable orderTable) {
        this.orderTableId = orderTable.getId();
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
