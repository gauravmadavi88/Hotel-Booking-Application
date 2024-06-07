package com.ums3.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @JsonIgnore
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @JsonIgnore
    @Column(name = "user_role", nullable = false)
    private String userRole;

}