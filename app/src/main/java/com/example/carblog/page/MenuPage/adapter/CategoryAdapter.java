package com.example.carblog.page.MenuPage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carblog.R;
import com.example.carblog.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    ArrayList<CategoryModel> listCategory;
    public interface IOnClickCategory{
        void onClick(CategoryModel c);
    }

    private IOnClickCategory iOnClickCategory;
    public CategoryAdapter(ArrayList<CategoryModel> listCategory, IOnClickCategory iOnClickCategory) {
        this.listCategory = listCategory;
        this.iOnClickCategory = iOnClickCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = listCategory.get(position);
        if(categoryModel == null) return;
        holder.txtCategoryName.setText(categoryModel.name);
        holder.layout.setOnClickListener(v -> {
            iOnClickCategory.onClick(categoryModel);
        });
    }

    @Override
    public int getItemCount() {
        return listCategory == null ? 0 : listCategory.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtCategoryName;
        private RelativeLayout layout;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            layout = itemView.findViewById(R.id.layoutParentCategoryItem);
        }
    }
}
