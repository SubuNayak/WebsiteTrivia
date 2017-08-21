package com.example.websitetrivia.domain.interactor;


import com.example.websitetrivia.domain.interactor.base.Interactor;

public interface AddWebsiteInteractor extends Interactor{
    interface Callback{
        void onWebsiteAdded();
        void onWebsiteAddedError();
    }
}
