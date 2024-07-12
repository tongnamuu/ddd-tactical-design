package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.tobe.domain.service.AcceptBaseOrder;
import kitchenpos.order.common.tobe.domain.vo.OrderType;
import kitchenpos.order.common.tobe.domain.entity.Order;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface AcceptEatInOrder {
    Order execute(Order order);
}

@Service
class DefaultAcceptEatInOrder implements AcceptEatInOrder {
    private final AcceptBaseOrder acceptBaseOrder;

    public DefaultAcceptEatInOrder(AcceptBaseOrder acceptBaseOrder) {
        this.acceptBaseOrder = acceptBaseOrder;
    }

    @Override
    public Order execute(Order order) {
        if (order.getOrderType() != OrderType.EAT_IN) {
            throw new IllegalArgumentException("Order type is not EAT_IN");
        }
        return acceptBaseOrder.execute(order);
    }
}
