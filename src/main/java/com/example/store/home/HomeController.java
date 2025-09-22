package com.example.store.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public HomeDto welcome(){;
        return new HomeDto( """
        Welcome to the Store API! Here are some available endpoints:
        - GET:    /           : Welcome message (this page)
        - GET:    /products   : List all products
        - GET:    /orders     : List all orders
        - POST:   /orders     : Create a new order
       """);
    }
}
