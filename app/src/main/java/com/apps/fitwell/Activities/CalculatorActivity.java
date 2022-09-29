package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.fitwell.R;

public class CalculatorActivity extends AppCompatActivity {

    private EditText height,weight;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weight.getText().toString().equals("")){
                    Toast.makeText(CalculatorActivity.this, "You must write the weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (height.getText().toString().equals("")){
                    Toast.makeText(CalculatorActivity.this, "You must write the height", Toast.LENGTH_SHORT).show();
                    return;
                }

                float h = Float.valueOf(height.getText().toString());
                h = h/100;
                float w = Float.valueOf(weight.getText().toString());

                float result =w/(h*h);

                Intent intent = new Intent(CalculatorActivity.this,CalculatorResultActivity.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        });
    }
}
