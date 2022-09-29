package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.fitwell.R;

public class SettingsActivity extends AppCompatActivity {

    private TextView edit_profile,history,logout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edit_profile = findViewById(R.id.edit_profile);
        history = findViewById(R.id.history);
        logout = findViewById(R.id.logout);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("id", "");
                editor.putString("full_name","");
                editor.putString("email","");
                editor.putString("password","");
                editor.commit();

                Intent intent = new Intent(SettingsActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
