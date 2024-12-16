package com.example.yodreik;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.createWorkoutButton).setOnClickListener(this::onCreateWorkoutButtonClick);

        return view;
    }

    public void onCreateWorkoutButtonClick(View view) {
//         LocaleHelper.Set(getActivity(), "en");
        CreateWorkoutFragment dialog = new CreateWorkoutFragment();
        dialog.show(getChildFragmentManager(), "CREATE_WORKOUT");
    }
}