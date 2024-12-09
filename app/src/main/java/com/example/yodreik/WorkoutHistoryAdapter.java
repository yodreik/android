package com.example.yodreik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private List<WorkoutHistoryRecord> workoutHistoryRecords;
    private Context context;

    public WorkoutHistoryAdapter(Context context, List<WorkoutHistoryRecord> workoutHistoryRecords) {
        this.context = context;
        this.workoutHistoryRecords = workoutHistoryRecords;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkoutHistoryRecord record = workoutHistoryRecords.get(position);
        holder.workoutNameTextView.setText(record.getWorkoutType());
        holder.workoutDateTextView.setText("Date: " + record.getWorkoutDate());
        holder.workoutDurationTextView.setText("Duration: " + record.getWorkoutDuration());
    }

    @Override
    public int getItemCount() {
        return workoutHistoryRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutNameTextView;
        public TextView workoutDateTextView;
        public TextView workoutDurationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.trainingTypeTextView);
            workoutDateTextView = itemView.findViewById(R.id.workout_date_text_view);
            workoutDurationTextView = itemView.findViewById(R.id.workout_duration_text_view);
        }
    }
}