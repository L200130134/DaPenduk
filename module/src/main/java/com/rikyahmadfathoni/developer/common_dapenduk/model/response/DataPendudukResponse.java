package com.rikyahmadfathoni.developer.common_dapenduk.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rikyahmadfathoni.developer.common_dapenduk.model.DataPendudukModel;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsInteger;

import java.util.List;

public class DataPendudukResponse {

    @Expose
    @SerializedName("dataTotal")
    private Integer dataTotal;

    @Expose
    @SerializedName("dataList")
    private List<DataPendudukModel> dataPendudukModels;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("error")
    private boolean error;

    private boolean loadCompleted;

    private int codeRequest;

    private boolean manipulated;

    private boolean onexecute;

    public DataPendudukResponse(Integer dataTotal, List<DataPendudukModel> dataPendudukModels,
                                String message, boolean error, boolean loadCompleted,
                                int codeRequest, boolean manipulated) {
        this.dataTotal = dataTotal;
        this.dataPendudukModels = dataPendudukModels;
        this.message = message;
        this.error = error;
        this.loadCompleted = loadCompleted;
        this.codeRequest = codeRequest;
        this.manipulated = manipulated;
    }

    public DataPendudukResponse(boolean manipulated, boolean onexecute) {
        this.manipulated = manipulated;
        this.onexecute = onexecute;
    }

    public void setCodeRequest(int codeRequest) {
        this.codeRequest = codeRequest;
    }

    public void setManipulated(boolean manipulated) {
        this.manipulated = manipulated;
    }

    public int getDataTotal() {
        return UtilsInteger.validateNull(dataTotal);
    }

    public List<DataPendudukModel> getDataPenduduk() {
        return dataPendudukModels;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public boolean isLoadCompleted() {
        return loadCompleted;
    }

    public int getCodeRequest() {
        return codeRequest;
    }

    public boolean isManipulated() {
        return manipulated;
    }

    public boolean isOnexecute() {
        return onexecute;
    }
}
