package com.easyWaySolution.User_Management.Controller;

import com.easyWaySolution.User_Management.Security.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
//
//    @Autowired
//    LoggedInUser loggedInUser;

    @GetMapping("/test")
    public String hello(){
        return "hello bro";
    }
}
