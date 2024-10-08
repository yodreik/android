package com.example.yodreik;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private TextView usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        usernameLabel = findViewById(R.id.usernameLabel);

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
                    String username = userJson.getString("username");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            usernameLabel.setText(String.format("@%s", username));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SettingsActivity.this, "Ошибка получения данных: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}