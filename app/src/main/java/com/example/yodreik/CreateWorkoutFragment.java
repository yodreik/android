package com.example.yodreik;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.yodreik.utils.Validator;

public class CreateWorkoutFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_create_workout, null);

        builder.setView(dialogView)
                .setTitle("Create workout record")
                .setPositiveButton("Create", (dialog, id) -> {
                    // ok
                    EditText editWorkoutDate = dialogView.findViewById(R.id.edit_workout_date);
                    String date = editWorkoutDate.getText().toString();

                    if (!Validator.Date(date)) {
                        Toast.makeText(getContext(), "Invalid date format!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    EditText editWorkoutDuration = dialogView.findViewById(R.id.edit_workout_duration);
                    String duration = editWorkoutDuration.getText().toString();

                    if (!Validator.Duration(duration)) {
                        Toast.makeText(getContext(), "Invalid duration!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    EditText editWorkoutKind = dialogView.findViewById(R.id.edit_workout_kind);
                    String kind = editWorkoutKind.getText().toString();

                    String accessToken = Preference.GetAccessToken(requireContext());

                    try {
                        UserService.CreateWorkout(date, duration, kind, accessToken);
                        Toast.makeText(getContext(), "Workout created successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Can't create workout: " + e, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // cancel
                    CreateWorkoutFragment.this.getDialog().cancel();
                });

        return builder.create();
    }
}
