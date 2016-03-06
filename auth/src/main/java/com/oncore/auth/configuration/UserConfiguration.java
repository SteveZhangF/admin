package com.oncore.auth.configuration;

import com.oncore.auth.mail.MailSenderService;
import com.oncore.auth.user.*;
import com.oncore.auth.user.resource.MeResource;
import com.oncore.auth.user.resource.PasswordResource;
import com.oncore.auth.user.resource.UserResource;
import com.oncore.auth.user.resource.VerificationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import javax.validation.Validator;

@Configuration
public class UserConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public VerificationTokenService verificationTokenService() {
        return new VerificationTokenServiceImpl(userRepository, verificationTokenRepository, mailSenderService, validator, passwordEncoder);
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, validator, passwordEncoder);
    }

    @Bean
    public UserResource userResource() {
        return new UserResource(userService(), verificationTokenService(), tokenServices, passwordEncoder, clientDetailsService);
    }

    @Bean
    public PasswordResource passwordResource() {
        return new PasswordResource();
    }

    @Bean
    public VerificationResource verificationResource() {
        return new VerificationResource();
    }

    @Bean
    public MeResource meResource() {
        return new MeResource();
    }

}
