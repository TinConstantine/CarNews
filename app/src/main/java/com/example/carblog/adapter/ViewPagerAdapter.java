package com.example.carblog.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.carblog.page.HomePage.HomePage;
import com.example.carblog.page.MenuPage.MenuPage;
import com.example.carblog.page.NotificationPage.NotificationPage;
import com.example.carblog.page.RadioPage.RadioPage;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomePage();
            case 1:
                return new RadioPage();
            case 2:
                return new NotificationPage();
            case 3:
                return new MenuPage();
            default: return new HomePage();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
