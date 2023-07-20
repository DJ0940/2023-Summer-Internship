package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class IntegrationTesting {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void test() {
        PRWebLogin login = new PRWebLogin();
        login.logIn("https://ranch.***REMOVED***/", "***REMOVED***", "***REMOVED***");
    }
}
