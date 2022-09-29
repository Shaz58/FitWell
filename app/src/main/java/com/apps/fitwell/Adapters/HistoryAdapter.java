package com.apps.fitwell.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.fitwell.Activities.WorkoutDetailsActivity;
import com.apps.fitwell.Interfaces.OnHistoryDelete;
import com.apps.fitwell.Models.BmiObject;
import com.apps.fitwell.Models.WorkoutObject;
import com.apps.fitwell.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<BmiObject> list;
    private OnHistoryDelete onHistoryDelete;

    public HistoryAdapter(Context context,List<BmiObject> list,OnHistoryDelete onHistoryDelete) {
        this.context = context;
        this.list = list;
        this.onHistoryDelete = onHistoryDelete;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView bmi;
        private TextView date;
        private ImageView color;
        private Button delete;

        public ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            bmi = view.findViewById(R.id.bmi);
            date = view.findViewById(R.id.date);
            color = view.findViewById(R.id.color);
            delete = view.findViewById(R.id.delete);
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
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final BmiObject o = list.get(position);

        holder.text.setText(o.getText());
        holder.color.setImageResource(o.getColor());
        holder.bmi.setText(String.valueOf(Math.round(o.getValue())));
        holder.date.setText(o.getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("bmis");
                        database.child(o.getId()).removeValue();
                        list.remove(position);
                        notifyDataSetChanged();
                        onHistoryDelete.onHistoryDeleteListener(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
}
