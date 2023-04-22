package com.example.finalJava.Repository;

import com.example.finalJava.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findByOrderIdAndProductId(Long orderId, int productId);

    List<OrderItem> findByOrderId(Long orderId);
}
