package com.example.tutorial.Admin.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tutorial.Admin.Riwayat.HistoryAdminActivity;
import com.example.tutorial.Admin.StatusLaporan.StatusLaporanActivity;
import com.example.tutorial.Auth.MainActivity;
import com.example.tutorial.R;

public class HomeAdminActivity extends AppCompatActivity {

    CardView cvHistory,cvStatusLaporan;
    private Button buttonLogout;
//    private TextView textViewUserId;
    private SharedPreferences sharedPreferences;
    private static final String userId = "userId";
    private static final String P = "MyPrefs";

    ImageView btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        sharedPreferences = getSharedPreferences(P, MODE_PRIVATE);

        btnLogout = findViewById(R.id.ivLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        int id = sharedPreferences.getInt(userId, 0);
        setInitLayout();
    }
    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(userId);
        editor.apply();

        Intent intent = new Intent(HomeAdminActivity.this, MainActivity.class);
        Toast.makeText(HomeAdminActivity.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    private void setInitLayout() {
        cvHistory = findViewById(R.id.cvRiwayatLaporan);
        cvStatusLaporan = findViewById(R.id.cvStatusLaporan);

        cvHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdminActivity.this, HistoryAdminActivity.class);
            startActivity(intent);
        });
        cvStatusLaporan.setOnClickListener(v->{
            Intent intent = new Intent(HomeAdminActivity.this, StatusLaporanActivity.class);
            startActivity(intent);
        });

    }
}