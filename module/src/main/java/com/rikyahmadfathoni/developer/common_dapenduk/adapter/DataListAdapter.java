package com.rikyahmadfathoni.developer.common_dapenduk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;
import com.rikyahmadfathoni.developer.common_dapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.callback.DataPendudukListener;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends PagedListAdapter<DataPendudukModel, DataListAdapter.ViewHolder> {

    private DataPendudukListener dataPendudukListener;

    public DataListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<DataPendudukModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataPendudukModel>() {
        @Override
        public boolean areItemsTheSame(DataPendudukModel oldItem, DataPendudukModel newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.getNama().equals(newItem.getNama()) &&
                    oldItem.getTanggal_lahir().equals(newItem.getTanggal_lahir());
        }

        @Override
        public boolean areContentsTheSame(DataPendudukModel oldItem, DataPendudukModel newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.getNama().equals(newItem.getNama()) &&
                    oldItem.getTanggal_lahir().equals(newItem.getTanggal_lahir());
        }
    };

    @NonNull
    @Override
    public DataListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data_penduduk, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataListAdapter.ViewHolder holder, int position) {

        DataPendudukModel pendudukModel = getItem(position);

        if (pendudukModel != null) {
            BaseApplication.getInstance().mRequestManager
                    .load(pendudukModel.getFoto()).into(holder.imageFoto);
            holder.textNama.setText(pendudukModel.getNama());
            holder.textDeskripsi.setText(pendudukModel.getAlamat());
        }
    }

    @NonNull
    public List<DataPendudukModel> getListData() {
        List<DataPendudukModel> models = getCurrentList();
        if (models != null) {
            return models;
        }
        return new ArrayList<>();
    }

    public void setOnClickListener(DataPendudukListener dataPendudukListener) {
        this.dataPendudukListener = dataPendudukListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageFoto;
        private TextView textNama, textDeskripsi;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFoto = itemView.findViewById(R.id.imageFoto);
            textNama = itemView.findViewById(R.id.textNama);
            textDeskripsi = itemView.findViewById(R.id.textDeskripsi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (dataPendudukListener != null) {
                dataPendudukListener.onclick(getItem(position), position);
            }
        }
    }
}
