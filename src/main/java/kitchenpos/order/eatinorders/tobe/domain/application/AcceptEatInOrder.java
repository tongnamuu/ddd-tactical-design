package kitchenpos.order.eatinorders.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.domain.vo.OrderStatus;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface AcceptEatInOrder {
    Order execute(UUID orderId);
}

@Service
class DefaultAcceptEatInOrder implements AcceptEatInOrder {
    private final OrderRepository orderRepository;

    DefaultAcceptEatInOrder(OrderRepository orderRepository) {
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
        orderRepository.save(order);
        return order;
    }
}
