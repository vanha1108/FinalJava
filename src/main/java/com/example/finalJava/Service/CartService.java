package com.example.finalJava.Service;

import com.example.finalJava.Repository.CartRepository;
import com.example.finalJava.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

//    @Autowired
//    private ICartItemService cartItemService;

//    @Autowired
//    private IOrderService orderService;

//    @Autowired
//    private IOrderItemService orderItemService;


    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }

    @Override
    public Cart delete(Cart cart) {
        cartRepository.delete(cart);
        return cart;
    }

    @Override
    public Cart addToCart(Long cartId, Integer productId, Integer quantity) {
        Cart cart = new Cart();
        cart.setId(cartId);
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        }
        Product product = productService.getProductById(productId);
        if (cart.getTotalPrice() != null) {
            cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        } else {
            cart.setTotalPrice(product.getPrice() * quantity);
        }
//        CartItem cartItem = cartItemService.getCartItem(cartId, productId);
//        if (cartItem != null) {
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        } else {
//            cartItem = new CartItem();
//            cartItem.setPro(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setCart(cart);
//        }
        cart = cartRepository.save(cart);
//        cartItemService.save(cartItem);
        return cart;
    }

//    @Override
//    public Orders checkout(Long cartId) {
//        Cart cart = this.getCartById(cartId);
//        List<CartItem> cartItems = cartItemService.getByCartId(cart.getId());
//        if (CollectionUtils.isEmpty(cartItems)) {
//            throw new IllegalArgumentException("Cart must be contained item!");
//        }
//        Double totalPrice = 0.0;
//        Orders orders = new Orders();
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (CartItem cartItem : cartItems) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setPro(cartItem.getPro());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setOrder(orders);
//            orderItems.add(orderItem);
//            totalPrice += cartItem.getPro().getPrice() * cartItem.getQuantity();
//        }
//        orders.setTotalPrice(totalPrice);
//        orders = orderService.save(orders);
//        orderItemService.saveAll(orderItems);
//        cartItemService.deleteAll(cartItems);
//        this.delete(cart);
//        return orders;

    @Override
    public CartInfo addToCart(CartInfo cartInfo, Integer productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found!");
        }
        Order order = orderService.getById(cartInfo.getOrderId());
        if (order == null) {
            order = new Order();
            order.setOrderDate(new Date());
            order.setTotalPrice(0.0);
            order.setUserId(cartInfo.getUser().getId());
            order.setStatus(Boolean.FALSE);
        } else {
            if (order.getStatus()) {
                throw new IllegalArgumentException("Order status invalid!");
            }
        }
        OrderItem orderItem = orderItemService.getByOrderIdAndProductId(order.getId(), product.getId());
        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(1);
            orderItem.setPrice(product.getPrice());
        } else {
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            orderItem.setPrice(orderItem.getQuantity() * product.getPrice());
        }
        order.setTotalPrice(order.getTotalPrice() + product.getPrice());
        order = orderService.save(order);
        orderItem.setOrderId(order.getId());
        orderItemService.save(orderItem);
        cartInfo.setOrderId(order.getId());
        return cartInfo;
    }

    @Override
    public List<CartItem> getItems(CartInfo cartInfo) {
        List<CartItem> cartItems = new ArrayList<>();
        if (cartInfo != null) {
            List<OrderItem> orderItems = orderItemService.getByOrderId(cartInfo.getOrderId());
            if (!CollectionUtils.isEmpty(orderItems)) {
                for (OrderItem orderItem : orderItems) {
                    CartItem cartItem = new CartItem();
                    Product product = productService.getProductById(orderItem.getProductId());
                    cartItem.setProduct(product);
                    cartItem.setQuantity(orderItem.getQuantity());
                    cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
                    cartItems.add(cartItem);
                }
            }
        }
        return cartItems;
    }

    @Override
    public Double getCartTotal(List<CartItem> cartItems) {
        Double total = 0.0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getPrice();
        }
        return total;
    }

    @Override
    public void checkout(CartInfo cartInfo) {
        Order order = orderService.getById(cartInfo.getOrderId());
        order.setStatus(Boolean.TRUE);
        order.setOrderDate(new Date());
        orderService.save(order);
    }
}

