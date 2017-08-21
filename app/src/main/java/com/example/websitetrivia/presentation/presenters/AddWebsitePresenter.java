package com.example.websitetrivia.presentation.presenters;


import com.example.websitetrivia.presentation.presenters.base.BasePresenter;
import com.example.websitetrivia.presentation.ui.BaseView;

public interface AddWebsitePresenter extends BasePresenter{
    interface View extends BaseView {
        void onWebsiteAdded();
    }

    void addWebsite(String name, String foundingYear, String founders, String location, String CEO, String rank, String timeSpent);
}
