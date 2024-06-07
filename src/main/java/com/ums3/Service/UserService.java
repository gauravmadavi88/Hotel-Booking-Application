package com.ums3.Service;

import com.ums3.payload.UserDto;
import com.ums3.payload.loginDto;

public interface UserService {

    UserDto AddUser(UserDto userdto);

    String verifyLogin(loginDto logindto);
}
