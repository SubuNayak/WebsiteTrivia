package com.example.websitetrivia.presentation.presenters;


import android.content.Context;

import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.presentation.presenters.base.BasePresenter;
import com.example.websitetrivia.presentation.ui.BaseView;

import java.util.List;

public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {

        void firstTimeOpsFinished();
        void showWebsites(List<Website> websites);
        void onWebsiteDeleted();

    }
    void runFirstTimeOps(Context context);
    void getWebsites();
    void deleteWebsite(String id);

}
