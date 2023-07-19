package com.example.day24_canvas_pracv1.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.day24_canvas_pracv1.model.OrderDetails;

@Repository
public class OrderDetailsRepo {
    
    @Autowired
    JdbcTemplate template;

    private final String insertSQL = "insert into order_details (product, unit_price, discount, quantity, orders_id) values(?,?,?,?,?)";

    public int[] add (List<OrderDetails> orderDetails) {
        List<Object[]> params = orderDetails.stream().map(od -> new Object[] {od.getProduct(),
        od.getUnitPrice(), 
        od.getDiscount(), 
        od.getQuantity(), 
        od.getOrderId()})
        .collect(Collectors.toList());
        
        int added[] = template.batchUpdate(insertSQL, params);
        System.out.println("orderdetailsrepo >>>>>>>>>>>>"+added);
        return added;
    }
}
