package com.example.animtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout lv;
    Button bt;

    Animation topAn, botan;

    boolean hiden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.view1);
        bt = findViewById(R.id.button1);
        topAn = new AnimationUtils().loadAnimation(this, R.anim.top_animation);
        botan = new AnimationUtils().loadAnimation(this, R.anim.bottom_animation);

        /*LinearLayout.LayoutParams layParms = (LinearLayout.LayoutParams) lv.getLayoutParams();
        layParms.height = 0;
        lv.setLayoutParams(layParms);*/
        //lv.setAnimation(botan);

        lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0));

        botan.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void testClick(View view) {
        if (hiden){
            lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            bt.startAnimation(topAn);
            lv.startAnimation(topAn);
            hiden = false;
        }else{
            bt.startAnimation(botan);
            lv.startAnimation(botan);
            //lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0));
            hiden = true;
        }
    }
}
