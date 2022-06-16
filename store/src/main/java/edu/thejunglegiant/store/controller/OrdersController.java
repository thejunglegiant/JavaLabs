package edu.thejunglegiant.store.controller;

import edu.thejunglegiant.store.service.OrdersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService service;

    public OrdersController(OrdersService service) {
        this.service = service;
    }

    @GetMapping
    public String showOrders(Model model) {
        model.addAttribute("orders", service.fetchOrders());
        return "orders/orders";
    }
}
