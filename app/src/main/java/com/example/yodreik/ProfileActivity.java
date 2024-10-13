package com.example.yodreik;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONObject;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProfileActivity extends AppCompatActivity {

    private String username = "";
    private String displayName = "";
    private String avatarURL = "";

    private TextView usernameLabel;
    private TextView displayNameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameLabel = findViewById(R.id.usernameLabel);
        displayNameLabel = findViewById(R.id.displayNameLabel);

        if (!Preference.HasAccessToken(getApplicationContext())) {
            return;
        }

        String token = Preference.GetAccessToken(getApplicationContext());

        getCurrentAccount(token);

        ImageView userAvatar = findViewById(R.id.user_avatar);

        Glide.with(this)
                .load(avatarURL)
                .apply(new RequestOptions().circleCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(userAvatar);
    }

    private void getCurrentAccount(String accessToken) {
        new Thread(new Runnable() {
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
                    Log.e("DREIK", "ERROR: " + e);
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            loginStatus.setText("Not Logged In");
                        }
                    });
                }
            }
        }).start();
    }
}