package com.example.yodreik;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.yodreik.utils.Toast;

public class NutritionHubFragment extends Fragment {

    // Constants for SharedPreferences keys
    private static final String TOTAL_CALORIES_KEY = "total_calories";
    private static final String TOTAL_PROTEIN_KEY = "total_protein";
    private static final String TOTAL_CARB_KEY = "total_carb";
    private static final String TOTAL_FAT_KEY = "total_fat";
    private static final String DAILY_CALORIE_GOAL_KEY = "daily_calorie_goal";
    private static final String DAILY_PROTEIN_GOAL_KEY = "daily_protein_goal";
    private static final String DAILY_CARB_GOAL_KEY = "daily_carb_goal";
    private static final String DAILY_FAT_GOAL_KEY = "daily_fat_goal";

    // UI components
    private EditText calorieInput;
    private EditText proteinInput;
    private EditText carbInput;
    private EditText fatInput;
    private Button saveButton;
    private Button saveTargetsButton;
    private Button resetButton;
    private TextView totalCaloriesTextView;
    private TextView totalProteinTextView;
    private TextView totalCarbTextView;
    private TextView totalFatTextView;
    private EditText dailyCalorieGoalInput;
    private EditText dailyProteinGoalInput;
    private EditText dailyCarbGoalInput;
    private EditText dailyFatGoalInput;

    // SharedPreferences instance
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_hub, container, false);

        // Initialize UI components
        calorieInput = view.findViewById(R.id.calorie_input);
        proteinInput = view.findViewById(R.id.protein_input);
        carbInput = view.findViewById(R.id.carb_input);
        fatInput = view.findViewById(R.id.fat_input);
        saveButton = view.findViewById(R.id.save_button);
        resetButton = view.findViewById(R.id.reset_button);
        saveTargetsButton = view.findViewById(R.id.save_target_button);
        totalCaloriesTextView = view.findViewById(R.id.total_calories_text_view);
        totalProteinTextView = view.findViewById(R.id.total_protein_text_view);
        totalCarbTextView = view.findViewById(R.id.total_carb_text_view);
        totalFatTextView = view.findViewById(R.id.total_fat_text_view);
        dailyCalorieGoalInput = view.findViewById(R.id.daily_calorie_goal_input);
        dailyProteinGoalInput = view.findViewById(R.id.daily_protein_goal_input);
        dailyCarbGoalInput = view.findViewById(R.id.daily_carb_goal_input);
        dailyFatGoalInput = view.findViewById(R.id.daily_fat_goal_input);

        // Initialize SharedPreferences instance
        sharedPreferences = getActivity().getSharedPreferences("nutrition_data", Context.MODE_PRIVATE);

        // Set up button click listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNutritionData();
            }
        });

        saveTargetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDailyNutritionGoals();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetNutritionData();
            }
        });

        // Load initial data
        loadTotalNutritionData();

        return view;
    }

    // Method to save nutrition data
    private void saveNutritionData() {
        // Get input values
        String calorieString = calorieInput.getText().toString();
        String proteinString = proteinInput.getText().toString();
        String carbString = carbInput.getText().toString();
        String fatString = fatInput.getText().toString();

        // Validate input values
        if (!calorieString.isEmpty() && !proteinString.isEmpty() && !carbString.isEmpty() && !fatString.isEmpty()) {
            int calories = Integer.parseInt(calorieString);
            int protein = Integer.parseInt(proteinString);
            int carb = Integer.parseInt(carbString);
            int fat = Integer.parseInt(fatString);

            // Calculate total values
            int totalCalories = sharedPreferences.getInt(TOTAL_CALORIES_KEY, 0) + calories;
            int totalProtein = sharedPreferences.getInt(TOTAL_PROTEIN_KEY, 0) + protein;
            int totalCarb = sharedPreferences.getInt(TOTAL_CARB_KEY, 0) + carb;
            int totalFat = sharedPreferences.getInt(TOTAL_FAT_KEY, 0) + fat;

            // Save total values to SharedPreferences
            sharedPreferences.edit()
                    .putInt(TOTAL_CALORIES_KEY, totalCalories)
                    .putInt(TOTAL_PROTEIN_KEY, totalProtein)
                    .putInt(TOTAL_CARB_KEY, totalCarb)
                    .putInt(TOTAL_FAT_KEY, totalFat)
                    .apply();

            // Update UI
            loadTotalNutritionData();

            // Clear input fields
            calorieInput.setText("");
            proteinInput.setText("");
            carbInput.setText("");
            fatInput.setText("");
        }
    }

    // Method to reset nutrition data
    private void resetNutritionData() {
        // Reset total values to 0
        sharedPreferences.edit()
                .putInt(TOTAL_CALORIES_KEY, 0)
                .putInt(TOTAL_PROTEIN_KEY, 0)
                .putInt(TOTAL_CARB_KEY, 0)
                .putInt(TOTAL_FAT_KEY, 0)
                .apply();

        // Update UI
        loadTotalNutritionData();
    }

    // Method to load total nutrition data
    // Method to load total nutrition data
    private void loadTotalNutritionData() {
        // Get total values from SharedPreferences
        int totalCalories = sharedPreferences.getInt(TOTAL_CALORIES_KEY, 0);
        int totalProtein = sharedPreferences.getInt(TOTAL_PROTEIN_KEY, 0);
        int totalCarb = sharedPreferences.getInt(TOTAL_CARB_KEY, 0);
        int totalFat = sharedPreferences.getInt(TOTAL_FAT_KEY, 0);

        // Get daily goal values from SharedPreferences
        int dailyCalorieGoal = sharedPreferences.getInt(DAILY_CALORIE_GOAL_KEY, 0);
        int dailyProteinGoal = sharedPreferences.getInt(DAILY_PROTEIN_GOAL_KEY, 0);
        int dailyCarbGoal = sharedPreferences.getInt(DAILY_CARB_GOAL_KEY, 0);
        int dailyFatGoal = sharedPreferences.getInt(DAILY_FAT_GOAL_KEY, 0);

        // Update UI
        totalCaloriesTextView.setText(String.format("%d/%d kcal", totalCalories, dailyCalorieGoal));
        totalProteinTextView.setText(String.format("%d/%d g", totalProtein, dailyProteinGoal));
        totalCarbTextView.setText(String.format("%d/%d g", totalCarb, dailyCarbGoal));
        totalFatTextView.setText(String.format("%d/%d g", totalFat, dailyFatGoal));

        // Check if all goals are met
        if (totalCalories >= dailyCalorieGoal &&
                totalProtein >= dailyProteinGoal &&
                totalCarb >= dailyCarbGoal &&
                totalFat >= dailyFatGoal) {
            Toast.Success(getActivity(), "Congratulations! You've met all your daily nutrition goals!");
        }
    }

    // Method to save daily nutrition goals
    private void saveDailyNutritionGoals() {
        // Get input values
        String dailyCalorieGoalString = dailyCalorieGoalInput.getText().toString();
        String dailyProteinGoalString = dailyProteinGoalInput.getText().toString();
        String dailyCarbGoalString = dailyCarbGoalInput.getText().toString();
        String dailyFatGoalString = dailyFatGoalInput.getText().toString();

        try {
        int dailyCalorieGoal = Integer.parseInt(dailyCalorieGoalString);
        int dailyProteinGoal = Integer.parseInt(dailyProteinGoalString);
        int dailyCarbGoal = Integer.parseInt(dailyCarbGoalString);
        int dailyFatGoal = Integer.parseInt(dailyFatGoalString);


            // Save daily goal values to SharedPreferences
            sharedPreferences.edit()
                    .putInt(DAILY_CALORIE_GOAL_KEY, dailyCalorieGoal)
                    .putInt(DAILY_PROTEIN_GOAL_KEY, dailyProteinGoal)
                    .putInt(DAILY_CARB_GOAL_KEY, dailyCarbGoal)
                    .putInt(DAILY_FAT_GOAL_KEY, dailyFatGoal)
                    .apply();
        } catch (Exception e) {
            sharedPreferences.edit()
                    .putInt(DAILY_CALORIE_GOAL_KEY, 0)
                    .putInt(DAILY_PROTEIN_GOAL_KEY, 0)
                    .putInt(DAILY_CARB_GOAL_KEY, 0)
                    .putInt(DAILY_FAT_GOAL_KEY, 0)
                    .apply();
        }

        loadTotalNutritionData();
    }
}