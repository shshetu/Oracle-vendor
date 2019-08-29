package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.*;
import com.shetu.bookstore.repository.OrderRepository;
import com.shetu.bookstore.service.CartItemService;
import com.shetu.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress, Payment payment, String shippingMethod, User user) {
        Order order = new Order();
        order.setBillingAddress(billingAddress);
        order.setOrderStatus("created");
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);

        /////////////////////////
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem: cartItemList){
        Book book =cartItem.getBook();
        cartItem.setOrder(order);
        book.setIntStockNumber(book.getIntStockNumber() - cartItem.getQty());
        }
        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Order findById(Long id) {
       Order order = new Order();
        Optional<Order> tempOrder = orderRepository.findById(id);
        if(tempOrder.isPresent()){
            order = tempOrder.get();
        }
        return order;
    }
}
