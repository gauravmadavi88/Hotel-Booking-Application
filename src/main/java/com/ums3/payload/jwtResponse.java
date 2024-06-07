package com.ums3.payload;

import lombok.Data;

@Data
public class jwtResponse {
    private String Type = "Bearer";
    private String token;

}
