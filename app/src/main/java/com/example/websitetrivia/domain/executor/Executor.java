package com.example.websitetrivia.domain.executor;


import com.example.websitetrivia.domain.interactor.base.AbstractInteractor;

// Managing Threads, Middleman

public interface Executor {

    void execute(final AbstractInteractor interactor);

}
