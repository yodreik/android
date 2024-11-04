package com.example.yodreik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yodreik.utils.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONObject;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileFragment extends Fragment {

    private String TAG = "ProfileFragment";

    private String username = "";
    private String displayName = "";
    private String avatarURL = "";

    private TextView usernameLabel;
    private TextView displayNameLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false); // Use fragment_profile.xml as the layout

        // Initialize UI elements
        usernameLabel = view.findViewById(R.id.usernameLabel);
        displayNameLabel = view.findViewById(R.id.displayNameLabel);
        ImageView userAvatar = view.findViewById(R.id.user_avatar);

        // Check for access token
        if (!Preference.HasAccessToken(requireContext())) {
            Toast.Info(getContext(), "Please, log in first");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return view;
        }

        String token = Preference.GetAccessToken(requireContext());

        try {
            getCurrentAccount(token);
        } catch (Exception e) {
            Toast.Info(getContext(), "Please, log in first");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            e.printStackTrace();
        }

        Log.i(TAG, "Avatar URL:" + avatarURL);

        Glide.with(this)
                .load(avatarURL)
                .apply(new RequestOptions().circleCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(userAvatar);

        view.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutButtonOnClick(v);
            }
        });

        return view;
    }

    private void getCurrentAccount(final String accessToken) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject userJson = UserService.GetCurrentAccount(accessToken);

                    username = userJson.getString("username");
                    displayName = userJson.getString("display_name");
                    avatarURL = userJson.getString("avatar_url");

                    if (displayName.isEmpty()) {
                        displayName = "@" + username;
                    }

                    if (avatarURL.isEmpty()) {
                        avatarURL = "https://t3.ftcdn.net/jpg/09/70/98/46/360_F_970984614_DyP7gQmyTUgQdq8fnRP26H0cZ9dnPDv8.jpg";
                    }

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            usernameLabel.setText(String.format("@%s", username));
                            displayNameLabel.setText(String.format("%s", displayName));
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonOnClick(View view) {
        Preference.ClearAccessToken(requireContext());
        Toast.Success(getContext(), "Logged out");
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
