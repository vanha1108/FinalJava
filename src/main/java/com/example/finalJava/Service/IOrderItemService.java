package com.example.finalJava.Service;

import com.example.finalJava.dto.OrderItem;

import java.util.List;

public interface IOrderItemService {

    OrderItem getByOrderIdAndProductId(Long orderId, int productId);

    OrderItem save(OrderItem orderItem);

    List<OrderItem> getByOrderId(Long orderId);

    void delete(List<OrderItem> orderItems);
}
