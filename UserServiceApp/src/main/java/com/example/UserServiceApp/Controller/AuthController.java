package com.example.UserServiceApp.Controller;

import com.example.UserServiceApp.Service.UserService;
import com.example.UserServiceApp.dtos.Userdto;
import com.example.UserServiceApp.dtos.ValidateTokenRequestdto;
import com.example.UserServiceApp.dtos.loginRequestdto;
import com.example.UserServiceApp.dtos.signUprequestDto;
import com.example.UserServiceApp.model.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/signUp")
    public ResponseEntity<Userdto>  SignUp(@RequestBody signUprequestDto requestDto){
        Userdto userdto = userService.signup(requestDto.getEmailID(),requestDto.getPassword());
        return  ResponseEntity.ok(userdto);
    }

    @PostMapping("/login")
    public ResponseEntity<Userdto> login(@RequestBody loginRequestdto loginRequest){
         return userService.login(loginRequest.getEmailID(),loginRequest.getPassword());
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestdto tokenRequestdto){
          SessionStatus session = userService.validate(tokenRequestdto.getToken());
          return  new ResponseEntity<>(session, HttpStatus.OK);
    }
}
