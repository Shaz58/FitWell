package com.apps.fitwell.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.fitwell.Activities.WorkoutDetailsActivity;
import com.apps.fitwell.Models.WorkoutObject;
import com.apps.fitwell.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {
    private Context context;
    private List<WorkoutObject> list;

    public WorkoutsAdapter(Context context,List<WorkoutObject> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            layout = view.findViewById(R.id.layout);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final WorkoutObject o = list.get(position);

        holder.title.setText(o.getTitle());
        Glide.with(context).load(o.getImage()).into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkoutDetailsActivity.class);
                intent.putExtra("workout",new Gson().toJson(o));
                context.startActivity(intent);
            }
        });
    }
}
