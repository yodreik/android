package com.example.yodreik;

public class WorkoutHistoryRecord {
    public String workoutType;
    public String workoutDate;
    public int workoutDuration;
    public String workoutId;

    public WorkoutHistoryRecord(String workoutType, String workoutDate, int workoutDuration, String workoutId) {
        this.workoutType = workoutType;
        this.workoutDate = workoutDate;
        this.workoutDuration = workoutDuration;
        this.workoutId = workoutId;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public int getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(int workoutDuration) {
        this.workoutDuration = workoutDuration;
    }
}