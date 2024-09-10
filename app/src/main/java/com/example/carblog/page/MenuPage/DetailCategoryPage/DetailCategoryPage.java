package com.example.carblog.page.MenuPage.DetailCategoryPage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.carblog.R;
import com.example.carblog.api.ApiService;
import com.example.carblog.model.CategoryModel;
import com.example.carblog.page.HomePage.HomePage;
import com.example.carblog.page.MenuPage.MenuPage;

public class DetailCategoryPage extends AppCompatActivity {
    ImageButton imgButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_category_page);
        Intent intent = getIntent();
        CategoryModel categoryModel = (CategoryModel) intent.getSerializableExtra(MenuPage.KEY_CATEGORY);
        TextView toolBarTitleDetailCategory = findViewById(R.id.toolBarTitleDetailCategory);
        assert categoryModel != null;
        toolBarTitleDetailCategory.setText(categoryModel.name);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerPostByCategory, HomePage.newInstance(categoryModel.id)).commit();
        }
        imgButton = findViewById(R.id.imgDetailCategoryBack);
        imgButton.setOnClickListener(v -> onBackPressed());
    }



}