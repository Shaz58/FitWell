package com.apps.fitwell.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.fitwell.Models.UserObject;
import com.apps.fitwell.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText email,password,full_name;
    private Button register;
    private TextView login;

    private DatabaseReference user_data;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<UserObject> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        full_name = findViewById(R.id.full_name);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        users = new ArrayList<>();
        getUsers();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(full_name.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "You must write your full name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "You must write your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.getText().toString().contains("@")){
                    Toast.makeText(RegisterActivity.this, "You must write your email with a right format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkEmailExists(email.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Email is already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length()<8){
                    Toast.makeText(RegisterActivity.this, "Password must be 8 characters at least", Toast.LENGTH_SHORT).show();
                    return;
                }

                user_data = FirebaseDatabase.getInstance().getReference("users");
                String id = user_data.push().getKey();
                UserObject user = new UserObject();
                user.setId(id);
                user.setFull_name(full_name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user_data.child(id).setValue(user);
                editor.putString("id",id);
                editor.putString("full_name",full_name.getText().toString());
                editor.putString("email",email.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.commit();
                Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUsers(){
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Loading..");
        dialog.cancel();
        user_data = FirebaseDatabase.getInstance().getReference("users");
        user_data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnap : snapshot.getChildren()){
                    UserObject user = userSnap.getValue(UserObject.class);
                    users.add(user);
                }

                dialog.cancel();
                user_data.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkEmailExists(String email){
        boolean res = false;

        if (users.size() > 0){
            for (UserObject u : users){
                if (u.getEmail().equals(email)){
                    res = true;
                    break;
                }
            }
        }

        return res;
    }
}
