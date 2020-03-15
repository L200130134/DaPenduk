package com.rikyahmadfathoni.developer.common_dapenduk.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse implements Parcelable {

    @Expose
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("error")
    private boolean error;
    @Expose
    @SerializedName("completed")
    private boolean completed;

    public LoginResponse() {
        super();
    }

    public LoginResponse(Integer id, String email, String password, String message,
                         boolean error, boolean completed) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.message = message;
        this.error = error;
        this.completed = completed;
    }

    public LoginResponse(boolean error) {
        this.error = error;
    }

    public LoginResponse(boolean error, boolean completed) {
        this.error = error;
        this.completed = completed;
    }

    private LoginResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        email = in.readString();
        password = in.readString();
        message = in.readString();
        error = in.readByte() != 0;
        completed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(message);
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public boolean isCompleted() {
        return completed;
    }
}
