package com.example.carblog.page.MenuPage.LoginPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.RegisterMessage;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    private View v;
    private EditText txtEmail, txtNameAccount, txtPassword, txtRepeatPassword;
    private TextView txtShowError;
    private Button btnRegister;
    private LoginPage loginPage;
    private Boolean isValidEmail = false, isValidPassword = false, isValidRepeatPassword = false , isValidNameAccount = false, isClearText = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register, container, false);
        loginPage = (LoginPage) getActivity();
        txtEmail = v.findViewById(R.id.txtEmailRegister);
        txtNameAccount = v.findViewById(R.id.txtNameAccount);
        txtPassword = v.findViewById(R.id.txtPasswordRegister);
        txtRepeatPassword = v.findViewById(R.id.txtRepeatPasswordRegister);
        txtShowError = v.findViewById(R.id.txtShowError);
        btnRegister = v.findViewById(R.id.btnRegister);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isClearText){
                    if(Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
                        isValidEmail = true;
                        checkHideError();
                    }
                    else {
                        txtEmail.setError("Email không đúng định dạng !");
                        isValidEmail = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if(!isClearText){
                  if(txtPassword.getText().toString().length() < 6 || txtPassword.getText().toString().isEmpty()){
                      txtPassword.setError("Mật khẩu phải có ít nhất 6 kí tự");
                      isValidPassword = false;
                  }
                  else {
                      isValidPassword = true;
                      checkHideError();
                  }
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtRepeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if(!isClearText){
                  if(Objects.equals(txtPassword.getText().toString(),txtRepeatPassword.getText().toString())){
                      isValidRepeatPassword = true;
                      checkHideError();
                  } else {
                      txtRepeatPassword.setError("Mật khẩu không trùng khớp");
                      isValidRepeatPassword = false;
                  }
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       txtNameAccount.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!isClearText){
                if(txtNameAccount.getText().toString().isEmpty()){
                    txtNameAccount.setError("Vui lòng nhập tên người dùng");
                    isValidNameAccount = false;
                }
                else {
                    isValidNameAccount = true;
                    checkHideError();
                }
            }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

       btnRegister.setOnClickListener(v1 -> {
           if(isValidEmail && isValidPassword && isValidRepeatPassword && isValidNameAccount ){
               loginPage.showLoadingOverlay();
               // register
               ApiService.API_SERVICE.registerAccount(txtEmail.getText().toString(), txtNameAccount.getText().toString(), txtPassword.getText().toString()).enqueue(new Callback<RegisterMessage>() {
                   @Override
                   public void onResponse(@NonNull Call<RegisterMessage> call, @NonNull Response<RegisterMessage> response) {
                       if(response.isSuccessful()){
                           assert response.body() != null;
                           Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_LONG).show();
                           isClearText = true;
                           txtNameAccount.setText("");
                           txtPassword.setText("");
                           txtRepeatPassword.setText("");
                           txtEmail.setText("");
                           loginPage.goToFragmentLogin();
                           isClearText = false;

                       }
                       else {
                           Toast.makeText(getActivity(), "Email đã được đăng ký", Toast.LENGTH_LONG).show();

                       }

                       loginPage.hideLoadingOverLay();

                   }

                   @Override
                   public void onFailure(Call<RegisterMessage> call, Throwable throwable) {
                       Log.d("Freeze Error", throwable.toString());
                       loginPage.hideLoadingOverLay();
                   }
               });
           }
           else {
               txtShowError.setVisibility(View.VISIBLE);

           }
       });

        return v;
    }
    void checkHideError(){
        if(isValidRepeatPassword && isValidEmail && isValidPassword && isValidNameAccount){
            txtShowError.setVisibility(View.GONE);
        }
    }
}