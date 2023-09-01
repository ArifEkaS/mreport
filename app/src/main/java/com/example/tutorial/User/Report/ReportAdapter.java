package com.example.tutorial.User.Report;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tutorial.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    String res;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String status = "STATUS";
    List<ReportItem> modelDatabase;
    Context mContext;
    private String text;

    HistoryAdapterCallback mAdapterCallback;

    public ReportAdapter(Context context, List<ReportItem> modelDatabaseList,
                         HistoryAdapterCallback adapterCallback) {
        this.mContext = context;
        this.modelDatabase = modelDatabaseList;
        this.mAdapterCallback = adapterCallback;
    }

    public void setDataAdapter(List<ReportItem> items) {
        modelDatabase.clear();
        modelDatabase.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ViewHolder holder, int position) {
        final ReportItem data = modelDatabase.get(position);
        holder.tvKategori.setText(data.getStrType());
        holder.tvHP.setText(data.getStrPhone());
        holder.tvDate.setText(data.getStrDate());
        holder.tvLaporan.setText(data.getStrContent());
        holder.imageLaporan.setVisibility(View.VISIBLE);

        if (data.getStrImg() != null && !data.getStrImg().isEmpty()) {
            Glide.with(mContext)
                    .load(data.getStrImg())
                    .into(holder.imageLaporan);
        } else {
            holder.imageLaporan.setVisibility(View.GONE);
//            holder.tvNoImage.setVisibility(View.VISIBLE);
        }

        switch (data.getIntStatus()) {
            case 0:
                holder.tvstatus.setText("Belum Diproses");
                holder.tvstatus.setTextColor(Color.RED);
                break;
            case 1:
                holder.tvstatus.setText("Sedang Diproses");
                holder.tvstatus.setTextColor(Color.BLUE);
                break;
            case 2:
                holder.tvstatus.setText("Sudah Diproses");
                holder.tvstatus.setTextColor(Color.GREEN);
                break;
            case 3:
                holder.tvstatus.setText("Tidak Berhasil");
                holder.tvstatus.setTextColor(Color.RED);
                break;
        }

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

    public interface HistoryAdapterCallback {
        void onDelete(ReportItem modelDatabase);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvKategori;
        public TextView tvDate;
        public TextView tvLaporan;
        public TextView tvHP;
        public TextView tvstatus;
        public CardView cvHistory;
        public LinearLayout layoutHeader;
        public ImageView imageLaporan;

        public ViewHolder(View itemView) {
            super(itemView);

            tvKategori = itemView.findViewById(R.id.tvKategori);
            tvLaporan = itemView.findViewById(R.id.tvLaporan);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHP = itemView.findViewById(R.id.tvHP);
            cvHistory = itemView.findViewById(R.id.cvHistory);
            layoutHeader = itemView.findViewById(R.id.layoutHeader);
            tvstatus = itemView.findViewById(R.id.tvStatuslapor);
            imageLaporan = itemView.findViewById(R.id.imageLaporan);
            cvHistory.setOnClickListener(view -> {
                ReportItem modelLaundry = modelDatabase.get(getAdapterPosition());
                mAdapterCallback.onDelete(modelLaundry);
            });

        }
    }
}