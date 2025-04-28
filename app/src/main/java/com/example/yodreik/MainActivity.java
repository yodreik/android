package com.example.yodreik;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        if (!Preference.HasAccessToken(getApplicationContext())) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return;
        }

        loadFragment(new HomeFragment());

        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (item.getItemId() == R.id.profile) {
                loadFragment(new ProfileFragment());
                return true;
            } else if (item.getItemId() == R.id.motivation) {
                loadFragment(new NutritionHubFragment());
                return true;
            } else if (item.getItemId() == R.id.history) {
                loadFragment(new HistoryFragment());
                return true;
            } else if (item.getItemId() == R.id.workout) {
                loadFragment(new WorkoutFragment());
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}