package kitchenpos.order.common.domain.vo;

import kitchenpos.order.eatinorders.tobe.domain.entity.Order;

public enum OrderType {
    DELIVERY {
        @Override
        public Order createOrder() {
            return null;
        }
    },
    TAKEOUT {
        @Override
        public Order createOrder() {
            return null;
        }
    },
    EAT_IN {
        @Override
        public Order createOrder() {
            return null;
        }
    },
    ;

    public abstract Order createOrder();
}
