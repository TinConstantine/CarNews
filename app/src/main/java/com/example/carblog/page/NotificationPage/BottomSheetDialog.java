package com.example.carblog.page.NotificationPage;

import static com.example.carblog.page.HomePage.HomePage.KEY_POST_MODEL;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.PostModel;
import com.example.carblog.model.user.UserManager;
import com.example.carblog.page.HomePage.DetailPost;
import com.example.carblog.page.NotificationPage.viewmodel.NotificationViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    public BottomSheetDialog(PostModel postModel, boolean isDeleteHomePost) {
        this.postModel = postModel;
        this.isDeleteHomePost = isDeleteHomePost;
    }
    private NotificationViewModel viewModel;
    private PostModel postModel;
    private TextView txtBtnView;
    private TextView txtBtnAcp;
    private TextView txtBtnReject;
    private boolean isDeleteHomePost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notify_page_dialog, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        txtBtnView = view.findViewById(R.id.txtBtnView);
        txtBtnAcp = view.findViewById(R.id.txtBtnAcp);
        txtBtnReject = view.findViewById(R.id.txtBtnReject);

        if(isDeleteHomePost) {
            txtBtnView.setVisibility(View.GONE);
            txtBtnAcp.setVisibility(View.GONE);
        }
        txtBtnView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailPost.class);
            intent.putExtra(KEY_POST_MODEL, postModel);
            startActivity(intent);
        });

        txtBtnAcp.setOnClickListener(v -> {
            ApiService.API_SERVICE.getDetailPost(postModel.id,"Bearer "+ UserManager.getInstance().getUser().getToken()).enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    Log.d("Freeze Error", response.toString());

                    if(response.isSuccessful()){
                      if(Objects.equals(response.body().status, "pending")) updateStatus("publish");

                    }
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable throwable) {
                    Log.d("Freeze Error", throwable.toString());
                }
            });


        });

        txtBtnReject.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setMessage("Bạn có muốn xóa bài viết này ?");
            builder.setTitle("Thông báo");
            builder.setCancelable(false);
            builder.setPositiveButton("Đồng ý", (dialog, which) -> {
               // api xoa
                ApiService.API_SERVICE.getDetailPost(postModel.id,"Bearer "+ UserManager.getInstance().getUser().getToken()).enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        Log.d("Freeze Error", response.toString());

                        if(response.isSuccessful()){
                            if(Objects.equals(response.body().status, "pending")
                                    || (isDeleteHomePost && Objects.equals(response.body().status, "publish")))
                                deletePost();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable throwable) {
                        Log.d("Freeze Error", throwable.toString());
                    }
                });

            });

            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        return view;
    }
    void updateStatus(String status){

        ApiService.API_SERVICE.updateStatusPost(postModel.id, "Bearer "+UserManager.getInstance().getUser().getToken(),status)
                .enqueue(new Callback<Objects>() {

                    @Override
                    public void onResponse(Call<Objects> call, Response<Objects> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(requireActivity(), "Phê duyệt bài viết thành công", Toast.LENGTH_LONG).show();
                            viewModel.setOnReloadForNotyPage(true);
                            viewModel.setOnReloadForHomePage(true);
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Objects> call, Throwable throwable) {
                        Log.d("Freeze Error", throwable.toString());
                    }
                });
    }

    void deletePost(){
        ApiService.API_SERVICE.deletePost(postModel.id, "Bearer "+UserManager.getInstance().getUser().getToken())
                .enqueue(new Callback<Objects>() {

                    @Override
                    public void onResponse(Call<Objects> call, Response<Objects> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(requireActivity(), "Bài viết đã được xóa", Toast.LENGTH_LONG).show();
                            viewModel.setOnReloadForNotyPage(true);
                            if(isDeleteHomePost) viewModel.setOnReloadForHomePage(true);
                            dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<Objects> call, Throwable throwable) {
                        Log.d("Freeze Error", throwable.toString());
                    }
                });
    }
}
