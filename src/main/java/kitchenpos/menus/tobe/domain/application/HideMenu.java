package kitchenpos.menus.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface HideMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultHideMenu implements HideMenu {
    private final MenuRepository menuRepository;

    public DefaultHideMenu(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.hide();
        menuRepository.saveMenu(menu);
        return menu;
    }
}
