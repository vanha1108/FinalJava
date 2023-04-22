package com.example.finalJava.Service;

import com.example.finalJava.Repository.OrderItemRepository;
import com.example.finalJava.dto.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem getByOrderIdAndProductId(Long orderId, int productId) {
        return orderItemRepository.findByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void delete(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }
}
