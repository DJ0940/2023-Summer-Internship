package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class PRAndroidNavigate implements Constants {

    private AndroidDriver driver;

    /* To setup this driver go to the setUp method IntegrationTesting.
       Then use the last class' getDriver method to set that driver as the driver
       of this test so it can pick up where the last test left off.
    */
    public void setUp(AndroidDriver d) throws Exception {
        driver = d;
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void navigateToOverview() {
        // Same as the PRAndroidLogin, the driver is allowed to wait up to three seconds to find an element.
        WebDriverWait wait = new WebDriverWait(driver, TIMEWAIT);

        // Checks to see if the driver is already on the overview page. If they are then the test will return
        // a success.
        try {
            driver.findElementById("com.perfomancebeef.android:id/nav_home");
            return;
        } catch (NoSuchElementException nse) {
        }

        // Checks to see if the left facing return arrow is present. If it is then click it.
        // This is used if the user is searching an animal by name or viewing an animal.
        try {
            driver.findElementByAccessibilityId("Navigate up").click();

        } catch (NoSuchElementException nse) {
        }

        // Checks to see if there is a pop up that has two options to escape (usually OK or Cancel).
        // This is used in scenarios like being in the calendar pop up or the filters pop up.
        try {
            driver.findElementById("android:id/button1").click();
        } catch (NoSuchElementException nse) {
        }

        // Checks to see if the driver is currently in drop down menu. If they are click the empty element.
        // Used in scenarios like selecting gender, breed, etc.
        try {
            driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget." +
                    "FrameLayout/android.widget.ListView/android.widget.TextView[1]").click();
        } catch (NoSuchElementException nse) {
        }

        // Find the "Hamburger Icon" and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Open navigation drawer"))).click();

        // The driver waits for the presence of the Animal Overview button to confirm
        // that the driver is in the correct area.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/nav_home")));

    }
}

