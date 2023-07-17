package com.performancelivestockanalytics.integrationtesting;

public interface LogInInterface<T> {
    static final int TIMEWAIT = 3;
    static final int SYNCWAIT = 20;

    void setUp() throws Exception;

    void tearDown();

    void logIn(String targetServer, String username, String password) throws Exception;

    T getDriver();
}
