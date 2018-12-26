package com.squidswap.songshare.songshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainFragmentManager extends AppCompatActivity {

    private boolean MENU_OPEN = false;
    private int MENU_WIDTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_fragment_manager);

        final Button b = (Button) findViewById(R.id.movelayout);
        final RelativeLayout r = (RelativeLayout) findViewById(R.id.movinglayout);
        final DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        final Animation anim  = new TranslateAnimation(0,d.widthPixels / MENU_WIDTH,0,0);

        anim.setDuration(500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                r.setTranslationX(d.widthPixels / MENU_WIDTH);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MENU_OPEN){
                    r.startAnimation(anim);
                    MENU_OPEN = false;
                    b.setText(String.valueOf(MENU_OPEN));
                }else{
                    r.startAnimation(anim);
                    MENU_OPEN = true;
                    b.setText(String.valueOf(MENU_OPEN));
                }
            }
        });
    }
}
