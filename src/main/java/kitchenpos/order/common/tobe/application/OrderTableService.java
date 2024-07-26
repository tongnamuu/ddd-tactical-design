package kitchenpos.order.common.tobe.application;

import kitchenpos.order.common.tobe.domain.entity.OrderTable;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.repository.OrderTableRepository;
import kitchenpos.order.common.tobe.domain.vo.OrderStatus;
import kitchenpos.order.eatinorders.tobe.domain.application.ClearOrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderTableService {
    private final OrderTableRepository orderTableRepository;
    private final OrderRepository orderRepository;
    private final ClearOrderTable clearOrderTable;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderRepository orderRepository, ClearOrderTable clearOrderTable) {
        this.orderTableRepository = orderTableRepository;
        this.orderRepository = orderRepository;
        this.clearOrderTable = clearOrderTable;
    }

    @Transactional
    public OrderTable create(final OrderTable request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName(name);
        orderTable.setNumberOfGuests(0);
        orderTable.setOccupied(false);
        return orderTableRepository.save(orderTable);
    }

    @Transactional
    public OrderTable sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.setOccupied(true);
        return orderTable;
    }

    @Transactional
    public OrderTable clear(final UUID orderTableId) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTableId, OrderStatus.COMPLETED)) {
            throw new IllegalStateException();
        }
        return clearOrderTable.execute(orderTableId);
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final UUID orderTableId, final OrderTable request) {
        final int numberOfGuests = request.getNumberOfGuests();
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        orderTable.setNumberOfGuests(numberOfGuests);
        return orderTable;
    }

    @Transactional(readOnly = true)
    public List<OrderTable> findAll() {
        return orderTableRepository.findAll();
    }
}
