package com.example.websitetrivia.domain.interactor;

import com.example.websitetrivia.domain.interactor.base.Interactor;
import com.example.websitetrivia.domain.model.Website;


public interface UpdateWebsiteInteractor extends Interactor{

    interface Callback{
        void onWebsiteUpdated(Website website);
        void onWebsiteUpdatedError();
    }

}
