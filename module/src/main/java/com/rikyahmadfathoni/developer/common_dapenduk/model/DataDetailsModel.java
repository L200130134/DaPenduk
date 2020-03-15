package com.rikyahmadfathoni.developer.common_dapenduk.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IdRes;

import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsString;

public class DataDetailsModel implements Parcelable {

    public static final String TAG = "DataDetailsModel";

    @IdRes
    private int iconRes;
    private String imageUrl;
    private String textTitle;
    private String textValue;
    private boolean enableEdit;
    private int itemType;

    public DataDetailsModel() {
        super();
    }

    public DataDetailsModel(int iconRes, String imageUrl, String textTitle,
                            String textValue, boolean enableEdit) {
        this.iconRes = iconRes;
        this.imageUrl = imageUrl;
        this.textTitle = textTitle;
        this.textValue = textValue;
        this.enableEdit = enableEdit;
    }

    public DataDetailsModel(int iconRes, String imageUrl, String textTitle,
                            String textValue, boolean enableEdit, int itemType) {
        this.iconRes = iconRes;
        this.imageUrl = imageUrl;
        this.textTitle = textTitle;
        this.textValue = textValue;
        this.enableEdit = enableEdit;
        this.itemType = itemType;
    }

    private DataDetailsModel(Parcel in) {
        iconRes = in.readInt();
        imageUrl = in.readString();
        textTitle = in.readString();
        textValue = in.readString();
        enableEdit = in.readByte() != 0;
        itemType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iconRes);
        dest.writeString(imageUrl);
        dest.writeString(textTitle);
        dest.writeString(textValue);
        dest.writeByte((byte) (enableEdit ? 1 : 0));
        dest.writeInt(itemType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataDetailsModel> CREATOR = new Creator<DataDetailsModel>() {
        @Override
        public DataDetailsModel createFromParcel(Parcel in) {
            return new DataDetailsModel(in);
        }

        @Override
        public DataDetailsModel[] newArray(int size) {
            return new DataDetailsModel[size];
        }
    };

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getTextValue() {
        return UtilsString.validateNull(textValue);
    }

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public int getItemType() {
        return itemType;
    }
}
