package com.example.tutorial.User.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tutorial.Auth.MainActivity;
import com.example.tutorial.User.Riwayat.HistoryUserActivity;
import com.example.tutorial.R;
import com.example.tutorial.User.Report.ReportActivity;

public class HomeActivity extends AppCompatActivity {

    private Button buttonLogout;
    private TextView textViewUserId;



    private SharedPreferences sharedPreferences;
    private static final String userId = "userId";
    private static final String P = "MyPrefs";

    String  strTitle;

    CardView cvSekertariat, cvLakwas, cvTurbin, cvP2, cvP3, cvHistory;

    ImageView btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(P, MODE_PRIVATE);

//        textViewUserId = findViewById(R.id.txtfullname);
        btnLogout = findViewById(R.id.ivLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        int id = sharedPreferences.getInt(userId, 0);
//        textViewUserId.setText("User ID: " + id);
        setInitLayout();
    }

    private void setInitLayout() {
        cvHistory = findViewById(R.id.cvRiwayatUser);

        cvSekertariat = findViewById(R.id.cvSekertariat);
        cvLakwas = findViewById(R.id.cvLakwas);
        cvTurbin = findViewById(R.id.cvTurbin);
        cvP2    = findViewById(R.id.cvP5);
        cvP3    = findViewById(R.id.cvP3);
        btnLogout = findViewById(R.id.ivLogout);

        cvSekertariat.setOnClickListener(v -> {
            strTitle = "Laporan Kerusakan Barang Sekertariat";
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvLakwas.setOnClickListener(v -> {
            strTitle = "Laporan Kerusakan Barang Lakwas";
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);

        });

        cvTurbin.setOnClickListener(v -> {
            strTitle = "Laporan Kerusakan Barang Turbin";
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvP2.setOnClickListener(v -> {
            strTitle = "Laporan Kerusakan Barang P5";
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });

        cvP3.setOnClickListener(v -> {
            strTitle = "Laporan Kerusakan Barang P3";
            Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
            intent.putExtra(ReportActivity.DATA_TITLE, strTitle);
            startActivity(intent);
        });


        cvHistory.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoryUserActivity.class);
//            intent.putExtra(HistoryUserActivity.Data, strTitle);
            startActivity(intent);
        });


    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(userId);
        editor.apply();

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(HomeActivity.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
