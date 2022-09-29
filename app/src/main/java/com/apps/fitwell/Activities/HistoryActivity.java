package com.apps.fitwell.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apps.fitwell.Adapters.HistoryAdapter;
import com.apps.fitwell.Interfaces.OnHistoryDelete;
import com.apps.fitwell.Models.BmiObject;
import com.apps.fitwell.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnHistoryDelete {

    private RecyclerView recycler;
    private List<BmiObject> list;
    private HistoryAdapter adapter;
    private ProgressBar loading;

    private DatabaseReference bmi_data;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loading = findViewById(R.id.loading);
        recycler = findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new HistoryAdapter(this,list,this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        getHistory();
    }

    @Override
    public void onHistoryDeleteListener(int position) {
        getHistory();
    }

    private void getHistory(){
        bmi_data = FirebaseDatabase.getInstance().getReference("bmis");
        loading.setVisibility(View.VISIBLE);
        list.clear();
        adapter.notifyDataSetChanged();
        bmi_data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot bmiSnap : snapshot.getChildren()){
                    BmiObject o = bmiSnap.getValue(BmiObject.class);
                    if (o.getUser_id().equals(sharedPreferences.getString("id",""))){
                        list.add(o);
                        adapter.notifyDataSetChanged();
                    }
                }

                if (list.size() == 0){
                    Toast.makeText(HistoryActivity.this, "No history", Toast.LENGTH_SHORT).show();
                }
                loading.setVisibility(View.GONE);
                bmi_data.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
