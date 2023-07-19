package com.example.day24_canvas_pracv1.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.day24_canvas_pracv1.model.OrderDetails;
import com.example.day24_canvas_pracv1.model.Orders;
import com.example.day24_canvas_pracv1.service.OrderService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path="/home")
public class OrderController {

    @Autowired
    OrderService orderSvc;

    @GetMapping()
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping(path="/orderform")
    public ModelAndView getOrderForm () {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("orderform");
        mav.addObject("orderForm", new Orders());
        return mav;
    }

    // form gets directed to post mapping set the model attributes of Order in a HTTP Session
    @PostMapping(path="/todetails")
    public ModelAndView setOrderSession (HttpSession session, @ModelAttribute("orderForm") Orders order) {
        session.setAttribute("order", order);
        System.out.println("order in setOrderSession: >>>>>>" + session.getAttribute("order"));
        ModelAndView mav = new ModelAndView("redirect:/home/orderdetails");
        return mav;
    }

    @GetMapping(path="/orderdetails")
    public ModelAndView getOrderDetails() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("orderdetails");
        mav.addObject("orderdetails", new OrderDetails());
        return mav;
    }

    @PostMapping(path="/nextdetails")
    public ModelAndView addDetails(HttpSession session, @ModelAttribute("orderdetails") OrderDetails details) {
        System.out.println("order in addDetails: >>>>>>" + session.getAttribute("order"));

        // Create list of Order Details to save when user adds another item
        List<OrderDetails> orderDetailsList = null;

        // Instantiate orderDetailsList if it doesn't exist in current session
        if(session.getAttribute("orderDetails") == null) {
            orderDetailsList = new LinkedList<>();
            orderDetailsList.add(details);

            // Set details added in a new session for order details
            session.setAttribute("orderDetails", orderDetailsList);
        } else{
            // If session already exists, get the list from existing order details session & add to that list
            orderDetailsList = (List<OrderDetails>) session.getAttribute("orderDetails");
            orderDetailsList.add(details);

            // Re-set the order details list as it has updated details
            session.setAttribute("orderDetails", orderDetailsList);
        }

        System.out.println(orderDetailsList);
        ModelAndView mav = new ModelAndView();

        mav.setViewName("orderdetails");
        mav.addObject("orderdetails", new OrderDetails());
        return mav;
    }

    @PostMapping(path="/completeorder")
    public ModelAndView completeOrder(HttpSession session, @ModelAttribute("orderdetails") OrderDetails details) {
        //  List<OrderDetails> orderDetailsList = null;
        // if(session.getAttribute("orderDetails") == null) {
        //     orderDetailsList = new ArrayList<OrderDetails>();
        //     orderDetailsList.add(details);
        //     session.setAttribute("orderDetails", orderDetailsList);
        // }else{
        //     orderDetailsList = (List<OrderDetails>) session.getAttribute("orderDetails");
        //     orderDetailsList.add(details);
        //     session.setAttribute("orderDetails", orderDetailsList);
        // }

        // System.out.println("ordDetailList>>>>>" + orderDetailsList);

        ModelAndView mav = new ModelAndView("redirect:/home/revieworder");
        return mav;
    }

    @GetMapping(path="/revieworder")
    public ModelAndView reviewOrder(HttpSession session) {
        System.out.println("order in reviewOrder: >>>>>>" + session.getAttribute("order"));
        ModelAndView mav = new ModelAndView();
        mav.setViewName("revieworder");
        mav.addObject("orderform", session.getAttribute("order"));
        mav.addObject("orderDetails", (List<OrderDetails>)session.getAttribute("orderDetails"));
        return mav;
    }

    @GetMapping(path="/closeorder")
    public ModelAndView closeOrder(HttpSession session) {
        // Get details of both sessions
        Orders orderSession = (Orders) session.getAttribute("order");
        List<OrderDetails> orderDetailSession = (List<OrderDetails>) session.getAttribute("orderDetails");
        orderSvc.makeOrder(orderSession, orderDetailSession);
        session.invalidate();
        ModelAndView mav = new ModelAndView("redirect:/home");
        return mav;
    }

}
