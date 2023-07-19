package com.example.day24_canvas_pracv1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.day24_canvas_pracv1.model.Orders;

@Repository
public class OrderRepo {

    @Autowired
    JdbcTemplate template;
    private final String insertSQL = "insert into orders(order_date, customer_name, ship_address, notes, tax) values(?,?,?,?,?)";


    public Integer insertOrder(Orders order) {
        // To store generated PK of Orders
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // new String[] order_id is to store. Method expects to retrieve generated key value for "order_id" 
                // after performing insert operation. generated key holder is used to store the PK
                PreparedStatement ps = con.prepareStatement(insertSQL, new String[] {"order_id"});
                ps.setDate(1, order.getOrderDate());
                ps.setString(2, order.getCustName());
                ps.setString(3, order.getShipAddress());
                ps.setString(4, order.getNotes());
                ps.setFloat(5, order.getTax());

                return ps;
            }
            ;
        };

        // updates with order and auto generated PK
        template.update(psc, generatedKey);
        Integer priKeyValue = generatedKey.getKey().intValue();
        return priKeyValue;
    }
}
