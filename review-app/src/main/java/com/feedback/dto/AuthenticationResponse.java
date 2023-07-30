package com.feedback.dto;

import com.feedback.bean.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String jwt;

}