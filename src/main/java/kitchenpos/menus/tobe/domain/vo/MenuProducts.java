package kitchenpos.menus.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    private MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("menuProducts is null or empty");
        }
        return new MenuProducts(menuProducts);
    }

    public BigDecimal getSumOfProductPriceAndQuantity() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : this.menuProducts) {
            sum = sum.add(
                menuProduct.getProductPrice()
                           .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }
}
