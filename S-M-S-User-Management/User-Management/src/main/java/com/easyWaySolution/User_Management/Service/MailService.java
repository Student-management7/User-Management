package com.easyWaySolution.User_Management.Service;


import com.easyWaySolution.User_Management.Dto.PermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mail-Service" )
public interface MailService {

    @GetMapping("/sendMail")
     String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text);

    @GetMapping("/send-reset-PasswordMail")
    public String newPasswordMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text , @RequestParam String name);


    @GetMapping("/user-created")
    public String newUserCreate(@RequestParam String to, @RequestParam String subject, @RequestParam String text , @RequestParam String name, @RequestParam String permissionDto);

}
