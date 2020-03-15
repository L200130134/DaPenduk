package com.rikyahmadfathoni.developer.common_dapenduk.network;

import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseLoginActivity;
import com.rikyahmadfathoni.developer.common_dapenduk.ui.BaseMainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void doMainInjection(BaseMainActivity baseActivity);

    void doLoginInjection(BaseLoginActivity baseActivity);
}
