package com.performancelivestockanalytics.integrationtesting;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.appium.java_client.android.AndroidDriver;

public class IntegrationTesting {

    //Example PBiOSLogin and PBiOSNavigate test
    //PBiOSLogin login = new PBiOSLogin();
    //PBiOSNavigate nav = new PBiOSNavigate();
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
        //login.logIn("beta\n", "***REMOVED***", "***REMOVED***");
        //nav.navigateToOverview();
    }
}