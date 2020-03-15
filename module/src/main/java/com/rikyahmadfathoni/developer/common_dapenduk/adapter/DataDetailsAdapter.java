package com.rikyahmadfathoni.developer.common_dapenduk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rikyahmadfathoni.developer.common_dapenduk.BaseApplication;
import com.rikyahmadfathoni.developer.common_dapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.callback.EditPendudukListener;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;

public class DataDetailsAdapter extends ListAdapter<DataDetailsModel, RecyclerView.ViewHolder> {

    private final int IMAGE_VIEW_LAYOUT = 1;

    private EditPendudukListener editPendudukListener;

    public DataDetailsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<DataDetailsModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataDetailsModel>() {
        @Override
        public boolean areItemsTheSame(DataDetailsModel oldItem, DataDetailsModel newItem) {
            return oldItem.getIconRes() == newItem.getIconRes();
        }

        @Override
        public boolean areContentsTheSame(DataDetailsModel oldItem, DataDetailsModel newItem) {
            return oldItem.getTextValue().equals(newItem.getTextValue());
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == IMAGE_VIEW_LAYOUT) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_details_image, parent, false);

            return new ImageViewHolder(itemView);
        } else {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_details_view, parent, false);

            return new ViewHolder(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        DataDetailsModel dataDetailsModel = getItem(position);

        if (dataDetailsModel != null) {
            return dataDetailsModel.getItemType();
        }

        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DataDetailsModel dataDetailsModel = getItem(position);

        int itemViewType = holder.getItemViewType();

        if (itemViewType == IMAGE_VIEW_LAYOUT) {

            ImageViewHolder viewHolder = (ImageViewHolder) holder;

            BaseApplication.getInstance().mRequestManager
                    .load(dataDetailsModel.getImageUrl()).into(viewHolder.imageProfile);

            viewHolder.imageEdit.setVisibility(dataDetailsModel.isEnableEdit() ? View.VISIBLE : View.GONE);
        } else {

            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.imageIcon.setImageResource(dataDetailsModel.getIconRes());
            viewHolder.imageEdit.setVisibility(dataDetailsModel.isEnableEdit() ? View.VISIBLE : View.GONE);
            viewHolder.textTitle.setText(dataDetailsModel.getTextTitle());
            viewHolder.textValue.setText(dataDetailsModel.getTextValue());
        }
    }

    public DataDetailsModel getDataItem(int position) {
        return getItem(position);
    }

    public void setOnClickListener(EditPendudukListener editPendudukListener) {
        this.editPendudukListener = editPendudukListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageIcon;
        private ImageView imageEdit;
        private TextView textTitle;
        private TextView textValue;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.imageIcon);
            imageEdit = itemView.findViewById(R.id.imageEdit);
            textTitle = itemView.findViewById(R.id.textTitle);
            textValue = itemView.findViewById(R.id.textValue);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (editPendudukListener != null) {
                editPendudukListener.onclick(getItem(position), position);
            }
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageProfile;
        private ImageView imageEdit;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.imageProfile);
            imageEdit = itemView.findViewById(R.id.imageEdit);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (editPendudukListener != null) {
                editPendudukListener.onclick(getItem(position), position);
            }
        }
    }
}
