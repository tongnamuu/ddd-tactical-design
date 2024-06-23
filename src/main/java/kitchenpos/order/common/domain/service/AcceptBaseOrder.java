package kitchenpos.order.common.domain.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.domain.vo.OrderStatus;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

public interface AcceptBaseOrder {
    Order execute(UUID orderId);
}

@Service
class DefaultAcceptBaseOrder implements AcceptBaseOrder {
    private final OrderRepository orderRepository;

    public DefaultAcceptBaseOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                                           .orElseThrow(NoSuchElementException::new);
        if (order.getOrderStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setOrderStatus(OrderStatus.ACCEPTED);
        return order;
    }
}
