package com.example.tutorial.Auth;

public class LoginResponse {
    public int user_id;

    public LoginResponse(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
