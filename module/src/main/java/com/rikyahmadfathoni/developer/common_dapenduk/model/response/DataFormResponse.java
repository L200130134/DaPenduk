package com.rikyahmadfathoni.developer.common_dapenduk.model.response;

public class DataFormResponse {

    private boolean namaError;
    private boolean jenisKelaminError;
    private boolean alamatError;
    private boolean tempatLahirError;
    private boolean tanggalLahirError;
    private boolean pekerjaanError;
    private String message;
    private boolean isDataValid;

    public DataFormResponse() {
        super();
    }

    public DataFormResponse(String message, boolean isDataValid) {
        this.message = message;
        this.isDataValid = isDataValid;
    }

    public void setNamaError(boolean namaError) {
        this.namaError = namaError;
    }

    public void setJenisKelaminError(boolean jenisKelaminError) {
        this.jenisKelaminError = jenisKelaminError;
    }

    public void setAlamatError(boolean alamatError) {
        this.alamatError = alamatError;
    }

    public void setTempatLahirError(boolean tempatLahirError) {
        this.tempatLahirError = tempatLahirError;
    }

    public void setTanggalLahirError(boolean tanggalLahirError) {
        this.tanggalLahirError = tanggalLahirError;
    }

    public void setPekerjaanError(boolean pekerjaanError) {
        this.pekerjaanError = pekerjaanError;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }

    public boolean isNamaError() {
        return namaError;
    }

    public boolean isJenisKelaminError() {
        return jenisKelaminError;
    }

    public boolean isAlamatError() {
        return alamatError;
    }

    public boolean isTempatLahirError() {
        return tempatLahirError;
    }

    public boolean isTanggalLahirError() {
        return tanggalLahirError;
    }

    public boolean isPekerjaanError() {
        return pekerjaanError;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
