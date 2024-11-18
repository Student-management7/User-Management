package com.easyWaySolution.User_Management.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

        private StudentPermissions student;
        private AttendancePermissions attendance;
        private FeesPermissions fees;
        private TeacherPermissions teacher;
        private CoursePermissions course;
        private ExamPermissions exam;
        private EventPermissions event;
        private ReportPermissions report;
        private boolean createSubUser ;

}
