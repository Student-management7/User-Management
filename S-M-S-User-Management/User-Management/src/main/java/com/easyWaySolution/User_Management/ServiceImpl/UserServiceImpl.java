package com.easyWaySolution.User_Management.ServiceImpl;

import com.easyWaySolution.User_Management.DTO.UserDto;
import com.easyWaySolution.User_Management.Entity.Users;
import com.easyWaySolution.User_Management.FeignService.DatabaseService;
import com.easyWaySolution.User_Management.Security.JWTService;
import com.easyWaySolution.User_Management.Security.LoggedInUser;
import com.easyWaySolution.User_Management.Security.UserDeatilsServices;
import com.easyWaySolution.User_Management.Service.MailService;
import com.easyWaySolution.User_Management.Service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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

        String token = jwtService.generateToken(dto.getEmail());
        System.out.println(token);
        return token;
    }

    @Override
    public String forgetUserPassword(UserDto userDto) {

      LoggedInUser loggedInUser  = (LoggedInUser) userDeatilsServices.loadUserByUsername(userDto.getEmail());

      String newPassword = generatePassword();
        userDto.setPassword(encoder.encode(newPassword));
      databaseService.updateUser(userDto);
//      usersRepo.save(users);
      mailService.newPasswordMail(userDto.getEmail() , "New Password " , newPassword , " ... ");
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


}
