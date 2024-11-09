package com.easyWaySolution.User_Management.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubUserController {


    @PostMapping("/create/subUser")
    public ResponseEntity<String> createSubUser(){
        return null;
    }
}
