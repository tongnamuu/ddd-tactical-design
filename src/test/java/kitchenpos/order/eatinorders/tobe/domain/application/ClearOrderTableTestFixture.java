package kitchenpos.order.eatinorders.tobe.domain.application;

import kitchenpos.order.common.tobe.domain.repository.OrderTableRepository;


public class ClearOrderTableTestFixture extends DefaultClearOrderTable {
    public ClearOrderTableTestFixture(OrderTableRepository orderTableRepository) {
        super(orderTableRepository);
    }
}
