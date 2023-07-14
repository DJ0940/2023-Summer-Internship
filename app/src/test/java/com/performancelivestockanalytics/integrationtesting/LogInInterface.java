package com.performancelivestockanalytics.integrationtesting;

import java.net.MalformedURLException;

public interface LogInInterface<T> {
    static final int TIMEWAIT = 3;
    static final int SYNCWAIT = 20;

    void setUp() throws MalformedURLException;

    void tearDown();

    void logIn(String targetServer, String username, String password) throws Exception;

    T getDriver();
}
