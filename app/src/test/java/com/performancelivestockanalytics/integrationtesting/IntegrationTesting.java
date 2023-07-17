package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IntegrationTesting {

    //Example PBLogIn test
    //public PBLogIn  login = new PBLogIn();
    @Before
    public void setUp() throws Exception {
        //login.setUp();
    }

    @After
    public void tearDown() {
       //login.tearDown();
    }

    @Test
    public void test() throws Exception {
        //login.logIn("beta\n", "***REMOVED***", "***REMOVED***");
    }
}
