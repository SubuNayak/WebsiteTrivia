package com.example.websitetrivia.domain.interactor;


import com.example.websitetrivia.domain.interactor.base.Interactor;
import com.example.websitetrivia.domain.model.Website;

import java.util.List;

public interface GetWebsitesInteractor extends Interactor{
   interface Callback{
      void onGetWebsites(List<Website> websites);
      void onGetWebsitesError();
   }

}
