package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PBWebLogout {
    private static final int TIMEWAIT = 3; // Normal timeout waiting for components
    private WebDriver driver;
    WebDriverWait wait;

    PBWebLogout (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    public void logOut() {
        // Assume that there is no pop-up and is able to access account setting when it's called
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("account-settings-glyphicon")))).click();

        // Wait for the account setting page to load, then click the log out button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("account_change")))).click();

        /* Now confirm the logout. If successful, the assert will pass.
           Determine the success by the existence of username box in login page
           */
        assert(checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("user")))).isDisplayed());
    }

    private WebElement checkVisibilityOrScroll(WebElement element) {
        if (element.isDisplayed()) {
            return element;
        } else {
            // The element is not displayed in current view, so scroll to that element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            return wait.until(ExpectedConditions.visibilityOf(element));
        }
    }
}
