package com.example.day24_canvas_pracv1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.day24_canvas_pracv1.model.OrderDetails;
import com.example.day24_canvas_pracv1.model.Orders;

import com.example.day24_canvas_pracv1.repository.OrderDetailsRepo;
import com.example.day24_canvas_pracv1.repository.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderDetailsRepo detailsRepo;

    @Transactional
    public Boolean makeOrder(Orders order, List<OrderDetails> orderDetails) {
       // Retrieve primary key of order
       Integer createdOrderId = orderRepo.insertOrder(order); 

       // Set order Id in orderdetails
       for(OrderDetails od : orderDetails) {
            od.setOrderId(createdOrderId);
       }

       detailsRepo.add(orderDetails);

       return true;
    }
}
