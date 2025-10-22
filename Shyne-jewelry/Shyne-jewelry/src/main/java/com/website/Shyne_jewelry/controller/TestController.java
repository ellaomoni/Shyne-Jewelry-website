package com.website.Shyne_jewelry.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

@GetMapping()
    public String getStatus() { return "App is running and time, " + LocalDateTime.now();
}

}
