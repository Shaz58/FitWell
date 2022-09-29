package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.fitwell.Models.WorkoutObject;
import com.apps.fitwell.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class WorkoutDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title,details;
    private RelativeLayout ok,start;
    private ProgressBar progress;
    private TextView progress_txt;
    private CountDownTimer timer;

    int v = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        image = findViewById(R.id.image);
        ok = findViewById(R.id.ok);
        start = findViewById(R.id.start);
        progress = findViewById(R.id.progress);
        progress_txt = findViewById(R.id.progress_value);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        getData();
    }

    private void getData(){
        String json = getIntent().getStringExtra("workout");
        WorkoutObject work = new Gson().fromJson(json,WorkoutObject.class);

        title.setText(work.getTitle());
        details.setText(work.getDetails());
        Glide.with(this).load(work.getImage()).into(image);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void startTimer(){
        progress.setProgress(30);
        v = 30;
        progress_txt.setText("30");
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                v = v-1;
                if (v>=0) {
                    progress_txt.setText(String.valueOf(v));
                    progress.setProgress(v);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    private void stopTimer(){
        try {
            timer.cancel();
        }catch (Exception e){}
    }
}
