package com.example.finalJava.Service;

import com.example.finalJava.Repository.OrderRepository;
import com.example.finalJava.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order getById(Long orderId) {
        if (orderId == null) {
            return null;
        }
        return orderRepository.findById(orderId).orElse((null));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
