package com.apps.fitwell.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.apps.fitwell.Models.WorkoutObject;
import com.apps.fitwell.R;
import com.apps.fitwell.Adapters.WorkoutsAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<WorkoutObject> list;
    private WorkoutsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        recycler = findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new WorkoutsAdapter(this,list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);


        WorkoutObject o1 = new WorkoutObject();
        o1.setId(1);
        o1.setTitle("Prisoner Squat");
        o1.setDetails("Start with standing on your feet that need to be wider than hip-width apart. Your feet should be turned out and put your arms behind your head. Then, bend your knees and lower into a squat like you are supposed to sit on a chair. Keep your knees tracking over and your chest lifted. Drive through your feet and get back to the starting position. Repeat as much as you can for not more than 30 seconds.");
        o1.setImage(R.drawable.one);

        WorkoutObject o2 = new WorkoutObject();
        o2.setId(2);
        o2.setTitle("Squat Jump");
        o2.setDetails("Get in a squat position. Then jump straight up by throwing your hands over your head. Land on your feet in the squatting position; keep your knees bent and repeat this exercise for 30 seconds.");
        o2.setImage(R.drawable.two);

        WorkoutObject o3 = new WorkoutObject();
        o3.setId(3);
        o3.setTitle("Push-Ups");
        o3.setDetails("Prepare yourself in a full plank position. Your hands and feet need to be positioned slightly wider than shoulder-width apart. Bend your elbows and engage your abs. Lower your body to the floor and try to stay in a straight line from head o hills. Return to the starting position and repeat with good form for 30 seconds. If you find it very hard to do this kind of push-ups, try bending your knees on the floor.");
        o3.setImage(R.drawable.three);

        WorkoutObject o4 = new WorkoutObject();
        o4.setId(4);
        o4.setTitle("Mountain Climbers");
        o4.setDetails("Start in a full plank position and from there, engage your abs and bend one of your knees in toward your chest. Quickly jump and change both legs alternately. Try repeating it as quickly as possible for 30 seconds.");
        o4.setImage(R.drawable.four);

        WorkoutObject o5 = new WorkoutObject();
        o5.setId(5);
        o5.setTitle("Stance Jacks");
        o5.setDetails("In a standing position, put your feet together and your arms by your sides. Jump with your feet wide and bend your knees. Push your hips back and your chest will reach slightly forward. Tap your left hand to your right foot as your right arm extends back behind your body.  Repeat the same steps on the opposite side. Do reps as much as you can for 30 seconds.");
        o5.setImage(R.drawable.five);

        WorkoutObject o6 = new WorkoutObject();
        o6.setId(6);
        o6.setTitle("Speed Skaters");
        o6.setDetails("Put your feet together while standing and put your arms by your sides. Take a large step out to the side with your right foot and lower into a squat position, bending your left knee behind your body with your foot lifted and swinging arms to the right. Push off your right leg fast and jump out over to the left, landing on your left leg with the right leg crossed back. Your arms should be swinging to the left side.  Repeat as fast as possible for 30 seconds.");
        o6.setImage(R.drawable.six);

        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        list.add(o5);
        list.add(o6);

        adapter.notifyDataSetChanged();
    }
}
