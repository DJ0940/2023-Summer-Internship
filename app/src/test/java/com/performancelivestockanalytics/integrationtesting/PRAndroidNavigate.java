package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class PRAndroidNavigate {

    private AndroidDriver driver;

    public void setUp(AndroidDriver d) throws Exception {
        driver = d;
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void navigateToHome() {

        WebDriverWait wait = new WebDriverWait(driver, 3);

        //Checks to see if the user is already on the home page
        try {
            driver.findElementByAccessibilityId("Active");
            return;
        } catch (NoSuchElementException nse){
        }

        //Checks to see if the left facing return arrow is present. If it is then click it
        //This is used if the user is searching an animal by name or viewing an animal
        try {
            driver.findElementByAccessibilityId("Navigate up").click();

        } catch (NoSuchElementException nse) {
        }

        //Checks to see if there is a pop up that has two options to escape (usually OK or Cancel)
        //This is used in scenarios like being in the calendar pop up or the filters pop up
        try {
            driver.findElementById("android:id/button1").click();
        } catch (NoSuchElementException nse) {
        }

        //Checks to see if the user is currently in drop down menu
        //Used in scenarios like selecting gender, breed, etc
        try {
            driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget." +
                    "FrameLayout/android.widget.ListView/android.widget.TextView[1]").click();
        } catch (NoSuchElementException nse) {
        }

        //Find the "Hamburger Icon"
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Open navigation drawer"))).click();

        //Return to the home page
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/nav_home"))).click();

        //Confirm we have made it back to the home screen
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Active")));
    }
}

