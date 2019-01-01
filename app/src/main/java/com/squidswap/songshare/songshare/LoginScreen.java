package com.squidswap.songshare.songshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Set;

public class LoginScreen extends AppCompatActivity {

    private Button LoginButton;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_screen);

        shared = getSharedPreferences("songshare-prefs",MODE_PRIVATE);

        LoginButton = (Button) findViewById(R.id.LoginButton);

        SetListeners();
    }

    private void SetListeners(){
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Temporary for UI demo purposes.
                shared.edit().putInt("logged-in",1).commit();

                Intent i = new Intent(getApplicationContext(),MainFragmentManager.class);
                startActivity(i);
            }
        });
    }
}
