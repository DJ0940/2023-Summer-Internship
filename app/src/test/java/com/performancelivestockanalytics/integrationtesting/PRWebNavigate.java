package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PRWebNavigate {
    private static final int TIMEWAIT = 3;
    private WebDriverWait wait;
    private WebDriver driver;

    PRWebNavigate(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    public void navigateToOverview() {
        // Refresh the page to remove any pop-up so that navigation bar is visible
        driver.navigate().refresh();

        /* If refresh was successful, the navigation bar should be visible on the left or right (depending on the window size)
           Click it to navigate to overview page
           */
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("nav-overview")))).click();

        /* Now confirm the navigation to overview. If successful, the assert will pass.
           Determine the success by the existence of animals(pull-right class)
           */
        assert(wait.until(visibilityOfElementLocated(By.className("pull-right"))).isDisplayed());
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
