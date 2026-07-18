package com.EcommerceProject.Controller;

import com.EcommerceProject.Model.AppRole;
import com.EcommerceProject.Model.Role;
import com.EcommerceProject.Model.User;
import com.EcommerceProject.Repositories.RoleRepository;
import com.EcommerceProject.Repositories.UserRepository;
import com.EcommerceProject.Security.Request.LoginRequest;
import com.EcommerceProject.Security.Request.SignupRequest;
import com.EcommerceProject.Security.Response.MessageResponse;
import com.EcommerceProject.Security.Response.UserInfoResponse;
import com.EcommerceProject.Security.Services.UserDetailsImpl;
import com.EcommerceProject.Security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles,userDetails.getEmail(), jwtToken);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Username is already taken! "));

        }

        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is already taken! "));

        }

        User user = new User(
              signupRequest.getUsername(),
              signupRequest.getEmail(),
              encoder.encode(signupRequest.getPassword())
        );

        Set<String> strRole = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRole == null){
           Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                   .orElseThrow(()-> new RuntimeException("Role is not found"));
           roles.add(userRole);
        }else {
           strRole.forEach(role -> {
               switch (role){
                   case "admin":
                       Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                               .orElseThrow(()-> new RuntimeException("Role is not found"));
                       roles.add(adminRole);
                       break;
                   case "seller":
                       Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                               .orElseThrow(()-> new RuntimeException("Role is not found"));
                       roles.add(sellerRole);
                       break;
                   default:
                       Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                               .orElseThrow(()-> new RuntimeException("Role is not found"));
                       roles.add(userRole);

               }
           });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
