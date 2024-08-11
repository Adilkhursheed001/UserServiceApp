package com.example.UserServiceApp;


//@SpringBootTest
//public class RegisterClientTest {
//
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    JpaRegisteredClientRepository jpaRegisteredClientRepository;
//
//    @Test
//    @Commit
//    public void RegisterClienttoDb(){
//        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                    .clientId("postman")
//                    .clientSecret(bCryptPasswordEncoder.encode("postmanpassword"))
//                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                    .redirectUri("http://oauth.pstmn.io/v1/callback")
//                    .postLogoutRedirectUri("http://127.0.0.1:9000/oauth2/token")
//                    .scope(OidcScopes.OPENID)
//                    .scope(OidcScopes.PROFILE)
//                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                    .build();
//
//        jpaRegisteredClientRepository.save(oidcClient);
//
//    }
//}
