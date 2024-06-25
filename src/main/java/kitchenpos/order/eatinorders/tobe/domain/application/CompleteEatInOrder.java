package kitchenpos.order.eatinorders.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.domain.vo.OrderStatus;
import kitchenpos.order.common.domain.vo.OrderType;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

public interface CompleteEatInOrder {
    Order execute(UUID orderId);
}

@Service
class DefaultCompleteEatInOrder implements CompleteEatInOrder {
    private final OrderRepository orderRepository;

    public DefaultCompleteEatInOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                                           .orElseThrow(NoSuchElementException::new);
        if (order.getOrderType() != OrderType.EAT_IN) {
            throw new IllegalArgumentException("Order type is not EAT_IN");
        }
        final OrderStatus status = order.getOrderStatus();
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        final OrderTable orderTable = order.getOrderTable();
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.setNumberOfGuests(0);
            orderTable.setOccupied(false);
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        return order;
    }
}
