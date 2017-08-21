package com.example.websitetrivia.domain.interactor;


import com.example.websitetrivia.domain.interactor.base.Interactor;

public interface DeleteWebsiteInteractor extends Interactor{
        interface Callback{
            void onWebsiteDeleted();
            void onWebsiteDeletedError();
        }
}
