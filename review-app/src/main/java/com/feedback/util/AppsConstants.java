package com.feedback.util;

public class AppsConstants {
    /**
     * Authentication related API constant
     */
    public static final String AUTHENTICATION_BASE_API = "/api/v1/auth";
    public static final String AUTHENTICATION_REGISTER_API = "/register";
    public static final String AUTHENTICATION_LOGIN_API = "/authenticate";
    public static final String AUTHENTICATION_FORGET_PASSWORD_API = "/forgetPassword/{email}";
    public static final String AUTHENTICATION_RESET_PASSWORD_API = "/reset-password";

    public static final String USER_NOT_FOUND_MSG = "User not found with username: %s";
    public static final String OTP_GENERATED_MSG = "Otp generated successfully";
    public static final String OTP_EMAIL_SUBJECT_MSG = "OTP Request";
    public static final String PASSWORD_CHANGE_SUCCESS_MSG = "Password changes successfully";
    public static final String INVALID_OTP_MSG = "Invalid OTP";
    public static final String REVIEW_END_POINT = "review";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    /**
     * Response status code Mapping 4xx for client error
     */
    public static int INVALID_OTP_CODE = 401;

    /**
     * Response status code Mapping 2xx for Success
     */

    /**
     * Common API End Point
     */

    private AppsConstants() {
    }
}
