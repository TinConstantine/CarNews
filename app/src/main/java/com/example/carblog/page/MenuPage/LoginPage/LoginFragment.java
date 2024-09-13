package com.example.carblog.page.MenuPage.LoginPage;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.user.LoginViewModel;
import com.example.carblog.model.user.SingletonLoginViewModelFactory;
import com.example.carblog.model.user.UserInfoModel;
import com.example.carblog.model.user.UserJwt;
import com.example.carblog.model.user.UserRole;
import com.example.carblog.model.user.UserManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    public static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";

    private View view;
    private TextInputEditText txtUsername;
    private TextInputEditText txtPassword;
    private Button btnLogin;
    private LoginPage loginPage;
    private CheckBox checkBoxRememberAccount;
    private boolean isValidEmail = false, isValidPassword = false;
    private TextView txtShowErrorLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginPage = (LoginPage) getActivity();
        view =  inflater.inflate(R.layout.fragment_login, container, false);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);

        btnLogin = view.findViewById(R.id.btnLogin);
        txtShowErrorLogin = view.findViewById(R.id.txtShowErrorLogin);
        checkBoxRememberAccount = view.findViewById(R.id.ckRememberAccount);

        btnLogin.setOnClickListener(v -> {
            if((!isValidPassword || !isValidEmail)&&!Objects.equals(txtUsername.getText().toString(), "admin") ) {
                txtShowErrorLogin.setVisibility(View.VISIBLE);
                return;
            };
            loginPage.showLoadingOverlay();
//            login(txtUsername.getText().toString(), txtPassword.getText().toString());
            LoginViewModel loginViewModel = LoginViewModel.getInstance();
            loginViewModel.login(txtUsername.getText().toString(), txtPassword.getText().toString());
            loginViewModel.getCombinedLiveData().observe(requireActivity(), stringBooleanMap -> {
                boolean isLogin = Boolean.TRUE.equals(stringBooleanMap.get(LoginViewModel.IS_LOGIN));
                boolean isLogging = Boolean.TRUE.equals(stringBooleanMap.get(LoginViewModel.IS_LOGGING));
                if(Boolean.FALSE.equals(isLogging)){
                    loginPage.hideLoadingOverLay();
                    if(isLogin) {
                        Toast.makeText(requireActivity(), "Đăng nhập thành công !", Toast.LENGTH_LONG).show();
                        if(checkBoxRememberAccount.isChecked()){
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Account", MODE_PRIVATE);

                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_ACCOUNT, Objects.requireNonNull(txtUsername.getText()).toString());
                            editor.putString(KEY_PASSWORD, Objects.requireNonNull(txtPassword.getText()).toString());
                            editor.apply();
                        }
                        requireActivity().finish();
                    } else {
                        Toast.makeText(requireActivity(), "Tài khoản hoặc mật khẩu không đúng !", Toast.LENGTH_LONG).show();
                    }
                }

            });


        });


        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtPassword.getText().toString().length() < 6){
                    txtPassword.setError("Mật khẩu phải có ít nhất 6 kí tự");
                    isValidPassword = false;
                }
                else {
                    isValidPassword = true;
                    checkHideError();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.EMAIL_ADDRESS.matcher(txtUsername.getText().toString()).matches()){
                    isValidEmail = true;
                    checkHideError();
                }
                else {
                    txtUsername.setError("Email không đúng định dạng !");
                    isValidEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    void checkHideError(){
        if(isValidEmail & isValidPassword){
            txtShowErrorLogin.setVisibility(View.GONE);
        }
    }

//    public void login(String username, String password){
//        ApiService.API_SERVICE.getJwtToken(username, password).enqueue(new Callback<UserJwt>() {
//            @Override
//            public void onResponse(Call<UserJwt> call, Response<UserJwt> response) {
//                if(response.isSuccessful()){
//                    UserManager.getInstance().setUser(response.body());
//                    getUserInfo(UserManager.getInstance().getUser().getToken());
//
//                }
//                else{
//                    Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
//                    loginPage.hideLoadingOverLay();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserJwt> call, Throwable throwable) {
//                Log.d("Freeze Error", throwable.toString());
//            }
//        });
//    }
//    void getUserInfo(String token){
//        ApiService.API_SERVICE.getInfoUser("Bearer "+token).enqueue(new Callback<UserInfoModel>() {
//            @Override
//            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
//                if(response.isSuccessful()){
//                    UserManager.getInstance().setUserInfoModel(response.body());
//                    getRoleUser(UserManager.getInstance().getUserInfoModel().id);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoModel> call, Throwable throwable) {
//                Log.d("Freeze Error", throwable.toString());
//            }
//        });
//    }

//    void getRoleUser(int id){
//        ApiService.API_SERVICE.getRoleUser(id).enqueue(new Callback<UserRole>() {
//            @Override
//            public void onResponse(Call<UserRole> call, Response<UserRole> response) {
//                if(response.isSuccessful()){
//                    UserManager.getInstance().setUserRole(response.body());
//                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
//                    if(checkBoxRememberAccount.isChecked()){
//                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Account", MODE_PRIVATE);
//
//                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(KEY_ACCOUNT, Objects.requireNonNull(txtUsername.getText()).toString());
//                        editor.putString(KEY_PASSWORD, Objects.requireNonNull(txtPassword.getText()).toString());
//                        editor.apply();
//                    }
//                    LoginViewModel loginViewModel =new ViewModelProvider(requireActivity(), new SingletonLoginViewModelFactory()).get(LoginViewModel.class);;
//                    loginViewModel.setIsLogin(true);
//                    loginPage.hideLoadingOverLay();
//                    loginPage.finish();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserRole> call, Throwable throwable) {
//                Log.d("Freeze Error", throwable.toString());
//            }
//        });
//    }
}