package com.example.carblog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.carblog.adapter.ViewPagerAdapter;
import com.example.carblog.model.user.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView toolbarTitle;
    private ImageView imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        addControl();
        addEvent();
    }

    private void addControl() {
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.navBar);
        viewPagerAdapter = new ViewPagerAdapter(HomeActivity.this);
        toolbarTitle = findViewById(R.id.toolBarTitle);
        imgLogout = findViewById(R.id.imgLogout);
    }

    private void addEvent() {
        viewPager.setAdapter(viewPagerAdapter);

        imgLogout.setOnClickListener(v -> {
            UserManager.logout();
            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            finishAffinity(); // Xóa tất cả activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        });
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.navHomePage) {
                viewPager.setCurrentItem(0);
                toolbarTitle.setText(R.string.home_page);
                    imgLogout.setVisibility(View.GONE);


            } else if (menuItem.getItemId() == R.id.navRadio) {
                viewPager.setCurrentItem(1);
                toolbarTitle.setText(R.string.radio);
                    imgLogout.setVisibility(View.GONE);

            } else if (menuItem.getItemId() == R.id.navNotification) {
                viewPager.setCurrentItem(2);
                toolbarTitle.setText(R.string.notification);
                    imgLogout.setVisibility(View.GONE);

            } else if (menuItem.getItemId() == R.id.navMenu) {

                viewPager.setCurrentItem(3);
                toolbarTitle.setText(R.string.menu);
                if(UserManager.getInstance().getUser()!=null){
                    imgLogout.setVisibility(View.VISIBLE);
                }

            }
            return true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        toolbarTitle.setText(R.string.home_page);
                            imgLogout.setVisibility(View.GONE);

                        break;
                    case 1:
                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        toolbarTitle.setText(R.string.radio);
                            imgLogout.setVisibility(View.GONE);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        toolbarTitle.setText(R.string.notification);
                            imgLogout.setVisibility(View.GONE);

                        break;
                    case 3:
                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        toolbarTitle.setText(R.string.menu);
                        if(UserManager.getInstance().getUser()!=null){
                            imgLogout.setVisibility(View.VISIBLE);
                        }
                        break;

                }
            }
        });

    }
}