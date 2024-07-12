package kitchenpos.common.domainevent.event;

import kitchenpos.order.common.tobe.domain.entity.OrderTable;

public class EatInOrderCompleted {
    private final OrderTable orderTable;

    public EatInOrderCompleted(OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
