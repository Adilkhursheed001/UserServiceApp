package com.example.UserServiceApp.model;

import com.example.UserServiceApp.dtos.Userdto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseClass{
    private String name;
    private String emailID;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public Userdto from(User user){
        Userdto userdto = new Userdto();
        userdto.setEmailID(user.getEmailID());
        userdto.setId(user.getId());
        userdto.setName(user.getName());
        userdto.setPassword(user.getPassword());
        return  userdto;
    }
}
