package com.easyWaySolution.User_Management.Controller;

import com.easyWaySolution.User_Management.Dto.*;
import com.easyWaySolution.User_Management.Entity.Users;
import com.easyWaySolution.User_Management.FeignService.DatabaseService;
import com.easyWaySolution.User_Management.Service.MailService;
import com.easyWaySolution.User_Management.Service.UserService;
import com.easyWaySolution.User_Management.Util.Helper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;

@RestController
public class SubUserController {



    @Autowired
    DatabaseService databaseService;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(11);

    @PostMapping("/create/subUser/{username}")
    public ResponseEntity<String> createSubUser(@PathVariable String username , @RequestBody SubUserDTO subUserDTO , HttpSession session) {

        ResponseEntity<String> UNAUTHORIZED = userService.subUserCreation(username, subUserDTO, session);
        if (UNAUTHORIZED != null) return UNAUTHORIZED;
        return ResponseEntity.ok("Sub-user created successfully");
    }
}
