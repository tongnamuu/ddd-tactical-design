package kitchenpos.order.common.domain.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.domain.vo.OrderStatus;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

public interface ServeBaseOrder {
    Order execute(UUID orderId);
}

@Service
class DefaultServeOrder implements ServeBaseOrder {
    private final OrderRepository orderRepository;

    public DefaultServeOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                                           .orElseThrow(NoSuchElementException::new);
        if (order.getOrderStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setOrderStatus(OrderStatus.SERVED);
        return order;
    }
}
