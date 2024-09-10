package com.example.carblog.model.user;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carblog.api.ApiService;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private static LoginViewModel instance;
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_LOGGING = "isLogging";

//    private static boolean getJwt = false, getUserInfo = false, getUserRole = false;

    public static LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }
    public LoginViewModel(){
       combinedLiveData.addSource(isLogging, aBoolean -> {
           updateCombinedLiveData();

       });

       combinedLiveData.addSource(isLogin, aBoolean -> {
           updateCombinedLiveData();

       });
    }

    private void updateCombinedLiveData() {
        Map<String, Boolean> result = new HashMap<>();
        result.put(IS_LOGIN, getIsLogin().getValue());
        result.put(IS_LOGGING, getIsLogging().getValue());
        combinedLiveData.setValue(result);

    }
    public MutableLiveData<Map<String,Boolean>> getCombinedLiveData() {
        return combinedLiveData;
    }

    private final MutableLiveData<Boolean> isLogin = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLogging = new MutableLiveData<>(false);
    private MediatorLiveData<Map<String, Boolean>> combinedLiveData  = new MediatorLiveData<>();


    public void setIsLogin(boolean isLogin) {
        this.isLogin.setValue(isLogin);
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public void setIsLogging(boolean isLogging){
        this.isLogging.setValue(isLogging);
    }
    public MutableLiveData<Boolean> getIsLogging(){
        return isLogging;
    }
    public void login(String username, String password){
        isLogging.setValue(true);
        ApiService.API_SERVICE.getJwtToken(username, password).enqueue(new Callback<UserJwt>() {
            @Override
            public void onResponse(Call<UserJwt> call, Response<UserJwt> response) {
                if(response.isSuccessful()){
                    UserManager.getInstance().setUser(response.body());
                    getUserInfo(UserManager.getInstance().getUser().getToken());

                }
                else{
                    isLogging.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserJwt> call, Throwable throwable) {
                Log.d("Freeze Error", throwable.toString());
                isLogging.setValue(false);

            }
        });
    }
    void getUserInfo(String token){
        ApiService.API_SERVICE.getInfoUser("Bearer "+token).enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                if(response.isSuccessful()){
                    UserManager.getInstance().setUserInfoModel(response.body());
                    getRoleUser(UserManager.getInstance().getUserInfoModel().id);
                }
                else {
                    isLogging.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable throwable) {
                Log.d("Freeze Error", throwable.toString());
                isLogging.setValue(false);

            }
        });
    }
    void getRoleUser(int id){
        ApiService.API_SERVICE.getRoleUser(id).enqueue(new Callback<UserRole>() {
            @Override
            public void onResponse(Call<UserRole> call, Response<UserRole> response) {
                if(response.isSuccessful()){
                    UserManager.getInstance().setUserRole(response.body());
                    isLogin.setValue(true);
                    }
                    isLogging.setValue(false);
            }

            @Override
            public void onFailure(Call<UserRole> call, Throwable throwable) {
                Log.d("Freeze Error", throwable.toString());
                isLogging.setValue(false);

            }
        });
    }
}

