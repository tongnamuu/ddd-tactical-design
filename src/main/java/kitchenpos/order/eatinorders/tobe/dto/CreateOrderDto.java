package kitchenpos.order.eatinorders.tobe.dto;

import kitchenpos.order.common.tobe.domain.vo.OrderType;

import java.util.List;
import java.util.UUID;

public class CreateOrderDto {
    private OrderType orderType;
    private UUID orderTableId;
    private List<CreateOrderLineItemDto> orderLineItems;
    private String deliveryAddress;

    public CreateOrderDto(OrderType orderType, String deliveryAddress, List<CreateOrderLineItemDto> orderLineItems,) {
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderType = orderType;
    }

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
