package com.example.carblog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.carblog.adapter.ViewPagerAdapter;
import com.example.carblog.model.user.LoginViewModel;
import com.example.carblog.page.MenuPage.LoginPage.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String username = sharedPreferences.getString(LoginFragment.KEY_ACCOUNT, "");
        String password = sharedPreferences.getString(LoginFragment.KEY_PASSWORD, "");
        Log.d("Freeze", username + password);

        if(username.isEmpty() || password.isEmpty()){
            new Handler().postDelayed(() -> {
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    },2000
            );
        }
        else {
            LoginViewModel loginViewModel = LoginViewModel.getInstance();
            loginViewModel.login(username,password);
            loginViewModel.getCombinedLiveData().observe(this, stringBooleanMap -> {
                boolean isLogin = Boolean.TRUE.equals(stringBooleanMap.get(LoginViewModel.IS_LOGIN));
                boolean isLogging = Boolean.TRUE.equals(stringBooleanMap.get(LoginViewModel.IS_LOGGING));
                if(Boolean.FALSE.equals(isLogging)){
                    if(isLogin) {
                        Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Đăng nhập thất bại, vui lòng thử lại sau !", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            });
        }
    }



}