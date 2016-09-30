package com.arm.hackbri.landmoney.presenter;

import com.arm.hackbri.landmoney.interactor.NetworkInteractorImpl;
import com.arm.hackbri.landmoney.interactor.OnFetchDataListener;
import com.arm.hackbri.landmoney.model.ParamNetwork;
import com.arm.hackbri.landmoney.model.response.Profile;
import com.arm.hackbri.landmoney.network.exception.GeneralErrorException;
import com.arm.hackbri.landmoney.network.exception.HttpErrorException;
import com.arm.hackbri.landmoney.view.LoginView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * LoginPresenterImpl
 * Created by anggaprasetiyo on 9/30/16.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView viewListener;
    private final NetworkInteractorImpl netDataInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.viewListener = loginView;
        this.netDataInteractor = new NetworkInteractorImpl();
    }

    @Override
    public void processPostLogin() {
        netDataInteractor.login(new ParamNetwork.Builder().put("phone", viewListener.getPhoneNumber())
                .put("password", viewListener.getPassword())
                .build(), new OnFetchDataListener<Profile>() {
            @Override
            public void onSuccessFetchData(Profile data) {
                viewListener.renderProfileData(data);
            }

            @Override
            public void onFailedFetchData(Throwable throwable) {
                if (throwable instanceof SocketTimeoutException) {
                    viewListener.renderErrorConnection("Server timeout, silahkan coba kembali");
                } else if (throwable instanceof UnknownHostException) {
                    viewListener.renderErrorConnection("Tidak ada internet, Silahkan coba kembali");
                } else if (throwable instanceof HttpErrorException) {
                    viewListener.renderErrorServerFetchData(throwable.getMessage());
                } else if (throwable instanceof GeneralErrorException) {
                    viewListener.renderErrorResponseFetchData(throwable.getMessage());
                } else {
                    viewListener.renderErrorUnknown(throwable.getMessage());
                }
            }
        });
    }
}
