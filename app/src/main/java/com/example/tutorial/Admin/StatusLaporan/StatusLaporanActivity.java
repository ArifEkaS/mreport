package com.example.tutorial.Admin.StatusLaporan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial.API.ApiInterface;
import com.example.tutorial.Admin.Home.HomeAdminActivity;
import com.example.tutorial.R;
import com.example.tutorial.User.Report.ReportResponse;
import com.example.tutorial.Retrofit.RetrofitClient;
import com.example.tutorial.User.Report.ReportItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusLaporanActivity extends AppCompatActivity {
    ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
    public static final String DATA_STATUS = "STATUS";

    List<ReportItem> modelDatabaseList = new ArrayList<>();

    StatusLaporanAdapter statusLaporanAdapter;
    StatusLaporanAdapter historyViewModel;
    RecyclerView rvStatusLaporan;
    TextView tvNotFound;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_laporan);

        setInitLayout();
        setViewModel();
    }

    private void setInitLayout() {

        rvStatusLaporan = findViewById(R.id.rvStatusLaporan);
        tvNotFound = findViewById(R.id.tvNotFound);

        tvNotFound.setVisibility(View.GONE);

        statusLaporanAdapter = new StatusLaporanAdapter(this, modelDatabaseList);
        rvStatusLaporan.setHasFixedSize(true);
        rvStatusLaporan.setLayoutManager(new LinearLayoutManager(this));
        rvStatusLaporan.setAdapter(statusLaporanAdapter);


        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(StatusLaporanActivity.this, HomeAdminActivity.class);
            startActivity(intent);
        });
    }

    private void setViewModel() {
        Call<ReportResponse> reportResponseCall = apiInterface.getAllReports();
        reportResponseCall.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportResponse> call, @NotNull Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse leagueResponse = response.body();
                    if (leagueResponse != null && leagueResponse.getReports() != null) {
                        if(leagueResponse.getReports().isEmpty()){
                            tvNotFound.setVisibility(View.VISIBLE);
                            rvStatusLaporan.setVisibility(View.GONE);
                        }else{
                            tvNotFound.setVisibility(View.GONE);
                            rvStatusLaporan.setVisibility(View.VISIBLE);
                        }
                        statusLaporanAdapter.setDataAdapter(leagueResponse.getReports());
                    } else {
                        Log.d("NetworkCall", "Empty Data");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReportResponse> call, @NotNull Throwable t) {
                Log.d("NetworkCall", "Failed Fetch getLeague()/Failure");
            }
        });

    }
}
