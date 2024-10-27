package com.easyWaySolution.User_Management.Service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mailService" , url = "http://localhost:8082")
public interface MailService {

    @GetMapping("/sendMail")
     String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text);

    @GetMapping("/send-reset-PasswordMail")
    public String newPasswordMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text , @RequestParam String name);

}
