package com.example.UAS_10120094;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.example.uas_10120094.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Nim  : 10120094
// Nama : Tiara Trisanti Ramadhani
// Kelas: IF3

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser==null){
                    startActivity(new Intent(SplashActivity.this,IntroActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                finish();
            }
        },1000);
    }
}