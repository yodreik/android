package com.example.yodreik;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;
    private String username = "";

    private TextView usernameLabel;
    private TextView loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        usernameLabel = findViewById(R.id.usernameLabel);
        loginStatus = findViewById(R.id.loginStatus);

        SharedPreferences sharedPreferences = getSharedPreferences("dreik_prefs", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", null);

        getCurrentAccount(accessToken);
    }

    private void getCurrentAccount(String accessToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject userJson = UserService.GetCurrentAccount(accessToken);

                    isLoggedIn = true;
                    loginStatus.setText("Logged In");

                    username = userJson.getString("username");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            usernameLabel.setText(String.format("@%s", username));
                        }
                    });
                } catch (Exception e) {
                    Log.e("DREIK", "ERROR: " + e);
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginStatus.setText("Not Logged In");
                        }
                    });
                }
            }
        }).start();
    }
}