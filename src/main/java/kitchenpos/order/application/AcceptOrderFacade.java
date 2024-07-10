package kitchenpos.order.application;

import kitchenpos.order.common.domain.vo.OrderType;
import kitchenpos.order.eatinorders.tobe.domain.application.AcceptEatInOrder;
import kitchenpos.order.eatinorders.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AcceptOrderFacade {
    private final OrderService orderService;
    private final AcceptEatInOrder acceptEatInOrder;
    private final OrderRepository orderRepository;

    public AcceptOrderFacade(OrderService orderService, AcceptEatInOrder acceptEatInOrder, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.acceptEatInOrder = acceptEatInOrder;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order acceptOrder(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                                           .orElseThrow(NoSuchElementException::new);
        if (order.getOrderType() == OrderType.EAT_IN) {
            return acceptEatInOrder.execute(order);
        }
        return orderService.accept(orderId);
    }

}
