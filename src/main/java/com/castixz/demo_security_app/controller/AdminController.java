package com.castixz.demo_security_app.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    String accessAdminEndpoint(){
        return "Hello from admin controller";
    }
}
