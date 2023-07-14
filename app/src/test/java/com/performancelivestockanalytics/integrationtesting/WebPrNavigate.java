package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebPrNavigate {
    private static final int TIMEWAIT = 3;
    private WebDriverWait wait;
    private WebDriver driver;

    /**
     * Constructor
     */
    WebPrNavigate(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Inside PR, navigate to overview page (home / default page)
     */
    public void navigateToOverview() {
        // Refresh the page. To remove the pop up or it doesn't see the left (bottom if window size is small) scroll bar (overview, herds, pastures, etc)
        driver.navigate().refresh();

        // Navigate to overview page
        checkVisibilityOrScroll(driver.findElement(By.className("nav-overview"))).click();

        // Check if page successfully navigated to the overview (Animals)
        assert(driver.findElement(By.className("pull-right")).isDisplayed());
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
