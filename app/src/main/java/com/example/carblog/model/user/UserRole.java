package com.example.carblog.model.user;

import java.util.Objects;

public class UserRole {
        public int user_id;
        public String role;

        public boolean isHaveRole(){
                return Objects.equals(role,"administrator") || Objects.equals(role, "editor");
        }
}
