package kitchenpos.menus.tobe.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed,
                List<MenuProduct> menuProducts) {
        this.id = id;
        this.name = name.getValue();
        this.price = price.getValue();
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(final MenuPrice menuPrice, ProductRepository productRepository) {
        BigDecimal threshHoldPrice = this.getSumOfProductPriceAndQuantity(productRepository);
        if (menuPrice.getValue().compareTo(threshHoldPrice) > 0) {
            throw new IllegalArgumentException("New Price cannot be greater than threshHoldPrice");
        }
        this.price = menuPrice.getValue();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void displayOn(ProductRepository productRepository) {
        BigDecimal threshHoldPrice = getSumOfProductPriceAndQuantity(productRepository);
        if (this.price.compareTo(threshHoldPrice) > 0) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public BigDecimal getSumOfProductPriceAndQuantity(ProductRepository productRepository) {
        BigDecimal sum = BigDecimal.ZERO;
        List<UUID> productIds = this.menuProducts.stream().map(MenuProduct::getProductId).toList();

        Map<UUID, BigDecimal> productIdPriceMap = productRepository.findAllByIdIn(productIds).stream().collect(
            Collectors.toMap(
                Product::getId,
                Product::getPrice
            ));

        for (final MenuProduct menuProduct : this.menuProducts) {
            if (!productIdPriceMap.containsKey(menuProduct.getProductId())) {
                throw new NoSuchElementException("MenuProduct 에 올바르지 않은 ProductId 가 있습니다");
            }
            sum = sum.add(
                productIdPriceMap.get(menuProduct.getProductId())
                                 .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }
}
