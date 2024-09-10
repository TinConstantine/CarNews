package com.example.carblog.model.user;

public class UserManager {
private static UserManager instance;
private UserJwt userJwt;
private UserRole userRole;

private UserInfoModel userInfoModel;

    public UserManager() {

    }

    public static synchronized UserManager getInstance(){
        if(instance==null)  instance = new UserManager();
        return instance;
    }
    public static void logout(){
        if (instance == null) return;
        UserManager.instance = null;
        LoginViewModel.getInstance().setIsLogin(false);
    }

    public UserJwt getUser() {
        return userJwt;
    }

    public void setUser(UserJwt user) {
        this.userJwt = user;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }
}
