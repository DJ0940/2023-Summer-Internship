package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSLogout implements Constants{
    IOSDriver driver;


    public void setUp(IOSDriver d){
        // This driver should come from the PBiOSLogin's getDriver method.
        driver = d;
    }

    public void logout() {

        // Maximum time the driver is allowed to search for an element.
        WebDriverWait wait = new WebDriverWait(driver, TIMEWAIT);

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
        scroll();

        // Now that the Log Out button is visible the driver clicks it.
        driver.findElement(MobileBy.AccessibilityId("Log Out")).click();

        // The driver confirms it is on the login screen by detecting
        // the login button.

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Login")));
    }

    private void scroll(){
        // Setting up all of the interactions.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Interaction moveToStart = finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 186, 519);
        Interaction pressDown = finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg());
        Interaction moveToEnd = finger.createPointerMove(Duration.ofMillis(400L), PointerInput.Origin.viewport(), 186, 75);
        Interaction pressUp = finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg());
        Sequence swipe = new Sequence(finger, 0);

        // Executing the actions.
        swipe.addAction(moveToStart);
        swipe.addAction(pressDown);
        swipe.addAction(moveToEnd);
        swipe.addAction(pressUp);
        this.driver.perform(Arrays.asList(swipe));
    }
}
