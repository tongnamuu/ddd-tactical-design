package kitchenpos.order.common.tobe.application.event;

import kitchenpos.common.domainevent.event.EatInOrderCompleted;
import kitchenpos.order.eatinorders.tobe.domain.application.ClearOrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderEventListener {
    private final ClearOrderTable clearOrderTable;

    public OrderEventListener(ClearOrderTable clearOrderTable) {
        this.clearOrderTable = clearOrderTable;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEatInOrderCompleted(EatInOrderCompleted event) {
        clearOrderTable.execute(event.getOrderTableId());
    }
}
