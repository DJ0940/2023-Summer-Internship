package com.performancelivestockanalytics.integrationtesting;


import java.net.MalformedURLException;

public interface LogInInterface {

    void setUp() throws MalformedURLException;

    void tearDown();

    void logIn(String URL, String username, String password);


}
