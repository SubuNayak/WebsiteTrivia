package com.example.websitetrivia.domain.interactor.impl;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.UpdateWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;

public class UpdateWebsiteInteractorImpl extends AbstractInteractor implements UpdateWebsiteInteractor,
        WebsitesDataSource.UpdateWebsiteCallback{


    private UpdateWebsiteInteractor.Callback mCallback;
    private WebsitesRepository mWebsitesRepository;
    private Website mWebsite;
    private int mFoundingYear, mRank;
    private String mId, mName, mFounders, mLocation, mCEO, mtimeSpent;


    public UpdateWebsiteInteractorImpl(Executor executor, MainThread mainThread, Callback callback,
                                       WebsitesRepository websitesRepository, Website website,
                                       String name, int foundingYear,
                                       String founders, String location, String CEO, int rank, String timeSpent){
        super(executor, mainThread);
        mCallback = callback;
        mWebsitesRepository = websitesRepository;
        mWebsite = website;
        mName = name;
        mFoundingYear = foundingYear;
        mFounders = founders;
        mLocation = location;
        mCEO = CEO;
        mRank = rank;
        mtimeSpent = timeSpent;
    }

    @Override
    public void onWebsiteUpdated(final Website website) {
       mMainThread.post(new Runnable() {
           @Override
           public void run() {
               mCallback.onWebsiteUpdated(website);
           }
       });

    }

    @Override
    public void onUpdateWebsiteError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onWebsiteUpdatedError();
            }
        });

    }

    @Override
    public void run() {
        mWebsite.setName(mName);
        mWebsite.setFoundingYear(mFoundingYear);
        mWebsite.setFounders(mFounders);
        mWebsite.setLocation(mLocation);
        mWebsite.setCEO(mCEO);
        mWebsite.setRank(mRank);
        mWebsite.setTimeSpent(mtimeSpent);
        mWebsitesRepository.updateWebsite(mWebsite, this);
    }
}
