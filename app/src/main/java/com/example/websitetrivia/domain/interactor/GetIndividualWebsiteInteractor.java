package com.example.websitetrivia.domain.interactor;


import com.example.websitetrivia.domain.interactor.base.Interactor;
import com.example.websitetrivia.domain.model.Website;

public interface GetIndividualWebsiteInteractor extends  Interactor{
    interface Callback{
        void onGetIndividualWebsite(Website website);
        void onGetIndividualWebsiteError();
    }
}
