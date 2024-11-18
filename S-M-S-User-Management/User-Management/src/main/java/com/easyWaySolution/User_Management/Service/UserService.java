package com.easyWaySolution.User_Management.Service;

import com.easyWaySolution.User_Management.Dto.SubUserDTO;
import com.easyWaySolution.User_Management.Dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface UserService {

    String registerUser(UserDto userDto );

    String loginUser (UserDto dto) throws BadRequestException;

    String forgetUserPassword(UserDto userDto);

    ResponseEntity<String> subUserCreation(String username, SubUserDTO subUserDTO, HttpSession session)

}
