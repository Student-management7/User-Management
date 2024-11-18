package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public  class StudentPermissions {
    private boolean viewStudent;
    private boolean createStudent;
    private boolean updateStudent;
    private boolean deleteStudent;

    public StudentPermissions(){
        this.viewStudent = false;
        this.createStudent = false;
        this.updateStudent = false;
        this.deleteStudent = false;
    }
}