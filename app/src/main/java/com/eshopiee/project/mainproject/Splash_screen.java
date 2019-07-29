package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash_screen extends AppCompatActivity {
    private static int time_out=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent=new Intent( Splash_screen.this, loginActivity.class);
                startActivity(homeIntent);
                finish();

            }
        },time_out);
    }
}
