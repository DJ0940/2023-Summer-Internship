package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSLogout {
    IOSDriver driver;


    public void setUp(IOSDriver d){
        // This driver should come from the PBiOSLogin's getDriver method.
        driver = d;
    }

    public void logout(){

        // Maximum time the driver is allowed to search for an element.
        WebDriverWait wait = new WebDriverWait(driver, 3);

        // Because the test could be anywhere in the app the driver navigates
        //  back to the dashboard for consistent testing.
        PBiOSNavigate nav = new PBiOSNavigate();
        nav.setUp(driver);
        nav.navigateToOverview();

        // Since the navigate function confirms that the driver has made it
        // to the animal overview the driver doesn't need to wait for the hamburger icon.
        driver.findElementByAccessibilityId("HamburgerIcon").click();

        // The driver waits for the settings button to appear and clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("SettingsIcon"))).click();

        // Driver waits for the close button to confirm it is on the
        // the settings screen.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Close")));

        // Now the driver scrolls down until the Log Out button is visible.
        HashMap<String,Object> scrollObject = new HashMap<>();

        scrollObject.put("direction","down");
        scrollObject.put("label", "Log Out");

        driver.executeScript("mobile:scroll", scrollObject);

        // Now that the Log Out button is visible the driver clicks it.
        driver.findElement(MobileBy.AccessibilityId("Log Out")).click();

        // The driver confirms it is on the login screen by detecting
        // the login button.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Login")));
    }
}
