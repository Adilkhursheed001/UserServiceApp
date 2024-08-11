package com.example.UserServiceApp.Service;

//import com.example.UserServiceApp.config.KafkaProducerConfig;

import com.example.UserServiceApp.dtos.Userdto;
import com.example.UserServiceApp.model.Session;
import com.example.UserServiceApp.model.SessionStatus;
import com.example.UserServiceApp.model.User;
import com.example.UserServiceApp.repository.SessionRepository;
import com.example.UserServiceApp.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;
    private SessionRepository sessionRepository;
//    private KafkaProducerConfig kafkaProducerConfig;
    private ObjectMapper objectMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,SessionRepository sessionRepository

                         ,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.objectMapper = objectMapper;
    }

    public Userdto signup(String email, String password,String name) throws JsonProcessingException {
     User user = new User();
     user.setEmailID(email);
     user.setName(name);
     user.setPassword(bCryptPasswordEncoder.encode(password));
     userRepository.save(user);
     Userdto savedUser = user.from(user);
//     SendEmailDto sendEmailDto = new SendEmailDto();
//     sendEmailDto.setTo(savedUser.getEmailID());
//     sendEmailDto.setFrom("www.scaler@077.com");
//     sendEmailDto.setSubject("New User Sign Up");
//     sendEmailDto.setBody("Welcome to Scaler , keep learning and exploring , Team Scaler");
//     kafkaProducerConfig.sendMessage("sendEmail", objectMapper.writeValueAsString(sendEmailDto));
//     kafkaProducerConfig.sendMessage("singUp",objectMapper.writeValueAsString(savedUser));
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
//
//        Map<String,Object> JasonForJwt = new HashMap<>();
//        JasonForJwt.put("email",usermodel.getEmailID());
//        JasonForJwt.put("expiration_date",new Date());
//        JasonForJwt.put("created_at",new Date());
//
//         // Making Signature
//        io.jsonwebtoken.security.MacAlgorithm algo = Jwts.SIG.HS256;
//        SecretKey key = algo.key().build();
//
//         String token = Jwts.builder().claims(JasonForJwt).signWith(key).compact();
//
//        Session session = new Session();
//        session.setToken(token);
//        session.setSessionStatus(SessionStatus.ACTIVE);
//        session.setUsermodel(usermodel);
//        sessionRepository.save(session);

        MultiValueMapAdapter<String,String> header = new MultiValueMapAdapter<>(new HashMap<>());
        header.add(HttpHeaders.SET_COOKIE,"auth_token" );

        Userdto userdto = usermodel.from(usermodel);
        ResponseEntity<Userdto> response = new ResponseEntity<>(userdto,header, HttpStatus.OK);
        return response;
    }

//    public Userdto findUser(Long userId){
//        Optional<User> userOptional = userRepository.findById(userId);
//         User user = userOptional.get();
//         Userdto userdto = user.from(user);
//         return userdto;
//    }

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
