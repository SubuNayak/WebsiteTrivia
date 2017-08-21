package com.example.websitetrivia.domain.executor;


public interface MainThread {
    void post(final Runnable runnable);
}
