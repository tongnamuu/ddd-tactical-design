package kitchenpos.order.eatinorders.tobe.ui;

import kitchenpos.order.common.tobe.application.AcceptOrderFacade;
import kitchenpos.order.common.tobe.application.CompleteOrderFacade;
import kitchenpos.order.common.tobe.application.CreateOrderFacade;
import kitchenpos.order.common.tobe.application.OrderService;
import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.eatinorders.tobe.dto.CreateOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {
    private final CreateOrderFacade createOrderFacade;
    private final AcceptOrderFacade acceptOrderFacade;
    private final CompleteOrderFacade completeOrderFacade;
    private final OrderService orderService;

    public OrderRestController(CreateOrderFacade createOrderFacade, AcceptOrderFacade acceptOrderFacade, CompleteOrderFacade completeOrderFacade, OrderService orderService) {
        this.createOrderFacade = createOrderFacade;
        this.acceptOrderFacade = acceptOrderFacade;
        this.completeOrderFacade = completeOrderFacade;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final CreateOrderDto request) {
        final Order response = createOrderFacade.createOrder(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
                             .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<Order> accept(@PathVariable final UUID orderId) {

        return ResponseEntity.ok(acceptOrderFacade.acceptOrder(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<Order> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<Order> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<Order> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Order> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(completeOrderFacade.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
