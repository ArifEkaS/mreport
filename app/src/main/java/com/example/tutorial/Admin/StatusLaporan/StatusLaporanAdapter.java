package com.example.tutorial.Admin.StatusLaporan;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial.API.ApiInterface;
import com.example.tutorial.R;
import com.example.tutorial.User.Report.ReportResponse;
import com.example.tutorial.Retrofit.RetrofitClient;
import com.example.tutorial.User.Report.ReportItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusLaporanAdapter extends RecyclerView.Adapter<StatusLaporanAdapter.ViewHolder> {
    String strStatus = "Laporan sudah diterima";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    List<ReportItem> modelDatabase;
    Context mContext;
//    Sharedpref sharedpref;

    public StatusLaporanAdapter(Context context, List<ReportItem> modelDatabaseList) {
        this.mContext = context;
        this.modelDatabase = modelDatabaseList;
    }

    public void setDataAdapter(List<ReportItem> items) {
        modelDatabase.clear();
        modelDatabase.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public StatusLaporanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_statuslaporan, parent, false);
        return new StatusLaporanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusLaporanAdapter.ViewHolder holder, int position) {
        final ReportItem data = modelDatabase.get(position);
        holder.tvKategorist.setText(data.getStrType());
        holder.tvHPst.setText(data.getStrPhone());
        holder.tvDatest.setText(data.getStrDate());
        holder.tvLaporanst.setText(data.getStrContent());
        final int[] checkedItem = {-1};
        holder.tvDone.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Ubah Status Laporan");

            // list of the items to be displayed to the user in the
            // form of list so that user can select the item from
            final String[] listItems = new String[]{"Belum Diproses", "Sedang Diproses", "Sudah Diproses", "Tidak Berhasil"};

            // the function setSingleChoiceItems is the function which
            // builds the alert dialog with the single item selection
            builder.setSingleChoiceItems(listItems, data.getIntStatus(), (dialog, which) -> {
                // update the selected item which is selected by the user so that it should be selected
                // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
//                tvSelectedItemPreview.setText("Selected Item is : " + listItems[which]);
                Log.d("Alert Dialog","Selected Item is : " + which);

                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<ReportResponse> reportResponseCall = apiInterface.getAllReports();
                Call<UpdateStatusResponse> updateStatusResponseCall = apiInterface.updateStatus(new UpdateStatusRequest(which), data.getIntId());
                updateStatusResponseCall.enqueue(new Callback<UpdateStatusResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<UpdateStatusResponse> call, @NotNull Response<UpdateStatusResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UpdateStatusResponse> call, @NotNull Throwable t) {
                        Log.d("NetworkCall", "Failed Fetch getLeague()/Failure");
                    }
                });
                reportResponseCall.enqueue(new Callback<ReportResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ReportResponse> call, @NotNull Response<ReportResponse> response) {
                        if (response.isSuccessful()) {
                            ReportResponse leagueResponse = response.body();
                            if (leagueResponse != null && leagueResponse.getReports() != null) {

                                setDataAdapter(leagueResponse.getReports());
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

            });

            // set the negative button if the user is not interested to select or change already selected item
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });

            // create and build the AlertDialog instance with the AlertDialog builder instance
            AlertDialog customAlertDialog = builder.create();

            // show the alert dialog when the button is clicked
            customAlertDialog.show();
        });
        switch (data.getStrType()) {
            case "Laporan Kerusakan Barang Sekertariat":
                holder.layoutHeader.setBackgroundResource(R.color.red);
                break;
            case "Laporan Kerusakan Barang Lakwas":
                holder.layoutHeader.setBackgroundResource(R.color.blue);
                break;
            case "Laporan Kerusakan Barang Turbin":
                holder.layoutHeader.setBackgroundResource(R.color.purple_700);
                break;
            case "Laporan Kerusakan Barang P5":
                holder.layoutHeader.setBackgroundResource(R.color.teal_200);
                break;
            case "Laporan Kerusakan Barang P3":
                holder.layoutHeader.setBackgroundResource(R.color.green);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return modelDatabase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvKategorist, tvDatest, tvLaporanst, tvHPst;
        public CardView cv;
        public LinearLayout layoutHeader;

        public ImageView tvDone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvKategorist = itemView.findViewById(R.id.tvKategoristat);
            tvLaporanst = itemView.findViewById(R.id.tvLaporanstat);
            tvDatest = itemView.findViewById(R.id.tvDatestat);
            tvHPst = itemView.findViewById(R.id.tvHPstat);

            cv = itemView.findViewById(R.id.cvStatusLaporan);
            tvDone = itemView.findViewById(R.id.btnDone);
            layoutHeader = itemView.findViewById(R.id.layoutHeader);
        }
    }


}