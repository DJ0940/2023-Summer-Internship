package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;


public class PRAndroidLogout {

    AndroidDriver driver;

    public void setUp(AndroidDriver d){
        // This driver should come from the PRAndroidLogin's getDriver method.
        driver = d;
    }

    public void logout() throws Exception {

        // Maximum time the driver is allowed to search for an element.
        WebDriverWait wait = new WebDriverWait(driver, 3);

        // Because the test could be anywhere in the app the driver navigates
        //  back to the dashboard for consistent testing.
        PRAndroidNavigate nav = new PRAndroidNavigate();
        nav.setUp(driver);
        nav.navigateToOverview();

        // Since the navigate function confirms that the driver has made it
        // to the animal overview the driver doesn't need to wait for the navigation drawer.
        driver.findElementByAccessibilityId("Open navigation drawer").click();

        // The driver waits for the settings button to appear and clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/nav_prefs"))).click();

        // The driver then waits for the sign out button to appear and clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/sign_out_textview"))).click();

        // The app asks the user if they want to sign out. The driver will wait for
        // the message and click Sign Out.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("android:id/button3"))).click();

       // The driver confirms the logout was successful by the presence of the log out button.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/login_btn")));
    }
}
