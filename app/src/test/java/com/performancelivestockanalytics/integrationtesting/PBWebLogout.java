package com.performancelivestockanalytics.integrationtesting;

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
    JavascriptExecutor js;

    /**
     * Constructor
     */
    PBWebLogout (WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) this.driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    public void logOut() {
        // Click Account Setting
        checkVisibilityOrScroll(driver.findElement(By.className("account-settings-glyphicon"))).click();

        // Click Log Out Button
        checkVisibilityOrScroll(driver.findElement(By.id("account_change"))).click();

        // Check if logged out successfully
        assert(checkVisibilityOrScroll(driver.findElement(By.id("user"))).isDisplayed());
    }

    /**
     * Check if the element is visible, if not scroll to that element and return it
     */
    private WebElement checkVisibilityOrScroll(WebElement element) {
        if (element.isDisplayed()) {
            return element;
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            return wait.until(ExpectedConditions.visibilityOf(element));
        }
    }
}
