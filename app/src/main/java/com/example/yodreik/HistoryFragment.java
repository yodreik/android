package com.example.yodreik;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HistoryFragment extends Fragment {
private RecyclerView workoutRecordsRecyclerView;
private WorkoutHistoryAdapter workoutHistoryAdapter;
private List<WorkoutHistoryRecord> workoutRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Initialize RecyclerView
        workoutRecordsRecyclerView = view.findViewById(R.id.workoutRecordsRecyclerView);
        workoutRecordsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Adapter
        workoutRecords = fetchWorkoutHistory(); // Fetch workout history records
        workoutHistoryAdapter = new WorkoutHistoryAdapter(getContext(), workoutRecords);
        workoutRecordsRecyclerView.setAdapter(workoutHistoryAdapter);

        return view;
    }

    private List<WorkoutHistoryRecord> fetchWorkoutHistory() {
        List<WorkoutHistoryRecord> workouts = new ArrayList<>();

        try {
            ActivityResponse activity = UserService.GetActivity(Preference.GetAccessToken(getContext()));

            for (Workout workout : activity.getWorkouts()) {
                workouts.add(new WorkoutHistoryRecord(workout.getKind(), workout.getDate(), workout.getDuration(), workout.getId()));
            }
        } catch (Exception e) {
            workouts.add(new WorkoutHistoryRecord("Running", "01-10-2023", 30, "aaa-bbb-ccc-aaa"));
            workouts.add(new WorkoutHistoryRecord("Cycling", "02-10-2023", 69, "aaa-bbb-ccc-aaa"));
            workouts.add(new WorkoutHistoryRecord("Yoga", "03-10-2023", 121, "aaa-bbb-ccc-aaa"));
            workouts.add(new WorkoutHistoryRecord("Yoga", "01-09-2023", 121, "aaa-bbb-ccc-aaa"));
        }

        // sort by date
        Collections.sort(workouts, new Comparator<WorkoutHistoryRecord>() {
            @Override
            public int compare(WorkoutHistoryRecord record1, WorkoutHistoryRecord record2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date1 = dateFormat.parse(record1.getWorkoutDate());
                    Date date2 = dateFormat.parse(record2.getWorkoutDate());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        return workouts;
    }
}