package com.example.yodreik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;
    private Button confirmButton;
    private Button startWorkoutButton;
    private long workoutStartTime;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        startWorkoutButton = view.findViewById(R.id.start_workout_button);
        recyclerView = view.findViewById(R.id.exercise_list);
        recyclerView.setVisibility(View.GONE);

        confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setVisibility(View.GONE);

        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press", "Lie on a flat bench and press a barbell upwards", 3, 8, 100));
        exerciseList.add(new Exercise("Squats", "Stand with your feet shoulder-width apart and squat down", 3, 12, 150));
        exerciseList.add(new Exercise("Lunges", "Stand with your feet together and take a large step forward with one foot", 3, 10, 120));
        exerciseList.add(new Exercise("Deadlifts", "Stand with your feet shoulder-width apart and lift a barbell up to hip level", 3, 8, 180));
        exerciseList.add(new Exercise("Bicep Curls", "Stand with your feet shoulder-width apart and curl a dumbbell up to shoulder level", 3, 12, 20));

        exerciseAdapter = new ExerciseAdapter(getActivity(), exerciseList);

        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(exerciseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                startWorkoutButton.setVisibility(View.GONE);
                workoutStartTime = System.currentTimeMillis();
            }
        });

        confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < exerciseList.size(); i++) {
                    if (!exerciseList.get(i).isDone()) {
                        Toast.makeText(getActivity(), "Please complete all exercises before confirming", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                long workoutDurationMinutes = ((System.currentTimeMillis() - workoutStartTime) / 1000) / 60;
                Toast.makeText(getActivity(), "Workout took " + workoutDurationMinutes + "mins", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
