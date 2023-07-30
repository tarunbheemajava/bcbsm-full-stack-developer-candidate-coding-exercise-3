package com.feedback.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;	
}