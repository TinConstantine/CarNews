package com.example.carblog.page.NotificationPage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carblog.R;
import com.example.carblog.model.PostModel;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class NotifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 2;
    private final ArrayList<PostModel> listPost = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void clearAllData() {
        listPost.clear();
        notifyDataSetChanged();
    }

    public interface IOnOpenDialog {
        void onClick(PostModel p);
    }
    private boolean isLoading;
    private IOnOpenDialog iOnOpenDialog;

    public NotifyAdapter(IOnOpenDialog iOnOpenDialog) {
        this.iOnOpenDialog = iOnOpenDialog;
    }
    
    @SuppressLint("NotifyDataSetChanged")
    public void addData(ArrayList<PostModel> data){
        listPost.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(listPost!=null && position== (listPost.size() - 1) && isLoading) return VIEW_TYPE_LOADING;
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM ) return new NotifyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify, parent, false));
        else return new ProgressBarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,false));
        
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder.getItemViewType() == VIEW_TYPE_ITEM){
                PostModel p = listPost.get(position);
                ((NotifyViewHolder) holder).txtContentNotify.setText("Người dùng "+p.getNameAuthor()+" đã gửi bài viết với nội dung: " + p.getTitle());
                ((NotifyViewHolder) holder).imgOpenDialog.setOnClickListener(v -> {
                    iOnOpenDialog.onClick(p);
                });

            }
    }

    @Override
    public int getItemCount() {
        return listPost == null ? 0 : listPost.size();
    }

    class NotifyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgOpenDialog;
        private TextView txtContentNotify;
        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOpenDialog = itemView.findViewById(R.id.imgOpenDialog);
            txtContentNotify = itemView.findViewById(R.id.txtContentNotify);
        }
    }
    
    class ProgressBarViewHolder extends RecyclerView.ViewHolder{

        public ProgressBarViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    
    @SuppressLint("NotifyDataSetChanged")
   public void addItemLoading(){
        isLoading = true;
        listPost.add(null);
        notifyDataSetChanged();
    }
    
    public void removeItemLoading(){
        isLoading = false;
        int loadingItemIndex = listPost.size() - 1;
        PostModel p = listPost.get(loadingItemIndex);
        if(p == null){
            listPost.remove(loadingItemIndex);
            notifyItemRemoved(loadingItemIndex);
        }
    }
    
}
