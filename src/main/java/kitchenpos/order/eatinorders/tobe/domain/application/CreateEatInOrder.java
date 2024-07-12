package kitchenpos.order.eatinorders.tobe.domain.application;

import static kitchenpos.order.common.tobe.domain.entity.Order.createEatInOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.order.common.tobe.domain.service.CreateOrderValidator;
import kitchenpos.order.common.tobe.domain.vo.OrderType;
import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.entity.OrderLineItem;
import kitchenpos.order.common.tobe.domain.entity.OrderTable;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorders.tobe.dto.CreateOrderDto;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface CreateEatInOrder {
    Order execute(CreateOrderDto createEatInOrderDto);
}

@Service
class DefaultCreateEatInOrder implements CreateEatInOrder {
    private final OrderRepository orderRepository;
    private final CreateOrderValidator createOrderValidator;
    private final OrderTableRepository orderTableRepository;

    public DefaultCreateEatInOrder(OrderRepository orderRepository,
                                   CreateOrderValidator createOrderValidator, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.createOrderValidator = createOrderValidator;
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public Order execute(CreateOrderDto createEatInOrderDto) {
        this.validate(createEatInOrderDto);
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        OrderTable orderTable = orderTableRepository.findById(createEatInOrderDto.getOrderTableId()).orElseThrow();
        Order order = createEatInOrder(UUID.randomUUID(), orderLineItems, orderTable);
        return orderRepository.save(order);
    }

    private void validate(CreateOrderDto createEatInOrderDto) {
        createOrderValidator.validate(createEatInOrderDto);
        if (createEatInOrderDto.getOrderType() != OrderType.EAT_IN) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTable = orderTableRepository.findById(createEatInOrderDto.getOrderTableId())
                                                          .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }
}
