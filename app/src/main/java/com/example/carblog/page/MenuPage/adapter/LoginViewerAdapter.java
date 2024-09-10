package com.example.carblog.page.MenuPage.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.carblog.page.MenuPage.LoginPage.LoginFragment;
import com.example.carblog.page.MenuPage.LoginPage.RegisterFragment;

public class LoginViewerAdapter extends FragmentStateAdapter {
    public LoginViewerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return new LoginFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }


}
