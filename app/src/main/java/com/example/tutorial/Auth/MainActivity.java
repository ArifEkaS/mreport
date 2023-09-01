package com.example.tutorial.Auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial.API.ApiInterface;
import com.example.tutorial.User.Home.HomeActivity;
import com.example.tutorial.Admin.Home.HomeAdminActivity;
import com.example.tutorial.R;
import com.example.tutorial.Retrofit.RetrofitClient;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnSendPostRequest;
    private static final String TAG = "MainActivity";

    private SharedPreferences sharedPreferences;
    private static final String userId = "userId";
    private static final String P = "MyPrefs";

    private static final String ONESIGNAL_APP_ID = "58482fcd-5f0a-47f3-8fe2-81968252abaa";
    private Object data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(P, MODE_PRIVATE);

        int id = sharedPreferences.getInt(userId, 0);

        if(id != 0 && id != 4)
        {

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }else if(id == 4){

            Intent intent = new Intent(MainActivity.this, HomeAdminActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }


        setContentView(R.layout.activity_main);

        btnSendPostRequest = findViewById(R.id.btnSendPostRequest);
        btnSendPostRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSendPostRequestClicked();
            }
        });
    }


    private void btnSendPostRequestClicked() {

        EditText txtUser = findViewById(R.id.txtUser);
        EditText txtPass = findViewById(R.id.txtPass);

        String username = txtUser.getText().toString();
        String password = txtPass.getText().toString();

//        int id = sharedPreferences.getInt(userId, 0);
//        if (id != 0) {
//            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(intent);
//            return;
//        }

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.login(new LoginRequest(username, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    int userId = loginResponse.getUser_id();
                    Log.e(TAG, "onResponse: " + userId);

                    SharedPreferences sharedPreferences = getSharedPreferences(P, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", userId);
                    editor.apply();
                    if(userId == 4){
                        Intent intentAdmin = new Intent(MainActivity.this, HomeAdminActivity.class);
                        intentAdmin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentAdmin);
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onResponse: Response not successful");
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
