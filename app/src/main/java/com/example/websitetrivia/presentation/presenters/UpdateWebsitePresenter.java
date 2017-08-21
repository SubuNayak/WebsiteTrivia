package com.example.websitetrivia.presentation.presenters;


import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.presentation.ui.BaseView;

public interface UpdateWebsitePresenter {
    interface View extends BaseView{
        void onGetWebsite(Website website);
        void onWebsiteUpdated(Website website);
    }

    void getWebsiteById(String id);
    void updateWebsite(Website website, String name, int foundingYear, String founders, String location, String CEO, int rank, String timeSpent);

}
