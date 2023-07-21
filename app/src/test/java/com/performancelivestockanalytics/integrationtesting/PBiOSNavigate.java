package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSNavigate {
    IOSDriver driver;

    /* To setup this driver go to the setUp method IntegrationTesting.
       Then use the last class' getDriver method to set that driver as the driver
       of this test so it can pick up where the last class left off.
    */
    public void setUp(IOSDriver d){
        driver = d;
    }

    public void navigateToOverview() {

        /* Setup both of the waits. Using wait will give the driver 3 seconds to find an element
           while syncwait gives the driver 20 seconds. Sync wait is useful if the user is syncing data
           after logging in.
        */

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebDriverWait syncwait = new WebDriverWait(driver, 20);

        // Check to see if the driver is already at the overview.
        // If they are the test will end in a success.
        try {
            driver.findElementByAccessibilityId("+/- Head");
            return;
        } catch (NoSuchElementException nse) {
        }

        //T his takes care of all of the checks if the driver is in the New Session area.
        newSessionChecks();

        // Check to see if the driver is loading into the app right after logging in.
        try {
            driver.findElementByAccessibilityId("Reloading all account data...");
            syncwait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            MobileBy.AccessibilityId("HamburgerIcon")));

        } catch (NoSuchElementException nse) {
        }

        // Check to see if the driver needs to press a cancel button.
        // Example: used when discarding an animal in "Add Animal."
        try {
            driver.findElementByAccessibilityId("Cancel").click();
        } catch (NoSuchElementException nse) {
        }


        // Check to see if the driver needs to click a close button.
        // This is for a very specific case and I can't seem to repeat the steps for it to appear.
        // However, it can appear and must be dealt with.
        try {
            driver.findElementByAccessibilityId("Close").click();
        } catch (NoSuchElementException nse) {
        }

        // Check to see if the driver has clicked on a QR code. Simply the test will click out of it.
        try {
            driver.findElementByAccessibilityId("You're running in the simulator" +
                    ", which means the camera isn't available. Tap anywhere to send back " +
                    "some simulated data.").click();
        } catch (NoSuchElementException nse) {
        }

        // Find the hamburger icon and click it.
        driver.findElementByAccessibilityId("HamburgerIcon").click();

        // Wait for Feedyard Web View to be present and click on it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("webView"))).click();

        // If the previous starts a new session then tries to leave a popup will appear
        // with two options (stay/leave). Inorder to get to the dashboard the driver must click leave.
        try {
            driver.findElementByAccessibilityId("Leave").click();
        } catch (NoSuchElementException nse) {
        }

        // Make sure the driver has loaded into the dashboard by checking for the presence of the
        // +/- head button. If the driver finds it then the test passes. If the driver doesn't find it
        // the test will fail.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("+/- Head")));
    }

    public void newSessionChecks(){

        // This method checks to see if the previous has pressed start new session.

        // This will exit this screen if the driver is typing on the top text box
        // or if the driver has just pressed "Start New Session" w/o performing
        // any more actions
        try{
            driver.findElementByAccessibilityId("Notes");
            driver.findElementByAccessibilityId("Name").click();
            driver.findElementByAccessibilityId("Back").click();
            return;
        } catch (NoSuchElementException nse){
        }

        // This will exit this screen if the driver is typing on the bottom text box
        try{
            driver.findElementByAccessibilityId("Done Editing").click();
            driver.findElementByAccessibilityId("Back").click();
        } catch (NoSuchElementException nse){
        }
        return;
    }
}
