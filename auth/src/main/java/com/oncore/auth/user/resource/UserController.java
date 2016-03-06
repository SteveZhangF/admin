package com.oncore.auth.user.resource;//package com.oshippa.auth.user.resource;
//
//import com.oncore.common.exception.BaseWebApplicationException;
//import com.oncore.auth.resource.BaseResource;
//import com.oncore.auth.resource.GenericExceptionMapper;
//import com.oncore.auth.user.Role;
//import com.oncore.auth.user.User;
//import com.oncore.auth.user.UserService;
//import com.oncore.auth.user.VerificationTokenService;
//import com.oncore.auth.user.api.ApiUser;
//import com.oncore.auth.user.api.CreateUserRequest;
//import com.oncore.auth.user.api.CreateUserResponse;
//import com.oncore.auth.user.exception.UserNotFoundException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.GET;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.SecurityContext;
//import java.io.Serializable;
//import java.util.*;
//
///**
// * Created by steve on 1/18/16.
// */
//@Controller
//public class UserController extends BaseResource{
//
//    private UserService userService;
//    private VerificationTokenService verificationTokenService;
//    private DefaultTokenServices tokenServices;
//    private PasswordEncoder passwordEncoder;
//    private ClientDetailsService clientDetailsService;
//
//    private static Logger LOG = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    public UserController(final UserService userService, final VerificationTokenService verificationTokenService,
//                          final DefaultTokenServices defaultTokenServices,
//                          final PasswordEncoder passwordEncoder, ClientDetailsService clientDetailsService) {
//        this.userService = userService;
//        this.verificationTokenService = verificationTokenService;
//        this.tokenServices = defaultTokenServices;
//        this.passwordEncoder = passwordEncoder;
//        this.clientDetailsService = clientDetailsService;
//    }
//
//    @RolesAllowed({"ROLE_USER"})
//    @RequestMapping(name="/v1.0/me/", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ApiUser getUser(HttpServletRequest request) {
//        User requestingUser = request.getUserPrincipal()
//        if(requestingUser == null) {
//            throw new UserNotFoundException();
//        }
//        return new ApiUser(requestingUser);
//    }
//
//    @PermitAll
//    @RequestMapping(name = "/v1.0/users/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CreateUserResponse> singupUser(@RequestBody CreateUserRequest requestBody,
//                                                         HttpServletRequest request
//    ) {
//        ApiUser user = userService.createUser(requestBody);
//        CreateUserResponse createUserResponse = new CreateUserResponse(user, createTokenForNewUser(
//                user.getId(), requestBody.getPassword().getPassword(), request.getUserPrincipal().getName()));
//        verificationTokenService.sendEmailRegistrationToken(createUserResponse.getApiUser().getId());
//        System.out.println("creating user");
//        return new ResponseEntity<CreateUserResponse>(createUserResponse, HttpStatus.OK);
//    }
//
//    private OAuth2AccessToken createTokenForNewUser(String userId, String password, String clientId) {
//        String hashedPassword = passwordEncoder.encode(password);
//        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
//                userId,
//                hashedPassword, Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.toString())));
//        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
//        OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
//                Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.toString())),
//                true, authenticatedClient.getScope(), null, null, null, null);
//        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
//        return tokenServices.createAccessToken(oAuth2Authentication);
//    }
//
//    private OAuth2Request createOAuth2Request(Map<String, String> requestParameters, String clientId,
//                                              Collection<? extends GrantedAuthority> authorities, boolean approved, Collection<String> scope,
//                                              Set<String> resourceIds, String redirectUri, Set<String> responseTypes,
//                                              Map<String, Serializable> extensionProperties) {
//        return new OAuth2Request(requestParameters, clientId, authorities, approved, scope == null ? null
//                : new LinkedHashSet<String>(scope), resourceIds, redirectUri, responseTypes, extensionProperties);
//    }
//
//
//}
