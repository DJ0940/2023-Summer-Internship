package com.performancelivestockanalytics.integrationtesting;

public interface LogInInterface {

    void setUp();

    void tearDown();

    void logIn(String URL, String username, String password);


}
