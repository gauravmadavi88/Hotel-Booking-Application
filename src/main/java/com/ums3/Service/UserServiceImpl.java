package com.ums3.Service;

import com.ums3.payload.UserDto;
import com.ums3.payload.loginDto;
import com.ums3.Entity.AppUser;
import com.ums3.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    public UserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDto AddUser(UserDto userdto) {
        AppUser user = new AppUser();
        user.setName(userdto.getName());
        user.setUsername(userdto.getUsername());
        user.setEmailId(userdto.getEmailId());
        user.setPassword( new BCryptPasswordEncoder().encode(userdto.getPassword()));
        user.setUserRole(userdto.getUserRole());
        AppUser appUser = appUserRepository.save(user);
        UserDto dto = new UserDto();
        dto.setId(appUser.getId());
        dto.setName(appUser.getName());
        dto.setUsername(appUser.getUsername());
        dto.setEmailId(appUser.getEmailId());
        dto.setPassword(appUser.getPassword());
        dto.setUserRole(appUser.getUserRole());

        return dto;
    }

    @Override
    public String verifyLogin(loginDto logindto) {
        Optional<AppUser> ByUsername = appUserRepository.findByUsernameOrEmailId(logindto.getUsername(), logindto.getEmailId());
        if (ByUsername.isPresent()){
            AppUser appUser= ByUsername.get();
            if (BCrypt.checkpw(logindto.getPassword(), appUser.getPassword())){
               return jwtService.generateToken(appUser);
            }
        }
        return null;

    }
}
