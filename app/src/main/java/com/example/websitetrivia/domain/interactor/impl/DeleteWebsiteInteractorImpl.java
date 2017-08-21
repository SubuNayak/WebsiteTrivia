package com.example.websitetrivia.domain.interactor.impl;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.DeleteWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;

public class DeleteWebsiteInteractorImpl extends AbstractInteractor implements DeleteWebsiteInteractor, WebsitesDataSource.DeleteWebsiteCallback {


    private DeleteWebsiteInteractor.Callback mCallback;
    private WebsitesRepository mWebsitesRepository;
    private String mId;

    public DeleteWebsiteInteractorImpl(Executor executor, MainThread mainThread, String id,
                                       Callback callback, WebsitesRepository websitesRepository){
        super(executor, mainThread);
        mId = id;
        mCallback = callback;
        mWebsitesRepository = websitesRepository;
    }

    @Override
    public void onWebsiteDeleted() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onWebsiteDeleted();
            }
        });
    }

    @Override
    public void onWebsiteDeleteError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onWebsiteDeletedError();
            }
        });
    }

    @Override
    public void run() {
        mWebsitesRepository.deleteWebsite(mId, this);
    }
}
