/*
 * Created By Kulomady on 9/30/16 2:00 PM
 * Copyright (c) 2016. All rights reserved
 *
 * Last Modified 9/30/16 2:00 PM
 */

package com.arm.hackbri.landmoney.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.arm.hackbri.landmoney.R;
import com.arm.hackbri.landmoney.interactor.PreferencesInteractor;
import com.arm.hackbri.landmoney.interactor.PreferencesInteractorImpl;
import com.arm.hackbri.landmoney.model.response.Profile;
import com.arm.hackbri.landmoney.presenter.LoginPresenter;
import com.arm.hackbri.landmoney.presenter.LoginPresenterImpl;
import com.arm.hackbri.landmoney.view.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity implements LoginView {

    @Bind(R.id.input_phone)
    EditText edtPhone;
    @Bind(R.id.input_password)
    EditText edtPassword;

    LoginPresenter loginPresenter;
    ProgressDialog progressDialog;

    PreferencesInteractor preferencesInteractor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesInteractor = new PreferencesInteractorImpl();
        if (preferencesInteractor.isLogin(this)) {
            CreditActivity.openActivity(this);
            this.finish();
        }

        this.loginPresenter = new LoginPresenterImpl(this);

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Please Wait");
    }

    @Override
    public void showProgressFetchCreditList() {
        this.progressDialog.show();
    }

    @Override
    public void dismissProgressFetchCreditList() {
        this.progressDialog.dismiss();
    }

    @Override
    public void renderProfileData(Profile profile) {
        CreditActivity.openActivity(this);
    }

    @Override
    public void renderErrorServerFetchData(String messageError) {
        showToastMessage(messageError);
    }

    @Override
    public void renderErrorResponseFetchData(String messageError) {
        showToastMessage(messageError);
    }

    @Override
    public void renderErrorConnection(String messageError) {
        showToastMessage(messageError);
    }

    @Override
    public void renderErrorUnknown(String messageError) {
        showToastMessage(messageError);
    }

    @Override
    public String getPhoneNumber() {
        return edtPhone.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }

    @OnClick(R.id.btnLogin)
    void doLogin() {
        loginPresenter.processPostLogin(this);
    }

    @OnClick(R.id.btnSignup)
    void doRegister() {
        navigateToRegister();
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
