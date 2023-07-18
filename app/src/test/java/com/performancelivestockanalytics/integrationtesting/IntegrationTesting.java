package com.performancelivestockanalytics.integrationtesting;

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



    //Examole PRAndroidLogin Test
    //PRAndroidLogin test = new PRAndroidLogin();


    @Before
    public void setUp() throws Exception {
        //test.setUp();
    }

    @After
    public void tearDown() {
        //test.tearDown();
    }

    @Test
    public void test() throws Exception {

        //test.logIn("https://ranch.***REMOVED***", "***REMOVED***", "***REMOVED***");
    }
}
