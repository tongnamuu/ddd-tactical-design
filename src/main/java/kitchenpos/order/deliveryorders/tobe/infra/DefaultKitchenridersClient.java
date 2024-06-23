package kitchenpos.order.deliveryorders.tobe.infra;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.order.deliveryorders.tobe.domain.service.KitchenridersClient;
import org.springframework.stereotype.Component;

@Component
class DefaultKitchenridersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }

}
