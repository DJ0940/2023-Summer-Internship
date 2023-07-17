package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class  PRLogInTest implements LogInInterface {
    private AndroidDriver driver;

    @Override
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("deviceName", "Pixel_3a_API_33_arm64-v8a|");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", "/Users/logan/pb-android/app/build/intermediates/apk/debug/app-debug.apk");
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
    }

    @Override
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void logIn(String targetServer, String user, String pass) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebDriverWait syncwait = new WebDriverWait(driver, 20);

        /*The loading screen AND login screen BOTH have the logo's accesibility ID
        as "PR Logo" so we load in the login button to confirm we are on the log in screen
        and then we click it*/

        wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.id("com.perfomancebeef.android:id/login_btn")));


        for(int i = 0; i < 11; i++) {
            driver.findElementByAccessibilityId("PR Logo").click();
        }


        //Find the server text box first
        WebElement serverTextBox = syncwait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/edit_server_string")));

        //Find the save button while it is still on the screen to possibly avoid scrolling later
        WebElement saveButton = driver.findElementById("com.perfomancebeef.android:id/edit_server_save_btn");

        //type in the server url then save and leave
        serverTextBox.sendKeys(targetServer);
        saveButton.click();

        //Wait until we are back at the original log in screen
        WebElement usernameTextBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/login_user_name_text")));

        //Now that we are at the home screen we can just find it w/o waiting
        WebElement passwordTextBox = driver.findElementById("com.perfomancebeef.android:id/login_password_text");

        //Enter email and password
        usernameTextBox.sendKeys(user);
        passwordTextBox.sendKeys(pass);

        //Again don't need to wait for the log in button since
        //we are already at the home screen
        driver.findElementById("com.perfomancebeef.android:id/login_btn").click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }

        //If the username text box is no longer detected means that the test has passed
        try {
            driver.findElement(MobileBy.id("com.perfomancebeef.android:id/login_user_name_text"));
        }
        catch (NoSuchElementException nse) {
            return;
        }

        throw new Exception("Failed to login");
    }


    @Override
    public AndroidDriver getDriver() {
            return driver;
    }
}
