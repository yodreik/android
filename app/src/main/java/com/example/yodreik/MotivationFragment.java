package com.example.yodreik;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MotivationFragment extends Fragment {
    private TextView quoteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motivation, container, false);
        quoteTextView = view.findViewById(R.id.quoteTextView);
        String[] quotes = getResources().getStringArray(R.array.motivation_quotes);
        int quoteIndex = (int) (Math.random() * quotes.length);
        quoteTextView.setText(quotes[quoteIndex]);
        return view;
    }
}