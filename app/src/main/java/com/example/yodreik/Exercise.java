package com.example.yodreik;

public class Exercise {
    private String name;
    private String description;
    private int sets;
    private int reps;
    private double weight;
    private boolean done;

    public Exercise(String name, String description, int sets, int reps, double weight) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.done = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }
}