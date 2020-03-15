package com.rikyahmadfathoni.developer.common_dapenduk.utils;

import androidx.annotation.Nullable;

import com.rikyahmadfathoni.developer.common_dapenduk.Constants;
import com.rikyahmadfathoni.developer.common_dapenduk.R;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;

import java.util.ArrayList;
import java.util.List;

public class UtilsData {

    public static List<DataDetailsModel> toDataDetails(DataPendudukModel dataPendudukModel,
                                                       String fragmentMode) {

        List<DataDetailsModel> dataDetailsModels = new ArrayList<>();

        dataDetailsModels.add(new DataDetailsModel(R.drawable.placeholder, dataPendudukModel.getFoto(),
                "Pilih Gambar", "", isEditableData(fragmentMode), 1));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_person, null,
                "Nama", dataPendudukModel.getNama(), isEditableData(fragmentMode)));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_gender, null,
                "Jenis Kelamin", dataPendudukModel.getJenis_kelamin(), isEditableData(fragmentMode)));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_home_address, null,
                "Alamat", dataPendudukModel.getAlamat(), isEditableData(fragmentMode)));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_address, null,
                "Tempat Lahir", dataPendudukModel.getTempat_lahir(), isEditableData(fragmentMode)));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_calendar, null,
                "Tanggal Lahir", dataPendudukModel.getTanggal_lahir(), isEditableData(fragmentMode)));

        dataDetailsModels.add(new DataDetailsModel(R.drawable.ic_work, null,
                "Pekerjaan", dataPendudukModel.getPekerjaan(), isEditableData(fragmentMode)));

        return dataDetailsModels;
    }

    public static void updateDataPenduduk(@Nullable DataPendudukModel oldDataPendudukModel,
                                          List<DataDetailsModel> dataDetailsModels) {

        try {

            if (oldDataPendudukModel != null) {

                oldDataPendudukModel.setFotoPath(dataDetailsModels.get(0).getImageUrl());
                oldDataPendudukModel.setNama(dataDetailsModels.get(1).getTextValue());
                oldDataPendudukModel.setJenis_kelamin(dataDetailsModels.get(2).getTextValue());
                oldDataPendudukModel.setAlamat(dataDetailsModels.get(3).getTextValue());
                oldDataPendudukModel.setTempat_lahir(dataDetailsModels.get(4).getTextValue());
                oldDataPendudukModel.setTanggal_lahir(dataDetailsModels.get(5).getTextValue());
                oldDataPendudukModel.setPekerjaan(dataDetailsModels.get(5).getTextValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void addUrlHost(DataPendudukModel dataPendudukModel) {

        if (dataPendudukModel != null) {

            String fotoUrl = dataPendudukModel.getFoto();

            if (!fotoUrl.isEmpty() && !fotoUrl.contains(Constants.BASE_URL)) {

                dataPendudukModel.setFoto(Constants.BASE_URL + fotoUrl);
            }
        }
    }

    public static void addUrlHost(List<DataPendudukModel> dataPendudukModels) {

        if (dataPendudukModels != null) {

            for (DataPendudukModel dataPendudukModel : dataPendudukModels) {
                addUrlHost(dataPendudukModel);
            }
        }
    }

    public static void removeUrlHost(DataPendudukModel dataPendudukModel) {

        if (dataPendudukModel != null) {

            String fotoUrl = dataPendudukModel.getFoto();

            if (fotoUrl != null && fotoUrl.contains(Constants.BASE_URL)) {

                dataPendudukModel.setFoto(fotoUrl.replace(Constants.BASE_URL, ""));
            }
        }
    }

    private static boolean isEditableData(String fragmentMode) {

        if (fragmentMode != null) {
            return fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_EDIT) ||
                    fragmentMode.equals(Constants.FRAGMENT_MODE_ADMIN_ADD);
        }

        return false;
    }
}
