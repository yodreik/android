package com.example.yodreik;

import android.util.Log;
import com.example.yodreik.Exercise;

import java.util.List;
import java.util.Random;

public class WorkoutLogic {
    private List<Exercise> exerciseList;
    private int currentExerciseIndex;
    private Random random;

    public WorkoutLogic(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        this.currentExerciseIndex = 0;
        this.random = new Random();
    }

    public Exercise suggestExercise() {
        // If we've reached the end of the exercise list, start over
        if (currentExerciseIndex >= exerciseList.size()) {
            currentExerciseIndex = 0;
        }

        // Select a random exercise from the list
        Exercise exercise = exerciseList.get(currentExerciseIndex);

        // Increment the current exercise index
        currentExerciseIndex++;

        return exercise;
    }

    public void confirmExercise(Exercise exercise) {
        // Log the exercise as completed
        Log.d("WorkoutLogic", "Exercise completed: " + exercise.getName());

        // If we've completed all exercises, start over
        if (currentExerciseIndex >= exerciseList.size()) {
            currentExerciseIndex = 0;
        }
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
}