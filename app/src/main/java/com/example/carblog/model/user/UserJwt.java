package com.example.carblog.model.user;

public class UserJwt {
    private String token;
    private String user_email;
    private String user_nicename;
    private String user_display_name;

    public UserJwt(String token, String user_email, String user_nicename, String user_display_name) {
        this.token = token;
        this.user_email = user_email;
        this.user_nicename = user_nicename;
        this.user_display_name = user_display_name;
    }

    public String getToken() {
        return token;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_nicename() {
        return user_nicename;
    }

    public String getUser_display_name() {
        return user_display_name;
    }
}
