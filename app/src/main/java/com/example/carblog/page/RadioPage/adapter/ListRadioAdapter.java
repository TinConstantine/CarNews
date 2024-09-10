package com.example.carblog.page.RadioPage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carblog.R;
import com.example.carblog.model.RadioModel;

import java.util.Arrays;
import java.util.List;

public class ListRadioAdapter extends RecyclerView.Adapter<ListRadioAdapter.ListRadioViewHolder>{
    private List<RadioModel> listRadio;
    private int indexSelected = -1;
    private boolean isLoading = false;

    public interface IOnClickRadioItem {
        void onClick(RadioModel r, int position);
    }

    private IOnClickRadioItem iOnClickRadioItem;
    public ListRadioAdapter(IOnClickRadioItem iOnClickRadioItem) {
        listRadio =  Arrays.asList(RadioModel.listRadio);
        this.iOnClickRadioItem = iOnClickRadioItem;
    }

    @NonNull
    @Override
    public ListRadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListRadioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListRadioViewHolder holder, int position) {
        RadioModel r = listRadio.get(position);
        if(r == null) return;
        holder.imgRadio.setImageResource(r.getIdImg());
        if(indexSelected == position) {
            if(isLoading){
                holder.imgButtonRadio.setVisibility(View.INVISIBLE);
                holder.progressBarItemRadio.setVisibility(View.VISIBLE);
                holder.cvRadioItem.setEnabled(false);
            }
            else {
                holder.progressBarItemRadio.setVisibility(View.GONE);
                holder.imgButtonRadio.setVisibility(View.VISIBLE);
                holder.imgButtonRadio.setImageResource(R.drawable.ic_pause);
            }
        }
        else holder.imgButtonRadio.setImageResource(R.drawable.ic_play);
        holder.cvRadioItem.setOnClickListener(v -> {
            iOnClickRadioItem.onClick(r, position);
        });
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public int getItemCount() {
        return listRadio == null ? 0 : listRadio.size();
    }

    public int getIndexSelected() {
        return indexSelected;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setIndexSelected(int index){
        indexSelected = index;
        notifyDataSetChanged();
    }

    class ListRadioViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgRadio;
        private ImageView imgButtonRadio;
        private CardView cvRadioItem;
        private ProgressBar progressBarItemRadio;
        public ListRadioViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRadio = itemView.findViewById(R.id.imgRadioItem);
            imgButtonRadio = itemView.findViewById(R.id.imgControlRadio);
            cvRadioItem = itemView.findViewById(R.id.cvRadioItem);
            progressBarItemRadio = itemView.findViewById(R.id.progressBarItemRadio);

        }
    }

}
