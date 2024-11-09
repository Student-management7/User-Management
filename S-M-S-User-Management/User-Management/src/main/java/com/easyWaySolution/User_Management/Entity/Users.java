package com.easyWaySolution.User_Management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class Users {
    private UUID id;
    private LocalDateTime creationDateTime ;
    private String email;
    private String password;

}
