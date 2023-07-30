package com.feedback.service;

import java.util.HashSet;
import java.util.Set;


import com.feedback.bean.User;
import com.feedback.dto.AuthenticationRequest;
import com.feedback.dto.AuthenticationResponse;
import com.feedback.repository.UserRepository;
import com.feedback.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	public AuthenticationResponse createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
		String userName = authenticationRequest.getUsername();
		String userPassword = authenticationRequest.getPassword();
		authenticate(userName, userPassword);

		UserDetails userDetails = loadUserByUsername(userName);
		String newGeneratedToken = jwtUtils.generateToken(userDetails);

		User user = userRepository.findByUsername(userName).get();
		return new AuthenticationResponse(user, newGeneratedToken);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					getAuthority(user));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		return authorities;
	}

	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}