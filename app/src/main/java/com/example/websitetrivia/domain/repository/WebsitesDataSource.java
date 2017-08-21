package com.example.websitetrivia.domain.repository;


//Went on did CRUD operations, next time GreenDAO or Rxjava.
//Checking for duplicates can be added.
//Presenter uses this to communicate with repo, and so does Database.

import com.example.websitetrivia.domain.model.Website;


import java.util.List;

public interface WebsitesDataSource {

    interface GetWebsitesCallback{
        void onGetWebsites(List<Website> websites);
        void onDataNotAvailable();
    }

    interface GetIndividualWebsiteCallback{
        void onGetIndividualWebsite(Website website);
        void onDataNotAvailable();
    }

    interface UpdateWebsiteCallback{
        void onWebsiteUpdated(Website website);
        void onUpdateWebsiteError();
    }

    interface DeleteWebsiteCallback{
        void onWebsiteDeleted();
        void onWebsiteDeleteError();
    }

    interface AddWebsiteCallback{
        void onWebsiteAdded();
        void onWebsiteAddedError();
    }

    void getWebsites(GetWebsitesCallback callback);
    void addWebsite(Website website, AddWebsiteCallback callback);
    void updateWebsite(Website website, UpdateWebsiteCallback callback);
    void getIndividualWebsite(String id, GetIndividualWebsiteCallback callback);
    void deleteWebsite(String id, DeleteWebsiteCallback callback);

}
