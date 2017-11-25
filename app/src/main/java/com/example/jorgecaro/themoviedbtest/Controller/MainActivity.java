package com.example.jorgecaro.themoviedbtest.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.jorgecaro.themoviedbtest.R;
import com.example.jorgecaro.themoviedbtest.Controller.Fragments.MainFragment;
import com.example.jorgecaro.themoviedbtest.Preference.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE, MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.API_KEY, getResources().getString(R.string.api_key));
        editor.apply();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
    }
}
