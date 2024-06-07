package com.ums3.payload;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String emailId;
    private String userRole;

}
