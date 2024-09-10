package com.example.carblog.page.HomePage.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PageOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;

    public PageOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItem = linearLayoutManager.getChildCount();
        int totalItem = linearLayoutManager.getItemCount();
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        if(isLoading() || canLoadMore()) return;
        if(firstVisibleItem >= 0 && (visibleItem + firstVisibleItem) >= totalItem ) loadMore();
    }

    public abstract void loadMore();
    public abstract boolean isLoading();
    public abstract boolean canLoadMore();
}
