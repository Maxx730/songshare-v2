package com.squidswap.songshare.songshare;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import NetworkServices.NetworkResponseInterface;
import NetworkServices.UserServices;

public class MainFragmentManager extends AppCompatActivity implements StreamFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,PreferencesFragment.OnFragmentInteractionListener {

    private boolean MENU_OPEN = false;
    private int MENU_WIDTH = 5,ANIM_LENGTH = 250;
    private float StartX,OldX;

    private RelativeLayout MenuSide,ContentSide,MenuShade;
    private ImageButton OpenMenu,CloseMenu;
    private Animation FadeIn,FadeOut,SlideIn;
    private FrameLayout Content,MenuTouch;
    private FragmentTransaction trans;
    private StreamFragment streams;
    private LinearLayout NavTop,StreamToggle,ProfileToggle,SettingsToggle,DetailsCard;
    private SharedPreferences shared;
    private ValueAnimator ValueAnim,SlideOut,SlideUp,SlideDown;
    private FragmentTransaction frag;
    private StreamFragment streamfrag;
    private TextView MenuUser;
    private ImageView MenuImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_fragment_manager);

        ValueAnim = ValueAnimator.ofInt(0,900);
        SlideOut = ValueAnimator.ofInt(900,0);

        DisplayMetrics met = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(met);

        SlideUp = ValueAnimator.ofInt(0,(int) (met.heightPixels / 3) * 2);
        SlideDown = ValueAnimator.ofInt((int) (met.heightPixels / 3) * 2,0);

        shared = getSharedPreferences("songshare-prefs",MODE_PRIVATE);

        //Check if the user has logged in or not, if not send them back to the login screen
        if(shared.getInt("logged-in",0) == 1){
            SetListeners();
            InitElements();

            if(shared.contains("user-data")){
                try{
                    JSONObject obj = new JSONObject(shared.getString("user-data",""));

                    MenuUser.setText(obj.getString("username"));
                    Glide.with(getApplicationContext()).load(obj.getString("profile")).into(MenuImage);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            frag = getSupportFragmentManager().beginTransaction();
            streamfrag = new StreamFragment();
            frag.replace(R.id.SelectedContent,streamfrag);
            frag.commit();
        }else{
            Intent i = new Intent(getApplicationContext(),LoginScreen.class);
            startActivity(i);
        }
    }

    private void InitElements(){
        Content = (FrameLayout) findViewById(R.id.SelectedContent);
        MenuSide = (RelativeLayout) findViewById(R.id.MenuSide);
        ContentSide = (RelativeLayout) findViewById(R.id.ContentSide);
        OpenMenu = (ImageButton) findViewById(R.id.OpenMenuButton);
        StreamToggle = (LinearLayout) findViewById(R.id.StreamToggleLayout);
        ProfileToggle = (LinearLayout) findViewById(R.id.ProfileToggleLayout);
        SettingsToggle = (LinearLayout) findViewById(R.id.SettingsToggleLayout);
        NavTop = (LinearLayout) findViewById(R.id.NavTop);
        MenuTouch = (FrameLayout) findViewById(R.id.MenuTouchLayout);
        MenuShade = (RelativeLayout) findViewById(R.id.MenuShade);
        MenuUser = (TextView) findViewById(R.id.MenuUserName);
        MenuImage = (ImageView) findViewById(R.id.CircleUserProfile);
        DetailsCard = (LinearLayout) findViewById(R.id.DetailsCard);

        ValueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                ViewGroup.LayoutParams lay = MenuSide.getLayoutParams();
                lay.width = value;
                MenuSide.setLayoutParams(lay);
            }
        });
        ValueAnim.setDuration(ANIM_LENGTH);

        SlideOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                ViewGroup.LayoutParams lay = MenuSide.getLayoutParams();
                lay.width = value;
                MenuSide.setLayoutParams(lay);
            }
        });
        SlideOut.setDuration(ANIM_LENGTH);

        SlideUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lay = DetailsCard.getLayoutParams();
                lay.height = value;
                DetailsCard.setLayoutParams(lay);
            }
        });
        SlideUp.setDuration(ANIM_LENGTH);

        SlideDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lay = DetailsCard.getLayoutParams();
                lay.height = value;
                DetailsCard.setLayoutParams(lay);
            }
        });
        SlideDown.setDuration(ANIM_LENGTH);

        DetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuShade.setVisibility(View.GONE);
                SlideDown.start();
            }
        });


        InitClickEvents();
    }

    private void InitClickEvents(){
        OpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MENU_OPEN){
                    ValueAnim.start();
                    MenuShade.setVisibility(View.VISIBLE);
                    MENU_OPEN = true;
                }
            }
        });

        StreamToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(StreamToggle,R.color.colorPrimary);
                ClearToggles(ProfileToggle,R.color.gray_2);
                ClearToggles(SettingsToggle,R.color.gray_2);

                ToggleMenu(false);
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                StreamFragment streamfrag = new StreamFragment();
                frag.replace(R.id.SelectedContent,streamfrag);
                frag.commit();

                if(MENU_OPEN){
                    SlideOut.start();
                    MenuShade.setVisibility(View.GONE);
                    MENU_OPEN = false;
                }
            }
        });

        ProfileToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(ProfileToggle,R.color.colorPrimary);
                ClearToggles(StreamToggle,R.color.gray_2);
                ClearToggles(SettingsToggle,R.color.gray_2);

                ToggleMenu(false);
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.SelectedContent,new ProfileFragment());
                frag.commit();

                if(MENU_OPEN){
                    SlideOut.start();
                    MenuShade.setVisibility(View.GONE);
                    MENU_OPEN = false;
                }
            }
        });

        SettingsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearToggles(SettingsToggle,R.color.colorPrimary);
                ClearToggles(StreamToggle,R.color.gray_2);
                ClearToggles(ProfileToggle,R.color.gray_2);

                ToggleMenu(false);
                FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.SelectedContent,new PreferencesFragment());
                frag.commit();

                if(MENU_OPEN){
                    SlideOut.start();
                    MenuShade.setVisibility(View.GONE);
                    MENU_OPEN = false;
                }
            }
        });

        MenuShade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MENU_OPEN){
                    SlideOut.start();
                    MenuShade.setVisibility(View.GONE);
                    MENU_OPEN = false;
                }
            }
        });
    }

    private void ClearToggles(LinearLayout selected,int color){
        for(int i = 0;i < selected.getChildCount();i++){
            View v = selected.getChildAt(i);

            if(v instanceof ImageButton){
                Log.d("TOGGLE ::: ",v.getClass().getName());
                ImageButton but = (ImageButton) v;
                but.setImageTintList(ColorStateList.valueOf(getResources().getColor(color)));
            }

            v.setForegroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
        }
    }

    private void ToggleMenu(boolean val){

    }

    private void SetListeners(){

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
