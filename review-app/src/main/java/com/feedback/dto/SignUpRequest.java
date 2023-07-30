package com.feedback.dto;


import com.feedback.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpRequest {
	@NotEmpty(message = "username can not be null")
	private String username;
	@NotEmpty(message = "name can not be null")
	private String name;
	@NotEmpty(message = "password can not be null")
	private String password;
	private Role role;


}
