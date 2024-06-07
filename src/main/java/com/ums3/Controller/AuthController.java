package com.ums3.Controller;

import com.ums3.payload.UserDto;
import com.ums3.payload.jwtResponse;
import com.ums3.payload.loginDto;
import com.ums3.Entity.AppUser;
import com.ums3.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Api/v3/Auth")
public class AuthController {
    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/{AddUser}")
    public ResponseEntity<UserDto> AddUser(@RequestBody UserDto userdto){
        UserDto dto = service.AddUser(userdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginDto logindto){

        String token = service.verifyLogin(logindto);
        if (token!=null){
            jwtResponse response = new jwtResponse();
            response.setToken(token);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Incredentials",HttpStatus.OK);
    }
    @GetMapping("/profile")
    public AppUser getUserProfile(@AuthenticationPrincipal AppUser user){
        return user;
    }
}
