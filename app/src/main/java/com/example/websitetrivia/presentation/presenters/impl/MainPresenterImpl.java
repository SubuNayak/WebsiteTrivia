package com.example.websitetrivia.presentation.presenters.impl;


import android.content.Context;
import android.util.Log;

import com.example.websitetrivia.R;
import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.AddWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.DeleteWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.GetWebsitesInteractor;
import com.example.websitetrivia.domain.interactor.impl.AddWebsiteInteractorImpl;
import com.example.websitetrivia.domain.interactor.impl.DeleteWebsiteInteractorImpl;
import com.example.websitetrivia.domain.interactor.impl.GetWebsitesInteractorImpl;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.MainPresenter;
import com.example.websitetrivia.presentation.presenters.base.AbstractPresenter;
import com.example.websitetrivia.storage.data.WebsiteDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        GetWebsitesInteractor.Callback, DeleteWebsiteInteractor.Callback, AddWebsiteInteractor.Callback {


    private MainPresenter.View mView;
    private WebsitesRepository mWebsitesRepository;

    private List<Website> data = new ArrayList<Website>();

    private boolean firstRunOps=false;
    private int totalWebsites = -1;
    private int addedCount = 0;
    public MainPresenterImpl(Executor executor, MainThread mainThread, View view, WebsitesRepository websitesRepository){
        super(executor, mainThread);
        mView = view;
        mWebsitesRepository = websitesRepository;
    }
    @Override
    public void resume() {
        getWebsites();
    }



    @Override
    public void onError(String message) {

    }

    @Override
    public void onWebsiteDeleted() {
        mView.onWebsiteDeleted();
    }

    @Override
    public void onWebsiteDeletedError() {

    }

    @Override
    public void onGetWebsites(List<Website> websites) {
        mView.showWebsites(websites);
    }

    @Override
    public void onGetWebsitesError() {

    }


    private void createWebsite(Website website){
        AddWebsiteInteractor addWebsiteInteractor = new AddWebsiteInteractorImpl(mExecutor, mMainThread, this,
                mWebsitesRepository, website);
        addWebsiteInteractor.execute();
    }

    private List<String[]> getWebsitesArray(Context context){
        List<String[]> data = new ArrayList<String[]>();
        data.add(context.getResources().getStringArray(R.array.Google));
        data.add(context.getResources().getStringArray(R.array.Yahoo));
        data.add(context.getResources().getStringArray(R.array.Wikipedia));
        data.add(context.getResources().getStringArray(R.array.Facebook));
        return data;
    }

    @Override
    public void runFirstTimeOps(Context context) {
        data =  WebsiteDataGenerator.getListOfWebsites(getWebsitesArray(context));
        createWebsite(data.get(addedCount));
        totalWebsites = data.size();
        addedCount++;
        firstRunOps = true;
    }

    @Override
    public void getWebsites() {
        GetWebsitesInteractor getWebsitesInteractor = new GetWebsitesInteractorImpl(
                mExecutor, mMainThread, mWebsitesRepository, this);
        getWebsitesInteractor.execute();
    }

    @Override
    public void deleteWebsite(String id) {
        DeleteWebsiteInteractor deleteWebsiteInteractor = new DeleteWebsiteInteractorImpl(mExecutor,
                mMainThread, id, this, mWebsitesRepository);
        deleteWebsiteInteractor.execute();
    }

    @Override
    public void onWebsiteAdded() {
        if(firstRunOps&&addedCount==totalWebsites){
            firstRunOps=false;
            addedCount=-1;
            mView.firstTimeOpsFinished();
        }
        else if(firstRunOps){
            createWebsite(data.get(addedCount));
            addedCount++;
        }

    }

    @Override
    public void onWebsiteAddedError() {

    }
}
