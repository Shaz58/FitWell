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

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private TextView register;
    private Button login;

    private DatabaseReference user_data;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<UserObject> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        users = new ArrayList<>();
        getUsers();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "You must write your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.getText().toString().contains("@")){
                    Toast.makeText(LoginActivity.this, "You must write your email with a right format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length()<8){
                    Toast.makeText(LoginActivity.this, "Password must be 8 characters at least", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(email.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUsers(){
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
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

    private void login(String email,String password){
        boolean res = false;
        UserObject user = new UserObject();

        if (users.size() > 0){
            for (UserObject u : users){
                if (u.getEmail().equals(email)&&password.equals(password)){
                    res = true;
                    user.setId(u.getId());
                    user.setFull_name(u.getFull_name());
                    user.setEmail(u.getEmail());
                    user.setPassword(u.getPassword());
                    break;
                }
            }
        }

        if (res){
            editor.putString("id", user.getId());
            editor.putString("full_name",user.getFull_name());
            editor.putString("email",user.getEmail());
            editor.putString("password",user.getPassword());
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Email or password is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
