package com.example.carblog.page.RadioPage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carblog.HomeActivity;
import com.example.carblog.MainActivity;
import com.example.carblog.R;
import com.example.carblog.adapter.SpacingItemDecoration;
import com.example.carblog.page.RadioPage.adapter.ListRadioAdapter;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;


public class RadioPage extends Fragment {
    private RecyclerView rvRadio;
    private View view;
    private ExoPlayer exoPlayer;
    private HomeActivity homeActivity;
    private ListRadioAdapter radioAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_radio_page, container, false);
        homeActivity = (HomeActivity) getActivity();
        rvRadio = view.findViewById(R.id.rvRadio);
        rvRadio.setLayoutManager(new LinearLayoutManager(homeActivity));
        radioAdapter = new ListRadioAdapter((r, position) -> {
            new Handler().postDelayed(() -> {if(position == radioAdapter.getIndexSelected()){
                radioAdapter.setIndexSelected(-1);
                stopRadio();
            }
            else {
                playRadio(r.getUrl());
                radioAdapter.setIndexSelected(position);
            }},300);

        });
        rvRadio.setAdapter(radioAdapter);

        rvRadio.addItemDecoration(new SpacingItemDecoration(28));

        exoPlayer = new ExoPlayer.Builder(homeActivity).build();


//        btnControlRadio.setOnClickListener(v -> {
//            isPlay = !isPlay;
//            btnControlRadio.setImageResource(isPlay ? R.drawable.ic_pause : R.drawable.ic_play);
//            if (isPlay) {
//                playRadio();
//            } else {
//                stopRadio();
//            }
//        });
        return view;
    }

    private void playRadio(String uri){
        MediaItem mediaItem = MediaItem.fromUri(uri);
        exoPlayer.setMediaItem(mediaItem);
       exoPlayer.addListener(new Player.Listener() {
           @Override
           public void onPlayerError(PlaybackException error) {
               Toast.makeText(homeActivity, "Failed to play radio, please try again.", Toast.LENGTH_LONG).show();
           }

           @SuppressLint("NotifyDataSetChanged")
           @Override
           public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
               if(playbackState == Player.STATE_READY){
                   radioAdapter.setLoading(false);
                   radioAdapter.notifyDataSetChanged();
                   Toast.makeText(homeActivity, "Radio Started", Toast.LENGTH_LONG).show();}
               else if(playbackState == Player.STATE_BUFFERING){
                   radioAdapter.setLoading(true);
               }
       }});
        exoPlayer.prepare();
        exoPlayer.play();

    }

    private void stopRadio(){
        if(exoPlayer.isPlaying()){
            exoPlayer.stop();
            Toast.makeText(homeActivity, "Radio Started", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exoPlayer!=null){
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}