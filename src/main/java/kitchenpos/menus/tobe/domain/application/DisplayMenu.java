package kitchenpos.menus.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface DisplayMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultDisplayMenu implements DisplayMenu {
    private final MenuRepository menuRepository;

    public DefaultDisplayMenu(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.displayOn();
        return menu;
    }
}
