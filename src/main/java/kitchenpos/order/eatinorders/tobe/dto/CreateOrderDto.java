package kitchenpos.order.eatinorders.tobe.dto;

import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.domain.vo.OrderType;

public class CreateOrderDto {
    private OrderType orderType;
    private UUID orderTableId;
    private List<CreateOrderLineItemDto> orderLineItems;
    private String deliveryAddress;

    public OrderType getOrderType() {
        return orderType;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<CreateOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
