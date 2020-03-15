package com.rikyahmadfathoni.developer.admindapenduk.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikyahmadfathoni.developer.admindapenduk.model.LoginFormState;
import com.rikyahmadfathoni.developer.admindapenduk.repository.LoginRepository;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel() {
        super();
        this.loginRepository = new LoginRepository();
    }

    public MutableLiveData<LoginFormState> getLoginFormState() {
        return loginRepository.getLoginFormState();
    }

    public void loginDataChanged(String username, String password) {
        loginRepository.loginDataChanged(username, password);
    }
}
