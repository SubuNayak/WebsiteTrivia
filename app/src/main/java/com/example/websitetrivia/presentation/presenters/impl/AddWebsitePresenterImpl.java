package com.example.websitetrivia.presentation.presenters.impl;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.AddWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.DeleteWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.GetIndividualWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.GetWebsitesInteractor;
import com.example.websitetrivia.domain.interactor.impl.AddWebsiteInteractorImpl;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.AddWebsitePresenter;
import com.example.websitetrivia.presentation.presenters.MainPresenter;
import com.example.websitetrivia.presentation.presenters.base.AbstractPresenter;
import com.example.websitetrivia.storage.data.WebsiteDataGenerator;

public class AddWebsitePresenterImpl extends AbstractPresenter implements AddWebsitePresenter,
        GetIndividualWebsiteInteractor.Callback, AddWebsiteInteractor.Callback{

    private AddWebsitePresenter.View mView;
    private WebsitesRepository mWebsitesRepository;

    public AddWebsitePresenterImpl(Executor executor, MainThread mainThread, AddWebsitePresenter.View view, WebsitesRepository websitesRepository){
        super(executor, mainThread);
        mView = view;
        mWebsitesRepository = websitesRepository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onWebsiteAdded() {
        mView.onWebsiteAdded();
    }

    @Override
    public void onWebsiteAddedError() {

    }

    @Override
    public void onGetIndividualWebsite(Website website) {

    }

    @Override
    public void onGetIndividualWebsiteError() {

    }

    @Override
    public void addWebsite(String name, String foundingYear, String founders, String location, String CEO, String rank, String timeSpent) {
        Website website = WebsiteDataGenerator.createWebsiteFromData(name, foundingYear, founders, location, CEO, rank, timeSpent);
        AddWebsiteInteractor addWebsiteInteractor = new AddWebsiteInteractorImpl(mExecutor, mMainThread, this,
                mWebsitesRepository, website);
        addWebsiteInteractor.execute();
    }
}
