package kitchenpos.order.common.tobe.application;

import kitchenpos.common.domainevent.event.EatInOrderCompleted;
import kitchenpos.order.eatinorders.tobe.domain.application.CompleteEatInOrderEventHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public class OrderEventListener {
    private final CompleteEatInOrderEventHandler completeEatInOrderEventHandler;

    public OrderEventListener(CompleteEatInOrderEventHandler completeEatInOrderEventHandler) {
        this.completeEatInOrderEventHandler = completeEatInOrderEventHandler;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEatInOrderCompleted(EatInOrderCompleted event) {
        completeEatInOrderEventHandler.clearOrderTable(event.getOrderTable());
    }
}
