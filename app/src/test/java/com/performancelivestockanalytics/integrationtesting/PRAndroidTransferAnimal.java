package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

// Important note. A bug occurs where the animal will not be transferred if the
// driver is teared down immediately following the test.

public class PRAndroidTransferAnimal implements Constants {

    private AndroidDriver driver;

    public void setUp(AndroidDriver d) {
        // The driver should come from PRAndroidLogin's getDriver method.
        driver = d;
    }

    public void transferAnimal(String animalName) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, TIMEWAIT);

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

        // The driver waits for the "Record a Departure" button and clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/nav_departure"))).click();

        // Wait for the transfer label at the top of the screen to
        // appear and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Transfer"))).click();



        // Wait for the calendar button to appear and click it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/departure_transfer_date_button"))).click();

        /* Selecting July 15th for the date because the birthday for the addAnimal is July 2nd.
           When transferring an animal the date can't be close to the present day AND it has
           to be after the animals birthdate.
         */

        //TODO: Abstract the transfer date to be read from a json file.

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Previous month"))).click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("15 July 2023"))).click();

        // Click on OK
        driver.findElementById("android:id/button1").click();

        // The driver waits for the Visual ID button to appear and click on it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/departure_transfer_btn_visual_id"))).click();

        // Wait for search text box to appear and send in the ID for the desired animal.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("android:id/search_src_text"))).sendKeys(animalName);


        // Driver clicks on the searched animal.
        // If there is no animal then the test will fail because the animal does not exist.
        driver.findElementById("com.perfomancebeef.android:id/animal_list_item_layout").click();

        // Driver clicks the select button.
        driver.findElementById("com.perfomancebeef.android:id/select_animal_btn").click();

        // Driver waits for the group spinner to appear and clicks on it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/departure_transfer_group_spinner"))).click();

        // Now that the group is open we create an element that contains all of the selectable groups.
        WebElement groupSpinner = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.className("android.widget.ListView")));

        // Now a list is created that takes all of the child elements from the group spinner.
        List<WebElement> elements = groupSpinner.findElements(By.id("android:id/text1"));

        // This group element is set to null which is the same as it being empty.
        WebElement group = null;

        // For loop traverses through each child element in the list.
        for (WebElement element: elements ){

            // If the element's text is equal to the desired group then set
            // the group variable equal to found element and exit the loop.
            // TODO: Get the text of the desired group from a json file.
            if (element.getText().equals("Group1")){
                group = element;
                break;
            }
        }

        // Next the driver clicks on the group.
        if (group != null){
            group.click();
        }


        // Scroll so Pen and Transfer Animal are visible.
        scroll();

        // Driver clicks on Pen.
        driver.findElementById("com.perfomancebeef.android:id/departure_transfer_pen_spinner").click();

        // Lines 136-155 is near identical to selecting a group except this
        // time the driver selects a pen.
        WebElement penSpinner = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.className("android.widget.ListView")));

        List<WebElement> childElements = penSpinner.findElements(By.id("android:id/text1"));

        WebElement pen = null;

        for (WebElement element: childElements ){
            // TODO: Get the text of the desired pen from a json file.
            if (element.getText().equals("2")){
                pen = element;
                break;
            }
        }

        if (pen != null){
            pen.click();
        }

        // The driver now waits for Transfer Animal button and clicks it.

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("com.perfomancebeef.android:id/departure_transfer_finish_btn"))).click();

        // Set the alert message into the alert variable.
        WebElement alert =  wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.id("android:id/message")));

        // If the alert message is "The animal has been transferred" then the
        // transfer was successful and the test passes.
        if (alert.getText().equals("The animal has been transferred")){
            return;
        }

        // If the message says anything else the transfer has failed and the test throws an exception.
        throw new Exception("Failed to transfer animal");
    }

    private void scroll(){
        // Setting up all of the interactions.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Interaction moveToStart = finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 500, 2100);
        Interaction pressDown = finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg());
        Interaction moveToEnd = finger.createPointerMove(Duration.ofMillis(200L), PointerInput.Origin.viewport(), 500, 220);
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
