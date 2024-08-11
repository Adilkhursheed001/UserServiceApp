package com.example.UserServiceApp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Userdto {
    private Long id;
    private String name;
    private String emailID;
    private String password;
}
