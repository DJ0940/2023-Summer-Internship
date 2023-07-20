package com.performancelivestockanalytics.integrationtesting;

public interface LoginInterface<T> {
    static final int TIMEWAIT = 3;
    static final int SYNCWAIT = 20;

    void setUp();

    void tearDown();

    void login(String targetServer, String username, String password);

    T getDriver();
}
