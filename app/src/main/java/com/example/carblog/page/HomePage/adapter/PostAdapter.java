package com.example.carblog.page.HomePage.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carblog.R;
import com.example.carblog.model.PostModel;
import com.example.carblog.model.user.UserManager;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    private boolean isLoading;
    public interface IOnClickItem {
        void onClick(PostModel p);
    }

    public interface IOnLongClickItem{
        void onLongClick(PostModel p);
    }
    private IOnLongClickItem iOnLongClickItem;

    private IOnClickItem iOnClickItem;
    private Fragment postFragment;
    private final ArrayList<PostModel> listData = new ArrayList<>();
    public PostAdapter(Fragment postFragment, IOnClickItem iOnClickItem, IOnLongClickItem iOnLongClickItem) {
        this.postFragment = postFragment;
        this.iOnClickItem = iOnClickItem;
        this.iOnLongClickItem = iOnLongClickItem;
    }

    @Override
    public int getItemViewType(int position) {

        if(listData!=null && position == listData.size() - 1 && isLoading) return TYPE_LOADING;
        return TYPE_ITEM;

    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(ArrayList<PostModel> listData){
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData(){
        listData.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == TYPE_ITEM)
                return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
            else
                return new ProgressBarHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

      if(holder.getItemViewType() == TYPE_ITEM){
          PostModel p = listData.get(position);
          if(p == null) return;
          ((PostViewHolder) holder).tvPostAuthor.setText("Tác giả: "+ p.getNameAuthor());
          ((PostViewHolder) holder).tvPostSubTitle.setText(Html.fromHtml(p.getSubTitle(), Html.FROM_HTML_MODE_COMPACT));
          ((PostViewHolder) holder).tvPostTitle.setText(Html.fromHtml(p.getTitle()));
          Glide.with(postFragment)
                  .load(p.getImage())
                  .centerCrop()
                  .into(((PostViewHolder) holder).imgPost);
          ((PostViewHolder) holder).itemPostCardView.setOnClickListener(v->iOnClickItem.onClick(p));
          ((PostViewHolder) holder).itemPostCardView.setOnLongClickListener(v -> {
              iOnLongClickItem.onLongClick(p);
              return true;
          });

      }


    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPost;
        private TextView tvPostTitle;
        private TextView tvPostSubTitle;
        private TextView tvPostAuthor;
        private CardView itemPostCardView;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPost = itemView.findViewById(R.id.imgPost);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvPostAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPostSubTitle = itemView.findViewById(R.id.tvPostSubTitle);
            itemPostCardView = itemView.findViewById(R.id.itemPostCardView);
        }


    }

    class ProgressBarHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public ProgressBarHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBarItem);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItemLoading(){
        isLoading = true;
        listData.add(null);
        notifyDataSetChanged();
    }

    public void removeItemLoading(){
        isLoading = false;
        int loadingItemIndex = listData.size() - 1;
        PostModel p = listData.get(loadingItemIndex);
        if(p == null){
            listData.remove(loadingItemIndex);
            notifyItemRemoved(loadingItemIndex);
        }
    }
}
