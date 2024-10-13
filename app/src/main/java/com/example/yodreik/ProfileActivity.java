package com.example.yodreik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONObject;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "ProfileActivity";

    private String username = "";
    private String displayName = "";
    private String avatarURL = "";

    private TextView usernameLabel;
    private TextView displayNameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        usernameLabel = findViewById(R.id.usernameLabel);
        displayNameLabel = findViewById(R.id.displayNameLabel);

        if (!Preference.HasAccessToken(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Please, log in first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            return;
        }

        String token = Preference.GetAccessToken(getApplicationContext());

        try {
            getCurrentAccount(token);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please, log in first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

            Log.e("DREIK", "ERROR: " + e);
            e.printStackTrace();
            return;
        }

        ImageView userAvatar = findViewById(R.id.user_avatar);

        Log.i(TAG, "Avatar URL:" + avatarURL);

        Glide.with(this)
                .load(avatarURL)
                .apply(new RequestOptions().circleCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(userAvatar);
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

                    runOnUiThread(new Runnable() {
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
        Preference.ClearAccessToken(getApplicationContext());
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void backButtonOnClick(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}