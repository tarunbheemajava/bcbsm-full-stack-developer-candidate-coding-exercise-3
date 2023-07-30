package com.feedback.config;

import com.feedback.exception.AuthEntryPointJwt;
import com.feedback.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private AuthEntryPointJwt unauthorizedHandler;
  private String resources[] = {"/favicon.ico","/index.html", "/", "/home", "signin",  "/*.css", "/*.js",
          "/*.js.map", "static/js/*.js","/static/js/main.*.js","/static/css/main.*.css",
          "images/logo.png",
          "/logo192.png","static/css/main.*.css",
          "/static/media/*.*.ttf","/static/media/*.*.png","/signup",
          "/assets/images/*.png", "/assets/images/*.jpg", "/assets/*.pdf", "/api/v1/auth/**",
          "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .authorizeHttpRequests()
            .requestMatchers(resources)
            .permitAll()
            .anyRequest().authenticated().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider).
            addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
