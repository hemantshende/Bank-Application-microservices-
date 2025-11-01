package com.bank.auth_service.dtos;


import com.bank.auth_service.entity.User_Type;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private User_Type userType;
}
