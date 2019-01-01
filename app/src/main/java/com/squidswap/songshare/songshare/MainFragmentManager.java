package com.squidswap.songshare.songshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainFragmentManager extends AppCompatActivity implements StreamFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener {

    private boolean MENU_OPEN = false;
    private int MENU_WIDTH = 5,ANIM_LENGTH = 250;

    private RelativeLayout MenuSide,ContentSide;
    private ImageButton OpenMenu,CloseMenu,StreamToggle,ProfileToggle,SettingsToggle;
    private Animation FadeIn,FadeOut;
    private FrameLayout Content;
    private FragmentTransaction trans;
    private StreamFragment streams;

    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_fragment_manager);

        FadeIn = new AlphaAnimation(0,1);
        FadeIn.setDuration(ANIM_LENGTH);

        FadeOut = new AlphaAnimation(1,0);
        FadeOut.setDuration(ANIM_LENGTH);

        shared = getSharedPreferences("songshare-prefs",MODE_PRIVATE);

        //Check if the user has logged in or not, if not send them back to the login screen
        if(shared.getInt("logged-in",0) == 1){
            SetListeners();
            InitElements();

            streams = new StreamFragment();
        }else{
            Toast.makeText(getApplicationContext(),"working",Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(),LoginScreen.class);
            startActivity(i);
        }
    }

    private void InitElements(){
        Content = (FrameLayout) findViewById(R.id.SelectedContent);
        MenuSide = (RelativeLayout) findViewById(R.id.MenuSide);
        ContentSide = (RelativeLayout) findViewById(R.id.ContentSide);
        OpenMenu = (ImageButton) findViewById(R.id.OpenMenuButton);
        CloseMenu = (ImageButton) findViewById(R.id.CloseToggle);
        StreamToggle = (ImageButton) findViewById(R.id.StreamToggle);
        ProfileToggle = (ImageButton) findViewById(R.id.ProfileToggle);
        SettingsToggle = (ImageButton) findViewById(R.id.SettingsToggle);

        InitClickEvents();
    }

    private void InitClickEvents(){
        OpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MENU_OPEN = true;
                ToggleMenu(MENU_OPEN);
            }
        });

        CloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MENU_OPEN = false;
                ToggleMenu(MENU_OPEN);
            }
        });

        StreamToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(StreamToggle);
                ToggleMenu(false);
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.SelectedContent,new StreamFragment());
                frag.commitNowAllowingStateLoss();
            }
        });

        ProfileToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(ProfileToggle);
                ToggleMenu(false);
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.SelectedContent,new ProfileFragment());
                frag.commit();
            }
        });

        SettingsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(SettingsToggle);
                ToggleMenu(false);
            }
        });
    }

    private void ClearToggles(ImageButton selected){
        StreamToggle.setImageTintList(null);
        ProfileToggle.setImageTintList(null);
        SettingsToggle.setImageTintList(null);

        selected.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
    }

    private void ToggleMenu(boolean val){
        if(val){
            OpenMenu.startAnimation(FadeOut);
            MenuSide.setVisibility(View.VISIBLE);
        }else{
            OpenMenu.startAnimation(FadeIn);
            MenuSide.setVisibility(View.GONE);
        }
    }

    private void SetListeners(){
        FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                OpenMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        FadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                OpenMenu.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
