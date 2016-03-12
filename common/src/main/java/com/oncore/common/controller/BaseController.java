package com.oncore.common.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


/**
 * Created by steve on 3/3/16.
 */
public class BaseController {
    //    @Autowired
//    private UserRepository userRepository;
//
//    //TODO: Cache to reduce calls to userRepository
//    protected User ensureUserIsAuthorized(SecurityContextHolder securityContext, String userId) {
//        User user = loadUserFromSecurityContext(securityContext);
//        if (user != null && (user.getId().equals(userId) || user.getEmailAddress().equals(userId.toLowerCase()))) {
//            return user;
//        }
//        throw new AuthorizationException("User not permitted to access this resource");
//
//    }
    protected void checkUserContext() {
//        User user = loadUserFromSecurityContext(securityContext);
//        if (user != null && (user.getId().equals(userId) || user.getEmailAddress().equals(userId.toLowerCase()))) {
//            return user;
//        }
//        throw new AuthorizationException("User not permitted to access this resource");

    }


    protected void ensureUserIsAuthorized(SecurityContextHolder securityContext, String userId) {
//        User user = loadUserFromSecurityContext(securityContext);
//        if (user != null && (user.getId().equals(userId) || user.getEmailAddress().equals(userId.toLowerCase()))) {
//            return user;
//        }
//        throw new AuthorizationException("User not permitted to access this resource");

    }

    protected User loadUserFromSecurityContext() {
//        OAuth2Authentication requestingUser = (OAuth2Authentication) securityContext.getContext().getAuthentication().getPrincipal();
//        Object principal = requestingUser.getUserAuthentication().getPrincipal();
//        User user = null;
//        if(principal instanceof User) {
//            user = (User)principal;
//        } else {
//        }
        return null;
    }
}
