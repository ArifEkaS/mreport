package com.example.tutorial.API;

import com.example.tutorial.Auth.LoginResponse;
import com.example.tutorial.Admin.Riwayat.DeleteReportResponse;
import com.example.tutorial.Auth.LoginRequest;
import com.example.tutorial.User.Report.PostFormResponse;
import com.example.tutorial.User.Report.ReportResponse;
import com.example.tutorial.Admin.StatusLaporan.UpdateStatusRequest;
import com.example.tutorial.Admin.StatusLaporan.UpdateStatusResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("admin/reports")
    Call<ReportResponse> getAllReports();

    @DELETE("admin/report/{report_id}")
    Call<DeleteReportResponse> delReport(@Path("report_id") int reportId);

    @PUT("admin/report/{report_id}")
    Call<UpdateStatusResponse> updateStatus(@Body UpdateStatusRequest updateStatusRequest, @Path("report_id") int report_id);

    @GET("reports/{user_id}")
    Call<ReportResponse> getReportsById(@Path("user_id") int userId);

    @Multipart
    @POST("report/add/{user_id}")
    Call<PostFormResponse> uploadReport(@Part("type_report") RequestBody typeReport, @Part("content") RequestBody content, @Part("phone") RequestBody phone , @Part("date") RequestBody date, @Part MultipartBody.Part image, @Path("user_id") int userId);

}
