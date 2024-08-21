package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.*;

import java.time.Instant;
import java.util.HashSet;

public class OrderFactory {

    public static Order createOrder(User client) {
        Order order = Order.builder()
                .id(1L)
                .moment(Instant.now())
                .status(OrderStatus.WAITING_PAYMENT)
                .client(client)
                .payment(new Payment())
                .items(new HashSet<>())
                .build();

        Product product = ProductFactory.createProduct();
        OrderItem orderItem = new OrderItem(order, product, 1, 10.0);
        order.getItems().add(orderItem);
        return order;
    }
}
