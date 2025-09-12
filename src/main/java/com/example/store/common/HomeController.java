package com.example.store.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String welcome(){
        return
                """
        Welcome to the Store API!
        Here are some available endpoints:
        - GET    /           : Welcome message (this page)
        - GET    /products   : List all products
        - GET    /orders     : List all orders
        - POST   /orders     : Create a new order
       \s
        For full API documentation, visit: https://spring-boot-store-production-6ce7.up.railway.app/swagger-ui.html
        PS: For testing the endpoints, Postman app is recommended\s
       \s""";
    }
}
