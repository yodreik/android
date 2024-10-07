package com.example.yodreik;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yodreik.databinding.ActivityLoginBinding;
import com.example.yodreik.utils.Validator;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void loginButtonOnClick(View view) {
        String email = binding.inputEmail.getText().toString();
        if (!Validator.Email(email)) {
            Toast.makeText(getApplicationContext(), "Email is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = binding.inputPassword.getText().toString();
        if (password.length() < 8 || password.length() > 50) {
            Toast.makeText(getApplicationContext(), "Password must be between 8 and 50 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // TODO: Make login available by username or email
            UserService.Login(email, password);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Can't login", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
    }

    public void backButtonOnClick(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}