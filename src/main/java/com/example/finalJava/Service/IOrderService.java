package com.example.finalJava.Service;

import com.example.finalJava.dto.Order;

public interface IOrderService {

    Order getById(Long orderId);

    Order save(Order order);
}
