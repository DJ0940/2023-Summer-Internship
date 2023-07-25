package com.performancelivestockanalytics.integrationtesting;

import java.net.MalformedURLException;

public interface LoginInterface<T> {
    static final int TIMEWAIT = 3;
    static final int SYNCWAIT = 20;

    void setUp() throws Exception;

    void tearDown();

    void login(String targetServer, String username, String password) throws Exception;

    T getDriver();
}
