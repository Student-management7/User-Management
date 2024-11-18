package com.easyWaySolution.User_Management.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class Users {
    private UUID id;
    private LocalDateTime creationDateTime ;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private boolean isSchool;
    private String schoolID;
    private String schoolName;
    @Lob
    @Column(columnDefinition = "text")
    private String permission;
}
