package com.example.websitetrivia.domain.interactor.base;


import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.executor.MainThread;

public abstract class AbstractInteractor implements Interactor{

    protected Executor mThreadExecutor;
    protected MainThread mMainThread;

    protected volatile boolean mIsCancelled;
    protected volatile boolean mIsRunning;

    public AbstractInteractor(Executor executor, MainThread mainThread){
        this.mThreadExecutor = executor;
        mMainThread = mainThread;
    }

    /**
     * This method contains the actual business logic of the interactor. It SHOULD NOT BE USED DIRECTLY but, instead, a
     * developer should call the execute() method of an interactor to make sure the operation is done on a background thread.
     * <p/>
     * This method should only be called directly while doing unit/integration tests. That is the only reason it is declared
     * public as to help with easier testing.
     */

    public abstract void run();

    public void cancel(){
        mIsCancelled = true;
        mIsRunning = false;
    }

    public boolean isRunning(){
        return mIsRunning;
    }

    public void onFinished(){
        mIsCancelled = false;
        mIsRunning = false;
    }

    public void execute(){
        this.mIsRunning = true;
        mThreadExecutor.execute(this);
    }

}
