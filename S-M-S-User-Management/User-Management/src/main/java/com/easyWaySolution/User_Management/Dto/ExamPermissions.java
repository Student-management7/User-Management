package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public class ExamPermissions {
    private boolean scheduleExam;
    private boolean viewResult;

    ExamPermissions(){
        this.scheduleExam = false;
        this.viewResult = false;
    }

}
