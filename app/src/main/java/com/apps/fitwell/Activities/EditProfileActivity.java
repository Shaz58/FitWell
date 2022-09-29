package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.fitwell.Models.UserObject;
import com.apps.fitwell.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText email,password,full_name;
    private Button update;

    private DatabaseReference user_data;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        full_name = findViewById(R.id.full_name);
        update = findViewById(R.id.update);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(full_name.getText().toString().equals("")){
                    Toast.makeText(EditProfileActivity.this, "You must write your full name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(EditProfileActivity.this, "You must write your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.getText().toString().contains("@")){
                    Toast.makeText(EditProfileActivity.this, "You must write your email with a right format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length()<8){
                    Toast.makeText(EditProfileActivity.this, "Password must be 8 characters at least", Toast.LENGTH_SHORT).show();
                    return;
                }

                user_data = FirebaseDatabase.getInstance().getReference("users");
                UserObject user = new UserObject();
                user.setId(sharedPreferences.getString("id",""));
                user.setFull_name(full_name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user_data.child(sharedPreferences.getString("id","")).setValue(user);
                editor.putString("full_name",full_name.getText().toString());
                editor.putString("email",email.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.commit();
                Toast.makeText(EditProfileActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getData(){
        full_name.setText(sharedPreferences.getString("full_name",""));
        email.setText(sharedPreferences.getString("email",""));
        password.setText(sharedPreferences.getString("password",""));
    }
}
