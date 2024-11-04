package com.example.yodreik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.yodreik.utils.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.yodreik.databinding.ActivityLoginBinding;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loginButtonOnClick(View view) {
        String login = binding.inputLogin.getText().toString();
        if (login.length() < 5) {
            Toast.Error(getApplicationContext(), "Login is too short");
            return;
        } else if (login.length() > 254) {
            Toast.Error(getApplicationContext(), "Login is too long");
            return;
        }

        String password = binding.inputPassword.getText().toString();
        if (password.length() < 8 || password.length() > 50) {
            Toast.Error(getApplicationContext(), "Password must be between 8 and 50 characters");
            return;
        }

        try {
            JSONObject token = UserService.Login(login, password);

            String accessToken = token.getString("token");

            Preference.SaveAccessToken(getApplicationContext(), accessToken);

            JSONObject user = UserService.GetCurrentAccount(accessToken);

            Toast.Success(getApplicationContext(), "Logged in as @" + user.getString("username"));

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } catch (Exception e) {
            Toast.Error(getApplicationContext(), "Something went wrong");
        }
    }

    public void backButtonOnClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}