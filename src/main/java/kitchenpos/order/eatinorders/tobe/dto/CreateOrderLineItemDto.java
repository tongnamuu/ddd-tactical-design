package kitchenpos.order.eatinorders.tobe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderLineItemDto {
    private UUID menuId;
    private BigDecimal price;
    private Long quantity;

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
}
