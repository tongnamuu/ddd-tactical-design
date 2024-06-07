package kitchenpos.menus.tobe.application.query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuDto;
import kitchenpos.menus.tobe.dto.MenuProductDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuQueryHandler {
    private static final String ALL_MENU_SQL = """
        select
            hex(m.id) as m_id,
            m.name as m_name,
            m.price as m_price,
            m.displayed as m_displayed,
            hex(mg.id) as mg_id,
            mg.name as mg_name,
            mp.quantity as p_quantity,
            hex(p.id) as p_id,
            p.name as p_name,
            p.price as p_price
        from menu m
                 left join menu_group mg on m.menu_group_id = mg.id
                 left join kitchenpos.menu_product mp on m.id = mp.menu_id
                 left join kitchenpos.product p on mp.product_id = p.id
        order by m.id
        """;

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final JdbcTemplate jdbcTemplate;

    public MenuQueryHandler(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository,
                            JdbcTemplate jdbcTemplate) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAllMenu() {
        List<MenuQueryResult> result = jdbcTemplate.query(
            ALL_MENU_SQL, (rs, rowNum) -> {
                return new MenuQueryResult(
                    toUUID(rs.getString("m_id")).toString(),
                    rs.getString("m_name"),
                    rs.getBigDecimal("m_price"),
                    rs.getBoolean("m_displayed"),
                    toUUID(rs.getString("mg_id")).toString(),
                    rs.getString("mg_name"),
                    rs.getLong("p_quantity"),
                    toUUID(rs.getString("p_id")).toString(),
                    rs.getString("p_name"),
                    rs.getBigDecimal("p_price")
                );
            }
        );
        Map<String, MenuDto> menuDtoMap = new HashMap<>();
        for (MenuQueryResult r : result) {
            if (menuDtoMap.containsKey(r.getMenuId())) {
                MenuDto dto = menuDtoMap.get(r.getMenuId());
                dto.addMenuProduct(MenuProductDto.from(r));
                menuDtoMap.put(r.getMenuId(), dto);
            } else {
                MenuDto dto = MenuDto.from(r);
                menuDtoMap.put(r.getMenuId(), dto);
            }
        }
        return new ArrayList<>(menuDtoMap.values());
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAllMenuGroup() {
        return menuGroupRepository.findAll();
    }

    private UUID toUUID(String s) {
        return new UUID(
            new BigInteger(s.substring(0, 16), 16).longValue(),
            new BigInteger(s.substring(16), 16).longValue());
    }
}
