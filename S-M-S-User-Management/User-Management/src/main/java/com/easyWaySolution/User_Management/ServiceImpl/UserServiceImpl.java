package com.easyWaySolution.User_Management.ServiceImpl;

import com.easyWaySolution.User_Management.DTO.UserDto;
import com.easyWaySolution.User_Management.Entity.Users;
import com.easyWaySolution.User_Management.Repository.UsersRepo;
import com.easyWaySolution.User_Management.Security.JWTService;
import com.easyWaySolution.User_Management.Service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder(11);

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService ;

    @Override
    public String registerUser(UserDto userDto) {
        Users users = new Users();
        users.setEmail(userDto.getEmail());
        users.setPassword(encoder.encode(userDto.getPassword()));
        usersRepo.save(users);
        return "Register Successfully !";
    }

    @Override
    public String loginUser(UserDto dto) throws BadRequestException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        String token = jwtService.generateToken(dto.getEmail());
        System.out.println(token);
        return token;
    }
}
