package com.easyWaySolution.User_Management.Service;

import com.easyWaySolution.User_Management.DTO.UserDto;
import org.apache.coyote.BadRequestException;

public interface UserService {

    String registerUser(UserDto userDto );

    String loginUser (UserDto dto) throws BadRequestException;
}
