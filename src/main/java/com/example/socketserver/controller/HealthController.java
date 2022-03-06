package com.example.socketserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/health")
@Controller
public class HealthController {

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("I'M STILL ALIVE");
    }
}
