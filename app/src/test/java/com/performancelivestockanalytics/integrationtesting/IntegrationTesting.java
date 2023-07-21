package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IntegrationTesting {

    //Example PRAndroidLogin and PRAndroidNavigate test
    //PRAndroidLogin login = new PRAndroidLogin();
    //PRAndroidNavigate nav = new PRAndroidNavigate();
    @Before
    public void setUp() throws Exception {
       //login.setUp();
       //nav.setUp(login.getDriver());
    }

    @After
    public void tearDown() {
        //login.tearDown();
    }

    @Test
    public void test() throws Exception {
        //login.logIn("https://ranch.***REMOVED***", "***REMOVED***", "***REMOVED***");
        //nav.navigateToOverview();
    }
}