package com.squidswap.songshare.songshare;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.stream.Stream;

import UiServices.StreamPageAdapter;

public class StreamScreen extends AppCompatActivity {

    private static ViewPager StreamPager;
    private StreamPageAdapter StreamAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_screen);

        StreamPager = (ViewPager) findViewById(R.id.StreamPager);
        StreamAdaper = new StreamPageAdapter(getSupportFragmentManager());

        StreamPager.setAdapter(StreamAdaper);
    }
}
