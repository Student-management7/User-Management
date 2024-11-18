package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public class CoursePermissions {
    private boolean viewCourse;
    private boolean createCourse;
    private boolean updateCourse;
    private boolean deleteCourse;

    CoursePermissions(){
        this.viewCourse = false;
        this.createCourse = false;
        this.updateCourse = false;
        this.deleteCourse = false;
    }

}
