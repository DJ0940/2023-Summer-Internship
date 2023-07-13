package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class IntegrationTesting {
    private WebDriver driver;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void test() {
        WebLogin login = new WebLogin(driver);
        driver = login.returnDriver();
        login.login("https://***REMOVED***/", "***REMOVED***", "***REMOVED***");
    }
}
