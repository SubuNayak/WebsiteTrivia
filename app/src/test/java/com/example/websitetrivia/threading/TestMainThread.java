package com.example.websitetrivia.threading;


import com.example.websitetrivia.domain.executor.MainThread;

public class TestMainThread implements MainThread {


    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
