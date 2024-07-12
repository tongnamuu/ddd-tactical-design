package kitchenpos.order.common.tobe.application;

import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.vo.OrderType;
import kitchenpos.order.eatinorders.tobe.domain.application.CompleteEatInOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CompleteOrderFacade {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final CompleteEatInOrder completeEatInOrder;

    public CompleteOrderFacade(OrderRepository orderRepository, OrderService orderService, CompleteEatInOrder completeEatInOrder) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.completeEatInOrder = completeEatInOrder;
    }

    @Transactional
    public Order complete(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                                           .orElseThrow(NoSuchElementException::new);
        if (order.getOrderType() == OrderType.EAT_IN) {
            return completeEatInOrder.execute(order);
        } else {
            return orderService.complete(orderId);
        }
    }
}
