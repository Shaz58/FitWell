package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.fitwell.Models.BmiObject;
import com.apps.fitwell.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalculatorResultActivity extends AppCompatActivity {

    private ImageView back,color,save;
    private TextView history,bmi_text,text;

    private float bmi = 0f;
    private String res_text = "";
    private int color_value = 0;

    private DatabaseReference bmi_data;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        back = findViewById(R.id.back);
        color = findViewById(R.id.color);
        save = findViewById(R.id.save);
        history = findViewById(R.id.history);
        bmi_text = findViewById(R.id.bmi);
        text = findViewById(R.id.text);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        getData();

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorResultActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi_data = FirebaseDatabase.getInstance().getReference("bmis");
                String id = bmi_data.push().getKey();
                BmiObject o = new BmiObject();
                o.setId(id);
                o.setValue(bmi);
                o.setColor(color_value);
                o.setText(res_text);
                o.setDate(todayDateString());
                o.setUser_id(sharedPreferences.getString("id",""));
                bmi_data.child(id).setValue(o);
                Toast.makeText(CalculatorResultActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalculatorResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getData(){
        bmi = getIntent().getFloatExtra("result",0f);
        bmi_text.setText(String.valueOf(Math.round(bmi)));

        if (bmi<15){
            res_text = "Very severe weight loss";
            color_value = R.drawable.circle_one;
            text.setText("Very severe weight loss");
            color.setImageResource(R.drawable.circle_one);
        }else if (bmi>=15&&bmi<=16){
            res_text = "Severe weight loss";
            color_value = R.drawable.circle_two;
            text.setText("Severe weight loss");
            color.setImageResource(R.drawable.circle_two);
        }else if (bmi>16&&bmi<=18.5){
            res_text = "Weight loss";
            color_value = R.drawable.circle_three;
            text.setText("Weight loss");
            color.setImageResource(R.drawable.circle_three);
        }else if (bmi>18.5&&bmi<=25){
            res_text = "Normal weight";
            color_value = R.drawable.circle_four;
            text.setText("Normal weight");
            color.setImageResource(R.drawable.circle_four);
        }else if (bmi>25&&bmi<=30){
            res_text = "Increase in weight";
            color_value = R.drawable.circle_five;
            text.setText("Increase in weight");
            color.setImageResource(R.drawable.circle_five);
        }else if (bmi>30&&bmi<=35){
            res_text = "First degree obesity";
            color_value = R.drawable.circle_six;
            text.setText("First degree obesity");
            color.setImageResource(R.drawable.circle_six);
        }else if (bmi>35&&bmi<=40){
            res_text = "Second degree obesity";
            color_value = R.drawable.circle_seven;
            text.setText("Second degree obesity");
            color.setImageResource(R.drawable.circle_seven);
        }else if (bmi>40){
            res_text = "Very severe obesity";
            color_value = R.drawable.circle_eight;
            text.setText("Very severe obesity");
            color.setImageResource(R.drawable.circle_eight);
        }
    }

    private String todayDateString(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
