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
        nav.setUp(driver);
        nav.navigateToOverview();

        // scrolls over to the health section
        scroll();

        driver.findElementByAccessibilityId("Health").click();
    }

    private void navigateToAnimal() throws InterruptedException {

        driver.context(getWebContext());

        List<WebElement> elements = driver.findElements(By.tagName("h2"));
        WebElement group = null;
        for (WebElement childElement: elements){
            if (Objects.equals(childElement.getText(), "Group01")){;
                group = childElement;
            }
        }

       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", group);
        group.click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.cssSelector("#page-container > div.page-content.container-fluid.font-black.bg-white > div > div:nth-child(2) > div.col-md-8 > div:nth-child(1) > button")));

        WebElement textbox = driver.findElement(By.cssSelector("#DataTables_Table_0_filter > label > input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textbox);

        textbox.sendKeys("AppiumCalf");
        driver.findElement(By.cssSelector("#animal_table_body > tr > td.dtr-control.sorting_1")).click();

        driver.context("NATIVE_APP");
        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Edit"))).click();
    }

    public void changeAnimalGender() throws InterruptedException {
        navigateToHealth();
        navigateToAnimal();

        WebElement saveBtn =  wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Save Changes")));

        scroll();

        driver.findElement(MobileBy.xpath("//XCUIElementTypeOther[@name=\"Performance Beef\"]/XCUIElementTypeOther[2]/XCUIElementTypeOther[15]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther")).click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        MobileBy.AccessibilityId("Steer"))).click();

        saveBtn.click();

        Thread.sleep(3000);
    }


    private String getWebContext() throws InterruptedException {

        Thread.sleep(3000);
        ArrayList<String> contexts = new ArrayList(driver.getContextHandles());
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                return context;
            }
        }

        getWebContext();
        return null;

    }

        private void scroll(){
            // Setting up all of the interactions.
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Interaction moveToStart = finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 341, 631);
            Interaction pressDown = finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg());
            Interaction moveToEnd = finger.createPointerMove(Duration.ofMillis(200L), PointerInput.Origin.viewport(), 31, 631);
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
