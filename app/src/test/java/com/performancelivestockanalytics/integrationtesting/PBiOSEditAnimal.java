package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import io.appium.java_client.ios.IOSDriver;

public class PBiOSEditAnimal {

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

    public void changeWeight(int weight){
        navigateToHealth();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }
        driver.context(getWebContext());

        System.out.println(driver.getPageSource());

    }

    private String getWebContext(){
        ArrayList<String> contexts = new ArrayList(driver.getContextHandles());
        for (String context : contexts){
            System.out.println(context);
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
