package com.easyWaySolution.User_Management.ServiceImpl;


import com.easyWaySolution.User_Management.Dto.*;
import com.easyWaySolution.User_Management.Entity.Users;
import com.easyWaySolution.User_Management.FeignService.DatabaseService;
import com.easyWaySolution.User_Management.Security.JWTService;
import com.easyWaySolution.User_Management.Security.LoggedInUser;
import com.easyWaySolution.User_Management.Security.UserDeatilsServices;
import com.easyWaySolution.User_Management.Service.MailService;
import com.easyWaySolution.User_Management.Service.UserService;
import com.easyWaySolution.User_Management.Util.Helper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(11);

    // Character pool for the password
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789" +
                    "!@#$%^&*()-_+=<>?";

    private static final SecureRandom RANDOM = new SecureRandom();


    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService ;
    @Autowired
    private UserDeatilsServices userDeatilsServices;

    @Autowired
    private  MailService mailService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    Gson gson;


    @Override
    public String registerUser(UserDto userDto) {
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        databaseService.saveUser(userDto);
//        usersRepo.save(users);
        return "Register Successfully !";
    }

    @Override
    public String loginUser(UserDto dto) throws BadRequestException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        System.out.println(authentication.getAuthorities().toString());
        String token = jwtService.generateToken(dto.getEmail() , String.valueOf(authentication.getAuthorities().stream().toList().get(0)));
        System.out.println(token);
        return token;
    }

    @Override
    public String forgetUserPassword(UserDto userDto) {

      LoggedInUser loggedInUser  = (LoggedInUser) userDeatilsServices.loadUserByUsername(userDto.getEmail());

      String newPassword = generatePassword();
        userDto.setPassword(encoder.encode(newPassword));
  Users users = databaseService.updateUser(userDto);
//      usersRepo.save(users);
      mailService.newPasswordMail(users.getEmail() , "New Password " , newPassword , " ... ");
        return "New Password sent to your email";
    }



    public static String generatePassword() {
        StringBuilder password = new StringBuilder(10); // Fixed length: 10

        // Generate 10 random characters from the pool
        for (int i = 0; i < 10; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return password.toString();
    }


    public ResponseEntity<String> subUserCreation(String username, SubUserDTO subUserDTO, HttpSession session) {
        String attribute = (String) session.getAttribute("username");
        if (attribute == null || !attribute.equalsIgnoreCase(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session or session expired");
        }
        if(!Helper.isValidEmail(subUserDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email !");
        }
        Users existUser =  databaseService.findByMail(subUserDTO.getEmail());
        if(existUser !=null){
            throw new jakarta.ws.rs.BadRequestException("User already created by that Email");
        }


        String sessionPermissions = (String) session.getAttribute("permissions");

        // Check if the user has permission to assign the desired permissions to the sub-user
        PermissionDto desiredPermissions = subUserDTO.getPermissions();
        if (!hasPermissionToAssign(sessionPermissions, desiredPermissions)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have permission to assign these permissions");
        }

        Users user = new Users();
        user.setEmail(subUserDTO.getEmail());
        user.setSchoolID(subUserDTO.getSchoolID());
        user.setSchoolName(subUserDTO.getSchoolName());
        user.setCreationDateTime(LocalDateTime.now());
        user.setPassword(encoder.encode(subUserDTO.getPassword()));

        String subUserPermission =gson.toJson(desiredPermissions);
        user.setPermission(subUserPermission);
        databaseService.saveSubUser(user);
        mailService.newUserCreate(subUserDTO.getEmail() ,"New Account " , subUserDTO.getPassword() , subUserDTO.getEmail(),  subUserPermission);
        return null;
    }



    private boolean hasPermissionToAssign(String userPermissionsJson, PermissionDto desiredPermissions) {

        PermissionDto userPermissions = gson.fromJson(userPermissionsJson, PermissionDto.class);

        return checkStudentPermissions(userPermissions.getStudent(), desiredPermissions.getStudent()) &&
                checkAttendancePermissions(userPermissions.getAttendance(), desiredPermissions.getAttendance()) &&
                checkFeesPermissions(userPermissions.getFees(), desiredPermissions.getFees()) &&
                checkTeacherPermissions(userPermissions.getTeacher(), desiredPermissions.getTeacher()) &&
                checkCoursePermissions(userPermissions.getCourse(), desiredPermissions.getCourse()) &&
                checkExamPermissions(userPermissions.getExam(), desiredPermissions.getExam()) &&
                checkEventPermissions(userPermissions.getEvent(), desiredPermissions.getEvent())
                &&userPermissions.isCreateSubUser();
    }

    private boolean checkStudentPermissions(StudentPermissions user, StudentPermissions desired) {
        // Check if user has permission to create, update, delete, or view student
        boolean res = (user.isViewStudent() || !desired.isViewStudent()) &&
                (user.isCreateStudent() || !desired.isCreateStudent()) &&
                (user.isUpdateStudent() || !desired.isUpdateStudent()) &&
                (user.isDeleteStudent() || !desired.isDeleteStudent());
        return  res;
    }

    // Helper method to check Attendance Permissions
    private boolean checkAttendancePermissions(AttendancePermissions user, AttendancePermissions desired) {
        // Check if user has permission to view and mark attendance
        List<String> userViewAttendanceList = stream(user.getViewAttendance().split(","))
                .map(String::trim)
                .toList();
        List<String> SubUserViewAttendanceList = stream(desired.getViewAttendance().split(",")).map(String::trim).toList();
        for ( String  p :SubUserViewAttendanceList){
            if(!userViewAttendanceList.contains(p)){
                return false;
            }
        }

        List<String> userMarkAttendanceList = Arrays.asList(user.getMarkAttendance().split(","));
        List<String> SubMarkViewAttendanceList = Arrays.asList(desired.getMarkAttendance().split(","));
        for ( String  p :SubMarkViewAttendanceList){
            if(!userMarkAttendanceList.contains(p)){
                return false;
            }
        }

        return true;
    }

    // Helper method to check Fees Permissions
    private boolean checkFeesPermissions(FeesPermissions user, FeesPermissions desired) {
        // Check if user has permission to view or collect fees
        boolean res = (user.isViewFees() || !desired.isViewFees()) &&
                (user.isCollectFees() || !desired.isCollectFees());
        return res;
    }

    // Helper method to check Teacher Permissions
    private boolean checkTeacherPermissions(TeacherPermissions user, TeacherPermissions desired) {
        // Check if user has permission to view, create, update, delete, or assign teacher
        boolean res = (user.isViewTeacher() || !desired.isViewTeacher()) &&
                (user.isCreateTeacher() || !desired.isCreateTeacher()) &&
                (user.isUpdateTeacher() || !desired.isUpdateTeacher()) &&
                (user.isDeleteTeacher() || !desired.isDeleteTeacher()) &&
                (user.isAssignSubject() || !desired.isAssignSubject());
        return  res;
    }

    // Helper method to check Course Permissions
    private boolean checkCoursePermissions(CoursePermissions user, CoursePermissions desired) {
        // Check if user has permission to view, create, update, or delete course
        boolean res =  (user.isViewCourse() || !desired.isViewCourse()) &&
                (user.isCreateCourse() || !desired.isCreateCourse()) &&
                (user.isUpdateCourse() || !desired.isUpdateCourse()) &&
                (user.isDeleteCourse() || !desired.isDeleteCourse());
        return  res;
    }

    // Helper method to check Exam Permissions
    private boolean checkExamPermissions(ExamPermissions user, ExamPermissions desired) {
        // Check if user has permission to schedule exam or view result
        boolean res = (user.isScheduleExam() || !desired.isScheduleExam()) &&
                (user.isViewResult() || !desired.isViewResult());
        return  res;

    }

    // Helper method to check Event Permissions
    private boolean checkEventPermissions(EventPermissions user, EventPermissions desired) {

        boolean res   = (user.isViewEvents() || !desired.isViewEvents()) &&
                (user.isCreateEvent() || !desired.isCreateEvent()) &&
                (user.isUpdateEvent() || !desired.isUpdateEvent()) &&
                (user.isDeleteEvent() || !desired.isDeleteEvent());
        return  res;
    }






}
