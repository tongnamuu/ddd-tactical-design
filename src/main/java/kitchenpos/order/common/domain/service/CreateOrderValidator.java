package kitchenpos.order.common.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.order.common.domain.vo.OrderType;
import kitchenpos.order.eatinorders.tobe.dto.CreateOrderDto;
import kitchenpos.order.eatinorders.tobe.dto.CreateOrderLineItemDto;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface CreateOrderValidator {
    void validate(CreateOrderDto createEatInOrderDto);
}

@Service
class DefaultCreateOrderValidator implements CreateOrderValidator {
    private final MenuRepository menuRepository;

    public DefaultCreateOrderValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void validate(CreateOrderDto createEatInOrderDto) {
        if (createEatInOrderDto.getOrderType() == null || createEatInOrderDto.getOrderType() != OrderType.EAT_IN) {
            throw new IllegalArgumentException("Invalid order type");
        }
        final List<CreateOrderLineItemDto> orderLineItemRequests = createEatInOrderDto.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findMenusByIdIn(
            orderLineItemRequests.stream()
                                 .map(CreateOrderLineItemDto::getMenuId)
                                 .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        for (final CreateOrderLineItemDto orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            if (quantity <= 0) {
                throw new IllegalArgumentException();
            }
            final Menu menu = menuRepository.findMenuById(orderLineItemRequest.getMenuId())
                                            .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
