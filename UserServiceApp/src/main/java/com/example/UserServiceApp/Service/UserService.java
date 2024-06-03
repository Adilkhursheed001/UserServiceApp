package com.example.UserServiceApp.Service;

import com.example.UserServiceApp.dtos.Userdto;
import com.example.UserServiceApp.model.Session;
import com.example.UserServiceApp.model.SessionStatus;
import com.example.UserServiceApp.model.User;
import com.example.UserServiceApp.repository.SessionRepository;
import com.example.UserServiceApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserService {


    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public UserService(UserRepository userRepository,SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public Userdto signup(String email, String password){
     User user = new User();
     user.setEmailID(email);
     userRepository.save(user);

     Userdto savedUser = user.from(user);
     return savedUser;
    }

    public ResponseEntity<Userdto> login(String emailId, String password){

        Optional<User> user = userRepository.findByEmailID(emailId);

        if(user.isEmpty()){
            return null;
        }
        User usermodel = user.get();
        //Validation
//        if(!bCryptPasswordEncoder.matches(password,usermodel.getPassword())){
//            return null;
//        }

//        String token = RandomStringUtils.randomAlphabetic(30);
//        String message = "{\n" +
//       "   \"email\": \"naman@scaler.com\",\n" +
//       "   \"roles\": [\n" +
//        "      \"mentor\",\n" +
//        "      \"ta\"\n" +
//        "   ],\n" +
//        "   \"expirationDate\": \"23rdOctober2023\"\n" +
//        "}";
//         byte[] content = message.getBytes(StandardCharsets.UTF_8);

        Map<String,Object> JasonForJwt = new HashMap<>();
        JasonForJwt.put("email",usermodel.getEmailID());
        JasonForJwt.put("expiration_date",new Date());
        JasonForJwt.put("created_at",new Date());

         // Making Signature
        io.jsonwebtoken.security.MacAlgorithm algo = Jwts.SIG.HS256;
        SecretKey key = algo.key().build();

         String token = Jwts.builder().claims(JasonForJwt).signWith(key).compact();

        Session session = new Session();
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUsermodel(usermodel);
        sessionRepository.save(session);

        MultiValueMapAdapter<String,String> header = new MultiValueMapAdapter<>(new HashMap<>());
        header.add(HttpHeaders.SET_COOKIE,"auth_token" + token);

        Userdto userdto = usermodel.from(usermodel);
        ResponseEntity<Userdto> response = new ResponseEntity<>(userdto,header, HttpStatus.OK);
        return response;
    }

    public SessionStatus validate(String token){
        Optional<Session> sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty()){
            throw new RuntimeException("Session Expired");
        }
        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        try {
            Claims claims =
                    Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return  SessionStatus.ACTIVE;

    }
}
