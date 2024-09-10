package com.example.carblog.page.HomePage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carblog.R;
import com.example.carblog.model.PostModel;

public class DetailPost extends AppCompatActivity {
    PostModel postModel;
    WebView wvContentDetailPost;
    ImageView imgDetailPostBack;

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_post);
        postModel = getIntent().getSerializableExtra(HomePage.KEY_POST_MODEL, PostModel.class);
        imgDetailPostBack = findViewById(R.id.imgDetailPostBack);
        imgDetailPostBack.setOnClickListener(v -> onBackPressed());
        wvContentDetailPost = findViewById(R.id.wvContentDetailPost);
        wvContentDetailPost.getSettings().setJavaScriptEnabled(true); // Kích hoạt JavaScript nếu cần
        wvContentDetailPost.getSettings().setLoadWithOverviewMode(true);
        wvContentDetailPost.getSettings().setUseWideViewPort(true);
//        wvContentDetailPost.getSettings().setTextZoom(200);
        WebSettings webSettings = wvContentDetailPost.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webSettings.setDefaultTextEncodingName("utf-8"); // Đặt mã hóa văn bản
        webSettings.setSupportZoom(true); // Cho phép phóng to/thu nhỏ
        webSettings.setBuiltInZoomControls(true); // Sử dụng các điều khiển phóng to/thu nhỏ của WebView
        webSettings.setDisplayZoomControls(false); // Ẩn các điều khiển phóng to/thu nhỏ
        wvContentDetailPost.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Phóng to nội dung để vừa màn hình
                view.evaluateJavascript("document.body.style.zoom = '150%';", null);

                view.evaluateJavascript(

                        "document.querySelectorAll('img').forEach(function(img) { " +
                                "  img.onclick = function() { " +
                                "    Android.showImage(this.src); " +
                                "  }; " +
                                "});",
                        null);
            }
        });
        wvContentDetailPost.loadData(postModel.getContentPost(), "text/html", "UTF-8");


    }
}