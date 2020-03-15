package com.rikyahmadfathoni.developer.admindapenduk.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rikyahmadfathoni.developer.admindapenduk.R;
import com.rikyahmadfathoni.developer.admindapenduk.databinding.ActivityLoginBinding;
import com.rikyahmadfathoni.developer.admindapenduk.model.LoginFormState;
import com.rikyahmadfathoni.developer.admindapenduk.viewmodel.LoginViewModel;
import com.rikyahmadfathoni.developer.common_dapenduk.model.response.LoginResponse;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseLoginActivity;
import com.rikyahmadfathoni.developer.common_dapenduk.utils.UtilsDialog;

public class LoginActivity extends BaseLoginActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initListener();
    }

    private void initListener() {
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                binding.login.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    binding.username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    binding.password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        apiRequestViewModel.getLoginResponseLive().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(@NonNull LoginResponse loginResponse) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (loginResponse.isError()) {
                    UtilsDialog.showSnackbar(binding.container, loginResponse.getMessage());
                    return;
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginAdmin();
                }
                return false;
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });

        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
    }

    private TextWatcher afterTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //ignore
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //ignore
        }

        @Override
        public void afterTextChanged(Editable s) {
            loginViewModel.loginDataChanged(binding.username.getText().toString(),
                    binding.password.getText().toString());
        }
    };

    private void loginAdmin() {
        progressDialog = UtilsDialog.createProgressDialog(LoginActivity.this,
                getString(R.string.progress_title_login), getString(R.string.progress_message_default));

        apiRequestViewModel.login(binding.username.getText().toString(),
                binding.password.getText().toString());
    }
}
