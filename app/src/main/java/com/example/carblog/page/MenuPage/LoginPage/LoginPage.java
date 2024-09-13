package com.example.carblog.page.MenuPage.LoginPage;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.carblog.R;
import com.example.carblog.page.MenuPage.adapter.LoginViewerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginPage extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageButton imgAccountBack;

    private RelativeLayout loadingOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
       tabLayout = findViewById(R.id.tabLayoutLoginPage);
       viewPager = findViewById(R.id.viewPagerLoginPage);
        imgAccountBack = findViewById(R.id.imgAccountBack);

       LoginViewerAdapter loginViewerAdapter = new LoginViewerAdapter(this);
       viewPager.setAdapter(loginViewerAdapter);
       loadingOverlay = findViewById(R.id.screen_loading_overlay);
       new TabLayoutMediator(tabLayout, viewPager,((tab, i) -> {
           if(i==0)tab.setText("Đăng nhập");
           if(i==1) tab.setText("Đăng ký");
       })).attach();

       imgAccountBack.setOnClickListener(v -> {
           onBackPressed();
       });

    }
    public void showLoadingOverlay(){
        loadingOverlay.setVisibility(View.VISIBLE);
    }
    public void hideLoadingOverLay(){
        loadingOverlay.setVisibility(View.GONE);
    }
    public void goToFragmentLogin(){
        viewPager.setCurrentItem(0);
    }
}