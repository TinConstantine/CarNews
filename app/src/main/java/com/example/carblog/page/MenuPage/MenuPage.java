package com.example.carblog.page.MenuPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.carblog.HomeActivity;
import com.example.carblog.MainActivity;
import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.user.LoginViewModel;
import com.example.carblog.model.user.SingletonLoginViewModelFactory;
import com.example.carblog.model.user.UserManager;
import com.example.carblog.page.MenuPage.DetailCategoryPage.DetailCategoryPage;
import com.example.carblog.page.MenuPage.LoginPage.LoginPage;
import com.example.carblog.page.MenuPage.SearchPage.SearchPage;
import com.example.carblog.page.MenuPage.adapter.CategoryAdapter;
import com.example.carblog.model.CategoryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPage extends Fragment {
    public static final String KEY_CATEGORY = "key_category";
    public static final String KEY_SEARCH = "key_search";
    private RecyclerView rvListCategory;
    private CategoryAdapter categoryAdapter;
    private View view;
    private ProgressBar menuLoadingSpinner;
    private int retryCount = 0;
    private final int maxRetries = 3;
    private LinearLayout btnGoToLoginPage;
    private View accountFragment;
    private SearchView searchView;
    private TextView txtAccountName, txtAccountRole;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_menu_page, container, false);
         rvListCategory = view.findViewById(R.id.rvListCategory);
         menuLoadingSpinner = view.findViewById(R.id.menuLoadingSpinner);
         btnGoToLoginPage = view.findViewById(R.id.btnGoToLoginPage);
         accountFragment = view.findViewById(R.id.widgetAccount);
        txtAccountName = view.findViewById(R.id.txtAccountName);
        txtAccountRole = view.findViewById(R.id.txtAccountRole);
         searchView = view.findViewById(R.id.searchView);
            HomeActivity homeActivity = (HomeActivity) getActivity();

         btnGoToLoginPage.setOnClickListener(v -> {
             startActivity(new Intent(homeActivity, LoginPage.class));
         });

         rvListCategory.setLayoutManager(new LinearLayoutManager(homeActivity));
         initCategory();

        LoginViewModel loginViewModel =new ViewModelProvider(this, new SingletonLoginViewModelFactory()).get(LoginViewModel.class);;
        loginViewModel.getIsLogin().observe(requireActivity(),isLogin -> {
            Log.d("Freeze Viewmodel", isLogin.toString());
            if(isLogin){
                setInfo();
                btnGoToLoginPage.setVisibility(View.GONE);
                accountFragment.setVisibility(View.VISIBLE);
            }
            else {
                btnGoToLoginPage.setVisibility(View.VISIBLE);
                accountFragment.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(requireActivity(), SearchPage.class);
                intent.putExtra(KEY_SEARCH, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
         return view;
    }
    private void initCategory() {

        menuLoadingSpinner.setVisibility(View.VISIBLE);
        ApiService.API_SERVICE.getAllCategory().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if(response.isSuccessful()){
                    categoryAdapter = new CategoryAdapter(response.body(),c -> {
                        Intent intent = new Intent(getActivity(), DetailCategoryPage.class);
                        intent.putExtra(KEY_CATEGORY, c);
                        startActivity(intent);
                    });
                    rvListCategory.setAdapter(categoryAdapter);
                    menuLoadingSpinner.setVisibility(View.GONE);
                    retryCount = 0;
                }

            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable throwable) {
                handleFailure();
                Log.d("Freeze Error", throwable.toString());
            }
        });


    }
    private void handleFailure() {
        if (retryCount < maxRetries) {
            retryCount++;
            Log.d("Freeze Error", "Retrying... Attempt " + retryCount);
            new Handler().postDelayed(this::initCategory, 5000); // Retry after 5 seconds
        } else {
            Log.d("Freeze Error", "Failed to load after " + maxRetries + " attempts.");
            menuLoadingSpinner.setVisibility(View.GONE);
            // Show error message to user, enable onRefresh, etc.
        }
    }

    private void setInfo(){
        if (UserManager.getInstance().getUserRole() != null && UserManager.getInstance().getUserInfoModel() != null) {
            txtAccountRole.setText(UserManager.getInstance().getUserRole().role);
            txtAccountName.setText(UserManager.getInstance().getUserInfoModel().name);
        }
    }

}
