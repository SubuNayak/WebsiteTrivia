package com.example.websitetrivia.domain.interactor.impl;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.GetIndividualWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;

public class GetIndividualWebsiteInteractorImpl extends AbstractInteractor implements GetIndividualWebsiteInteractor,
        WebsitesDataSource.GetIndividualWebsiteCallback {

    private WebsitesRepository mWebsitesRepository;
    private GetIndividualWebsiteInteractor.Callback mCallback;
    private String mId;

    public GetIndividualWebsiteInteractorImpl(Executor executor, MainThread mainThread, WebsitesRepository websitesRepository,
                                              Callback callback, String id){
        super(executor, mainThread);
        mId = id;
        mWebsitesRepository = websitesRepository;
        mCallback = callback;
    }

    @Override
    public void onGetIndividualWebsite(final Website website) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetIndividualWebsite(website);
            }
        });
    }

    @Override
    public void onDataNotAvailable() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onGetIndividualWebsiteError();
            }
        });
    }

    @Override
    public void run() {
        mWebsitesRepository.getIndividualWebsite(mId, this);
    }
}
