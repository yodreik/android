package com.example.yodreik;

import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.yodreik.utils.Validator;
import com.example.yodreik.utils.Toast;

public class CreateWorkoutFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                        Toast.Error(getContext(), getString(R.string.toast_invalid_date_format));
                        return;
                    }

                    EditText editWorkoutDuration = dialogView.findViewById(R.id.edit_workout_duration);
                    String duration = editWorkoutDuration.getText().toString();

                    if (!Validator.Duration(duration)) {
                        Toast.Error(getContext(), getString(R.string.toast_invalid_duration));
                        return;
                    }

                    EditText editWorkoutKind = dialogView.findViewById(R.id.edit_workout_kind);
                    String kind = editWorkoutKind.getText().toString();

                    String accessToken = Preference.GetAccessToken(requireContext());

                    try {
                        UserService.CreateWorkout(date, duration, kind, accessToken);
                        Toast.Success(getContext(), getString(R.string.toast_workout_created_success));
                    } catch (Exception e) {
                        Toast.Error(getContext(), getString(R.string.toast_something_went_wrong));
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // cancel
                    CreateWorkoutFragment.this.getDialog().cancel();
                });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(calendar.getTime());

        EditText editWorkoutDate = dialogView.findViewById(R.id.edit_workout_date);
        editWorkoutDate.setText(currentDate);

        return builder.create();
    }
}
