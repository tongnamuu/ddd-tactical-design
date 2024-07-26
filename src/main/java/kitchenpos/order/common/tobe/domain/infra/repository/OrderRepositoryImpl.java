package kitchenpos.order.common.tobe.domain.infra.repository;

import kitchenpos.order.common.tobe.domain.entity.Order;
import kitchenpos.order.common.tobe.domain.repository.OrderRepository;
import kitchenpos.order.common.tobe.domain.vo.OrderStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository, JdbcTemplate jdbcTemplate) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order save(Order order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaOrderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll();
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(UUID orderTableId, OrderStatus status) {
        String sql = """
                select id from orders
                where order_table_id = '%s'
                and status != '%s'
                """.formatted(orderTableId, status.name());
        System.out.println("##### sql : " + sql);
        List<String> id = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("id"));
        if (id.isEmpty()) {
            return false;
        }
        return true;
    }
}
