package com.rikyahmadfathoni.developer.common_dapenduk.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPendudukModel implements Parcelable {

    public static final String TAG = "DataPendudukModel";

    private int id;
    private String nama;
    private String jenis_kelamin;
    private String alamat;
    private String tempat_lahir;
    private String tanggal_lahir;
    private String foto;
    private String pekerjaan;
    private String fotoPath;

    public DataPendudukModel() {
        super();
    }

    public DataPendudukModel(String nama, String jenis_kelamin, String alamat, String tempat_lahir,
                             String tanggal_lahir, String foto, String pekerjaan) {
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.foto = foto;
        this.pekerjaan = pekerjaan;
    }

    protected DataPendudukModel(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        jenis_kelamin = in.readString();
        alamat = in.readString();
        tempat_lahir = in.readString();
        tanggal_lahir = in.readString();
        foto = in.readString();
        pekerjaan = in.readString();
        fotoPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeString(jenis_kelamin);
        dest.writeString(alamat);
        dest.writeString(tempat_lahir);
        dest.writeString(tanggal_lahir);
        dest.writeString(foto);
        dest.writeString(pekerjaan);
        dest.writeString(fotoPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataPendudukModel> CREATOR = new Creator<DataPendudukModel>() {
        @Override
        public DataPendudukModel createFromParcel(Parcel in) {
            return new DataPendudukModel(in);
        }

        @Override
        public DataPendudukModel[] newArray(int size) {
            return new DataPendudukModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }
}
