package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.appium.java_client.ios.IOSDriver;

public class PBiOSEditAnimal {

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

    public void setUp(IOSDriver d){
        driver = d;
    }

    private void navigateToHealth() {

        PBiOSNavigate nav = new PBiOSNavigate();
        nav.setUp(driver);
        nav.navigateToOverview();

        // scrolls over to the health section
        scroll();

        driver.findElementByAccessibilityId("Health").click();


    }

    public void changeWeight(int weight) throws InterruptedException {
        navigateToHealth();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ie) {
        }
        driver.context(getWebContext());

        /*HashMap<String,Object> scrollObject = new HashMap<>();

        scrollObject.put("direction","down");
        scrollObject.put("value", "Rock Valley 9");

        driver.executeScript("mobile:scroll", scrollObject);
        */
        //WebElement element = driver.findElement(By.tagName("div"));
        List<WebElement> el = driver.findElements(By.xpath("//*[@class]"));
        WebElement group = null;
        for (WebElement childElement: el){
            System.out.println(childElement.getText());
            if (childElement.getText() == "GroupOwners"){
                group = childElement;
            }
        }

       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", group);
        group.click();
        Thread.sleep(2000);
    }

    private String getWebContext(){
        ArrayList<String> contexts = new ArrayList(driver.getContextHandles());
        for (String context : contexts){
            if (!context.equals("NATIVE_APP")){
                return context;
            }
        }
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
