package com.aman.microservices.service;

import com.aman.microservices.dto.OrderRequest;
import com.aman.microservices.model.Order;
import com.aman.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        //map Order Request to order object
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(order.getQuantity());

        //save order to order repository
        orderRepository.save(order);
    }
}
