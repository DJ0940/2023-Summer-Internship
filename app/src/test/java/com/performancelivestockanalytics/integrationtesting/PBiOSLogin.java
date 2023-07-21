package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.HashMap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSLogin implements LogInInterface {
    private IOSDriver driver;

    @Override
    public void setUp() throws Exception{
        /* The setUp method creates the driver that appium uses.
           The capabilities can be changed depending on the attributes of your
           emulator. The emulator that this test is using is an iPhone SE (3rd generation)
           running on iOS 16.4. Changing the capabilities is quite intuitive, just change
           the second parameter to the desired attribute of your emulator.
         */
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "16.4");
        caps.setCapability("deviceName", "iPhone SE (3rd generation)");

        // TODO: Abstract this out to a JSON file.
        caps.setCapability("app", "/Users/logan/Library/Developer/Xcode/" +
                "DerivedData/Performance_Beef-ajqdjnukyedrzgbbtcnrrshahdkp/Build/Products/Debug-" +
                "iphonesimulator/Performance Beef.app");

        // The XCUITest is used because that is the automator for iOS.
        caps.setCapability("automationName", "XCUITest");

        //  The driver is set with the desired capabilities and the Appium url.
        // Inorder to run the test make sure to run the command appium --base-path /wd/hub
        // into your systems terminal.
        driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), caps);
    }

    // This method quits the driver which will shut down the emulator and appium driver.
    @Override
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    // IMPORTANT: You still have to manual click on the bluetooth devices button.
    @Override
    public void login(String targetServer, String user, String pass) throws Exception {

        /* When looking for an element on a new screen it takes time for the new
           screen to load. The WebDriverWait variable is created so when the driver looks
           for an element on a new screen the driver will keeping looking for
           the element for up to 3 seconds before throwing an error. If the driver doesn't
           wait while a new screen is loading then an error will be thrown because the driver can't
           instantly find the desired element.
        */
        WebDriverWait wait = new WebDriverWait(driver, 3);

        /* Because the app is just loading in the driver waits for the settings button to load in the
           top left corner of the screen. Appium inspector presents the preferred handle
           for the settings button is finding it by its AccessibilityId which is "SettingsIcon".
           There are quite a few handles for finding elements such as AccessibilityId, id, className,
           xpath, and more.
         */
        WebElement settings = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("SettingsIcon")));

        // After finding the settings button the driver clicks it.
        settings.click();

        // Lines 82-87 allow Appium to scroll to the Bluetooth Devices bar.
        // TODO: Resurrect a way to automate multitap.
        HashMap<String,Object>scrollObject = new HashMap<>();

        scrollObject.put("direction","down");
        scrollObject.put("name", "Bluetooth Devices");

        driver.executeScript("mobile:scroll", scrollObject);

        // Now that the test is on the screen that allows the user to change the target server
        // the driver waits for the Dev Box to load in and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("DevBox"))).click();

        /* After the Dev Box has been clicked the driver waits for the text box to enter in the target
           server to load in. This time Appium Inspector says that xpath is the preferred (and only)
           handle to search for the text box. Usually it is ideal to avoid xpath because it can lead
           to inconsistent testing. After the driver finds the text box the sendKeys method is used.
           sendKeys writes the target server into the text field. It is important to note that the
           targetServer variable/parameter should have a string that ends with the new line character
           "\n" so which will allow the driver to exit the text box.
         */
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.xpath("//XCUIElementTypeApplication" +
                                "[@name=\"Performance Beef\"]/XCUIElementTypeWindow[1]" +
                                "/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElement" +
                                "TypeOther/XCUIElementTypeTextField"))).sendKeys(targetServer);

        // Now the driver waits for the button that says Update to appear then the driver clicks on it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Update"))).click();

        // Next a button that says close should appear on the same screen and the driver clicks it.
        // Unfortunately xpath must be used again.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.xpath("(//XCUIElementTypeButton[@name=\"Close\"])[2]"))).click();

        // Now the driver waits for the next close button to appear in the new
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Close"))).click();


        WebElement userName;
        WebElement password;

        // This next section checks to see if the user is using an iPhone or iPad emulator
        // by checking the xpath of the username and password text boxes.
        // TODO: Give the username and password text boxes unique Accessibility ID's.
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

        // If the driver gets to here and still have no userName (or password), the
        // next line will fail as it should. The next lines send in the username and password
        userName.sendKeys(user);
        password.sendKeys(pass);

        // Now the driver finds and clicks on the login button
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Login"))).click();


        /* Now the test is forced to wait for three seconds while the app keeps running.
           The test is forced to wait because it takes the app a couple seconds to register that
           login has been pressed and to enter in the new screen that syncs the users information
           if the login was successful. Waiting three seconds will allow the app to catch up
           for the next part of the test.
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        /* This checks to see if the driver can still detect the settings button. If it can't
           that means the login was successful. So this catches the error that would be thrown
           since the settings button can't be found and it returns passing the test. If the driver
           still detects the settings button even after waiting three seconds for the app to load
           that means the login was unsuccessful and will fail the test.
         */
        try {
            driver.findElementByAccessibilityId("SettingsIcon");
        }
        catch (NoSuchElementException nse) {
            return;
        }

        throw new Exception("Failed to login");

    }

    // This method returns the driver that is being used to run this test.
    // The driver will be used in other tests such as PBiOSNavigateToDashBoard.
    @Override
    public IOSDriver getDriver(){
        return driver;
    }
}
