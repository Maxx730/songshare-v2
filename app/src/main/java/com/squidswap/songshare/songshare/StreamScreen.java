package com.squidswap.songshare.songshare;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.stream.Stream;

import UiServices.StreamPageAdapter;
import UiServices.TrackTransformer;

public class StreamScreen extends AppCompatActivity {

    private static ViewPager StreamPager;
    private StreamPageAdapter StreamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.stream_screen);

        StreamPager = (ViewPager) findViewById(R.id.TrackPager);
        StreamAdapter = new StreamPageAdapter(getSupportFragmentManager());

        StreamPager.setOffscreenPageLimit(3);
        StreamPager.setPageTransformer(true, new TrackTransformer());
        StreamPager.setAdapter(StreamAdapter);
    }
}
