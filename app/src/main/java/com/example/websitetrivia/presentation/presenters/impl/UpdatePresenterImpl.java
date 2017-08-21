package com.example.websitetrivia.presentation.presenters.impl;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.GetIndividualWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.UpdateWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.impl.GetIndividualWebsiteInteractorImpl;
import com.example.websitetrivia.domain.interactor.impl.UpdateWebsiteInteractorImpl;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.UpdateWebsitePresenter;
import com.example.websitetrivia.presentation.presenters.base.AbstractPresenter;

public class UpdatePresenterImpl extends AbstractPresenter implements UpdateWebsitePresenter, GetIndividualWebsiteInteractor.Callback,
        UpdateWebsiteInteractor.Callback{

    private UpdateWebsitePresenter.View mView;
    private WebsitesRepository mWebsitesRepository;

    public UpdatePresenterImpl(Executor executor, MainThread mainThread, View view, WebsitesRepository websitesRepository){
        super(executor, mainThread);
        mView = view;
        mWebsitesRepository = websitesRepository;
    }
    @Override
    public void onWebsiteUpdated(Website website) {
        mView.onWebsiteUpdated(website);
    }

    @Override
    public void onWebsiteUpdatedError() {
        mView.showError("Sorry, an error occurred while updating the details");
    }



    @Override
    public void onGetIndividualWebsite(Website website) {
        mView.onGetWebsite(website);
    }

    @Override
    public void onGetIndividualWebsiteError() {
        mView.showError("Oops, we cannot find that website");
    }

    @Override
    public void getWebsiteById(String id) {
        GetIndividualWebsiteInteractor getIndividualWebsiteInteractor = new GetIndividualWebsiteInteractorImpl(mExecutor,
                    mMainThread, mWebsitesRepository, this, id);
        getIndividualWebsiteInteractor.execute();
    }

    @Override
    public void updateWebsite(Website website, String name, int foundingYear, String founders, String location, String CEO, int rank, String timeSpent) {
        UpdateWebsiteInteractor updateWebsiteInteractor = new UpdateWebsiteInteractorImpl(mExecutor, mMainThread,
                this, mWebsitesRepository, website, name, foundingYear, founders, location, CEO, rank, timeSpent);
        updateWebsiteInteractor.execute();
    }
}
