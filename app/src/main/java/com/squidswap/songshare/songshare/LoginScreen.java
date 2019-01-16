package com.squidswap.songshare.songshare;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Set;

import NetworkServices.LoginService;
import NetworkServices.NetworkResponseInterface;
import UiServices.LoginCanvas;
import UiServices.LoginPageAdapter;
import UiServices.LoginPager;

public class LoginScreen extends AppCompatActivity {

    private Button LoginButton;
    private SharedPreferences shared;
    private EditText LoginField,PasswordField;
    private TextView SignUp;
    private ViewPager LoginPag;
    private LoginPageAdapter Adapt;
    private ImageView Circle1;
    private int ANIM_LENGTH = 5000;
    private ValueAnimator Grow,Shrink;
    private LoginCanvas Canvas;
    private ConstraintLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_screen);

        //Check if the user has given the application internet access
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            Log.d("LOGIN SCREEN ::: ","Permission Check");
            //If not then we want to request permission for the internet.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},100);
        }

        shared = getSharedPreferences("songshare-prefs",MODE_PRIVATE);

        InitElements();
        SetListeners();
    }

    private void InitElements(){
        LoginPag = (ViewPager) findViewById(R.id.LoginPager);
        Canvas = new LoginCanvas(getApplicationContext());
        back = (ConstraintLayout) findViewById(R.id.CanvasView);
        back.addView(Canvas);

        //Set up the login pager for the login screen.
        Adapt = new LoginPageAdapter(getSupportFragmentManager());
        LoginPag.setPageMargin(20);
        LoginPag.setAdapter(Adapt);
    }

    private void SetListeners(){

    }
}
