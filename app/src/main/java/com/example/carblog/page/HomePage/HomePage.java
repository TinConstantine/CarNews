package com.example.carblog.page.HomePage;

import android.content.Intent;
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

import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.page.HomePage.adapter.PageOnScrollListener;
import com.example.carblog.page.HomePage.adapter.PostAdapter;
import com.example.carblog.model.PostModel;
import com.example.carblog.page.NotificationPage.BottomSheetDialog;
import com.example.carblog.page.NotificationPage.viewmodel.NotificationViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePage extends Fragment {
    public static final String KEY_POST_MODEL = "key_post";
    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String ARG_STRING_SEARCH = "string_search";

    private RecyclerView rvListPost;
    private PostAdapter postAdapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private int currentPage = 1;
    private int totalPage = 1;
    private boolean isLoading;
    private int categoryId;

    private NotificationViewModel viewModel;
    private String search;

    public static HomePage newInstance(int categoryId){
        HomePage homePage = new HomePage();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        homePage.setArguments(args);
        return homePage;
    }

    public static HomePage newInstanceForSearch(String search){
        HomePage homePage = new HomePage();
        Bundle args = new Bundle();
        args.putString(ARG_STRING_SEARCH, search);
        homePage.setArguments(args);
        return homePage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshHome);
        rvListPost = view.findViewById(R.id.rvListPost);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListPost.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter(this, p -> {
            Log.d("PostModel", p.getTitle());
//            DetailPostDialog detailPostDialog = new DetailPostDialog(p);
//            detailPostDialog.show(requireActivity().getSupportFragmentManager(), "DetailPostDialog");
            Intent intent = new Intent(getActivity(), DetailPost.class);
            intent.putExtra(KEY_POST_MODEL, p);
            startActivity(intent);

        }, p -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(p, true);
            bottomSheetDialog.show(requireActivity().getSupportFragmentManager(), "ModalBottomSheet");
        });

        rvListPost.setAdapter(postAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            postAdapter.clearData();
            initialPost();
        });

        initialPost();

        rvListPost.addOnScrollListener(new PageOnScrollListener(linearLayoutManager) {
            @Override
            public void loadMore() {
                isLoading = true;
                if (currentPage < totalPage) {
                    currentPage++;
                    postAdapter.addItemLoading();
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
        viewModel.getOnReloadForHomePage().observe(requireActivity(),aBoolean -> {
            if(aBoolean){
                postAdapter.clearData();
                initialPost();
                viewModel.setOnReloadForHomePage(false);
            }
        });
        return view;


    }

    private void initialPost() {
        swipeRefreshLayout.setRefreshing(true);
        currentPage = 1;
        getPost(currentPage);


    }

    private void getPost(int currentPage) {
        if(getArguments()!=null)
        {
            if(getArguments().getString(ARG_STRING_SEARCH) == null) {
                categoryId = getArguments().getInt(ARG_CATEGORY_ID);
                ApiService.API_SERVICE.getPostByCategory(categoryId, currentPage).enqueue(new Callback<ArrayList<PostModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<PostModel>> call, @NonNull Response<ArrayList<PostModel>> response) {
                        if (response.isSuccessful()) {
                            if (response.headers().get("X-WP-TotalPages") != null) {
                                totalPage = Integer.parseInt((response.headers().get("X-WP-TotalPages")));
                            }
                            if (currentPage == 1) {
                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                postAdapter.removeItemLoading();
                                isLoading = false;
                            }
                            postAdapter.addData(response.body());
                            Log.d("Freeze Success", "Total Page: " + response.headers().get("X-WP-TotalPages"));
//
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<PostModel>> call, @NonNull Throwable throwable) {
                        Log.d("Freeze error", throwable.toString());
                    }
                });
            } else {
                search = getArguments().getString(ARG_STRING_SEARCH);
                ApiService.API_SERVICE.getPostBySearch(currentPage, search).enqueue(new Callback<ArrayList<PostModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<PostModel>> call, @NonNull Response<ArrayList<PostModel>> response) {
                        if (response.isSuccessful()) {
                            if (response.headers().get("X-WP-TotalPages") != null) {
                                totalPage = Integer.parseInt((response.headers().get("X-WP-TotalPages")));
                            }
                            if (currentPage == 1) {
                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                postAdapter.removeItemLoading();
                                isLoading = false;
                            }
                            postAdapter.addData(response.body());
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

        else
        {
            ApiService.API_SERVICE.getAllPost(currentPage).enqueue(new Callback<ArrayList<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<PostModel>> call, @NonNull Response<ArrayList<PostModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.headers().get("X-WP-TotalPages") != null) {
                            totalPage = Integer.parseInt((response.headers().get("X-WP-TotalPages")));
                        }
                        if (currentPage == 1) {
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            postAdapter.removeItemLoading();
                            isLoading = false;
                        }
                        postAdapter.addData(response.body());
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
}