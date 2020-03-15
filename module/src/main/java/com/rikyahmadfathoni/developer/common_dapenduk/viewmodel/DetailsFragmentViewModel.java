package com.rikyahmadfathoni.developer.common_dapenduk.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikyahmadfathoni.developer.common_dapenduk.model.DataDetailsModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsData;

import java.util.List;

public class DetailsFragmentViewModel extends ViewModel {

    private MutableLiveData<List<DataDetailsModel>> listDetailsLiveData = new MutableLiveData<>();

    public DetailsFragmentViewModel() {
        super();
    }

    public MutableLiveData<List<DataDetailsModel>> getListDetailsLiveData() {
        return listDetailsLiveData;
    }

    public void setDataList(List<DataDetailsModel> detailsModels) {
        listDetailsLiveData.setValue(detailsModels);
    }

    public void setDataList(DataPendudukModel dataPendudukModel, String fragmentMode) {

        listDetailsLiveData.setValue(UtilsData.toDataDetails(dataPendudukModel, fragmentMode));
    }

}
