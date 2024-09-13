package com.example.carblog.page.MenuPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carblog.R;
import com.example.carblog.model.user.UserManager;

public class AccountFragment extends Fragment {
    private View view;
    private TextView txtAccountName, txtAccountRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        txtAccountName = view.findViewById(R.id.txtAccountName);
        txtAccountRole = view.findViewById(R.id.txtAccountRole);
        if (UserManager.getInstance().getUserRole() != null && UserManager.getInstance().getUserInfoModel() != null) {
            txtAccountRole.setText(UserManager.getInstance().getUserRole().role);
            txtAccountName.setText(UserManager.getInstance().getUserInfoModel().name);
        }


        return view;
    }
}