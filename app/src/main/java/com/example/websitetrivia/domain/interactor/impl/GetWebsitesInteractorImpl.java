package com.example.websitetrivia.domain.interactor.impl;


import android.util.Log;

import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.GetWebsitesInteractor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;

import java.util.List;

public class GetWebsitesInteractorImpl extends AbstractInteractor implements GetWebsitesInteractor, WebsitesDataSource.GetWebsitesCallback {

    private Callback mCallback;
    private WebsitesRepository mWebsitesRepository;

    public GetWebsitesInteractorImpl(Executor executor, MainThread mainThread, WebsitesRepository websitesRepository, Callback callback){
        super(executor, mainThread);
        if(websitesRepository ==null||callback==null){
            throw new IllegalArgumentException("Method parameters cannot be null");
        }
        mWebsitesRepository = websitesRepository;
        mCallback = callback;
    }
    @Override
    public void onGetWebsites(final List<Website> websites) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetWebsites(websites);
            }
        });
    }

    @Override
    public void onDataNotAvailable() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetWebsitesError();
            }
        });
    }

    @Override
    public void run() {
        mWebsitesRepository.getWebsites(this);
    }
}
