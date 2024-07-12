package kitchenpos.order.common.tobe.domain.vo;

import kitchenpos.order.common.tobe.domain.entity.Order;

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
