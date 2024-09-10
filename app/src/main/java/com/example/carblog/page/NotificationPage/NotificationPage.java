package com.example.carblog.page.NotificationPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.carblog.R;
import com.example.carblog.adapter.SpacingItemDecoration;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.PostModel;
import com.example.carblog.model.user.LoginViewModel;
import com.example.carblog.model.user.UserManager;
import com.example.carblog.page.HomePage.adapter.PageOnScrollListener;
import com.example.carblog.page.NotificationPage.adapter.NotifyAdapter;
import com.example.carblog.page.NotificationPage.viewmodel.NotificationViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationPage extends Fragment {

    private NotifyAdapter notifyAdapter;
    private boolean isLoading;
    private int totalPage = 1;
    private SwipeRefreshLayout swipeRefreshNotify;
    private RecyclerView rvListNotify;
    private View view;
    private int currentPage = 1;
    private NotificationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        view = inflater.inflate(R.layout.fragment_notification_page, container, false);
        swipeRefreshNotify = view.findViewById(R.id.swipeRefreshNotify);
        notifyAdapter = new NotifyAdapter(p -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(p, false);
            bottomSheetDialog.show(requireActivity().getSupportFragmentManager(), "ModalBottomSheet");
        });
        rvListNotify = view.findViewById(R.id.rvListNotify);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListNotify.setLayoutManager(linearLayoutManager);
        rvListNotify.setAdapter(notifyAdapter);
        rvListNotify.addItemDecoration(new SpacingItemDecoration(100));
        rvListNotify.addOnScrollListener(new PageOnScrollListener(linearLayoutManager) {
            @Override
            public void loadMore() {
                isLoading = true;
                if(currentPage < totalPage){
                    currentPage ++;
                    notifyAdapter.addItemLoading();
                    getPost(currentPage);
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean canLoadMore() {
                return currentPage == totalPage;
            }
        });
        initialPost();
        swipeRefreshNotify.setOnRefreshListener(() -> {
            notifyAdapter.clearAllData();
            initialPost();
        });

        viewModel.getOnReloadForNotyPage().observe(requireActivity(), aBoolean -> {
            if (aBoolean) {
                Log.d("Freeze", aBoolean + " onReload");
                notifyAdapter.clearAllData();
                initialPost();
                viewModel.setOnReloadForNotyPage(false);
            }
        });

        LoginViewModel.getInstance().getIsLogin().observe(requireActivity(), aBoolean -> {
            initialPost();
        });
        return view;
    }

    private void initialPost() {
        swipeRefreshNotify.setRefreshing(true);
        currentPage = 1;
        getPost(currentPage);
    }


    private void getPost(int currentPage) {
            if(UserManager.getInstance().getUserRole() == null || !UserManager.getInstance().getUserRole().role.equals("administrator")) {
                swipeRefreshNotify.setRefreshing(false);

                return;}
            ApiService.API_SERVICE.getPendingPost("Bearer "+UserManager.getInstance().getUser().getToken(),currentPage).enqueue(new Callback<ArrayList<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<PostModel>> call, @NonNull Response<ArrayList<PostModel>> response) {
                    Log.d("Freeze", response.body().toString());
                    if (response.isSuccessful()) {
                        if (response.headers().get("X-WP-TotalPages") != null) {
                            totalPage = Integer.parseInt((response.headers().get("X-WP-TotalPages")));
                        }
                        if (currentPage == 1) {
                            swipeRefreshNotify.setRefreshing(false);
                        } else {
                            notifyAdapter.removeItemLoading();
                            isLoading = false;
                        }
                        notifyAdapter.addData(response.body());
                        Log.d("Freeze Success", "Total Page: " + response.headers().get("X-WP-TotalPages"));
//
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<PostModel>> call, @NonNull Throwable throwable) {
                    Log.d("Freeze error", throwable.toString());
                }
            });
        }




}