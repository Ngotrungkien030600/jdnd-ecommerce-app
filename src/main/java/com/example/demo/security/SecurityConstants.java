package com.example.demo.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "VerySecretiveKey";
    public static final long EXPIRATION_TIME_MS = 864_000_000; // 10 days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}
