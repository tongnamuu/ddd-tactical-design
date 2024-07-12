package kitchenpos.order.application;

import kitchenpos.order.common.tobe.domain.vo.OrderType;
import kitchenpos.order.eatinorders.tobe.domain.application.CreateEatInOrder;
import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.dto.CreateOrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrderFacade {
    private final OrderService orderService;
    private final CreateEatInOrder createEatInOrder;

    public CreateOrderFacade(OrderService orderService, CreateEatInOrder createEatInOrder) {
        this.orderService = orderService;
        this.createEatInOrder = createEatInOrder;
    }

    @Transactional
    public Order createOrder(CreateOrderDto createOrderDto) {
        if (createOrderDto.getOrderType() == OrderType.EAT_IN) {
            return createEatInOrder.execute(createOrderDto);
        }
        return orderService.create(createOrderDto);
    }
}
