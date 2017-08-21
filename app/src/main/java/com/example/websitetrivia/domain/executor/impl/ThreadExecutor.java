package com.example.websitetrivia.domain.executor.impl;


// Creates a Threadpool
// Executes the Threads.



import com.example.websitetrivia.domain.executor.Executor;
import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor implements Executor{

    private static volatile ThreadExecutor sThreadExecutor;

    private static final int CORE_POOL_SIZE=3;
    private static final int MAX_POOL_SIZE=5;
    private static final int KEEP_ALIVE_TIME=120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>();

    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadExecutor(){
        long keepAlive = KEEP_ALIVE_TIME;
        mThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, keepAlive,
                        TIME_UNIT, WORK_QUEUE);
    }

// Communicates with Interactor.
    @Override
    public void execute(final AbstractInteractor interactor) {
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                interactor.run();
                interactor.onFinished();
            }
        });
    }

    public static Executor getInstance(){
        if(sThreadExecutor==null){
            sThreadExecutor = new ThreadExecutor();
        }
        return sThreadExecutor;
    }
}
