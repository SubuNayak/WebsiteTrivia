package com.example.websitetrivia.domain.repository;

// Storing the data in a Cache.
// Cache is stored in form of Map.
// Checks the Cache, if its available and avoid a costly database operation
// Checks if null, then gets value, then its done if there is no value available.
// UI doesnt need to know where the data is coming from. CLEAN ARCHITECTURE.



import android.util.Log;

import com.example.websitetrivia.domain.model.Website;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebsitesRepository implements WebsitesDataSource {
    private static WebsitesRepository INSTANCE=null;
    private final WebsitesDataSource mWesbsitesDataSource;
    //Implementing a caching mechanism for faster responsiveness
    Map<String, Website> mWebsitesCache;


    private WebsitesRepository(WebsitesDataSource dataSource){
        if(dataSource!=null){
            mWesbsitesDataSource = dataSource;
        }
        else{
            mWesbsitesDataSource = null;
        }

    }

    public static WebsitesRepository getInstance(WebsitesDataSource websitesDataSource){
        if(INSTANCE==null){
            INSTANCE = new WebsitesRepository(websitesDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getWebsites(final GetWebsitesCallback callback) {
        if(callback!=null){
            if(mWebsitesCache!=null){
                callback.onGetWebsites(new ArrayList<Website>(mWebsitesCache.values()));
                return;
            }

                mWesbsitesDataSource.getWebsites(new GetWebsitesCallback() {
                    @Override
                    public void onGetWebsites(List<Website> websites) {
                        refreshCache(Collections.synchronizedList(new ArrayList<Website>(websites)));
                        callback.onGetWebsites(Collections.synchronizedList(new ArrayList<Website>(mWebsitesCache.values())));
                    }

                    @Override
                    public void onDataNotAvailable() {
                    }
                });

        }
    }

    @Override
    public void addWebsite(Website website, final AddWebsiteCallback callback) {
        if(website !=null&&callback!=null){
            if(mWebsitesCache==null){
                mWebsitesCache = new ConcurrentHashMap<>();
            }
            mWebsitesCache.put(website.getId(), website);
            mWesbsitesDataSource.addWebsite(website, new AddWebsiteCallback() {
                @Override
                public void onWebsiteAdded() {
                    callback.onWebsiteAdded();
                }

                @Override
                public void onWebsiteAddedError() {
                    callback.onWebsiteAddedError();
                }
            });

        }
    }

    @Override
    public void updateWebsite(final Website website, final UpdateWebsiteCallback callback) {
        if(website !=null&&callback!=null){
            mWesbsitesDataSource.updateWebsite(website, new UpdateWebsiteCallback() {
                @Override
                public void onWebsiteUpdated(Website updatedWebsite) {
                    callback.onWebsiteUpdated(updatedWebsite);
                    mWebsitesCache.remove(updatedWebsite.getId());
                    mWebsitesCache.put(updatedWebsite.getId(), updatedWebsite);
                }

                @Override
                public void onUpdateWebsiteError() {
                    callback.onUpdateWebsiteError();
                }
            });

        }
    }

    @Override
    public void getIndividualWebsite(String id, final GetIndividualWebsiteCallback callback) {
        if(id!=null&&callback!=null){
            Website websiteFromCache = getWebsiteById(id);
            if (websiteFromCache !=null) {
                callback.onGetIndividualWebsite(websiteFromCache);
                return;
            }
            mWesbsitesDataSource.getIndividualWebsite(id, new GetIndividualWebsiteCallback() {
                @Override
                public void onGetIndividualWebsite(Website website) {
                    callback.onGetIndividualWebsite(website);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }

    }

    @Override
    public void deleteWebsite(String id, final DeleteWebsiteCallback callback) {
        if(id!=null && callback!=null){
            mWesbsitesDataSource.deleteWebsite(id, new DeleteWebsiteCallback() {
                @Override
                public void onWebsiteDeleted() {
                    callback.onWebsiteDeleted();
                }

                @Override
                public void onWebsiteDeleteError() {
                    callback.onWebsiteDeleteError();
                }
            });
            mWebsitesCache.remove(id);
        }
    }

    private void refreshCache(List<Website> websites){
        if(mWebsitesCache==null){
            mWebsitesCache = new ConcurrentHashMap<>();
        }

        mWebsitesCache.clear();
        for(Website website :websites){
            mWebsitesCache.put(website.getId(), website);
        }
    }

    private Website getWebsiteById(String id){
        if(id==null||mWebsitesCache==null||mWebsitesCache.isEmpty()){
            return null;
        } else {
            return mWebsitesCache.get(id);
        }

    }
}
