package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSLogin implements LoginInterface {

    private IOSDriver driver;

    @Override
    public void setUp() throws Exception{
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "16.4");
        caps.setCapability("deviceName", "iPhone SE (3rd generation)");

        //The next line is still not abstracted
        caps.setCapability("app", "/Users/logan/Library/Developer/Xcode/DerivedData/Performance_Beef-ajqdjnukyedrzgbbtcnrrshahdkp/Build/Products/Debug-iphonesimulator/Performance Beef.app");
        caps.setCapability("automationName", "XCUITest");

        driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), caps);
    }

    @Override
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    //IMPORTANT: You still have to manual click on the bluetooth devices button.
    //Still working on a solution
    @Override
    public void login(String targetServer, String user, String pass) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 3);

        /* When the app first fires up, the target server should be set
           prior to the access/modification of user data.  The process of
           setting the target server begins by selecting the gear icon.

           From the Appium Inspector, we can get the correct handle for the
           gear icon from its accessibility id: "SettingsIcon."
           */

        // For an overview of the hooks that can be used to get the handle
        // of the appropriate component, peruse:
        //   https://www.lambdatest.com/blog/locators-in-appium/

        /* The first hook that is used is by AccessibilityId */
        WebElement settings = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("SettingsIcon")));
        settings.click();

        /* There are several approaches to bringing up the server configuration
           configuration screen.  The approach taken herein will be to quad-tap
           the bluetooth devices bar.

           One drawback of the smaller iPhone layout is that the target for
           the desired action may not be visible by default.  If this is the case,
           we can move the view up until the bluetooth devices bar is
           visible.
           */

        //makeBluetoothBarVisible();

        /* As a result of the prior call, the Bluetooth Devices bar should be visible
           (that was it's purpose).  The next step is to quad (or more) tap on the
           main label area.

           "Unfortunately," Appium's TouchActions and MultiTap infrastructure is too
           slow to emulate this.

           Instead, the architecture-specific script of "tapWithNumberOfTaps" gets
           the desired tap rate in quick succession.
           */

        //Lines 91-96 allow Appium to scroll to the Bluetooth Devices bar.
        //Don't forget to click it a few times!
        HashMap<String,Object>scrollObject = new HashMap<>();

        scrollObject.put("direction","down");
        scrollObject.put("name", "Bluetooth Devices");

        driver.executeScript("mobile:scroll", scrollObject);



        /* Wait for the transition to the server setup screen, then grab the
           "DevBox" button. The "DevBox" button is
           chosen here as that should always be visible.
           */

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("DevBox"))).click();


        /* The next assumption is that the input box for the server
           string will be visible.  Find it and populate it with the
           server string.

           Unlike previous handles, here there is no "id", no "associated id," and
           essentially no other mechanism to get the handle for the input box other
           than using the system-specific xpath approach.
           */
        WebElement customServerHost = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.xpath("//XCUIElementTypeApplication" +
                                "[@name=\"Performance Beef\"]/XCUIElementTypeWindow[1]" +
                                "/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElement" +
                                "TypeOther/XCUIElementTypeTextField")));

        customServerHost.sendKeys(targetServer);

        // Save the changes
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Update"))).click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.xpath("(//XCUIElementTypeButton[@name=\"Close\"])[2]"))).click();

        // Back out of the stack
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Close"))).click();


        /* The next step is to actually log in.  There are some obstacles in
           the process here, inasmuch as there are only xpath hooks available
           to get the corresponding username and password input handles.

           Further the iPad and iPhone layouts have differing xpath targets.
           */


        WebElement userName;
        WebElement password;

        // First check to see if this is an iPad and check the iPad's XPATH to the username input:
        if (driver.findElementsByXPath("//XCUIElementTypeApplication"+
                "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"
                +"/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"
                +"/XCUIElementTypeOther/XCUIElementTypeTextField").size() > 0 ) {
            userName = driver.findElementByXPath("//XCUIElementTypeApplication"+
                    "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeTextField");
            password = driver.findElementByXPath("//XCUIElementTypeApplication"+
                    "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeSecureTextField");
        }
        else  // Check to see if this is an iPhone SE with a different XPATH
        {
            userName = driver.findElementByXPath("//XCUIElementTypeApplication"+
                    "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"+
                    "/XCUIElementTypeTextField");
            password = 	driver.findElementByXPath("//XCUIElementTypeApplication"+
                    "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"+
                    "/XCUIElementTypeSecureTextField");
        }

        // If we get to here and still have no userName (or password), the
        // next line will fail as it should.
        userName.sendKeys(user);
        password.sendKeys(pass);

        WebElement login = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Login")));
        login.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        try {
            //This is the code for the iPhone xpath since the capabilites set up this
            //Test for the iPhone SE
            driver.findElementByXPath("//XCUIElementTypeApplication"+
                    "[@name=\"Performance Beef\"]/XCUIElementTypeWindow/XCUIElementTypeOther"+
                    "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"+
                    "/XCUIElementTypeTextField");
        }
        catch (NoSuchElementException nse) {
            return;
        }

        throw new Exception("Failed to login");

    }

    @Override
    public IOSDriver getDriver(){
        return driver;
    }
}
