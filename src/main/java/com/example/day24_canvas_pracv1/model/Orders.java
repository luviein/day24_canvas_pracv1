package com.example.day24_canvas_pracv1.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    private Integer orderId;
    private Date orderDate;
    private String custName;
    private String shipAddress;
    private String notes;
    private Float tax;
}
