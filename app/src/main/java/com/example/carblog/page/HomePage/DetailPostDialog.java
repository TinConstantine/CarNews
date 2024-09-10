//package com.example.carblog.page.HomePage;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageButton;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//import com.example.carblog.R;
//import com.example.carblog.model.PostModel;
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//public class DetailPostDialog extends BottomSheetDialogFragment {
//    PostModel postModel;
//
//    public DetailPostDialog(PostModel postModel) {
//        this.postModel = postModel;
//    }
//
//    private WebView wvContentDetailPost;
//    @SuppressLint("SetJavaScriptEnabled")
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.detail_post_dialog, container, false);
//        wvContentDetailPost = view.findViewById(R.id.wvContentDetailPost);
//        wvContentDetailPost.getSettings().setJavaScriptEnabled(true); // Kích hoạt JavaScript nếu cần
//        wvContentDetailPost.getSettings().setLoadWithOverviewMode(true);
//        wvContentDetailPost.getSettings().setUseWideViewPort(true);
////        wvContentDetailPost.getSettings().setTextZoom(200);
//        WebSettings webSettings = wvContentDetailPost.getSettings();
//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
//        webSettings.setDefaultTextEncodingName("utf-8"); // Đặt mã hóa văn bản
//        webSettings.setSupportZoom(true); // Cho phép phóng to/thu nhỏ
//        webSettings.setBuiltInZoomControls(true); // Sử dụng các điều khiển phóng to/thu nhỏ của WebView
//        webSettings.setDisplayZoomControls(false); // Ẩn các điều khiển phóng to/thu nhỏ
//        wvContentDetailPost.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                // Phóng to nội dung để vừa màn hình
//                view.evaluateJavascript("document.body.style.zoom = '150%';", null);
//
//                view.evaluateJavascript(
//
//                        "document.querySelectorAll('img').forEach(function(img) { " +
//                                "  img.onclick = function() { " +
//                                "    Android.showImage(this.src); " +
//                                "  }; " +
//                                "});",
//                        null);
//            }
//        });
//        wvContentDetailPost.loadData(postModel.getContentPost(), "text/html", "UTF-8");
//        return view;
//    }
//}
