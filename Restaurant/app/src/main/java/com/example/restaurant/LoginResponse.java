package com.example.restaurant.api;

public class LoginResponse {
    // Keep fields flexible because API responses vary.
    public String message;

    // Often returned by auth APIs
    public String token;

    // If your API returns role/usertype
    public String usertype;
    public String username;
}
