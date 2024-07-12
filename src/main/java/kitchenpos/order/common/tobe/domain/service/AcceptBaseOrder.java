package kitchenpos.order.common.tobe.domain.service;

import kitchenpos.order.common.tobe.domain.vo.OrderStatus;
import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

public interface AcceptBaseOrder {
    Order execute(Order order);
}

@Service
class DefaultAcceptBaseOrder implements AcceptBaseOrder {
    private final OrderRepository orderRepository;

    public DefaultAcceptBaseOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(Order order) {
        if (order.getOrderStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setOrderStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
