package com.easyWaySolution.User_Management.Controller;

import com.easyWaySolution.User_Management.DTO.UserDto;
import com.easyWaySolution.User_Management.Service.UserService;
import com.easyWaySolution.User_Management.ServiceImpl.UserServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody UserDto userDto) {
         return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) throws BadRequestException {

        return ResponseEntity.ok(userService.loginUser(userDto));
    }

}
