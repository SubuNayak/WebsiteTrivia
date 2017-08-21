package com.example.websitetrivia;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;
import com.example.websitetrivia.domain.interactor.GetIndividualWebsiteInteractor;
import com.example.websitetrivia.domain.interactor.impl.GetIndividualWebsiteInteractorImpl;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesDataSource;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class GetWebsiteByIdTest {

    private MainThread mMainThread;
    @Mock
    private Executor mExecutor;
    @Mock
    private WebsitesRepository mWebsitesRepository;
    @Mock
    private GetIndividualWebsiteInteractor.Callback mCallback;
    @Mock
    private WebsitesDataSource.GetIndividualWebsiteCallback mRepoCallback;
    @Captor
    private ArgumentCaptor<WebsitesDataSource.GetIndividualWebsiteCallback> mGetWebsiteCaptor;

    private String mWebsiteId;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        mMainThread = new TestMainThread();
        mWebsiteId = "example.com";

    }

    @Test
    public void testWebsiteNotFound() throws Exception{

        GetIndividualWebsiteInteractorImpl getIndividualWebsiteInteractor = new GetIndividualWebsiteInteractorImpl(mExecutor,
                mMainThread, mWebsitesRepository, mCallback, mWebsiteId);
        getIndividualWebsiteInteractor.run();

        Mockito.verify(mWebsitesRepository).getIndividualWebsite(Mockito.eq(mWebsiteId), mGetWebsiteCaptor.capture());
        mGetWebsiteCaptor.getValue().onDataNotAvailable();
        Mockito.verifyNoMoreInteractions(mWebsitesRepository);
        Mockito.verify(mCallback).onGetIndividualWebsiteError();


    }

    @Test
    public void testWebsiteFound(){
        final Website website = new Website(mWebsiteId, "Example", 2017, "IEEE", "Atlantis", "Tux", 1, "Infinity");

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                WebsitesDataSource.GetIndividualWebsiteCallback callback =  (WebsitesDataSource.GetIndividualWebsiteCallback) invocation.getArguments()[1];
                callback.onGetIndividualWebsite(website);
                return null;
            }
        }).when(mWebsitesRepository).getIndividualWebsite(Mockito.eq(mWebsiteId), Mockito.any(mRepoCallback.getClass()));

        GetIndividualWebsiteInteractorImpl interactor = new GetIndividualWebsiteInteractorImpl(mExecutor, mMainThread, mWebsitesRepository, mCallback, mWebsiteId);
        interactor.run();
        Mockito.verify(mWebsitesRepository).getIndividualWebsite(Mockito.eq(mWebsiteId), Mockito.any(mRepoCallback.getClass()));
        Mockito.verifyNoMoreInteractions(mWebsitesRepository);
        Mockito.verify(mCallback).onGetIndividualWebsite(website);
    }



}
