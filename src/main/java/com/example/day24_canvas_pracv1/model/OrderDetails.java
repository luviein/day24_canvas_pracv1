package com.example.day24_canvas_pracv1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private Integer orderId;
    private Integer orderDetailsId;
    private String product;
    private Float unitPrice;
    private Float discount;
    private Integer quantity;
}
