package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class  PRAndroidLogin implements LoginInterface, Constants {
    private AndroidDriver driver;

    /* The setUp method creates the driver that appium uses.
       The capabilities can be changed depending on the attributes of the desired
       emulator. The emulator that this test is using is a Pixel_3a_API_33_arm64-v8a|
       running on Android 13.0.
    */
    @Override
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("deviceName", "Pixel_3a_API_33_arm64-v8a|");

        // Android uses UiAutomator2 for their automation platform.
        caps.setCapability("automationName", "UiAutomator2");

        // TODO: Abstract this out to a JSON file.
        caps.setCapability("app", "/Users/logan/pb-android/app/build/intermediates/apk/debug/app-debug.apk");

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
    }

    // This method quits the driver which will shut down the emulator and appium driver.
    @Override
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void login(String targetServer, String user, String pass) throws Exception {

        /* When looking for an element on a new screen it takes time for the new
           screen to load. This WebDriverWait is created variable so when the driver looks
           for an element on a new screen the driver will keeping looking for
           the element for up to 3 seconds before throwing an error. If the driver doesn't
           wait while a new screen is loading then an error will be thrown because the driver can't
           instantly find the desired element.
        */
        WebDriverWait wait = new WebDriverWait(driver, TIMEWAIT);


        /* The driver needs to click on the PR Logo 11 times in quick succession in order to change
           the target server. The driver can't just wait for the PR Logo on the login screen
           to appear because it has the same AccessibilityId as the PR Logo that shows
           when the user is loading into the app. To get around this the driver waits for the presence
           of the login button which confirms it is on the login screen.
         */
        wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.id("com.perfomancebeef.android:id/login_btn")));

        // Now the driver click on the logo 11 times which will allow it to change the target server.
        for(int i = 0; i < 11; i++) {
            driver.findElementByAccessibilityId("PR Logo").click();
        }


        // The driver waits for the text box to appear then it sends in the the URL of the
        // target server.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/edit_server_string"))).sendKeys(targetServer);

        // Now the driver finds the save button and clicks it.
         driver.findElementById("com.perfomancebeef.android:id/edit_server_save_btn").click();


        // Wait until the driver is back at the original login screen and enter in the username.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/login_user_name_text"))).sendKeys(user);

        // Now that the home screen is present the driver can find the password text box without waiting and
        // the driver sends in the password.
        driver.findElementById("com.perfomancebeef.android:id/login_password_text").sendKeys(pass);

        // Again don't need to wait for the login button since
        // the home screen is already present and the driver clicks it
        driver.findElementById("com.perfomancebeef.android:id/login_btn").click();

        /* When entering in incorrect login information the app goes into a loading
           screen and then returns to the login screen. This can cause problems because it
           detects a successful login by the presence of the username text box. So if the user
           enters incorrect login info and the screen goes right into the loading screen while
           the test checks for the presence of the text box it would result in a success. The solution
           is forcing the program to wait for two seconds to allow the app to catch up.
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }

        /* If the driver cannot detect the username text box after the app has caught up that
           means the test has passed. So if the text box is not detected it catches the
           NoSuchElementException and return which will pass the test. If the driver does
           detect the text box then the test has failed and an error will be thrown.
         */
        try {
            driver.findElement(MobileBy.id("com.perfomancebeef.android:id/login_user_name_text"));
        }
        catch (NoSuchElementException nse) {
            return;
        }

        throw new Exception("Failed to login");
    }


    // This method returns the driver that is being used to run this test.
    // The driver will be used in other tests such as PRAndroidNavigateToDashBoard
    @Override
    public AndroidDriver getDriver() {
            return driver;
    }
}
