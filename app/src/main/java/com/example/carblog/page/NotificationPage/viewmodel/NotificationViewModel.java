package com.example.carblog.page.NotificationPage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<Boolean> onReloadForNotyPage = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> onReloadForHomePage = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getOnReloadForNotyPage() {
        return onReloadForNotyPage;
    }

    public void setOnReloadForNotyPage(boolean b) {
        this.onReloadForNotyPage.setValue(b);
    }

    public MutableLiveData<Boolean> getOnReloadForHomePage() {
        return onReloadForHomePage;
    }

    public void setOnReloadForHomePage(boolean b) {
        onReloadForHomePage.setValue(b);
    }
}
