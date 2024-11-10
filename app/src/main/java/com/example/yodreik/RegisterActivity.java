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
            Toast.Error(getApplicationContext(), getString(R.string.toast_username_length_incorrect));
            return;
        }

        String login = binding.inputLogin.getText().toString();
        if (login.length() < 5) {
            Toast.Error(getApplicationContext(), getString(R.string.toast_login_too_short));
        } else if (login.length() > 254) {
            Toast.Error(getApplicationContext(), getString(R.string.toast_login_too_long));
        }

        String password = binding.inputPassword.getText().toString();
        String passwordRepeat = binding.inputRetypePassword.getText().toString();
        if (!password.equals(passwordRepeat)) {
            Toast.Error(getApplicationContext(), getString(R.string.toast_passwords_dont_match));
            return;
        }

        if (password.length() < 8 || password.length() > 50) {
            Toast.Error(getApplicationContext(), getString(R.string.toast_password_length_incorrect));
            return;
        }

        try {
            UserService.Create(login, password, username);
        } catch (Exception e) {
            Toast.Error(getApplicationContext(), getString(R.string.toast_something_went_wrong));
            return;
        }

        Toast.Success(getApplicationContext(), getString(R.string.toast_registered_success));
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void backButtonOnClick(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}