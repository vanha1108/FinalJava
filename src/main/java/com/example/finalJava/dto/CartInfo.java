package com.example.finalJava.dto;

import java.io.Serializable;

public class CartInfo implements Serializable {

    private Long orderId;

    private User user;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
