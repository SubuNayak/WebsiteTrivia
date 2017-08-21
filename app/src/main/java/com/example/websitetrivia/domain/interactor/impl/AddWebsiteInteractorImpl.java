package com.example.websitetrivia.domain.interactor.impl;


import android.util.Log;

import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.AddWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;

public class AddWebsiteInteractorImpl extends AbstractInteractor implements AddWebsiteInteractor,
        WebsitesDataSource.AddWebsiteCallback{

    private AddWebsiteInteractor.Callback mCallback;
    private WebsitesRepository mWebsitesRepository;
    private int mFoundingYear, mRank;
    private String mId, mName, mFounders, mLocation, mCEO, mtimeSpent;
    private Website mWebsite;
    private boolean createFromWebsite=false;


    public AddWebsiteInteractorImpl(Executor executor, MainThread mainThread, Callback callback,
                                    WebsitesRepository websitesRepository, Website website){
        super(executor, mainThread);
        mCallback = callback;
        mWebsitesRepository = websitesRepository;
        mWebsite = website;
        createFromWebsite = true;

    }
    @Override
    public void onWebsiteAdded() {
        mCallback.onWebsiteAdded();
    }

    @Override
    public void onWebsiteAddedError() {
        mCallback.onWebsiteAddedError();
    }


    //Actual adding of Website
    @Override
    public void run() {
        if(!createFromWebsite) {
            mWebsite = new Website(mId, mName, mFoundingYear, mFounders, mLocation, mCEO, mRank, mtimeSpent);
        }
        mWebsitesRepository.addWebsite(mWebsite, this);
    }
}

