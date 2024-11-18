package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public class SubUserDTO {
    private String email;
    private String password;
    private String schoolID;
    private String schoolName;
    private PermissionDto permissions;
}
