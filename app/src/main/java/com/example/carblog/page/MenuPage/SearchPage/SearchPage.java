package com.example.carblog.page.MenuPage.SearchPage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carblog.R;
import com.example.carblog.model.CategoryModel;
import com.example.carblog.page.HomePage.HomePage;
import com.example.carblog.page.MenuPage.MenuPage;

public class SearchPage extends AppCompatActivity {

    ImageButton imgButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);
        Intent intent = getIntent();
        String search =  intent.getStringExtra(MenuPage.KEY_SEARCH);
        TextView toolBarTitleDetailCategory = findViewById(R.id.toolBarTitleSearch);
        assert search != null;
        toolBarTitleDetailCategory.setText("Từ khóa: "+ getTenCharFromString(search));
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPostBySearch, HomePage.newInstanceForSearch(search)).commit();
        }
        imgButton = findViewById(R.id.imgDetailSearchBack);
        imgButton.setOnClickListener(v -> onBackPressed());
    }

    String getTenCharFromString(String s){
        return s.length() > 10 ? s.substring(0,9) : s;
    }
}