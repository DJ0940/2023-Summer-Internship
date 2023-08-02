package com.performancelivestockanalytics.integrationtesting;

import android.opengl.Visibility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class PBiOSEditAnimal implements Constants {

    /* IMPORTANT: There are issues that will arise with trying to inspect the webview
    without following these steps. Open up pb-ios in xcode. Next go to the left column
    and go to Performance Beef > Performance Beef > ViewControllers > HtmlViewController (if there
    are two HtmlViewController files open the first one). Locate the viewDidLoad function and under
    the line that says super.viewDidLoad() copy and paste this code:

    if self.webView.responds(to: Selector(("setInspectable:"))) {
            self.webView.perform(Selector(("setInspectable:")), with: true)
        }

    For the next steps go to https://stackoverflow.com/questions/75574268/missing-file-libarclite-iphoneos-a-xcode-14-3
    and view the answer with 226 up-votes. Open a terminal and type the following command:
    cd /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/
    Now enter each of these three commands:
    sudo mkdir arc
    cd arc
    sudo git clone https://github.com/kamyarelyasi/Libarclite-Files.git .

    After this go back to xcode and go to product > run and make sure the app builds and runs.
  */
    IOSDriver driver;

    WebDriverWait wait;

    public void setUp(IOSDriver d){
        driver = d;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    private void navigateToHealth() {

        PBiOSNavigate nav = new PBiOSNavigate();

        // First the driver must be set up.
        // The driver should come from PBiOSLogin
        nav.setUp(driver);

        // Next the driver navigates over to the feedyard webview.
        nav.navigateToOverview();

        // scrolls over to the health section.
        scroll(341, 631, 31,631);

        //Click on the health button.
        driver.findElementByAccessibilityId("Health").click();
    }

    private void navigateToAnimal(String animalName) throws InterruptedException {

        // Switch the context to a webview.
        driver.context(getWebContext());

        // The driver then finds correct group.
        List<WebElement> elements = driver.findElements(By.tagName("h2"));
        WebElement group = null;
        for (WebElement childElement: elements){
            if (Objects.equals(childElement.getText(), "D-Group")){;
                group = childElement;
            }
        }

        // Now the driver scrolls to the desired group and clicks it.
        // The driver can use this selenium style scrolling because it is in a webview context.
       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", group);
        group.click();

        // The driver waits for the presence of the Edit Group button to appear to make sure
        // its on the correct page.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.cssSelector("#page-container > div.page-content.container-fluid." +
                                "font-black.bg-white > div > div:nth-child(2) > div.col-md-8 > div:nth-child(1) > button")));

        // The driver then finds the textbox that looks up an animal and loads it into a variable.
        WebElement textbox = driver.findElement(By.cssSelector("#DataTables_Table_0_filter > label > input"));

        // Next the driver scrolls to the textbox.
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textbox);

        // Type in the desired animals name.
        textbox.sendKeys(animalName);

        // The only animal that should appear is the desired animal so the driver clicks it.
        driver.findElement(By.cssSelector("#animal_table_body > tr > td.dtr-control.sorting_1")).click();

        // The webview is no longer necessary so the driver can switch back to the native app view.
        driver.context("NATIVE_APP");

        // The driver waits for the edit button to appear and then clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Edit"))).click();
    }

    public void deleteAnimal(String animalName) throws InterruptedException {

        // First the driver navigates to the health section.
        navigateToHealth();

        // Then the driver navigates to the screen where it can edit the animal.
        navigateToAnimal(animalName);

        // The driver scrolls until it can view the delete animal button
        scrollUntilVisible("AccessibilityID","Delete Animal");

        // The driver then clicks on the button
        driver.findElement(MobileBy.AccessibilityId("Delete Animal")).click();

        // A popup appears to ask the user if they are sure about deleting the animal.
        // The driver waits for the confirm button to appear and the driver clicks it.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Confirm"))).click();

    }

    public void changeAnimalGender(String animalName) throws InterruptedException {

        // First the driver navigates to the health section.
        navigateToHealth();

        // Then the driver navigates to the screen where it can edit the animal.
        navigateToAnimal(animalName);

        // To confirm the driver is on the correct screen it looks for the save changes button
        // and stores it into a variable for later.
        WebElement saveBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Save Changes")));

        // The driver scrolls until the gender dropdown is visible.
        scrollUntilVisible("xpath", "//XCUIElementTypeOther[@name=\"" +
                "Performance Beef\"]/XCUIElementTypeOther[2]/XCUIElementTypeOther[15]/XCUI" +
                "ElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther");

        // The driver then clicks on the gender drop down.
        driver.findElement(MobileBy.xpath("//XCUIElementTypeOther[@name=\"" +
                "Performance Beef\"]/XCUIElementTypeOther[2]/XCUIElementTypeOther[15]/XCUI" +
                "ElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther")).click();

        // Next the gender is changed to Steer.
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Steer"))).click();

        // Finally the changes are saved.
        saveBtn.click();

    }

    private String getWebContext() throws InterruptedException {

        // The strategy of implicit wait does not work so the driver is forced to
        // wait three seconds for the webview to load.
        Thread.sleep(3000);

        // A list of all contexts is created.
        ArrayList<String> contexts = new ArrayList(driver.getContextHandles());
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                // If a context that is found that isn't the native app then
                // the method returns that context.
                return context;
            }
        }
        // Returns null when no other contexts are found.
        return null;
    }

        private void scroll(int x1,int y1, int x2, int y2){
            // Setting up all of the interactions.
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Interaction moveToStart = finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x1, y1);
            Interaction pressDown = finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg());
            Interaction moveToEnd = finger.createPointerMove(Duration.ofMillis(200L), PointerInput.Origin.viewport(), x2, y2);
            Interaction pressUp = finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg());
            Sequence swipe = new Sequence(finger, 0);

            // Executing the actions.
            swipe.addAction(moveToStart);
            swipe.addAction(pressDown);
            swipe.addAction(moveToEnd);
            swipe.addAction(pressUp);
            this.driver.perform(Arrays.asList(swipe));
        }

        // The two parameteres are selector or they locator strategy being used (Example: xpath, AccessibilityID,
        // ID, etc). The other parameter is the string that actually points to the element.
       private void scrollUntilVisible(String selector, String attribute) {
        /* While the desired element is not visible the driver will slowly scroll
           down until the desired element is then visible. The reason the condition only checks for xpath
           is because currently only two methods use this. This can be changed to support any locating.
           strategy.
         */
           boolean visibility = false;
           while (!visibility) {
               try {
                   if(Objects.equals(selector, "xpath")){
                       driver.findElement(MobileBy.xpath(attribute));
                   }
                   else{
                       driver.findElement(MobileBy.AccessibilityId(attribute));
                   }
                   visibility = true;

               } catch (Exception e) {
                   scroll(175, 560, 175, 460);
               }

           }
       }
    public IOSDriver getDriver(){
        return driver;
    }
}
