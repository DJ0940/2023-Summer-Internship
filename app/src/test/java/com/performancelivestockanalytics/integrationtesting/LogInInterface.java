package com.performancelivestockanalytics.integrationtesting;


import java.net.MalformedURLException;

public interface LogInInterface<T> {

    void setUp() throws Exception;

    void tearDown();

    void logIn(String URL, String username, String password) throws Exception;

    T getDriver();


}
