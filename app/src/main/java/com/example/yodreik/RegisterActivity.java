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
import com.example.yodreik.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createButtonOnClick(View view) {
        String username = binding.inputUsername.getText().toString();
        if (username.length() < 5 || username.length() > 32) {
            Toast.Info(getApplicationContext(), "Username must be between 5 and 32 characters");
            return;
        }

        String login = binding.inputLogin.getText().toString();
        if (login.length() < 5) {
            Toast.Info(getApplicationContext(), "Login is too short");
        } else if (login.length() > 254) {
            Toast.Info(getApplicationContext(), "Login is too long");
        }

        String password = binding.inputPassword.getText().toString();
        String passwordRepeat = binding.inputRetypePassword.getText().toString();
        if (!password.equals(passwordRepeat)) {
            Toast.Info(getApplicationContext(), "Passwords do not match");
            return;
        }

        if (password.length() < 8 || password.length() > 50) {
            Toast.Info(getApplicationContext(), "Password must be between 8 and 50 characters");
            return;
        }

        try {
            UserService.Create(login, password, username);
        } catch (Exception e) {
            Toast.Info(getApplicationContext(), "Something went wrong");
            return;
        }

        Toast.Info(getApplicationContext(), "Registered successfully. Check your email to verify");
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void backButtonOnClick(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}