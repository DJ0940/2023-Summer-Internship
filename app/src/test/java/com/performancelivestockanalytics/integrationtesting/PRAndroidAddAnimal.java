package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class PRAndroidAddAnimal {
    private AndroidDriver driver;

    public void setUp(AndroidDriver d){
        driver = d;
    }

    public void addAnimal() throws Exception {

        // Maximum time the driver is allowed to find an element
        WebDriverWait wait = new WebDriverWait(driver, 3);

        // Since the test could be anywhere in the app we head back to
        // the animal overview to guarantee the driver can get to the add animal screen.
        PRAndroidNavigate nav = new PRAndroidNavigate();
        nav.setUp(driver);
        nav.navigateToOverview();

        /* Open up the navigation drawer by clicking on the "Hamburger Icon."
           Don't need to wait because the navigateToOverview has confirmed the driver
           has made it to the animal overview screen.
         */
        driver.findElementByAccessibilityId("Open navigation drawer").click();

        // Wait for the button that says "Add an Animal" and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/nav_animals"))).click();

        /* The app by default should have the role set to be Calf so that step will be skipped
           until a json file with all of the parameters is created.
           TODO: Set Role, Birthdate, Visual ID, etc to values from a json file once it has been created
        */

        // Wait for the Birthdate selector to appear and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/add_animal_birthdate_textview"))).click();

        // Currently a hard coded date.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("02 July 2023"))).click();

        // The driver clicks the OK button.
        driver.findElementById("android:id/button1").click();

        // The driver waits for the Visual ID text box to appear and send in the desired ID for the animal.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/add_animal_visualID_edittext"))).sendKeys("AppiumTest");

        // The driver clicks on the gender spinner.
        driver.findElementById("com.perfomancebeef.android:id/add_animal_gender_spinner").click();

        // The genderSpinner is a list view element that contains all of the options within it.
        WebElement genderSpinner = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.className("android.widget.ListView")));

        // Now a list is created that contains all of the options.
        List<WebElement> childElements = genderSpinner.findElements(By.id("android:id/text1"));

        // Sets the gender variable to be empty.
        WebElement gender = null;

        // Traverses through the list.
        for (WebElement element: childElements ){

            // If the elements text is equal to the desired gender then
            // it is store in the gender variable.
            // TODO: Read the desired gender from a json file.
            if (element.getText().equals("Steer")){
                gender = element;
                break;
            }
        }

        // If the gender variable is not empty then the
        // driver clicks the desired gender.
        if (gender != null){
            gender.click();
        }
        // Since their are no other required attributes the driver can now
        // scroll to the "Add Animal" button

        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                + "new UiSelector().textContains(\"Add Animal\"))"));

        // Once the driver has scrolled it clicks it.
        driver.findElementById("com.perfomancebeef.android:id/add_animal_finish_btn").click();

        // After clicking the "Add Animal" button it will prompt the user with
        // an alert that it was either a success or a failure.
        WebElement alert =  wait.until(
                                ExpectedConditions.presenceOfElementLocated(
                                               MobileBy.id("android:id/message")));

        // If the text from the alert says Animal Added then the test has passed.
        if (alert.getText().equals("Animal Added")){
            return;
        }
        // If the text does not say Animal Added then the test has failed
        // so the test throws an exception.
        throw new Exception("Failed to add animal");
    }
}
