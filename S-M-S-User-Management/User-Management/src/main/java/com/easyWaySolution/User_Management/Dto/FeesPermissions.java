package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public class FeesPermissions {
    private boolean viewFees;
    private boolean collectFees;

    FeesPermissions(){
        this.viewFees = false;
        this.collectFees = false;
    }
}
