package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Performance Beef: Navigate to the overview page
 */
public class WebPbNavigate {
    private static final int TIMEWAIT = 3;
    private WebDriverWait wait;
    private WebDriver driver;

    /**
     * Constructor
     */
    WebPbNavigate(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Inside PB, navigate to overview page (home / default page)
     */
    public void navigateToOverview() {
        // Navigate to overview page
        checkVisibilityOrScroll(driver.findElement(By.className("nav-overview"))).click();

        // Check if page navigated to the overview
        assert(driver.findElement(By.id("btn_head_change")).isDisplayed());
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
