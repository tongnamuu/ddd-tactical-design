package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import kitchenpos.menus.tobe.dto.MenuProductCreateDto;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface CreateMenu {
    Menu execute(MenuCreateDto menucreateDto);
}

@Service
class DefaultCreateMenu implements CreateMenu {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public DefaultCreateMenu(MenuRepository menuRepository, ProductRepository productRepository,
                             PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public final Menu execute(MenuCreateDto menucreateDto) {
        final MenuPrice menuPrice = MenuPrice.of(menucreateDto.getPrice());
        final MenuGroup menuGroup = menuRepository.findMenuGroupById(menucreateDto.getMenuGroupId())
                                                  .orElseThrow(NoSuchElementException::new);
        final List<MenuProductCreateDto> menuProductRequests = menucreateDto.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                               .map(MenuProductCreateDto::getProductId)
                               .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProductCreateDto menuProductRequest : menuProductRequests) {
            final MenuProductQuantity quantity = MenuProductQuantity.of(menuProductRequest.getQuantity());
            final Product product = products.stream()
                                            .filter(it -> it.getId().equals(menuProductRequest.getProductId()))
                                            .findFirst()
                                            .orElseThrow(NoSuchElementException::new);
            final MenuProduct menuProduct = new MenuProduct(product, quantity);
            menuProducts.add(menuProduct);
        }
        MenuProducts menuProduct = MenuProducts.of(menuProducts);
        BigDecimal sum = menuProduct.getSumOfProductPriceAndQuantity();
        if (menuPrice.getValue().compareTo(sum) > 0) {
            throw new IllegalArgumentException("메뉴의 가격이 너무 큽니다");
        }

        final MenuName menuName = MenuName.of(menucreateDto.getName(), purgomalumClient);

        final Menu menu = new Menu(
            UUID.randomUUID(),
            menuName,
            menuPrice,
            menuGroup,
            menucreateDto.isDisplayed(),
            menuProducts
        );
        return menuRepository.saveMenu(menu);
    }
}
