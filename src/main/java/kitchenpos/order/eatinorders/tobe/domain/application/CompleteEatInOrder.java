package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.common.domainevent.DomainEventPublisher;
import kitchenpos.common.domainevent.event.EatInOrderCompleted;
import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.entity.OrderTable;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.vo.OrderStatus;
import kitchenpos.order.common.tobe.domain.vo.OrderType;
import org.springframework.stereotype.Service;

public interface CompleteEatInOrder {
    Order execute(Order order);
}

@Service
class DefaultCompleteEatInOrder implements CompleteEatInOrder {
    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    public DefaultCompleteEatInOrder(OrderRepository orderRepository, DomainEventPublisher domainEventPublisher) {
        this.orderRepository = orderRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Order execute(Order order) {
        if (order.getOrderType() != OrderType.EAT_IN) {
            throw new IllegalArgumentException("Order type is not EAT_IN");
        }
        final OrderStatus status = order.getOrderStatus();
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        final OrderTable orderTable = order.getOrderTable();
        domainEventPublisher.publishEvent(new EatInOrderCompleted(orderTable));
        return order;
    }
}
