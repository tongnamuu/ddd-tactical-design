package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.vo.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface ServeEatInOrder {
    Order execute(UUID orderId);
}

@Service
class DefaultServeEatInOrder implements ServeEatInOrder {
    private final OrderRepository orderRepository;

    public DefaultServeEatInOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order execute(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(NoSuchElementException::new);
        if (order.getOrderStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setOrderStatus(OrderStatus.SERVED);
        return orderRepository.save(order);
    }
}
