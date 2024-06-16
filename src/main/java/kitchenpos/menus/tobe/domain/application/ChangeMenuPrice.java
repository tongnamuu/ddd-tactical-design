package kitchenpos.menus.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface ChangeMenuPrice {
    Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto);
}

@Service
class DefaultChangeMenuPrice implements ChangeMenuPrice {
    private final MenuRepository menuRepository;

    public DefaultChangeMenuPrice(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId, MenuChangePriceDto menuChangePriceDto) {
        final MenuPrice menuPrice = MenuPrice.of(menuChangePriceDto.getPrice());
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.changePrice(menuPrice);
        menuRepository.saveMenu(menu);
        return menu;
    }
}
