package com.performancelivestockanalytics.integrationtesting;

<<<<<<< HEAD
import java.net.MalformedURLException;

=======
>>>>>>> master
public interface LogInInterface<T> {
    static final int TIMEWAIT = 3;
    static final int SYNCWAIT = 20;

<<<<<<< HEAD
    void setUp() throws MalformedURLException;

    void tearDown();

    void logIn(String targetServer, String username, String password) throws Exception;
=======
    void setUp();

    void tearDown();

    void logIn(String targetServer, String username, String password);
>>>>>>> master

    T getDriver();
}
