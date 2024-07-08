package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.domain.service.ServeBaseOrder;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

public interface ServeEatInOrder {
    Order execute(UUID orderId);
}

@Component
class DefaultServeEatInOrder implements ServeEatInOrder {
    private final ServeBaseOrder serveBaseOrder;
    private final OrderRepository orderRepository;

    public DefaultServeEatInOrder(ServeBaseOrder serveBaseOrder, OrderRepository orderRepository) {
        this.serveBaseOrder = serveBaseOrder;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order execute(UUID orderId) {
        Order order = serveBaseOrder.execute(orderId);
        return orderRepository.save(order);
    }
}
