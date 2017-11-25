package com.example.jorgecaro.themoviedbtest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(this).start();
    }

    //run method to sleep transaction to MainActivity for 2 seconds
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goToActivity();
    }

    private void goToActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
