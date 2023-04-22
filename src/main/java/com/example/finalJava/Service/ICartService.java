package com.example.finalJava.Service;

import com.example.finalJava.dto.Cart;
import com.example.finalJava.dto.CartInfo;
import com.example.finalJava.dto.CartItem;

import java.util.List;

public interface ICartService {

    Cart getCartById(Long id);

    Cart addToCart(Long cartId, Integer productId, Integer quantity);

    Cart delete(Cart cart);

    CartInfo addToCart(CartInfo cartInfo, Integer productId);

    List<CartItem> getItems(CartInfo cartInfo);

    Double getCartTotal(List<CartItem> cartItems);

    void checkout(CartInfo cartInfo);
}
