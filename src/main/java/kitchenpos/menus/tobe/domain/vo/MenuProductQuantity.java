package kitchenpos.menus.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private long value;

    private MenuProductQuantity(long value) {
        this.value = value;
    }

    protected MenuProductQuantity() {

    }

    public static MenuProductQuantity of(Long quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        return new MenuProductQuantity(quantity);
    }

    public long getValue() {
        return value;
    }
}
