package com.example.yodreik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yodreik.Exercise;
import com.example.yodreik.R;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList;
    private Context context;

    public ExerciseAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.bindExercise(exercise);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseNameTextView;
        private TextView exerciseDescriptionTextView;
        private CheckBox doneCheckBox;
        private TextView setsTextView;
        private TextView repsTextView;
        private TextView weightTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exercise_name);
            exerciseDescriptionTextView = itemView.findViewById(R.id.exercise_description);
            doneCheckBox = itemView.findViewById(R.id.done_checkbox);
            setsTextView = itemView.findViewById(R.id.sets);
            repsTextView = itemView.findViewById(R.id.reps);
            weightTextView = itemView.findViewById(R.id.weight);
        }

        public void bindExercise(Exercise exercise) {
            exerciseNameTextView.setText(exercise.getName());
            exerciseDescriptionTextView.setText(exercise.getDescription());
            doneCheckBox.setChecked(exercise.isDone());
            setsTextView.setText("Sets: " + exercise.getSets());
            repsTextView.setText("Reps: " + exercise.getReps());
            weightTextView.setText("Weight: " + exercise.getWeight());
            doneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    exercise.setDone(isChecked);
                    exerciseList.set(getAdapterPosition(), exercise);
                }
            });
        }
    }
}