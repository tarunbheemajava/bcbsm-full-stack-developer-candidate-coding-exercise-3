package com.feedback.service;


import com.feedback.bean.User;
import com.feedback.dto.AuthenticationRequest;
import com.feedback.dto.AuthenticationResponse;
import com.feedback.dto.SignUpRequest;
import com.feedback.repository.UserRepository;
import com.feedback.util.AppsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    final private Map<String, String> emailsOtp = new HashMap<String, String>();

    public AuthenticationResponse register(SignUpRequest request) {
        validateRequest(request);
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNonExpired(Boolean.TRUE)
                .accountNonLocked(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .isActive(Boolean.TRUE)
                .enabled(Boolean.TRUE)
                .role(request.getRole())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().jwt(jwtToken).user(user).build();
    }

    private void validateRequest(SignUpRequest request) {
        if(repository.findByUsername(request.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Username is already exists");
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = repository.findByUsername(request.getUsername()).orElse(null);
        if (Objects.isNull(user)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format(AppsConstants.USER_NOT_FOUND_MSG, request.getUsername()));
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().jwt(jwtToken).user(user).build();
    }
    public List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }
}
