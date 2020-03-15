package com.rikyahmadfathoni.developer.admindapenduk.repository;

import androidx.lifecycle.MutableLiveData;

import com.rikyahmadfathoni.developer.admindapenduk.R;
import com.rikyahmadfathoni.developer.admindapenduk.model.LoginFormState;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsValidate;

public class LoginRepository {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public MutableLiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public void loginDataChanged(String username, String password) {

        if (!UtilsValidate.isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.login_invalid_email, null));
        } else if (!UtilsValidate.isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.login_invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
}
