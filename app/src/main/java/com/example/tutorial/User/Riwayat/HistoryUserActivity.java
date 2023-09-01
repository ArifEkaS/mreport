package com.example.tutorial.User.Riwayat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial.API.ApiInterface;
import com.example.tutorial.R;
import com.example.tutorial.User.Report.ReportResponse;
import com.example.tutorial.Retrofit.RetrofitClient;
import com.example.tutorial.User.Report.ReportAdapter;
import com.example.tutorial.User.Report.ReportItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryUserActivity extends AppCompatActivity implements ReportAdapter.HistoryAdapterCallback{

    private SharedPreferences sharedPreferences;
    private static final String userId = "userId";
    private static final String P = "MyPrefs";
    ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

    public static final String DATA_STATUS = "STATUS";

    List<ReportItem> modelDatabaseList = new ArrayList<>();

    ReportAdapter historyAdapter;

    Toolbar toolbar;

    RecyclerView rvHistory;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        sharedPreferences = getSharedPreferences(P, MODE_PRIVATE);
        int id = sharedPreferences.getInt(userId, 0);
        setStatusBar();
        setToolbar();
        setInitLayout();
        setViewModel(id);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }



    private void setInitLayout() {

        rvHistory = findViewById(R.id.rvHistory);
        tvNotFound = findViewById(R.id.tvNotFound);

        tvNotFound.setVisibility(View.GONE);

        historyAdapter = new ReportAdapter(this, modelDatabaseList, this);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);
    }

    private void setViewModel(int id) {
        Call<ReportResponse> reportResponseCall = apiInterface.getReportsById(id);
        reportResponseCall.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NotNull Call<ReportResponse> call, @NotNull Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    ReportResponse leagueResponse = response.body();
                    if (leagueResponse != null && leagueResponse.getReports() != null) {
                        if(leagueResponse.getReports().isEmpty()){
                            tvNotFound.setVisibility(View.VISIBLE);
                            rvHistory.setVisibility(View.GONE);
                        }else{
                            tvNotFound.setVisibility(View.GONE);
                            rvHistory.setVisibility(View.VISIBLE);
                        }
                        historyAdapter.setDataAdapter(leagueResponse.getReports());
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

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

    @Override
    public void onDelete(ReportItem modelDatabase) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
