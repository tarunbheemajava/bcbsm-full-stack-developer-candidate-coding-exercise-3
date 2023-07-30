package com.feedback.controller;


import com.feedback.dto.AuthenticationRequest;
import com.feedback.dto.AuthenticationResponse;
import com.feedback.dto.SignUpRequest;
import com.feedback.service.AuthenticationService;
import com.feedback.util.AppsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AppsConstants.AUTHENTICATION_BASE_API)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(description = "API for registration ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "208", description = "User already register" ),
            @ApiResponse(responseCode = "404", description = "Bad Request")
    })
    @PostMapping(AppsConstants.AUTHENTICATION_REGISTER_API)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid SignUpRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(description = "API to Login using username/emailId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "EmailID/Username is not found")
    })
    @PostMapping(AppsConstants.AUTHENTICATION_LOGIN_API)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
