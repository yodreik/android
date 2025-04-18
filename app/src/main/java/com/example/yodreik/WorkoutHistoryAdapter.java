package com.example.yodreik;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.app.PendingIntent.getActivity;

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
        int pos = holder.getAdapterPosition();
        holder.workoutNameTextView.setText(record.getWorkoutType());
        holder.workoutDateTextView.setText(context.getString(R.string.date, record.getWorkoutDate()));
        holder.workoutDurationTextView.setText(context.getString(R.string.duration, record.getWorkoutDuration()));

        holder.workoutId = record.workoutId;

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.delete_workout_record);
                builder.setMessage(R.string.sure_to_delete_workout);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String accessToken = Preference.GetAccessToken(context);
                            UserService.DeleteWorkout(accessToken, holder.workoutId);
                            workoutHistoryRecords.remove(pos);
                            notifyItemRemoved(pos);

                            Toast.makeText(context, R.string.delete_workout_success, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, R.string.delete_workout_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutHistoryRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutNameTextView;
        public TextView workoutDateTextView;
        public TextView workoutDurationTextView;
        public String workoutId;
        public ImageButton deleteButton;


        public ViewHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.trainingTypeTextView);
            workoutDateTextView = itemView.findViewById(R.id.workout_date_text_view);
            workoutDurationTextView = itemView.findViewById(R.id.workout_duration_text_view);
            deleteButton = itemView.findViewById(R.id.deleteWorkoutButton);
        }
    }
}